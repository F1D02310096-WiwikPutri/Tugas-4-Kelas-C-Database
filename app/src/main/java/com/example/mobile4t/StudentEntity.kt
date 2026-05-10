package com.example.mobile4t

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")

data class StudentEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,

    val nim: String,

    val prodi: String,

    val email: String,

    val semester: Int
)