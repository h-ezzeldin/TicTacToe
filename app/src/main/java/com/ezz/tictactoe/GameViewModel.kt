package com.ezz.tictactoe

import android.util.Log
import android.util.Pair
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

open class GameViewModel : ViewModel() {
    private val TAG = "tag GameViewModel"
    private var primaryPlayer: Int = 0
    private var mode: Int = 0
    val activePlayer = MutableLiveData<Int>()
    val theWinner = MutableLiveData<Int>()
    val score = MutableLiveData<Pair<Int, Int>>()
    val gameArray = MutableLiveData<ArrayList<Int>>()
    val decidedPosition = MutableLiveData<Int>()

    init {
        reset(true)
    }

    fun tap(index: Int) {
        if (gameArray.value!![index] == 0) {

            val newArray = gameArray.value!!
            newArray[index] = activePlayer.value!!
            nextPlayer()
            gameArray.value = newArray
            Log.d(TAG, "tap: " + gameArray.value)
            Log.d(
                TAG, "tap: \n" +
                        "${gameArray.value!![0]},${gameArray.value!![1]},${gameArray.value!![2]}\n" +
                        "${gameArray.value!![3]},${gameArray.value!![4]},${gameArray.value!![5]}\n" +
                        "${gameArray.value!![6]},${gameArray.value!![7]},${gameArray.value!![8]}\n"
            )
        }
        checkWinner()
    }

    private fun nextPlayer() {
        when (activePlayer.value) {
            1 -> activePlayer.value = 2
            2 -> activePlayer.value = 1
        }
    }

    fun reset(isButton: Boolean) {
        activePlayer.value = primaryPlayer
        Log.d(TAG, "reset: ${activePlayer.value}")
        if (isButton) score.value = Pair(0, 0)
        gameArray.value = arrayListOf(
            0, 0, 0,
            0, 0, 0,
            0, 0, 0
        )

    }

    private fun checkWinner() {

        val array = gameArray.value!!
        Log.d(TAG, "checkWinner: ${array.subList(0, 3)}")
        val winner = when {
            (hashSetOf(array[0], array[1], array[2]).size == 1 && array[0] != 0) -> array[0]
            (hashSetOf(array[3], array[4], array[5]).size == 1 && array[3] != 0) -> array[3]
            (hashSetOf(array[6], array[7], array[8]).size == 1 && array[6] != 0) -> array[6]

            (hashSetOf(array[0], array[3], array[6]).size == 1 && array[0] != 0) -> array[0]
            (hashSetOf(array[1], array[4], array[7]).size == 1 && array[1] != 0) -> array[1]
            (hashSetOf(array[2], array[5], array[8]).size == 1 && array[2] != 0) -> array[2]

            (hashSetOf(array[6], array[4], array[2]).size == 1 && array[6] != 0) -> array[6]
            (hashSetOf(array[0], array[4], array[8]).size == 1 && array[0] != 0) -> array[0]
            !array.contains(0) -> 3
            else -> 0
        }

        Log.d(TAG, "checkWinner: $winner")
        if (winner != 0) theWinner.value = winner
        //else if (activePlayer.value != primaryPlayer && mode == 1) makeMove()
    }

    fun increaseScore(player: Int) {
        val newScore = score.value!!
        when (player) {
            1 -> score.value = Pair(newScore.first + 1, newScore.second)
            2 -> score.value = Pair(newScore.first, newScore.second + 1)
        }
    }

    fun makeMove() {

        val array = gameArray.value!!
        val ai = if (primaryPlayer == 1) 2 else 1
        val availablePositions = ArrayList<Int>()
        array.forEachIndexed { index, i -> if (i == 0) availablePositions.add(index) }


        val zeroTo3 = array.subList(0, 3)
        val threeTo6 = array.subList(3, 6)
        val sixTo9 = array.subList(6, 9)

        val zero36 = arrayListOf(array[0], array[3], array[6])
        val one47 = arrayListOf(array[1], array[4], array[7])
        val two58 = arrayListOf(array[2], array[5], array[8])

        val zero48 = arrayListOf(array[0], array[4], array[8])
        val two46 = arrayListOf(array[2], array[4], array[6])

        val newPosition: Int = when {
            // check if can win
            Collections.frequency(zeroTo3, ai) == 2 && zeroTo3.contains(0) -> zeroTo3.indexOf(0)
            Collections.frequency(
                threeTo6,
                ai
            ) == 2 && threeTo6.contains(0) -> threeTo6.indexOf(0) + 3
            Collections.frequency(sixTo9, ai) == 2 && sixTo9.contains(0) -> sixTo9.indexOf(0) + 6

            Collections.frequency(
                zero36,
                ai
            ) == 2 && zero36.contains(0) -> when (zero36.indexOf(0)) {
                1 -> 3
                2 -> 6
                else -> 0
            }

            Collections.frequency(one47, ai) == 2 && one47.contains(0) -> when (one47.indexOf(0)) {
                1 -> 4
                2 -> 7
                else -> 1
            }

            Collections.frequency(two58, ai) == 2 && two58.contains(0) -> when (two58.indexOf(0)) {
                1 -> 5
                2 -> 8
                else -> 2
            }

            Collections.frequency(
                zero48,
                ai
            ) == 2 && zero48.contains(0) -> when (zero48.indexOf(0)) {
                1 -> 4
                2 -> 8
                else -> 0
            }
            Collections.frequency(two46, ai) == 2 && two46.contains(0) -> when (two46.indexOf(0)) {
                1 -> 4
                2 -> 6
                else -> 2
            }

            // if cant win close the way
            Collections.frequency(
                zeroTo3,
                primaryPlayer
            ) == 2 && zeroTo3.contains(0) -> zeroTo3.indexOf(0)
            Collections.frequency(
                threeTo6,
                primaryPlayer
            ) == 2 && threeTo6.contains(0) -> threeTo6.indexOf(0) + 3
            Collections.frequency(
                sixTo9,
                primaryPlayer
            ) == 2 && sixTo9.contains(0) -> sixTo9.indexOf(0) + 6

            Collections.frequency(
                zero36,
                primaryPlayer
            ) == 2 && zero36.contains(0) -> when (zero36.indexOf(0)) {
                1 -> 3
                2 -> 6
                else -> 0
            }

            Collections.frequency(
                one47,
                primaryPlayer
            ) == 2 && one47.contains(0) -> when (one47.indexOf(0)) {
                1 -> 4
                2 -> 7
                else -> 1
            }

            Collections.frequency(
                two58,
                primaryPlayer
            ) == 2 && two58.contains(0) -> when (two58.indexOf(0)) {
                1 -> 5
                2 -> 8
                else -> 2
            }

            Collections.frequency(
                zero48,
                primaryPlayer
            ) == 2 && zero48.contains(0) -> when (zero48.indexOf(0)) {
                1 -> 4
                2 -> 8
                else -> 0
            }
            Collections.frequency(
                two46,
                primaryPlayer
            ) == 2 && two46.contains(0) -> when (two46.indexOf(0)) {
                1 -> 4
                2 -> 6
                else -> 2
            }
            // make a random move
            array.contains(0) -> availablePositions.random()

            else -> -1
        }

        if (newPosition > -1) decidedPosition.value = newPosition //tap(newPosition)
    }

    fun setPrimaryPlayer(player: Int) {
        primaryPlayer = player
        if (activePlayer.value == 0) activePlayer.value = player
    }

    fun setMode(myMode: Int) {
        mode = myMode
    }


}