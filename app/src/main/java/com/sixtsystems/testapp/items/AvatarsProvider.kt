package com.sixtsystems.testapp.items

import com.sixtsystems.testapp.R

interface AvatarsProvider {
    fun getAvatars(): List<Int>
}

class DefaultAvatarsProvider : AvatarsProvider {
    private val avatars = arrayListOf(
        R.drawable.avatar1,
        R.drawable.avatar2,
        R.drawable.avatar3,
        R.drawable.avatar4
    )

    override fun getAvatars(): List<Int> {
        return avatars
    }
}
