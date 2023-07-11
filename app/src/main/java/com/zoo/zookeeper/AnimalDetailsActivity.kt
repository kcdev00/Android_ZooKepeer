package com.zoo.zookeeper

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AnimalDetailsActivity : AppCompatActivity() {
    private lateinit var hungerEditText: EditText
    private lateinit var cleanlinessEditText: EditText
    private lateinit var happinessEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_details)

        val animalName = intent.getStringExtra("animal_name")
        supportActionBar?.title = animalName

        hungerEditText = findViewById(R.id.hunger_edit_text)
        cleanlinessEditText = findViewById(R.id.cleanliness_edit_text)
        happinessEditText = findViewById(R.id.happiness_edit_text)
        saveButton = findViewById(R.id.save_button)

        saveButton.setOnClickListener {
            val hunger = hungerEditText.text.toString()
            val cleanliness = cleanlinessEditText.text.toString()
            val happiness = happinessEditText.text.toString()

            val intent = Intent()
            intent.putExtra("animal_name", animalName)
            intent.putExtra("hunger", hunger)
            intent.putExtra("cleanliness", cleanliness)
            intent.putExtra("happiness", happiness)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}

