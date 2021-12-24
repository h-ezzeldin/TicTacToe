package com.ezz.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.ezz.tictactoe.Constants.LETTER_O
import com.ezz.tictactoe.Constants.LETTER_X
import com.ezz.tictactoe.Constants.SINGLE_PLAYER
import com.ezz.tictactoe.Constants.TWO_PLAYERS
import com.ezz.tictactoe.databinding.ActivityChoiceBinding

/**
 * here you can choice which letter to start
 * and which mode you want to play
 */
class ChoiceActivity : AppCompatActivity() {
    private val TAG = "tag ChoiceActivity"
    private lateinit var b: ActivityChoiceBinding

    var chosen = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.chosenLetter = chosen

        b.oLayout.setOnClickListener {
            b.chosenLetter = LETTER_O
            chosen = LETTER_O
        }
        b.xLayout.setOnClickListener {
            b.chosenLetter = LETTER_X
            chosen = LETTER_X
        }

        b.backButton.setOnClickListener { this@ChoiceActivity.onBackPressed() }
        b.pvpButton.setOnClickListener { startGame(TWO_PLAYERS, chosen) }
        b.pvaiButton.setOnClickListener { startGame(SINGLE_PLAYER, chosen) }
        // TODO network play button
    }

    /**
     * starts activity with shared transitions
     * @param mode game mode
     * @param letter which letter will start
     */
    private fun startGame(mode: Int, letter: Int) {

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