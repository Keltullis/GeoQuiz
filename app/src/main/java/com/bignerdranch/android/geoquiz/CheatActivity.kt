package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"
private const val CHEAT_INDEX = "Cheat"

class CheatActivity : AppCompatActivity() {

    private val cheatViewModel:CheatViewModel by lazy {
        ViewModelProvider(this).get(CheatViewModel::class.java)
    }

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val cheaterStatus = savedInstanceState?.getBoolean(CHEAT_INDEX,false) ?: false
        cheatViewModel.isCheater = cheaterStatus

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        checkCheater()

        showAnswerButton.setOnClickListener {
            cheatViewModel.isCheater = true
            answerTextView.text = displayAnswer()
            setAnswerShownResult(true)
        }

    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        setResult(Activity.RESULT_OK, data)
    }

    private fun displayAnswer():String = when{
        answerIsTrue -> getString(R.string.true_button)
        else -> getString(R.string.false_button)
    }

    private fun checkCheater(){
        if(cheatViewModel.isCheater) {
            answerTextView.text = displayAnswer()
            setAnswerShownResult(true)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBoolean(CHEAT_INDEX,cheatViewModel.isCheater)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}