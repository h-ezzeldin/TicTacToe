package com.ezz.tictactoe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ezz.tictactoe.Constants.LETTER_O
import com.ezz.tictactoe.Constants.LETTER_X
import com.ezz.tictactoe.Constants.SINGLE_PLAYER
import com.google.common.truth.Truth.assertThat
import org.junit.*
import org.junit.rules.TestRule

class GameViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val sleepDuration = 300L

    private lateinit var gameViewModel: GameViewModel

    @Before
    fun setup() {
        gameViewModel = GameViewModel()
        gameViewModel.setPrimaryPlayer(LETTER_X)
        gameViewModel.setMode(SINGLE_PLAYER)
    }

    @Test
    fun `tapping updates game array with active letter`() {
        val index = 0
        gameViewModel.tap(index)

        val newGameArray = gameViewModel.gameArray.getOrAwaitValueTest()

        assertThat(newGameArray[index]).isEqualTo(LETTER_X)
    }

    @Test
    fun `tapping on already tapped index won't update game array`() {
        val index = 0
        gameViewModel.gameArray.value = arrayListOf(
            1, 0, 0,
            0, 0, 0,
            0, 0, 0
        )
        gameViewModel.activePlayer.value = LETTER_O

        gameViewModel.tap(index)

        val newGameArray = gameViewModel.gameArray.getOrAwaitValueTest()
        assertThat(newGameArray[index]).isEqualTo(LETTER_X)
    }

    @Test
    fun `tapping changes active player X to O`() {
        val index = 1
        gameViewModel.tap(index)

        val activePlayer = gameViewModel.activePlayer.getOrAwaitValueTest()
        assertThat(activePlayer).isEqualTo(LETTER_O)
    }

    @Test
    fun `tapping changes active player O to X`() {
        gameViewModel.activePlayer.value = LETTER_O
        val index = 1
        gameViewModel.tap(index)

        val activePlayer = gameViewModel.activePlayer.getOrAwaitValueTest()
        assertThat(activePlayer).isEqualTo(LETTER_X)
    }

    @Test
    fun `tapping on already tapped index won't change active player`() {
        val index = 0
        gameViewModel.gameArray.value = arrayListOf(
            1, 0, 0,
            0, 0, 0,
            0, 0, 0
        )
        gameViewModel.tap(index)

        val activePlayer = gameViewModel.activePlayer.getOrAwaitValueTest()
        assertThat(activePlayer).isEqualTo(LETTER_X)
    }

    @Test
    fun `checking winner on indices zero to two`() {
        val winningLetter = LETTER_X
        gameViewModel.gameArray.value = arrayListOf(
            0, 1, 1,
            0, 0, 0,
            0, 0, 0
        )
        val index = 0
        gameViewModel.tap(index)

        val winner = gameViewModel.theWinner.getOrAwaitValueTest()

        assertThat(winner).isEqualTo(winningLetter)
    }

    @Test
    fun `checking winner on indices three to five`() {
        val winningLetter = LETTER_X
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 0,
            0, 1, 1,
            0, 0, 0
        )
        val index = 3
        gameViewModel.tap(index)

        val winner = gameViewModel.theWinner.getOrAwaitValueTest()
        System.out.println(winner)
        assertThat(winner).isEqualTo(winningLetter)
    }

    @Test
    fun `checking winner on indices six to eight`() {
        val winningLetter = LETTER_X
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 0,
            0, 0, 0,
            0, 1, 1
        )
        val index = 6
        gameViewModel.tap(index)

        val winner = gameViewModel.theWinner.getOrAwaitValueTest()
        assertThat(winner).isEqualTo(winningLetter)
    }

    @Test
    fun `checking winner on indices zero-three-six`() {
        val winningLetter = LETTER_X
        gameViewModel.gameArray.value = arrayListOf(
            1, 0, 0,
            0, 0, 0,
            1, 0, 0
        )
        val index = 3
        gameViewModel.tap(index)

        val winner = gameViewModel.theWinner.getOrAwaitValueTest()
        assertThat(winner).isEqualTo(winningLetter)
    }

    @Test
    fun `checking winner on indices one-four-seven`() {
        val winningLetter = LETTER_X
        gameViewModel.gameArray.value = arrayListOf(
            0, 1, 0,
            0, 1, 0,
            0, 0, 0
        )
        val index = 7
        gameViewModel.tap(index)

        val winner = gameViewModel.theWinner.getOrAwaitValueTest()
        assertThat(winner).isEqualTo(winningLetter)
    }

    @Test
    fun `checking winner on indices two-five-eight`() {
        val winningLetter = LETTER_X
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 0,
            0, 0, 1,
            0, 0, 1
        )
        val index = 2
        gameViewModel.tap(index)

        val winner = gameViewModel.theWinner.getOrAwaitValueTest()
        assertThat(winner).isEqualTo(winningLetter)
    }

    @Test
    fun `checking winner on indices zero-four-eight`() {
        val winningLetter = LETTER_X
        gameViewModel.gameArray.value = arrayListOf(
            1, 0, 0,
            0, 0, 0,
            0, 0, 1
        )
        val index = 4
        gameViewModel.tap(index)

        val winner = gameViewModel.theWinner.getOrAwaitValueTest()
        assertThat(winner).isEqualTo(winningLetter)
    }

    @Test
    fun `checking winner on indices two-four-six`() {
        val winningLetter = LETTER_X
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 1,
            0, 1, 0,
            0, 0, 0
        )
        val index = 6
        gameViewModel.tap(index)

        val winner = gameViewModel.theWinner.getOrAwaitValueTest()
        assertThat(winner).isEqualTo(winningLetter)
    }

    @Test
    fun `if player wins ,game array resets `() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 1,
            0, 0, 1,
            0, 0, 0
        )
        val index = 8
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val gameArray = gameViewModel.gameArray.getOrAwaitValueTest()
        System.out.println(gameArray)

        assertThat(gameArray).containsNoneOf(1, 2)

    }

    @Test
    fun `reset function resets gameArray`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 1,
            0, 0, 1,
            0, 0, 0
        )
        val gameArrayBefore = gameViewModel.gameArray.getOrAwaitValueTest()
        System.out.println(gameArrayBefore)
        gameViewModel.reset(true)
        val gameArray = gameViewModel.gameArray.getOrAwaitValueTest()
        System.out.println(gameArray)

        assertThat(gameArray).containsNoneOf(1, 2)

    }

    @Test
    fun `reset function resets activePlayer`() {
        gameViewModel.activePlayer.value = LETTER_O
        gameViewModel.reset(true)
        val activePlayer = gameViewModel.activePlayer.getOrAwaitValueTest()
        assertThat(activePlayer).isEqualTo(LETTER_X)
    }

    @Test
    fun `reset function resets score if reset button clicked`() {
        gameViewModel.score.value = Pair(5, 1)
        gameViewModel.reset(true)
        val score = gameViewModel.score.getOrAwaitValueTest()
        assertThat(score).isEqualTo(Pair(0, 0))
    }

    @Test
    fun `if x is winner increase score of x`() {
        gameViewModel.gameArray.value = arrayListOf(
            1, 0, 0,
            0, 0, 0,
            0, 0, 1
        )
        val index = 4
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val score = gameViewModel.score.getOrAwaitValueTest()
        assertThat(score).isEqualTo(Pair(1, 0))
    }

    @Test
    fun `if o is winner increase score of o`() {
        gameViewModel.activePlayer.value = LETTER_O
        gameViewModel.gameArray.value = arrayListOf(
            2, 0, 0,
            0, 0, 0,
            0, 0, 2
        )
        val index = 4
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val score = gameViewModel.score.getOrAwaitValueTest()
        assertThat(score).isEqualTo(Pair(0, 1))
    }

    @Test
    fun `request AI move updates decidedPosition with a random position`() {
        val index = 4
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isNotEqualTo(-1)
    }

    @Test
    fun `when primary player has two letters in indices 0 to 2 , AI decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            1, 0, 1,
            0, 0, 0,
            0, 0, 0
        )
        val index = 4
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(1)
    }

    @Test
    fun `when primary player has two letters in indices 3 to 5 , AI decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 0,
            1, 1, 0,
            0, 0, 0
        )
        val index = 0
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(5)
    }

    @Test
    fun `when primary player has two letters in indices 6 to 8 , AI decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 0,
            0, 0, 0,
            0, 1, 1
        )
        val index = 0
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(6)
    }

    @Test
    fun `when primary player has two letters in indices 0, 3, 6 , AI decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            1, 0, 0,
            0, 0, 0,
            0, 0, 0
        )
        val index = 3
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(6)
    }

    @Test
    fun `when primary player has two letters in indices 1, 4, 7 , AI decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 0,
            0, 1, 0,
            0, 1, 0
        )
        val index = 0
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(1)
    }

    @Test
    fun `when primary player has two letters in indices 2, 5, 8 , AI decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 1,
            0, 0, 0,
            0, 0, 0
        )
        val index = 5
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(8)
    }

    @Test
    fun `when primary player has two letters in indices 0, 4, 8 , AI decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            1, 0, 0,
            0, 0, 0,
            0, 0, 0
        )
        val index = 8
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(4)
    }

    @Test
    fun `when primary player has two letters in indices 2, 4, 6, AI decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 1,
            0, 0, 0,
            0, 0, 0
        )
        val index = 4
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(6)
    }

    @Test
    fun `when AI player has two letters in indices 0 to 2 , decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            2, 0, 2,
            0, 0, 0,
            0, 0, 0
        )
        val index = 4
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(1)
    }

    @Test
    fun `when AI player has two letters in indices 3 to 5 , decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 0,
            2, 2, 0,
            0, 0, 0
        )
        val index = 0
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(5)
    }

    @Test
    fun `when AI player has two letters in indices 6 to 8 , decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 0,
            0, 0, 0,
            0, 2, 2
        )
        val index = 0
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(6)
    }

    @Test
    fun `when AI player has two letters in indices 0, 3, 6 , decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            2, 0, 0,
            2, 0, 0,
            0, 0, 0
        )
        val index = 1
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(6)
    }

    @Test
    fun `when AI player has two letters in indices 1, 4, 7 , decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 0,
            0, 2, 0,
            0, 2, 0
        )
        val index = 0
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(1)
    }

    @Test
    fun `when AI player has two letters in indices 2, 5, 8 , decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 2,
            0, 0, 2,
            0, 0, 0
        )
        val index = 0
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(8)
    }

    @Test
    fun `when AI player has two letters in indices 0, 4, 8 , decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            2, 0, 0,
            0, 2, 0,
            0, 0, 0
        )
        val index = 3
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(8)
    }

    @Test
    fun `when AI player has two letters in indices 2, 4, 6 , decides to tap on the third index`() {
        gameViewModel.gameArray.value = arrayListOf(
            0, 0, 2,
            0, 0, 0,
            2, 0, 0
        )
        val index = 0
        gameViewModel.tap(index)
        Thread.sleep(sleepDuration)
        val position = gameViewModel.decidedPosition.getOrAwaitValueTest()
        System.out.println(position)
        assertThat(position).isEqualTo(4)
    }

}