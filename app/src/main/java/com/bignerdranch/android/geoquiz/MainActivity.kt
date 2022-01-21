package com.bignerdranch.android.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView:TextView

    private val quizViewModel:QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            //Начало CheatActivity
            val intent = Intent(this,CheatActivity::class.java)
            startActivity(intent)
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if(userAnswer == correctAnswer){
            R.string.correct_toast
        } else{
            R.string.incorrect_toast
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show()
    }

    // Сохранение состояния эксземпляра на случай его уничтожения.
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt(KEY_INDEX,quizViewModel.currentIndex)
    }
}