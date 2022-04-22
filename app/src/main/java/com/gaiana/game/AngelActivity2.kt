package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import com.gaiana.game.databinding.ActivityAngel2Binding
import com.gaiana.game.model.Media

class AngelActivity2 : AppCompatActivity() {

    private lateinit var b: ActivityAngel2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAngel2Binding.inflate(layoutInflater)
        setContentView(b.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.button30)
        Media.mp?.start()

        b.btnExit.setOnClickListener {
            if(Media.mp != null) {
                Media.mp?.stop()
                Media.mp?.prepare()
            }
            Media.mp = MediaPlayer.create(this, R.raw.button33)
            Media.mp?.start()

            b.btnExit.text = "... помисли си че е толкова лесно ли?"
            Handler(Looper.getMainLooper()).postDelayed({
                b.btnExit.isVisible = false
            }, 3000)
        }
        b.btnSpasqvam.setOnClickListener {
            Intent(this, FinalActivity::class.java).also {
                startActivity(it)
            }
        }
        b.btnNeSpasqvam.setOnClickListener {
            Intent(this, OrbitaliActivity::class.java).also {
                startActivity(it)
            }
        }
    }
    override fun onBackPressed() {

    }
    override fun onPause() {
        super.onPause()
        Media.mp?.pause()
    }
    override fun onResume() {
        super.onResume()
        Media.mp?.start()
    }
}