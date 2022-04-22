package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import com.gaiana.game.databinding.ActivityAngelBinding
import com.gaiana.game.model.Media

class AngelActivity : AppCompatActivity() {

    private lateinit var b: ActivityAngelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityAngelBinding.inflate(layoutInflater)
        setContentView(b.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.angel)
        Media.mp?.start()

        b.btnFanta.setOnClickListener {
            if(Media.mp != null) {
                Media.mp?.stop()
                Media.mp?.prepare()
            }
            Media.mp = MediaPlayer.create(this, R.raw.buttonanta)
            Media.mp?.start()

            b.btnNeFantaziram.isVisible = false
            b.btnFanta.isClickable = false
            Handler(Looper.getMainLooper()).postDelayed({
                Intent(this, ArbatActivity::class.java).also {
                startActivity(it)
            }}, 5000)

        }
        b.btnNeFantaziram.setOnClickListener {
            Intent(this, AngelActivity2::class.java).also {
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