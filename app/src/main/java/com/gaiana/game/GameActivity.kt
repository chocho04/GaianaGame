package com.gaiana.game

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import com.gaiana.game.databinding.ActivityGameBinding
import com.gaiana.game.model.Choice
import com.gaiana.game.model.Ending
import com.gaiana.game.model.Progress
import com.gaiana.game.model.Scene
import com.gaiana.game.model.SceneId
import com.gaiana.game.model.SceneKind
import com.gaiana.game.model.Story
import com.google.android.material.button.MaterialButton

/** Plays the whole story: every scene is a row in [Story], drawn on this one screen. */
class GameActivity : AppCompatActivity() {

    private lateinit var b: ActivityGameBinding
    private lateinit var voice: Voice
    private lateinit var progress: Progress
    private lateinit var scene: Scene

    // One pending timed step at a time (linger after a choice, death/finale reveal).
    // It is paused with the app so nothing jumps scenes while you're away.
    private val handler = Handler(Looper.getMainLooper())
    private var pending: Runnable? = null
    private var pendingDue = 0L
    private var pendingLeft = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityGameBinding.inflate(layoutInflater)
        setContentView(b.root)
        goImmersive()
        padForInsets(b.content)

        voice = Voice(this)
        progress = Progress(this)

        b.video.setOnPreparedListener { it.isLooping = true }
        b.skip.setOnClickListener { pending?.let { handler.removeCallbacks(it); it.run() } }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            // No way back in this game; one scene has a little surprise instead.
            override fun handleOnBackPressed() {
                scene.backSound?.let { voice.play(it) }
            }
        })

        val restored = savedInstanceState?.getString(KEY_SCENE)?.let(SceneId::valueOf)
        show(Story[restored ?: Story.start], fresh = restored == null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_SCENE, scene.id.name)
    }

    /** [fresh] is false when the screen is rebuilt after Android recreated it. */
    private fun show(next: Scene, fresh: Boolean = true) {
        cancelPending()
        scene = next
        next.voice?.let { voice.play(it) }

        b.overlay.isVisible = false
        b.note.isVisible = false
        b.extras.isVisible = false
        b.progress.isVisible = false
        b.title.isVisible = true
        b.title.text = next.title
        b.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, next.titleSp)
        b.title.setTextColor(ContextCompat.getColor(this, next.titleColor))

        b.image.isVisible = next.image != null
        next.image?.let { b.image.setImageResource(it) }
        b.video.stopPlayback()
        b.video.isVisible = false
        if (next.video != null && next.kind != SceneKind.DEATH) playVideo(next.video)

        renderChoices(next.choices)

        when (next.kind) {
            SceneKind.STORY -> Unit
            SceneKind.DEATH -> enterDeath(next, countIt = fresh)
            SceneKind.FINALE -> enterFinale()
        }
    }

    private fun playVideo(clip: Int) {
        b.video.setVideoURI(Uri.parse("android.resource://$packageName/$clip"))
        b.video.isVisible = true
        b.video.start()
    }

    private fun renderChoices(choices: List<Choice>) {
        b.choices.removeAllViews()
        choices.forEach { choice ->
            val button = MaterialButton(this).apply {
                text = choice.label
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { topMargin = dp(4) }
            }
            button.setOnClickListener { pick(choice, button) }
            b.choices.addView(button)
        }
    }

    private fun pick(choice: Choice, button: MaterialButton) {
        choice.ending?.let { progress.reach(it) }
        choice.sound?.let { voice.play(it) }
        val to = choice.to

        if (to == null) {
            // A button that pretends to do something, then gives up.
            button.isClickable = false
            choice.caption?.let { button.text = it }
            later(choice.delayMs, skippable = false) { button.isVisible = false }
            return
        }
        if (choice.delayMs == 0L) {
            show(Story[to])
            return
        }

        // Linger on this scene while the reply plays, then move on.
        for (i in 0 until b.choices.childCount) {
            val other = b.choices.getChildAt(i)
            // Not isEnabled: a disabled button greys out and the caption becomes unreadable.
            other.isClickable = false
            if (other !== button) other.isVisible = false
        }
        choice.caption?.let { button.text = it }
        choice.overlay?.let { b.overlay.setImageResource(it); b.overlay.isVisible = true }
        if (choice.hideTitle) b.title.isVisible = false
        later(choice.delayMs) { show(Story[to]) }
    }

    private fun enterDeath(death: Scene, countIt: Boolean) {
        if (countIt) progress.addDeath()
        b.choices.isVisible = false
        later(Story.DEATH_REVEAL_MS) {
            b.image.isVisible = false
            death.video?.let { playVideo(it) }
            b.title.text = Story.DEATH_TITLE
            b.note.text = "Вече умря ${progress.deaths}00 пъти. Пълен срам майна!"
            b.note.isVisible = true
            b.choices.isVisible = true
            showProgress()
        }
    }

    private fun enterFinale() {
        b.title.isVisible = false
        b.note.text = Story.FINALE_NOTE
        b.note.isVisible = true
        renderOuttakes()
        showProgress()
        b.choices.isVisible = false
        later(Story.FINALE_REVEAL_MS) { b.choices.isVisible = true }
    }

    private fun renderOuttakes() {
        b.extras.removeAllViews()
        b.extras.isVisible = true
        Story.outtakes.chunked(3).forEach { row ->
            val line = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
            row.forEach { extra ->
                line.addView(MaterialButton(this).apply {
                    text = extra.label
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    ).apply { marginStart = dp(4); marginEnd = dp(4) }
                    setOnClickListener {
                        voice.play(extra.sound)
                        b.note.text = extra.note
                    }
                })
            }
            b.extras.addView(line)
        }
    }

    private fun showProgress() {
        b.progress.text = getString(R.string.endings_found, progress.endings.size, Ending.entries.size)
        b.progress.isVisible = true
    }

    private fun later(delayMs: Long, skippable: Boolean = true, action: () -> Unit) {
        cancelPending()
        val step = Runnable {
            pending = null
            b.skip.isVisible = false
            action()
        }
        pending = step
        pendingLeft = delayMs
        b.skip.isVisible = skippable
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            pendingDue = SystemClock.uptimeMillis() + delayMs
            handler.postAtTime(step, pendingDue)
        }
    }

    private fun cancelPending() {
        pending?.let { handler.removeCallbacks(it) }
        pending = null
        b.skip.isVisible = false
    }

    override fun onPause() {
        super.onPause()
        voice.pause()
        b.video.pause()
        pending?.let {
            handler.removeCallbacks(it)
            pendingLeft = (pendingDue - SystemClock.uptimeMillis()).coerceAtLeast(0)
        }
    }

    override fun onResume() {
        super.onResume()
        goImmersive()
        voice.resume()
        if (b.video.isVisible) b.video.start()
        pending?.let {
            pendingDue = SystemClock.uptimeMillis() + pendingLeft
            handler.postAtTime(it, pendingDue)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        b.video.stopPlayback()
        voice.release()
    }

    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()

    private companion object {
        const val KEY_SCENE = "scene"
    }
}
