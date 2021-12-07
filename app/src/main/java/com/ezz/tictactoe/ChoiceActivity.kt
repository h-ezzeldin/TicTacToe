package com.ezz.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.ezz.tictactoe.databinding.ActivityChoiceBinding

class ChoiceActivity : AppCompatActivity() {
    private val TAG = "tag ChoiceActivity"
    lateinit var b: ActivityChoiceBinding

    var choosen = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityChoiceBinding.inflate(layoutInflater)
        setContentView(b.root)
        b.oLayout.setOnClickListener {
            b.oBackLayout.visibility = VISIBLE
            b.xBackLayout.visibility = GONE
            choosen = 2
            Log.d(TAG, "choosen:$choosen ")
        }
        b.xLayout.setOnClickListener {
            b.oBackLayout.visibility = GONE
            b.xBackLayout.visibility = VISIBLE
            choosen = 1
            Log.d(TAG, "choosen:$choosen ")
        }


        b.backButton.setOnClickListener { this@ChoiceActivity.onBackPressed() }
        b.pvpButton.setOnClickListener { startGame(2, choosen) }
        b.pvaiButton.setOnClickListener { startGame(1, choosen)}
    }

    private fun startGame(mode: Int, letter: Int){
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("letter", letter)
            intent.putExtra("mode", mode)
            val p1: Pair<View, String> = Pair(b.pvpButton, "button_trans")
            val p2: Pair<View, String> = Pair(b.xLayout, "x_trans")
            val p3: Pair<View, String> = Pair(b.oLayout, "o_trans")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3)
            startActivity(intent, options.toBundle())

    }

}