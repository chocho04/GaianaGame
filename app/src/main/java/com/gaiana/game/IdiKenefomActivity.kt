package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gaiana.game.databinding.ActivityIdiKenefomBinding
import com.gaiana.game.model.Media

class IdiKenefomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdiKenefomBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdiKenefomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.d1)
        Media.mp?.start()

        binding.btnVoda.setOnClickListener {
            Intent(this, ToiletVodaActivity::class.java).also {
                startActivity(it)
            }
        }
        binding.btnObratno.setOnClickListener {
            Intent(this, AngelActivity::class.java).also {
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