package com.depromeet.knockknock.util

import java.util.*

fun randomNum() : Int {
    val random = Random()
    return random.nextInt(5-1)+1
}