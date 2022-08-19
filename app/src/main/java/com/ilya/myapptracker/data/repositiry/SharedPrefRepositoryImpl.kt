package com.ilya.myapptracker.data.repositiry

import com.ilya.myapptracker.data.local.sharedpref.SharedPref
import com.ilya.myapptracker.domain.repositiry.SharedPrefRepository
import javax.inject.Inject

class SharedPrefRepositoryImpl @Inject constructor(
    private val sharedPref: SharedPref
):SharedPrefRepository {

    override fun getDataFromSharedPref(): Float {
       return sharedPref.getWeight()
    }

    override fun putDataInSharedPref(weight: Float) {
        sharedPref.putWeight(weight)
    }
}