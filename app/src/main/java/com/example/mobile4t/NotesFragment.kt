package com.example.mobile4t

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class NotesFragment :
    Fragment(R.layout.fragment_notes) {

    private lateinit var recyclerView:
            RecyclerView

    private lateinit var studentAdapter:
            StudentAdapter

    private lateinit var appDatabase:
            AppDatabase

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        appDatabase =
            AppDatabase.getDatabase(
                requireContext()
            )

        recyclerView =
            view.findViewById(
                R.id.recyclerStudents
            )

        val fabAdd =
            view.findViewById<
                    FloatingActionButton
                    >(R.id.fabAdd)

        recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext()
            )

        setupRecyclerView()

        fabAdd.setOnClickListener {

            startActivity(

                Intent(

                    requireContext(),

                    AddEditStudentActivity::class.java
                )
            )
        }

        loadStudentData()
    }

    private fun setupRecyclerView() {

        studentAdapter = StudentAdapter(

            studentList = emptyList(),

            isAdminMode = true,

            onEdit = { student ->

                moveToEdit(student)
            },

            onDelete = { student ->

                showDeleteDialog(student)
            }
        )

        recyclerView.adapter =
            studentAdapter
    }

    private fun loadStudentData() {

        lifecycleScope.launch {

            val studentList =

                appDatabase
                    .studentDao()
                    .getAllStudents()

            studentAdapter.updateData(
                studentList
            )
        }
    }

    private fun moveToEdit(
        student: StudentEntity
    ) {

        val intent = Intent(

            requireContext(),

            AddEditStudentActivity::class.java
        )

        intent.putExtra(
            "id",
            student.id
        )

        intent.putExtra(
            "name",
            student.name
        )

        intent.putExtra(
            "nim",
            student.nim
        )

        intent.putExtra(
            "prodi",
            student.prodi
        )

        intent.putExtra(
            "email",
            student.email
        )

        intent.putExtra(
            "semester",
            student.semester
        )

        startActivity(intent)
    }

    private fun showDeleteDialog(
        student: StudentEntity
    ) {

        AlertDialog.Builder(
            requireContext()
        )

            .setTitle(
                "Hapus Data"
            )

            .setMessage(
                "Yakin ingin menghapus ${student.name}?"
            )

            .setPositiveButton(
                "Hapus"
            ) { _, _ ->

                deleteStudent(student)
            }

            .setNegativeButton(
                "Batal",
                null
            )

            .show()
    }

    private fun deleteStudent(
        student: StudentEntity
    ) {

        lifecycleScope.launch {

            appDatabase
                .studentDao()
                .deleteById(student.id)

            loadStudentData()
        }
    }

    override fun onResume() {

        super.onResume()

        loadStudentData()
    }
}