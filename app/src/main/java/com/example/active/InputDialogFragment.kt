package com.example.active

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.*

class InputDialogFragment:DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view=activity?.layoutInflater?.inflate(R.layout.new_dailog_student,null)
        val numEditText=view?.findViewById(R.id.de_num_std) as EditText
        val nameEditText=view?.findViewById(R.id.de_name_std) as EditText
        val passCheckBox=view?.findViewById(R.id.student_pass) as CheckBox


        return AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(view)
            .setPositiveButton("Add"){ dialog,_ ->
                val std=Student(UUID.randomUUID(),numEditText.text.toString().toInt(),
                    nameEditText.text.toString(),
                    passCheckBox.isChecked)
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).onStudentAdded(std)
                }
            }.setNegativeButton("Cancel"){dialog,_ ->
                dialog.cancel()
            }.create()

    }

    interface Callbacks {
        fun onStudentAdded(student: Student)
        fun onStudentDelete(student: Student)
    }
}