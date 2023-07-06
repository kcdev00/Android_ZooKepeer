package com.zoo.kolosw65468


object SurveyData {
    private val options = mutableListOf<Option>()

    fun addOption(option: Option) {
        options.add(option)
    }

    fun getOptions(): List<Option> {
        return options
    }

    fun clearOptions() {
        options.clear()
    }
}
