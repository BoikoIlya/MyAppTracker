package com.ilya.myapptracker.data.local.sharedpref

import android.content.Context
import com.ilya.myapptracker.utils.Constants
import javax.inject.Inject

class SharedPrefImpl @Inject constructor(
    private val context: Context
): SharedPref {

    private val sharedPref = context.getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    override fun putWeight(weight: Float){
        editor.putFloat(Constants.SHARED_PREF_KEY, weight).apply()
    }

    override fun getWeight(): Float{
       return sharedPref.getFloat(Constants.SHARED_PREF_KEY,Constants.SHARED_PREF_DEF_VALUE)
    }
}