package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    // Список слов, использованных в игре
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    // Вызов функции отображения скремблированного слова при инициализации
    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    // Увеличивает счёт игрока
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    // Проверка правильности слова, введённого игроком
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
    * Обновляет currentWord и currentScrambledWord следующим словом
    */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()

        while (String(tempWord).equals(currentWord, false)){
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)){
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    /*
    * Реинициализирует игровые данные для перезапуска игры
    */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }

    /*
    * Возвращает true, если счётчик текущего слова меньше, чем MAX_NO_OF_WORDS
    * Обновляет следующее слово
    */
    fun nextWord(): Boolean {
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
}