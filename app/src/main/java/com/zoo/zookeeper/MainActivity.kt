package com.zoo.zookeeper
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var animalList: ListView
    private lateinit var dbHelper: DBHelper

    private val REQUEST_CODE_ANIMAL_DETAILS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)

        animalList = findViewById(R.id.animal_list)
        val animals = dbHelper.getAllAnimalNames()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, animals)
        animalList.adapter = adapter

        animalList.setOnItemClickListener { _, _, position, _ ->
            val selectedAnimal = animals[position]
            val intent = Intent(this, AnimalDetailsActivity::class.java)
            intent.putExtra("animal_name", selectedAnimal)
            startActivityForResult(intent, REQUEST_CODE_ANIMAL_DETAILS)
        }

        // Wywołanie metody wyświetlającej informacje o zapisanych zwierzętach
        displaySavedAnimalDetails()
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ANIMAL_DETAILS && resultCode == Activity.RESULT_OK && data != null) {
            val animalName = data.getStringExtra("animal_name")
            val hunger = data.getStringExtra("hunger")
            val cleanliness = data.getStringExtra("cleanliness")
            val happiness = data.getStringExtra("happiness")

            if (animalName != null && hunger != null && cleanliness != null && happiness != null) {
                val message = "Zapisano informacje o $animalName\nGłód: $hunger\nCzystość: $cleanliness\nZadowolenie: $happiness"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

                // Aktualizacja rekordu w bazie danych
                val dbHelper = DBHelper(this)
                dbHelper.updateAnimalDetails(animalName, hunger, cleanliness, happiness)

                // Aktualizacja listy po zapisaniu informacji o zwierzęciu
                displaySavedAnimalDetails()
            } else {
                Toast.makeText(this, "Nie udało się odczytać informacji o zwierzęciu", Toast.LENGTH_SHORT).show()
            }
        }
    }
        fun displaySavedAnimalDetails() {
            val animalDetails = dbHelper.getAllAnimalDetails()

            val animalDetailsList = mutableListOf<String>()
            for (animalDetail in animalDetails) {
                val message =
                    "Zwierzę: ${animalDetail.animalName}\nGłód: ${animalDetail.hunger}\nCzystość: ${animalDetail.cleanliness}\nZadowolenie: ${animalDetail.happiness}"
                animalDetailsList.add(message)
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, animalDetailsList)
            animalList.adapter = adapter
        }
    }
