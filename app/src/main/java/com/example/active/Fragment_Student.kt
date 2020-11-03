package com.example.active

import android.app.ProgressDialog.show
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Fragment_Student: Fragment(),InputDialogFragment.Callbacks  {


    private val studentListViewModel: StudentViewModel by lazy {
        ViewModelProviders.of(this).get(StudentViewModel::class.java)
    }
    companion object {
        fun newInstance():Fragment_Student {
            return Fragment_Student()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    private var adapter: StudentAdapter? = StudentAdapter(emptyList())
    private lateinit var noDataTextView: TextView
    private lateinit var addStuButton: Button
    private lateinit var studentRecyclerView: RecyclerView


   override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.newStudent -> {
                val student = Student()

                InputDialogFragment().apply{
                    setTargetFragment(this@Fragment_Student,0)
                    show(this@Fragment_Student.requireFragmentManager(),"Input")
                }
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?    ): View? {
        //  val view = inflater.inflate(R.layout.fragment_student, container, false)
        // clickButton = view.findViewById(R.id.click) as Button
        val view = inflater.inflate(R.layout.fragment__list, container, false)
        studentRecyclerView = view.findViewById(R.id.student_recycler_view) as RecyclerView
        noDataTextView = view.findViewById(R.id.empty_list_textview) as TextView
        addStuButton = view.findViewById(R.id.addStdBtn) as Button
        studentRecyclerView.layoutManager = LinearLayoutManager(context)
        studentRecyclerView.adapter = adapter
        //updateView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentListViewModel.studentListLiveData.observe(
            viewLifecycleOwner,
            Observer { students ->
                students?.let {
                    Log.i(TAG, "Got student ${students.size}")
                    updateView(students)
                }
            })
        addStuButton.setOnClickListener {
            InputDialogFragment().apply{
                setTargetFragment(this@Fragment_Student,0)
                show(this@Fragment_Student.requireFragmentManager(),"Input")
            }
        }
    }


        private fun updateView(students: List<Student>) {
        adapter = StudentAdapter(students)
        studentRecyclerView.adapter = adapter



    }

    private inner class StudentHolder(view: View)
        : RecyclerView.ViewHolder(view) , View.OnClickListener{
        private lateinit var student: Student

        val st_NameTextView: TextView = itemView.findViewById(R.id.student_name)
        val st_NumTextView: TextView = itemView.findViewById(R.id.stud_num)
        val st_passTextView: TextView = itemView.findViewById(R.id.stud_pass)
        val deleteButton = itemView.findViewById(R.id.delete_btn) as Button
        init {
            deleteButton.setOnClickListener(this)
        }
        fun bind(student:Student ) {
            this.student = student
            st_NameTextView.text = this.student.name
            st_NumTextView.text="Number: "+this.student.num.toString()
            st_passTextView.text="Pass:  "+this.student.pass.toString()
        }


        override fun onClick(p0: View?) {
            Toast.makeText(context, "${student.name}!", Toast.LENGTH_SHORT)
                .show()
            onStudentDelete(student)

        }
    }



    private inner class StudentAdapter(var students: List<Student>)
        : RecyclerView.Adapter<StudentHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
            val view = layoutInflater.inflate(R.layout.list_item_student, parent, false)
            return StudentHolder(view)
        }

        override fun onBindViewHolder(holder: StudentHolder, position: Int) {
            val student=students[position]
            holder.apply {
                holder.bind(student)
            }}
        override fun getItemCount(): Int{
            if (students.isNotEmpty()) {
                noDataTextView.visibility = View.GONE
                addStuButton.visibility = View.GONE
            } else {

                noDataTextView.visibility = View.VISIBLE
                addStuButton.visibility = View.VISIBLE
            }
            return students.size
        }

    }



    override fun onStart() {
        super.onStart()

    }

     override fun onStudentAdded(student: Student) {
        studentListViewModel.addStudent(student)

    }

    override fun onStudentDelete(student: Student) {
        studentListViewModel.deleteStudent(student)
    }



}



