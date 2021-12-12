package com.ezz.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.ezz.tictactoe.databinding.ActivityChoiceBinding

class ChoiceActivity : AppCompatActivity() {
    private val TAG = "tag ChoiceActivity"
    private val SINGLE_PLAYER = 1
    private val TWO_PLAYERS = 2
    private lateinit var b: ActivityChoiceBinding

    var chosen = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.chosenLetter = chosen

        b.oLayout.setOnClickListener {
            b.chosenLetter =  2
            chosen = 2
        }
        b.xLayout.setOnClickListener {
            b.chosenLetter =  1
            chosen = 1
        }

        b.backButton.setOnClickListener { this@ChoiceActivity.onBackPressed() }
        b.pvpButton.setOnClickListener { startGame(TWO_PLAYERS, chosen) }
        b.pvaiButton.setOnClickListener { startGame(SINGLE_PLAYER, chosen)}
    }

    private fun startGame(mode: Int, letter: Int){

            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("letter", letter)
                putExtra("mode", mode)
            }

            val p1: Pair<View, String> = Pair(b.pvpButton, "button_trans")
            val p2: Pair<View, String> = Pair(b.xLayout, "x_trans")
            val p3: Pair<View, String> = Pair(b.oLayout, "o_trans")

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3)

            startActivity(intent, options.toBundle())

    }

}