package com.ilya.myapptracker.domain.repositiry

interface SharedPrefRepository {

    fun getDataFromSharedPref(): Float

    fun putDataInSharedPref(weight: Float)
}