package com.sixtsystems.testapp.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

val Fragment.appCompactActivity
    get() = requireActivity() as AppCompatActivity

val Fragment.supportActionBar
    get() = appCompactActivity.supportActionBar
