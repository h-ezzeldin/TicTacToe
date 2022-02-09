package com.ezz.tictactoe

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.toAndroidPair
import androidx.lifecycle.ViewModelProvider
import com.ezz.tictactoe.Constants.DURATION
import com.ezz.tictactoe.Constants.LETTER_X
import com.ezz.tictactoe.Constants.SINGLE_PLAYER
import com.ezz.tictactoe.Constants.TWO_PLAYERS
import com.ezz.tictactoe.Constants.playSound
import com.ezz.tictactoe.databinding.ActivityGameBinding
import com.ezz.tictactoe.databinding.DialogWinnerBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * here you can play with your chosen mode
 */
class GameActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "tag GameActivity"

    private var activePlayer = 0
    private var primaryPlayer = 0
    private var mode = 0

    private lateinit var b: ActivityGameBinding

    private lateinit var lettersArray: ArrayList<FrameLayout>

    private lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityGameBinding.inflate(layoutInflater)
        setContentView(b.root)

        lettersArray = arrayListOf(
            b.zero, b.one, b.two,
            b.three, b.four, b.five,
            b.six, b.seven, b.eight,
        )

        lettersArray.forEach { it.setOnClickListener(this) }

        b.backButton.setOnClickListener { onBackPressed(); finish() }

        primaryPlayer = intent.getIntExtra("letter", LETTER_X)
        mode = intent.getIntExtra("mode", SINGLE_PLAYER)

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java].apply {
            setPrimaryPlayer(primaryPlayer)
            setMode(mode)
        }

        b.resetButton.setOnClickListener { gameViewModel.reset(true) }

        gameViewModel.gameArray.observe(this) {
            b.gameArray = it
            Log.d(
                TAG,
                "gameArray: \n" +
                    "${it[0]},${it[1]},${it[2]}\n" +
                    "${it[3]},${it[4]},${it[5]}\n" +
                    "${it[6]},${it[7]},${it[8]}\n"
            )
        }

        gameViewModel.decidedPosition.observe(this) {
            if (it != -1) tap(it)
            Log.d(TAG, "onCreate: $it")
        }

        gameViewModel.theWinner.observe(this) {
            if (it != 0) {
                Log.d(TAG, "---------------------the winner: $it ")
                // Handler(Looper.getMainLooper()).postDelayed({
                showWinnerDialog(it)
                // gameViewModel.theWinner.value = 0
                // gameViewModel.reset(false)
                // }, 250)
            }
        }

        gameViewModel.score.observe(this) { b.score = it.toAndroidPair() }

        gameViewModel.activePlayer.observe(this) {
            b.activePlayer = it
            this@GameActivity.activePlayer = it
        }
    }

    /**
     * shows a dialog of the winning letter with auto dismiss
     * @param winner winning letter
     */
    private fun showWinnerDialog(winner: Int) {

        if (mode == SINGLE_PLAYER) {
            if (winner == primaryPlayer) playSound(this, 1) else playSound(this, 2)
        } else {
            if (winner == 3) playSound(this, 2) else playSound(this, 1)
        }

        val dialogView = View.inflate(this, R.layout.dialog_winner, null)
        val bDialog = DialogWinnerBinding.bind(dialogView)

        bDialog.winner = winner

        val dialog = MaterialAlertDialogBuilder(this@GameActivity)
            .setView(bDialog.root)
            .show()

        Handler(Looper.getMainLooper()).postDelayed({ dialog.dismiss() }, 1000)
    }

    /**
     * checks if player can tap
     */
    override fun onClick(p0: View?) {
        if (p0 in lettersArray) {
            when (mode) {
                TWO_PLAYERS -> tap(lettersArray.indexOf(p0))
                SINGLE_PLAYER -> if (activePlayer == primaryPlayer) tap(lettersArray.indexOf(p0))
            }
        }
    }

    /**
     * gets which view is clicked
     * makes animation onClick
     * @param index clicked index
     */
    private fun tap(index: Int) {
        val letterView = lettersArray[index]
        if (letterView.background == null) {
            letterView.alpha = 0f
            letterView.scaleX = 0f
            letterView.scaleY = 0f

            gameViewModel.tap(index)

            playSound(this, 3)

            letterView
                .animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(DURATION)
                .interpolator = LinearInterpolator()
        }
    }

    override fun onStop() {
        super.onStop()
        Constants.releasePlayer()
    }
}
