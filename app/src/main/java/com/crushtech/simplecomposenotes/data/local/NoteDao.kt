package com.crushtech.simplecomposenotes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {

    @Query("SELECT * FROM noteentity ORDER BY date DESC")
    suspend fun getNotes(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNote(noteEntity: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNotes(notes: List<NoteEntity>)

    @Query("DELETE FROM noteentity WHERE id= :noteId")
    suspend fun deleteNote(noteId: String)

    @Query("DELETE FROM noteentity")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM noteentity WHERE id= :noteId")
    suspend fun getNoteById(noteId: String): NoteEntity?
}
