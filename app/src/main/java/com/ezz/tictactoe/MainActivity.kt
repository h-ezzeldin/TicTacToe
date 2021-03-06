package com.ezz.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.ezz.tictactoe.databinding.ActivityMainBinding
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.google.android.material.dialog.MaterialAlertDialogBuilder


/**
 * @author hussein Ezz eldin
 * mail: hezzeldin1@gmail.com
 */
class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.startButton.setOnClickListener { toChoiceActivity() }

        b.aboutButton.setOnClickListener { showAboutDeveloper() }

    }

    /**
     * starts choices Activity with some shared transitions
     */
    private fun toChoiceActivity() {
        val intent = Intent(this, ChoiceActivity::class.java)
        val p1: Pair<View, String> = Pair(b.startButton, "button_trans")
        val p2: Pair<View, String> = Pair(b.xLetter, "x_trans")
        val p3: Pair<View, String> = Pair(b.oLetter, "o_trans")
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3)
        startActivity(intent, options.toBundle())
    }

    /**
     * shows information about developer in alert dialog
     */
    private fun showAboutDeveloper() {
        MaterialAlertDialogBuilder(this@MainActivity)
            .setBackground(AppCompatResources.getDrawable(this@MainActivity, R.color.white))
            .setTitle(getString(R.string.about))
            .setMessage(getString(R.string.info))
            .setNegativeButton("Dismiss", null)
            .show()
    }
}