package com.sagar.ewudirectory.repository

import com.sagar.ewudirectory.data.Faculty

class FacultyRepository {
    private val facultyList = listOf(
        Faculty(
            id = 1,
            name = "Sagar karmoker",
            department = "CSE",
            email = "sagar@gmail.com",
            phoneNumber = "9876543210",
            photoUrl = "https://example.com/sagar.jpg"
        ),
        Faculty(
            id = 2,
            name = "Sagar karmoker",
            department = "CSE",
            email = "sagar@gmail.com",
            phoneNumber = "9876543210",
            photoUrl = "https://example.com/sagar.jpg"
        )
    )

    fun getFaculties(): List<Faculty> {
        return facultyList
    }
}
