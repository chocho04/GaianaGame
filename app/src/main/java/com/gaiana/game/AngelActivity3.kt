package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaiana.game.databinding.ActivityAngel3Binding
import com.gaiana.game.model.Media

class AngelActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityAngel3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAngel3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.angel3)
        Media.mp?.start()

        binding.btnOrbitali.setOnClickListener {
            Intent(this, OrbitaliActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnNeSpasqvam.setOnClickListener {
            Intent(this, IstorichkaActivity::class.java).also {
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