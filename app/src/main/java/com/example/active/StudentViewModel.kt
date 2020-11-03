package com.example.active

import androidx.lifecycle.ViewModel

class StudentViewModel:ViewModel() {

   // val students= mutableListOf<Student>()
   // init {
     //   for (i in 0 until 20) {
      //      val student=Student()

       //     student.name= "Student"+ i
       //     student.num=i
       //     student.pass=i % 2 ==0
      //      students+=student
      //  }
   //
        private val studentRepositary = StudentRepositary.get()
       val  studentListLiveData = studentRepositary.getStudents()

    fun addStudent(student: Student){
        studentRepositary.addStudent(student)

    }

    fun deleteStudent(student: Student){
        studentRepositary.deleteStudent(student)

    }

    fun updateStudent(student: Student){
        studentRepositary.updateStudent(student)

    }

}