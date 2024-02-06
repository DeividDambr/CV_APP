package com.example.nd1cv.data

import com.example.nd1cv.R

class DataSource {
    fun loadPhotos(): List<Photos>{
        return listOf(
            Photos(R.string.description1, R.drawable.photo1) ,
            Photos(R.string.description2, R.drawable.photo2),
            Photos(R.string.description3, R.drawable.photo3),
            Photos(R.string.description4, R.drawable.photo4),
            Photos(R.string.description5, R.drawable.photo5),
            Photos(R.string.description6, R.drawable.photo6),
            Photos(R.string.description7, R.drawable.photo7),
            Photos(R.string.description8, R.drawable.photo8),
            Photos(R.string.description9, R.drawable.photo9),
        )
    }

    fun loadSkills(): List<Skills>{
        return listOf(
            Skills(R.string.skill1),
            Skills(R.string.skill2),
            Skills(R.string.skill3),
            Skills(R.string.skill4),
            Skills(R.string.skill5),
            Skills(R.string.skill6),
            Skills(R.string.skill7),
            Skills(R.string.skill8)
        )
    }

    fun loadAdditionalSkills(): List<AddSkills>{
        return listOf(
            AddSkills(R.string.add_skill1),
            AddSkills(R.string.add_skill2),
            AddSkills(R.string.add_skill3),
            AddSkills(R.string.add_skill4),
            AddSkills(R.string.add_skill5),
            AddSkills(R.string.add_skill6),
            AddSkills(R.string.add_skill7)
        )
    }
}