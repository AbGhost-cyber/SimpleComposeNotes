package com.crushtech.simplecomposenotes.data.mapper

import com.crushtech.simplecomposenotes.data.local.NoteEntity
import com.crushtech.simplecomposenotes.data.remote.dto.NoteDto
import com.crushtech.simplecomposenotes.domain.model.Note

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        title = title,
        content = content,
        date = date,
        color = color,
        id = id
    )
}

fun NoteEntity.toNote(): Note {
    return Note(
        title = title,
        content = content,
        date = date,
        color = color,
        id = id
    )
}

fun NoteEntity.toNoteDto(): NoteDto {
    return NoteDto(
        title = title,
        content = content,
        date = date,
        color = color,
        id = id
    )
}

fun NoteDto.toNoteEntity(): NoteEntity {
    return NoteEntity(
        title = title,
        content = content,
        date = date,
        color = color,
        id = id
    )
}
