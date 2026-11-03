package com.gaiana.game

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gaiana.game.databinding.ActivityMainBinding
import com.gaiana.game.model.Ending
import com.gaiana.game.model.Progress

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var voice: Voice
    private lateinit var progress: Progress
    private val handler = Handler(Looper.getMainLooper())

    private val tickSeekBar = object : Runnable {
        override fun run() {
            b.sbSeek.max = voice.duration
            b.sbSeek.progress = voice.position
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        goImmersive()
        padForInsets(b.root)

        voice = Voice(this)
        progress = Progress(this)

        val credits = listOf("3Defects ltd. 1999", "Проектът е remaster осъществен от:", "Chocho", "Озвучаване: Цоцика, Цомпов, Чочо..", "Креативен Директор: Цоцика")
        credits.forEachIndexed { i, line ->
            handler.postDelayed({ b.tvAuthors.text = line }, 5000L * (i + 1))
        }

        b.vvBackground.setOnPreparedListener { it.isLooping = true }
        voice.play(R.raw.intro)

        b.sbSeek.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) voice.seekTo(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        // The title stays underneath the game so the launcher icon returns to the game.
        b.btnA0.setOnClickListener { startActivity(Intent(this, GameActivity::class.java)) }
        b.btnEndings.setOnClickListener { showEndings() }
    }

    private fun showEndings() {
        val found = progress.endings
        val lines = Ending.entries.joinToString("\n") { if (it in found) "✓  ${it.title}" else "✗  ???" }
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.endings_found, found.size, Ending.entries.size))
            .setMessage(lines + "\n\n" + getString(R.string.deaths_so_far, progress.deaths))
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    override fun onStart() {
        super.onStart()
        b.vvBackground.setVideoURI(Uri.parse("android.resource://$packageName/${R.raw.fog}"))
        b.vvBackground.start()
    }

    override fun onStop() {
        super.onStop()
        b.vvBackground.stopPlayback()
    }

    override fun onPause() {
        super.onPause()
        voice.pause()
        handler.removeCallbacks(tickSeekBar)
    }

    override fun onResume() {
        super.onResume()
        goImmersive()
        b.btnEndings.text = getString(R.string.endings_found, progress.endings.size, Ending.entries.size)
        voice.resume()
        handler.post(tickSeekBar)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        b.vvBackground.stopPlayback()
        voice.release()
    }
}
