package com.zoo.zookeeper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ZooApp.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(DBContract.SQL_CREATE_ANIMALS_TABLE)

        // Dodaj istniejące rekordy do bazy danych
        val values1 = ContentValues().apply {
            put(DBContract.AnimalEntry.COLUMN_ANIMAL_NAME, "Lew")
            put(DBContract.AnimalEntry.COLUMN_HUNGER, "Niski")
            put(DBContract.AnimalEntry.COLUMN_CLEANLINESS, "Czysty")
            put(DBContract.AnimalEntry.COLUMN_HAPPINESS, "Wysokie")
        }
        db.insert(DBContract.AnimalEntry.TABLE_NAME, null, values1)

        val values2 = ContentValues().apply {
            put(DBContract.AnimalEntry.COLUMN_ANIMAL_NAME, "Słoń")
            put(DBContract.AnimalEntry.COLUMN_HUNGER, "Średni")
            put(DBContract.AnimalEntry.COLUMN_CLEANLINESS, "Brudny")
            put(DBContract.AnimalEntry.COLUMN_HAPPINESS, "Niskie")
        }
        db.insert(DBContract.AnimalEntry.TABLE_NAME, null, values2)

        val values3 = ContentValues().apply {
            put(DBContract.AnimalEntry.COLUMN_ANIMAL_NAME, "Małpa")
            put(DBContract.AnimalEntry.COLUMN_HUNGER, "Wysoki")
            put(DBContract.AnimalEntry.COLUMN_CLEANLINESS, "Czysty")
            put(DBContract.AnimalEntry.COLUMN_HAPPINESS, "Średnie")
        }
        db.insert(DBContract.AnimalEntry.TABLE_NAME, null, values3)
        val values4 = ContentValues().apply {
            put(DBContract.AnimalEntry.COLUMN_ANIMAL_NAME, "Cielaczek")
            put(DBContract.AnimalEntry.COLUMN_HUNGER, "Wysoki")
            put(DBContract.AnimalEntry.COLUMN_CLEANLINESS, "Czysty")
            put(DBContract.AnimalEntry.COLUMN_HAPPINESS, "Średnie")
        }
        db.insert(DBContract.AnimalEntry.TABLE_NAME, null, values4)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DBContract.SQL_DELETE_ANIMALS_TABLE)
        onCreate(db)
    }

    fun getAllAnimalNames(): List<String> {
        val animalNames = mutableListOf<String>()
        val db = readableDatabase
        val projection = arrayOf(DBContract.AnimalEntry.COLUMN_ANIMAL_NAME)
        val sortOrder = "${DBContract.AnimalEntry.COLUMN_ANIMAL_NAME} ASC"
        val cursor: Cursor = db.query(
            DBContract.AnimalEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )
        with(cursor) {
            while (moveToNext()) {
                val animalName = getString(getColumnIndexOrThrow(DBContract.AnimalEntry.COLUMN_ANIMAL_NAME))
                animalNames.add(animalName)
            }
        }
        cursor.close()
        db.close()
        return animalNames
    }
    fun getAllAnimalDetails(): List<AnimalDetails> {
        val animalDetailsList = mutableListOf<AnimalDetails>()
        val db = readableDatabase
        val projection = arrayOf(
            DBContract.AnimalEntry.COLUMN_ANIMAL_NAME,
            DBContract.AnimalEntry.COLUMN_HUNGER,
            DBContract.AnimalEntry.COLUMN_CLEANLINESS,
            DBContract.AnimalEntry.COLUMN_HAPPINESS
        )
        val sortOrder = "${DBContract.AnimalEntry.COLUMN_ANIMAL_NAME} ASC"
        val cursor: Cursor = db.query(
            DBContract.AnimalEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )
        with(cursor) {
            while (moveToNext()) {
                val animalName = getString(getColumnIndexOrThrow(DBContract.AnimalEntry.COLUMN_ANIMAL_NAME))
                val hunger = getString(getColumnIndexOrThrow(DBContract.AnimalEntry.COLUMN_HUNGER))
                val cleanliness = getString(getColumnIndexOrThrow(DBContract.AnimalEntry.COLUMN_CLEANLINESS))
                val happiness = getString(getColumnIndexOrThrow(DBContract.AnimalEntry.COLUMN_HAPPINESS))
                val animalDetails = AnimalDetails(animalName, hunger, cleanliness, happiness)
                animalDetailsList.add(animalDetails)
            }
        }
        cursor.close()
        db.close()
        return animalDetailsList
    }
    fun updateAnimalDetails(animalName: String, hunger: String, cleanliness: String, happiness: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DBContract.AnimalEntry.COLUMN_HUNGER, hunger)
            put(DBContract.AnimalEntry.COLUMN_CLEANLINESS, cleanliness)
            put(DBContract.AnimalEntry.COLUMN_HAPPINESS, happiness)
        }
        val selection = "${DBContract.AnimalEntry.COLUMN_ANIMAL_NAME} = ?"
        val selectionArgs = arrayOf(animalName)
        db.update(DBContract.AnimalEntry.TABLE_NAME, values, selection, selectionArgs)
        db.close()
    }
}

