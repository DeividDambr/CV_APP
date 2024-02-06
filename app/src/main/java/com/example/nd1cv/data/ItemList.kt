package com.example.nd1cv.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Photos(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)

data class Skills(
    @StringRes val stringResourceId: Int
)

data class AddSkills(
    @StringRes val stringResourceId: Int
)
