package com.crushtech.simplecomposenotes.data.remote

import com.crushtech.simplecomposenotes.data.remote.dto.NoteDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NoteApi {
    @GET("notes")
    suspend fun getNotes(): List<NoteDto>

    @POST("notes")
    suspend fun upsertNote(
        noteDto: NoteDto
    ): SimpleResponse

    @DELETE("notes/{id}")
    suspend fun deleteNote(
        @Query("id") id: String
    ): SimpleResponse

    @GET("notes/{id}")
    suspend fun getNoteById(
        @Query("id") id: String
    ): NoteDto

    companion object {
        const val BASE_URL = "http://10.61.96.121:8080/"
    }
}
