package com.zoo.kolosw65468
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
class ResultsActivity : AppCompatActivity() {
    private lateinit var surveyViewModel: SurveyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        surveyViewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)

        var options = surveyViewModel.getOptions()

        options = options.sortedByDescending { it.votes }

        val resultsTextView: TextView = findViewById(R.id.results_textview)
        resultsTextView.text = ""
        for (option in options) {
            val percentage = (option.votes.toFloat() / options.sumBy { it.votes } * 100).toInt()
            val resultText = "${option.name}: $percentage%"
            resultsTextView.append(resultText)
            resultsTextView.append("\n")
        }

        val voteAgainButton: Button = findViewById(R.id.vote_again_button)
        voteAgainButton.setOnClickListener {
            surveyViewModel.clearOptions()
            SurveyData.clearOptions()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val exitButton: Button = findViewById(R.id.exit_button)
        exitButton.setOnClickListener {
            finish()
        }
    }

}