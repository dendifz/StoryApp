package com.dendi.storyapp.utils

import android.text.TextUtils

fun CharSequence.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun CharSequence.isPasswordValid(): Boolean {
    return !TextUtils.isEmpty(this) && this.length < 6
}


