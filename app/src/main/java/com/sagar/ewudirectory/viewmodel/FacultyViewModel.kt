package com.sagar.ewudirectory.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sagar.ewudirectory.data.Faculty

class FacultyViewModel : ViewModel() {
    // State for the list of faculty
    private val _facultyList = MutableLiveData<List<Faculty>>()
    val facultyList: LiveData<List<Faculty>> = _facultyList

    // State for selected tab
    private val _selectedTab = MutableLiveData(0)
    val selectedTab: LiveData<Int> = _selectedTab

    // Search query state
    private val _query = MutableLiveData("")
    val query: LiveData<String> = _query

    // search bar state
    private val _isSearchBarActive = MutableLiveData(false)
    val isSearchBarActive: LiveData<Boolean> = _isSearchBarActive

    init {
        // Initialize with some sample faculty data
        loadFaculty()
    }

    // Function to load faculty data (could come from a repository)
    private fun loadFaculty() {
        val sampleData = listOf(
            Faculty(1, "Dr. John Doe", "Computer Science", "johndoe@university.edu", "+123456789", "url1"),
            Faculty(2, "Dr. Jane Smith", "Physics", "janesmith@university.edu", "+987654321", "url2"),
            Faculty(3, "Dr. Michael Brown", "Mathematics", "michaelbrown@university.edu", "+234567890", "url3"),
            Faculty(4, "Dr. Emily Davis", "Biology", "emilydavis@university.edu", "+345678901", "url4"),
            Faculty(5, "Dr. William Wilson", "Chemistry", "williamwilson@university.edu", "+456789012", "url5"),
            Faculty(6, "Dr. Sophia Martinez", "Economics", "sophiamartinez@university.edu", "+567890123", "url6"),
            Faculty(7, "Dr. James Anderson", "History", "jamesanderson@university.edu", "+678901234", "url7"),
            Faculty(8, "Dr. Olivia Thompson", "Philosophy", "oliviathompson@university.edu", "+789012345", "url8"),
            Faculty(9, "Dr. Liam Garcia", "Engineering", "liamgarcia@university.edu", "+890123456", "url9"),
            Faculty(10, "Dr. Ava Johnson", "Psychology", "avajohnson@university.edu", "+901234567", "url10")
        )

        _facultyList.value = sampleData
    }

    // Function to update the search query
    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    // Function to change the selected tab
    fun selectTab(index: Int) {
        _selectedTab.value = index
    }

    fun toggleSearchBar() {
        _isSearchBarActive.value = !(_isSearchBarActive.value ?: false)
    }
}
