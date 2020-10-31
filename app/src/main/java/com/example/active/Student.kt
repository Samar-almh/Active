package com.example.active

import java.util.*


data class Student (val id : UUID = UUID.randomUUID(),
                    var num:Int=0,
                    var name: String="",
                    var pass:Boolean=false
)