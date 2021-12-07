package com.ezz.tictactoe


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import androidx.lifecycle.ViewModelProvider
import com.ezz.tictactoe.databinding.ActivityGameBinding
import com.ezz.tictactoe.databinding.DialogWinnerBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Timer
import kotlin.concurrent.schedule
import android.widget.FrameLayout

import android.view.animation.Animation





class GameActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "tag GameActivity"

    private var activePlayer = 0
    private var primaryPlayer = 0
    private var mode = -0

    private lateinit var b: ActivityGameBinding

    private lateinit var choicesArray: ArrayList<View>

    private lateinit var choicesLettersArray: ArrayList<View>

    lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityGameBinding.inflate(layoutInflater)
        setContentView(b.root)


        choicesArray = arrayListOf(
            b.zZ, b.zO, b.zT,
            b.oZ, b.oO, b.oT,
            b.tZ, b.tO, b.tT,
        )
        choicesLettersArray = arrayListOf(
            b.letterZZ, b.letterZO, b.letterZT,
            b.letterOZ, b.letterOO, b.letterOT,
            b.letterTZ, b.letterTO, b.letterTT,
        )

        choicesArray.forEach { it.setOnClickListener(this) }
        b.backButton.setOnClickListener { onBackPressed(); finish() }

        primaryPlayer = intent.getIntExtra("letter", 1)
        mode = intent.getIntExtra("mode", 1)

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java].apply {
            this.setPrimaryPlayer(primaryPlayer)
            this.setMode(mode)
        }

        b.resetButton.setOnClickListener { gameViewModel.reset(true) }

        gameViewModel.gameArray.observe(this, { b.gameArray = it })

        gameViewModel.decidedPosition.observe(this, {
            tap(choicesArray[it])
        })

        gameViewModel.theWinner.observe(this, {
            if (it != 0) {

                Handler(Looper.getMainLooper()).postDelayed({
                    gameViewModel.theWinner.postValue(0)
                    createDialog(it)
                    gameViewModel.increaseScore(it)
                    gameViewModel.reset(false)
                }, 250)
            }
        })

        gameViewModel.score.observe(this, { b.score = it })

        gameViewModel.activePlayer.observe(this, {
            b.activePlayer = it
            this@GameActivity.activePlayer = it})
    }

    private fun createDialog(winner: Int) {
        val dialogView = View.inflate(this, R.layout.dialog_winner, null)
        val bDialog = DialogWinnerBinding.bind(dialogView)
        bDialog.winner = winner

        val dialog = MaterialAlertDialogBuilder(this@GameActivity)
            .setView(bDialog.root)
            .show()

            Timer("SettingUp", false).schedule(1000) {
                dialog.dismiss()

        }


    }

    override fun onClick(p0: View?) {
        if (activePlayer == primaryPlayer) tap(p0!!)
    }

    private fun tap(view: View) {

        if (view in choicesArray) {
            val letterView = choicesLettersArray[choicesArray.indexOf(view)]
            if (letterView.visibility != View.VISIBLE) {

            letterView.alpha = 0f
            letterView.scaleX = 0f
            letterView.scaleY = 0f
                gameViewModel.tap(choicesArray.indexOf(view))

            letterView
                .animate()

                .scaleX(1f)
                .scaleY(1f)
                .alpha(1.0f)
                .setDuration(250)
                .setInterpolator(LinearInterpolator())
                .withEndAction { if (activePlayer != primaryPlayer && mode == 1)gameViewModel.makeMove() }

            }




        }

    }
}