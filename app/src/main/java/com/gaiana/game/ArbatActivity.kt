package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gaiana.game.databinding.ActivityArbatBinding
import com.gaiana.game.model.Media

class ArbatActivity : AppCompatActivity() {

    private lateinit var b: ActivityArbatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityArbatBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.vvBackground.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.teacher1))
        b.vvBackground.start()
        b.vvBackground.setOnPreparedListener { it.isLooping = true }

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.a0)
        Media.mp?.start()

        b.btnc1.setOnClickListener {
            Intent(this, ProitiActivity::class.java).also {
                startActivity(it)
            }
        }
        b.btnd1.setOnClickListener {
            Intent(this, IdiKenefomActivity::class.java).also {
                startActivity(it)
            }

        }
    }
    override fun onBackPressed() {
    }
    override fun onPause() {
        super.onPause()
        Media.mp?.pause()
        b.vvBackground.pause()
    }
    override fun onResume() {
        super.onResume()
        Media.mp?.start()
        b.vvBackground.start()
    }
}