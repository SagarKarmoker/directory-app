package com.sagar.ewudirectory.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sagar.ewudirectory.R
import com.sagar.ewudirectory.data.Faculty

@Composable
fun FavoritesScreen(innerPadding: PaddingValues, navController: NavController, facultyList: List<Faculty>) {
    LazyColumn(
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(facultyList.take(2)) { faculty ->
            FacultyCard(
                name = faculty.name,
                department = faculty.department,
                email = faculty.email,
                phoneNumber = faculty.phoneNumber,
                imageResId = R.drawable.ic_launcher_background,
                navController = navController
            )
        }
    }
}