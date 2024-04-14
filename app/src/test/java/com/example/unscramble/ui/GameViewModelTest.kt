package com.example.unscramble.ui

import com.example.unscramble.data.getUnscrambledWord
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel()

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = 20
    }

    @Test
    fun correctWordGuessed_scoreUpdated_errorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val guessedWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        viewModel.updateUserGuess(guessedWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value

        // Check that the value of isGuessedWordWrong is indeed false - so this is true if the value is false
        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    @Test
    fun incorrectWordGuessed_scoreNotUpdated_errorFlagSet() {
        val guessedWord = "assddjhghgh"
        viewModel.updateUserGuess(guessedWord)
        viewModel.checkUserGuess()

        val currentGameUiState: GameUiState = viewModel.uiState.value
        assertTrue(currentGameUiState.isGuessedWordWrong)
        assertNotEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    @Test
    fun initialState_firstWordLoaded() {
        val gameUiState: GameUiState = viewModel.uiState.value
        val unscrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        assertNotEquals(unscrambledWord, gameUiState.currentScrambledWord)
        assertEquals(1, gameUiState.currentWordCount)
        assertEquals(0, gameUiState.score)
        assertFalse(gameUiState.isGuessedWordWrong)
        assertFalse(gameUiState.isGameOver)
    }
}