package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    companion object{
        private const val TAG = "GameViewModel"
    }

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    private var _score = 0
    val score get() = _score

    private var _currentWordCount = 0
    val currentWordCount get() = _currentWordCount
    private lateinit var _currentScrambledWord: String
    val currentScrambledWord get() = _currentScrambledWord

    init {
        Log.d(TAG, "GameViewModel created")
        getNextWord()
    }

    private fun getNextWord(){
        // Get random word
        currentWord = allWordsList.random()

        // Shuffle the word
        var tempWord = currentWord.toCharArray()
        tempWord.shuffle()
        while (tempWord.toString().equals(currentWord, false)){
            tempWord.shuffle()
        }

        // Check if the word is used before
        if (wordsList.contains(currentWord)){
            getNextWord()
        }
        else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    /**
     * Checks if the game is over
     * If it is not, call getNextWord()
     */
    fun nextWord(): Boolean{
        return if (_currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }
        else false
    }

    /**
     * Increases the private _score
     */
    private fun increaseScore(){
        _score += SCORE_INCREASE
    }

    fun isUserCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)){
            increaseScore()
            return true
        }
        return false
    }

    /**
     * Re-initializes game when game is finished, but wants to play again
     */
    fun reinitializeData(){
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "GameViewModel destroyed")
    }
}