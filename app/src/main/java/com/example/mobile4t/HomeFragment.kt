package com.example.mobile4t

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class HomeFragment :
    Fragment(R.layout.fragment_home) {

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
                R.id.recyclerView
            )

        val tvWelcome =
            view.findViewById<TextView>(
                R.id.tvWelcome
            )

        tvWelcome.text =
            "Welcome 👋"

        setupRecyclerView()

        loadStudentData()
    }

    private fun setupRecyclerView() {

        studentAdapter =
            StudentAdapter(

                studentList = emptyList(),

                isAdminMode = false
            )

        recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext()
            )

        recyclerView.adapter =
            studentAdapter
    }

    private fun loadStudentData() {

        lifecycleScope.launch {

            insertDummyData()

            val studentList =

                appDatabase
                    .studentDao()
                    .getAllStudents()

            studentAdapter.updateData(
                studentList
            )
        }
    }

    private suspend fun insertDummyData() {

        val totalData =

            appDatabase
                .studentDao()
                .getStudentCount()

        if (totalData == 0) {

            val dummyList = listOf(

                StudentEntity(
                    name = "Ahmad Fauzi",
                    nim = "220001",
                    prodi = "Teknik Informatika",
                    email = "ahmad@gmail.com",
                    semester = 5
                ),

                StudentEntity(
                    name = "Budi Santoso",
                    nim = "220002",
                    prodi = "Sistem Informasi",
                    email = "budi@gmail.com",
                    semester = 3
                )
            )

            appDatabase
                .studentDao()
                .insertAll(dummyList)
        }
    }

    override fun onResume() {

        super.onResume()

        loadStudentData()
    }
}