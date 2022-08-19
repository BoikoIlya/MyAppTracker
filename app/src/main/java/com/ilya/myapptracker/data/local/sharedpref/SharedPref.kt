package com.ilya.myapptracker.data.local.sharedpref

interface SharedPref {

    fun putWeight(weight: Float)

    fun getWeight(): Float
}