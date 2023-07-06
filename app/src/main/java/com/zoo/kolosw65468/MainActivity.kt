package com.zoo.kolosw65468

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var surveyViewModel: SurveyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        surveyViewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)

        // Tworzenie opcji ankiety
        val option1 = Option("Opcja 1")
        val option2 = Option("Opcja 2")
        val option3 = Option("Opcja 3")
        val option4 = Option("Opcja 4")

        // Dodawanie opcji do ViewModel i Warstwy Danych
        surveyViewModel.addOption(option1)
        surveyViewModel.addOption(option2)
        surveyViewModel.addOption(option3)
        surveyViewModel.addOption(option4)

        SurveyData.addOption(option1)
        SurveyData.addOption(option2)
        SurveyData.addOption(option3)
        SurveyData.addOption(option4)

        val voteButton: Button = findViewById(R.id.vote_button)
        voteButton.setOnClickListener {
            val selectedOptions = mutableListOf<Option>()

            val checkbox1: CheckBox = findViewById(R.id.checkbox_option1)
            if (checkbox1.isChecked) {
                selectedOptions.add(option1)
            }

            val checkbox2: CheckBox = findViewById(R.id.checkbox_option2)
            if (checkbox2.isChecked) {
                selectedOptions.add(option2)
            }

            val checkbox3: CheckBox = findViewById(R.id.checkbox_option3)
            if (checkbox3.isChecked) {
                selectedOptions.add(option3)
            }

            val checkbox4: CheckBox = findViewById(R.id.checkbox_option4)
            if (checkbox4.isChecked) {
                selectedOptions.add(option4)
            }

            if (selectedOptions.isNotEmpty()) {
                castVotes(selectedOptions)
                openResultsActivity()
            } else {
                Toast.makeText(this, "Wybierz co najmniej jedną opcję.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun castVotes(selectedOptions: List<Option>) {
        for (option in selectedOptions) {
            option.votes++
        }
    }

    private fun openResultsActivity() {
        val intent = Intent(this, ResultsActivity::class.java)
        startActivity(intent)
    }
}
