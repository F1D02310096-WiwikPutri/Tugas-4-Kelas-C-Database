package com.example.mobile4t

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {

    @Insert(
        onConflict =
            OnConflictStrategy.REPLACE
    )

    suspend fun insert(
        student: StudentEntity
    )

    @Insert(
        onConflict =
            OnConflictStrategy.REPLACE
    )

    suspend fun insertAll(
        students: List<StudentEntity>
    )

    @Query(
        "SELECT * FROM students ORDER BY id DESC"
    )

    suspend fun getAllStudents():
            List<StudentEntity>

    @Update
    suspend fun update(
        student: StudentEntity
    )

    @Query(
        "DELETE FROM students WHERE id = :id"
    )

    suspend fun deleteById(
        id: Int
    )

    @Query(
        "SELECT COUNT(*) FROM students"
    )

    suspend fun getStudentCount():
            Int
}