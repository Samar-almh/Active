package com.example.active

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Student (@PrimaryKey val id : UUID = UUID.randomUUID(), var num:Int=0,
                    var name: String="",
                    var pass:Boolean=false
)