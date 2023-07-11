package com.zoo.zookeeper


import android.provider.BaseColumns

object DBContract {

    object AnimalEntry : BaseColumns {
        const val COLUMN_ANIMAL_ID = "ID"
        const val TABLE_NAME = "animals"
        const val COLUMN_ANIMAL_NAME = "animal_name"
        const val COLUMN_HUNGER = "hunger"
        const val COLUMN_CLEANLINESS = "cleanliness"
        const val COLUMN_HAPPINESS = "happiness"
    }

    const val SQL_CREATE_ANIMALS_TABLE = "CREATE TABLE ${AnimalEntry.TABLE_NAME} (" +
            "${AnimalEntry.COLUMN_ANIMAL_ID} INTEGER PRIMARY KEY," +
            "${AnimalEntry.COLUMN_ANIMAL_NAME} TEXT," +
            "${AnimalEntry.COLUMN_HUNGER} TEXT," +
            "${AnimalEntry.COLUMN_CLEANLINESS} TEXT," +
            "${AnimalEntry.COLUMN_HAPPINESS} TEXT)"

    const val SQL_DELETE_ANIMALS_TABLE = "DROP TABLE IF EXISTS ${AnimalEntry.TABLE_NAME}"
}
