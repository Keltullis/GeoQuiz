package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView:TextView

    private val questionBank = listOf(
        Question(R.string.question_australia,true),
        Question(R.string.question_oceans,   true),
        Question(R.string.question_mideast,  false),
        Question(R.string.question_africa,   false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia,     true))

    private var currentIndex = 0
    private var correctAnswerCounter = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
            promoterButton(false)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            promoterButton(false)
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            promoterButton(true)
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer:Boolean){
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if(userAnswer == correctAnswer){
            correctAnswerCounter++
            R.string.correct_toast
        } else{
            R.string.incorrect_toast
        }

        toastMaker(messageResId)
    }

    private fun promoterButton(Activate:Boolean){
        trueButton.isEnabled = Activate
        falseButton.isEnabled = Activate
    }

    private fun toastMaker(message:Int){
        if(currentIndex == questionBank.size-1){
            nextButton.isEnabled = false
            Toast.makeText(this,resources.getString(message) + getScore(),Toast.LENGTH_SHORT).show()
        } else{
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun getScore():String{
        val percentCorrectAnswer = (correctAnswerCounter/questionBank.size*100).toInt()
        return " You got $percentCorrectAnswer% of the marks."
    }
}