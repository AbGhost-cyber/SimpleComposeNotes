package com.crushtech.simplecomposenotes.domain.repository

import com.crushtech.simplecomposenotes.data.remote.SimpleResponse
import com.crushtech.simplecomposenotes.domain.model.Note
import com.crushtech.simplecomposenotes.utils.Resource
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNotes(): Flow<Resource<List<Note>>>
    suspend fun upsertNote(note: Note): Flow<SimpleResponse>
    suspend fun deleteNote(noteId: String): Flow<SimpleResponse>
    suspend fun getNoteById(noteId: String): Flow<Resource<Note>>
}
