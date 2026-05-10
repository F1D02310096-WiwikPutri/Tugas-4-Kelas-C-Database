package com.example.mobile4t

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class DetailActivity :
    AppCompatActivity() {

    private lateinit var etNote:
            EditText

    private lateinit var tvStatus:
            TextView

    private var studentNim =
        "default"

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_detail
        )

        initializeView()

        loadStudentData()

        setupButton()
    }

    private fun initializeView() {

        etNote =
            findViewById(R.id.etNote)

        tvStatus =
            findViewById(R.id.tvStatus)

        val btnBack =
            findViewById<ImageView>(
                R.id.btnBack
            )

        btnBack.setOnClickListener {

            finish()
        }
    }

    private fun loadStudentData() {

        val tvName =
            findViewById<TextView>(
                R.id.tvName
            )

        val tvInfo =
            findViewById<TextView>(
                R.id.tvInfo
            )

        val tvAvatar =
            findViewById<TextView>(
                R.id.tvAvatar
            )

        val studentName =
            intent.getStringExtra("name")
                ?: ""

        val studentProdi =
            intent.getStringExtra("prodi")
                ?: ""

        studentNim =
            intent.getStringExtra("nim")
                ?: "default"

        tvName.text =
            studentName

        tvInfo.text =
            "$studentNim - $studentProdi"

        tvAvatar.text =
            createInitial(studentName)

        checkNoteStatus()
    }

    private fun setupButton() {

        val btnSave =
            findViewById<Button>(
                R.id.btnSave
            )

        val btnLoad =
            findViewById<Button>(
                R.id.btnLoad
            )

        btnSave.setOnClickListener {

            saveNote()
        }

        btnLoad.setOnClickListener {

            loadNote()
        }
    }

    private fun saveNote() {

        try {

            val file = File(

                filesDir,

                "$studentNim.txt"
            )

            file.writeText(

                etNote.text.toString()
            )

            Toast.makeText(

                this,

                "Catatan berhasil disimpan",

                Toast.LENGTH_SHORT

            ).show()

            checkNoteStatus()

        } catch (e: Exception) {

            Toast.makeText(

                this,

                "Gagal menyimpan",

                Toast.LENGTH_SHORT

            ).show()
        }
    }

    private fun loadNote() {

        try {

            val file = File(

                filesDir,

                "$studentNim.txt"
            )

            if (file.exists()) {

                etNote.setText(
                    file.readText()
                )

                tvStatus.text =
                    "✔ Catatan dimuat"

            } else {

                tvStatus.text =
                    "Belum ada catatan"
            }

        } catch (e: Exception) {

            Toast.makeText(

                this,

                "Gagal memuat",

                Toast.LENGTH_SHORT

            ).show()
        }
    }

    private fun checkNoteStatus() {

        val file = File(

            filesDir,

            "$studentNim.txt"
        )

        if (file.exists()) {

            tvStatus.text =
                "✔ Tersimpan"

        } else {

            tvStatus.text =
                "Belum ada catatan"
        }
    }

    private fun createInitial(
        name: String
    ): String {

        return name

            .trim()

            .split(" ")

            .take(2)

            .joinToString("") {

                it.first()
                    .uppercase()
            }
    }
}