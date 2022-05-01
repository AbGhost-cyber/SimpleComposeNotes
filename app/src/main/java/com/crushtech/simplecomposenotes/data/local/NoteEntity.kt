package com.crushtech.simplecomposenotes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class NoteEntity(
    val title: String,
    val content: String,
    val date: Long,
    val color: String,
    @PrimaryKey(autoGenerate = false)
    val id: String = UUID.randomUUID().toString()
)
