package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaiana.game.databinding.ActivityOrbitaliBinding

import com.gaiana.game.model.Media

class OrbitaliActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrbitaliBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrbitaliBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.orbitali)
        Media.mp?.start()

        binding.btnTechno.setOnClickListener {
            Intent(this, TechnoActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnMetal.setOnClickListener {
            Intent(this, MetalActivity::class.java).also {
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