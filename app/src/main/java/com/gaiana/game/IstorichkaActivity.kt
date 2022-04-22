package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaiana.game.databinding.ActivityIstorichkaBinding
import com.gaiana.game.model.Media

class IstorichkaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIstorichkaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIstorichkaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.istorichka1)
        Media.mp?.start()

        binding.btnIstorichkaEnd.setOnClickListener {
            Media.mp = MediaPlayer.create(this, R.raw.istorichkaend)
            Media.mp?.start()

            val intent = Intent(this, DieActivity::class.java)
            startActivity(intent)
        }
        binding.btnFizikEnd.setOnClickListener {
            Media.mp = MediaPlayer.create(this, R.raw.fizikend)
            Media.mp?.start()
            val intent = Intent(this, DieActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        Media.mp = MediaPlayer.create(this, R.raw.istorichkafalse)
        Media.mp?.start()
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