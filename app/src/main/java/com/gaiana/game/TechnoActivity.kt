package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaiana.game.databinding.ActivityTechnoBinding
import com.gaiana.game.model.Media

class TechnoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTechnoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTechnoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.direktortehno)
        Media.mp?.start()

        binding.btnDishane2.setOnClickListener {
            if(Media.mp != null) {
                Media.mp?.stop()
                Media.mp?.prepare()
            }
            Media.mp = MediaPlayer.create(this, R.raw.direktorend)
            Media.mp?.start()
            val intent = Intent(this, DieActivity::class.java)
            startActivity(intent)
        }

        binding.btnZarqzvam2.setOnClickListener {
            val intent = Intent(this, GeshevActivity1::class.java)
            startActivity(intent)
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