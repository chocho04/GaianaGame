package com.gaiana.game

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import com.gaiana.game.databinding.ActivityGaianaBinding
import com.gaiana.game.model.Media

class GaianaActivity : AppCompatActivity() {

    private lateinit var b: ActivityGaianaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityGaianaBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.imgForeground.isVisible = false

        if(Media.mp != null) {
            Media.mp?.stop()
            Media.mp?.prepare()
        }
        Media.mp = MediaPlayer.create(this, R.raw.button15)
        Media.mp?.start()

        b.btnSave.setOnClickListener {
            if(Media.mp != null) {
                Media.mp?.stop()
                Media.mp?.prepare()
            }
            Media.mp = MediaPlayer.create(this, R.raw.button21)
            Media.mp?.start()

            Intent(this, DieActivity::class.java).also {
                startActivity(it)
            }
        }
        b.btnRlyNoSave.setOnClickListener {
            if(Media.mp != null) {
                Media.mp?.stop()
                Media.mp?.prepare()
            }
            Media.mp = MediaPlayer.create(this, R.raw.button22no)
            Media.mp?.start()

            b.btnSave.isVisible = false
            b.btnRlyNoSave.isVisible = false
            b.btnNoSave.isClickable = false
            b.btnRlyNoSave.text = "ами засега избяга..."
            b.imgForeground.isVisible = true
            b.tvTitle.isVisible = false

            Handler(Looper.getMainLooper()).postDelayed({
                Intent(this, AngelActivity::class.java).also {
                    startActivity(it)
                }
            }, 25000)
        }
        b.btnNoSave.setOnClickListener {
            if(Media.mp != null) {
                Media.mp?.stop()
                Media.mp?.prepare()
            }
            Media.mp = MediaPlayer.create(this, R.raw.button27rlyno)
            Media.mp?.start()

            b.btnSave.isVisible = false
            b.btnNoSave.isVisible = false
            b.btnRlyNoSave.isClickable = false
            b.tvTitle.isVisible = false
            b.btnNoSave.text = "ами засега избяга..."
            b.btnNoSave.isClickable = false

            Handler(Looper.getMainLooper()).postDelayed({
                Intent(this, AngelActivity3::class.java).also {
                    startActivity(it)
                }
            }, 22000)
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