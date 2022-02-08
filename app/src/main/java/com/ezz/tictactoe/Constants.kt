package com.ezz.tictactoe

import android.content.Context
import android.media.MediaPlayer

/**
 * stores constants that is frequently used
 */
object Constants {
    const val SINGLE_PLAYER = 1
    const val TWO_PLAYERS = 2
    const val NETWORK_PLAY = 3

    const val LETTER_X = 1
    const val LETTER_O = 2

    const val DURATION = 250L

    private var mediaPlayer: MediaPlayer? = null

    /**
     * creates a mediaPlayer sound and starts it
     *
     * @param context activity which called the method
     * @param type given sound number to play
     */
    fun playSound(context: Context, type: Int) {
        val sound = when (type) {
            1 -> R.raw.win // winning sound
            2 -> R.raw.lose      // losing sound
            3 -> R.raw.tap    // tapping sound
            else -> R.raw.tap
        }

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, sound)
        mediaPlayer?.start()
    }

    /**
     * releases created mediaPlayer file
     */
    fun releasePlayer() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

}