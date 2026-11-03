package com.gaiana.game

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

/**
 * The one narration track. Starting a new clip always releases the old one, so two
 * voices never talk over each other, and a clip that already finished is not
 * replayed when the app comes back from the background.
 */
class Voice(private val context: Context) {
    private var player: MediaPlayer? = null
    private var paused = false
    private var resumeOnReturn = false

    val position: Int get() = player?.currentPosition ?: 0
    val duration: Int get() = player?.duration ?: 0

    fun play(@RawRes clip: Int) {
        release()
        player = MediaPlayer.create(context, clip)
        if (paused) resumeOnReturn = true else player?.start()
    }

    fun seekTo(ms: Int) {
        player?.seekTo(ms)
    }

    fun pause() {
        paused = true
        resumeOnReturn = player?.isPlaying == true
        player?.pause()
    }

    fun resume() {
        paused = false
        if (resumeOnReturn) player?.start()
        resumeOnReturn = false
    }

    fun release() {
        player?.release()
        player = null
        resumeOnReturn = false
    }
}
