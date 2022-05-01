package com.crushtech.simplecomposenotes.data.repository

import com.crushtech.simplecomposenotes.data.local.NoteDatabase
import com.crushtech.simplecomposenotes.data.mapper.toNote
import com.crushtech.simplecomposenotes.data.mapper.toNoteDto
import com.crushtech.simplecomposenotes.data.mapper.toNoteEntity
import com.crushtech.simplecomposenotes.data.remote.NoteApi
import com.crushtech.simplecomposenotes.data.remote.SimpleResponse
import com.crushtech.simplecomposenotes.domain.model.Note
import com.crushtech.simplecomposenotes.domain.repository.NoteRepository
import com.crushtech.simplecomposenotes.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl(
    private val noteApi: NoteApi,
    db: NoteDatabase
) : NoteRepository {

    private val dao = db.noteDao

    override suspend fun getNotes(): Flow<Resource<List<Note>>> {
        return flow {
            emit(Resource.Loading())
            val cacheNotes = dao.getNotes().map { it.toNote() }
            emit(Resource.Loading(cacheNotes))
            try {
                val apiNotes = noteApi.getNotes()
                dao.deleteAllNotes()
                dao.upsertNotes(apiNotes.map { it.toNoteEntity() })
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        cacheNotes,
                        e.message ?: "something went wrong"
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        cacheNotes,
                        e.message ?: "no internet connection"
                    )
                )
            }
            val newNotes = dao.getNotes().map { it.toNote() }
            emit(Resource.Success(newNotes))
        }
    }

    override suspend fun upsertNote(note: Note): Flow<SimpleResponse> {
        return flow {
            try {
                dao.upsertNote(note.toNoteEntity())
                val cachedNote = dao.getNoteById(note.id)
                // cached note cannot be null here
                val response = noteApi.upsertNote(cachedNote!!.toNoteDto())
                emit(response)
            } catch (e: HttpException) {
                emit(
                    SimpleResponse(
                        false,
                        e.message ?: "something went wrong"
                    )
                )
            } catch (e: IOException) {
                emit(
                    SimpleResponse(
                        false,
                        e.message ?: "no internet connection"
                    )
                )
            }
        }
    }

    override suspend fun deleteNote(noteId: String): Flow<SimpleResponse> {
        return flow {
            try {
                val response = noteApi.deleteNote(noteId)
                dao.deleteNote(noteId)
                emit(response)
            } catch (e: HttpException) {
                emit(
                    SimpleResponse(
                        false,
                        e.message ?: "something went wrong"
                    )
                )
            } catch (e: IOException) {
                emit(
                    SimpleResponse(
                        false,
                        e.message ?: "no internet connection"
                    )
                )
            }
        }
    }

    override suspend fun getNoteById(noteId: String): Flow<Resource<Note>> {
        return flow {
            emit(Resource.Loading())
            try {
                val note = noteApi.getNoteById(noteId)
                val cachedNote = dao.getNoteById(note.id)
                if (cachedNote == null) {
                    dao.upsertNote(note.toNoteEntity())
                    val newCachedNote = dao.getNoteById(note.id)
                    emit(Resource.Success(newCachedNote!!.toNote()))
                } else {
                    emit(Resource.Success(cachedNote.toNote()))
                }
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = e.message ?: "something went wrong"
                    )
                )
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = e.message ?: "no network connection"
                    )
                )
            }
        }
    }
}
