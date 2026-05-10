package com.example.mobile4t

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddEditStudentActivity :
    AppCompatActivity() {

    private lateinit var appDatabase:
            AppDatabase

    private lateinit var etName:
            EditText

    private lateinit var etNim:
            EditText

    private lateinit var spProdi:
            Spinner

    private lateinit var etEmail:
            EditText

    private lateinit var etSemester:
            EditText

    private var studentId = 0

    private val prodiList = listOf(

        "Pilih Prodi",

        "Teknik Informatika",

        "Sistem Informasi",

        "Manajemen",

        "Akuntansi"
    )

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_add_edit_student
        )

        appDatabase =
            AppDatabase.getDatabase(this)

        initializeView()

        setupSpinner()

        getIntentData()

        setupSaveButton()
    }

    private fun initializeView() {

        etName =
            findViewById(R.id.etName)

        etNim =
            findViewById(R.id.etNim)

        spProdi =
            findViewById(R.id.spProdi)

        etEmail =
            findViewById(R.id.etEmail)

        etSemester =
            findViewById(R.id.etSemester)

        val btnBack =
            findViewById<ImageView>(R.id.btnBack)

        btnBack.setOnClickListener {

            finish()
        }
    }

    private fun setupSpinner() {

        val adapter = ArrayAdapter(

            this,

            android.R.layout.simple_spinner_item,

            prodiList
        )

        adapter.setDropDownViewResource(

            android.R.layout
                .simple_spinner_dropdown_item
        )

        spProdi.adapter = adapter
    }

    private fun getIntentData() {

        studentId =
            intent.getIntExtra("id", 0)

        val tvTitle =
            findViewById<TextView>(R.id.tvTitle)

        if (studentId != 0) {

            tvTitle.text =
                "Edit Mahasiswa"

            etName.setText(
                intent.getStringExtra("name")
            )

            etNim.setText(
                intent.getStringExtra("nim")
            )

            etEmail.setText(
                intent.getStringExtra("email")
            )

            etSemester.setText(

                intent.getIntExtra(
                    "semester",
                    1
                ).toString()
            )

            val prodi =
                intent.getStringExtra("prodi")

            val position =
                prodiList.indexOf(prodi)

            if (position >= 0) {

                spProdi.setSelection(position)
            }
        }
    }

    private fun setupSaveButton() {

        val btnSave =
            findViewById<Button>(R.id.btnSimpan)

        btnSave.setOnClickListener {

            saveStudent()
        }
    }

    private fun saveStudent() {

        val name =
            etName.text.toString().trim()

        val nim =
            etNim.text.toString().trim()

        val prodi =
            spProdi.selectedItem.toString()

        val email =
            etEmail.text.toString().trim()

        val semester =
            etSemester.text
                .toString()
                .toIntOrNull() ?: 1

        if (

            name.isEmpty() ||

            nim.isEmpty() ||

            prodi == "Pilih Prodi"

        ) {

            Toast.makeText(

                this,

                "Lengkapi semua data",

                Toast.LENGTH_SHORT

            ).show()

            return
        }

        val student = StudentEntity(

            id = studentId,

            name = name,

            nim = nim,

            prodi = prodi,

            email = email,

            semester = semester
        )

        lifecycleScope.launch {

            if (studentId == 0) {

                appDatabase
                    .studentDao()
                    .insert(student)

            } else {

                appDatabase
                    .studentDao()
                    .update(student)
            }

            Toast.makeText(

                this@AddEditStudentActivity,

                "Data berhasil disimpan",

                Toast.LENGTH_SHORT

            ).show()

            finish()
        }
    }
}