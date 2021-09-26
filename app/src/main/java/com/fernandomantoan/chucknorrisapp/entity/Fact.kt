package com.fernandomantoan.chucknorrisapp.entity

data class Fact(
    val icon_url: String,
    val id: String,
    val url: String,
    val value: String,
    val categories: List<String>
) {
    fun getTextSize(): Float {
        return if (value.length <= MAX_TEXT_SIZE)
            LARGE_TEXT
        else
            SMALL_TEXT
    }

    companion object {
        const val MAX_TEXT_SIZE = 80
        const val LARGE_TEXT = 25.0F
        const val SMALL_TEXT = 18.0F
    }
}