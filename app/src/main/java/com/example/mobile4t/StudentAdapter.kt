package com.example.mobile4t

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(

    private var studentList:
    List<StudentEntity>,

    private val isAdminMode:
    Boolean,

    private val onEdit:
    ((StudentEntity) -> Unit)? = null,

    private val onDelete:
    ((StudentEntity) -> Unit)? = null

) : RecyclerView.Adapter<
        StudentAdapter.StudentViewHolder>() {

    inner class StudentViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val cardStudent:
                CardView =
            itemView.findViewById(
                R.id.cardStudent
            )

        val tvAvatar:
                TextView =
            itemView.findViewById(
                R.id.tvAvatar
            )

        val tvName:
                TextView =
            itemView.findViewById(
                R.id.tvName
            )

        val tvNim:
                TextView =
            itemView.findViewById(
                R.id.tvNim
            )

        val btnEdit:
                TextView =
            itemView.findViewById(
                R.id.btnEdit
            )

        val btnDelete:
                TextView =
            itemView.findViewById(
                R.id.btnDel
            )

        val layoutAction:
                View =
            itemView.findViewById(
                R.id.layoutAction
            )
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StudentViewHolder {

        val view = LayoutInflater
            .from(parent.context)

            .inflate(
                R.layout.item_student,
                parent,
                false
            )

        return StudentViewHolder(view)
    }

    override fun getItemCount():
            Int = studentList.size

    override fun onBindViewHolder(
        holder: StudentViewHolder,
        position: Int
    ) {

        val student =
            studentList[position]

        holder.tvName.text =
            student.name

        holder.tvNim.text =
            student.nim

        holder.tvAvatar.text =
            generateInitial(student.name)

        setupAvatarColor(
            holder,
            position
        )

        setupActionButton(
            holder,
            student
        )

        holder.itemView
            .setOnClickListener {

                moveToDetail(
                    holder,
                    student
                )
            }
    }

    private fun setupAvatarColor(
        holder: StudentViewHolder,
        position: Int
    ) {

        val colorList = listOf(

            "#3F51B5",

            "#009688",

            "#FF9800",

            "#E91E63"
        )

        val selectedColor =
            Color.parseColor(

                colorList[
                    position %
                            colorList.size
                ]
            )

        val background =
            holder.tvAvatar.background
                    as GradientDrawable

        background.setColor(
            selectedColor
        )
    }

    private fun setupActionButton(
        holder: StudentViewHolder,
        student: StudentEntity
    ) {

        if (isAdminMode) {

            holder.layoutAction.visibility =
                View.VISIBLE

            holder.btnEdit
                .setOnClickListener {

                    onEdit?.invoke(student)
                }

            holder.btnDelete
                .setOnClickListener {

                    onDelete?.invoke(student)
                }

        } else {

            holder.layoutAction.visibility =
                View.GONE
        }
    }

    private fun moveToDetail(
        holder: StudentViewHolder,
        student: StudentEntity
    ) {

        val intent = Intent(

            holder.itemView.context,

            DetailActivity::class.java
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

        holder.itemView.context
            .startActivity(intent)
    }

    private fun generateInitial(
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

    fun updateData(
        newStudentList:
        List<StudentEntity>
    ) {

        studentList =
            newStudentList

        notifyDataSetChanged()
    }
}