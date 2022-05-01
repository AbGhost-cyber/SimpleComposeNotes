package com.crushtech.simplecomposenotes.data.remote.dto

data class NoteDto(
    val title: String,
    val content: String,
    val date: Long,
    val color: String,
    val id: String
)
