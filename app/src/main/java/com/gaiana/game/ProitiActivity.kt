package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gaiana.game.databinding.ActivityProitiBinding
import com.gaiana.game.model.Media

class ProitiActivity : AppCompatActivity() {

    private lateinit var b: ActivityProitiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProitiBinding.inflate(layoutInflater)
        setContentView(b.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.c1)
        Media.mp?.start()

        b.btnd1.setOnClickListener {
            Intent(this, IdiKenefomActivity::class.java).also {
                startActivity(it)
            }

        }
        b.btnc2end.setOnClickListener {
            if(Media.mp != null) {
                Media.mp?.stop()
                Media.mp?.prepare()
            }
            Media.mp = MediaPlayer.create(this, R.raw.c2end)
            Media.mp?.start()

            Intent(this, DieActivity::class.java).also {
                startActivity(it)
            }

        }
    }
    override fun onBackPressed() {

    }
    override fun onPause() {
        Media.mp?.pause()
        super.onPause()
    }
    override fun onResume() {
        Media.mp?.start()
        super.onResume()
    }
}