package com.sagar.ewudirectory.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sagar.ewudirectory.data.Faculty
import org.tensorflow.lite.support.label.Category

@Composable
fun ContactCategories(
    innerPadding: PaddingValues,
    navController: NavController,
    facultyList: List<Faculty>
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        color = MaterialTheme.colorScheme.background
    ) {
        val categories = listOf("Category 1", "Category 2", "Category 3", "Category 4", "Category 5", "Category 6")

        // LazyVerticalGrid for 2 columns layout
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 columns layout
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = PaddingValues(8.dp) // Padding around grid items
        ) {
            items(categories) { category ->
                // Card with modern design
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth() // Make the card fill available width (each card takes up half of the row)
                        .height(120.dp) // Fixed height for the card
                        .clip(RoundedCornerShape(16.dp)) // Rounded corners
                        .clickable {
                            // Handle category click here
                        },
                    shape = RoundedCornerShape(16.dp), // Rounded corners
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person, // Placeholder icon, use appropriate one
                                contentDescription = null,
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(bottom = 4.dp),
                                tint = MaterialTheme.colorScheme.surfaceTint
                            )
                            Text(
                                text = category,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

