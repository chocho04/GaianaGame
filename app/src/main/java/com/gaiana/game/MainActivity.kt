package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.gaiana.game.databinding.ActivityMainBinding
import com.gaiana.game.model.Media

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        val list = listOf("3Defects ltd. 1999", "Проектът е remaster осъществен от:", "ChochoSan", "Озвучаване: Цоцик, Цомпов, Чочо..", "Креативен Директор: Цоцика")
        Handler(Looper.getMainLooper()).postDelayed({b.tvAuthors.text = list[0]}, 5000)
        Handler(Looper.getMainLooper()).postDelayed({b.tvAuthors.text = list[1]}, 10000)
        Handler(Looper.getMainLooper()).postDelayed({b.tvAuthors.text = list[2]}, 15000)
        Handler(Looper.getMainLooper()).postDelayed({b.tvAuthors.text = list[3]}, 20000)
        Handler(Looper.getMainLooper()).postDelayed({b.tvAuthors.text = list[4]}, 25000)

        b.vvBackground.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.fog))
        b.vvBackground.start()
        b.vvBackground.setOnPreparedListener { it.isLooping = true }


        Media.mp = MediaPlayer.create(this, R.raw.intro)
        Media.mp?.start()

        initialiseSeekBar()
        b.sbSeek.setOnSeekBarChangeListener(object: OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) Media.mp?.seekTo(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        b.btnA0.setOnClickListener {
            b.vvBackground.suspend()
            Intent(this, ArbatActivity::class.java).also {
                startActivity(it)
            }
        }
    }
    private fun initialiseSeekBar() {
        b.sbSeek.max = Media.mp?.duration ?: 0
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                b.sbSeek.progress = Media.mp?.currentPosition ?: 0
                handler.postDelayed(this, 1000)
            }
        }, 0)
    }
    override fun onBackPressed() {
    }
    override fun onPause() {
        super.onPause()
        b.vvBackground.pause()
        Media.mp?.pause()
    }
    override fun onResume() {
        super.onResume()
        Media.mp?.start()
        b.vvBackground.start()
    }
}