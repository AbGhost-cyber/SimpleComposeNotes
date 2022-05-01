package com.crushtech.simplecomposenotes.domain.model

data class Note(
    val title: String,
    val content: String,
    val date: Long,
    val color: String,
    val id: String
)
