package com.sagar.ewudirectory.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigationTab(val index: Int, val title: String, val icon: ImageVector) {
    object Favorites : BottomNavigationTab(0, "Favorites", Icons.Default.Favorite)
    object Contacts : BottomNavigationTab(1, "Contacts", Icons.Default.Person)
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorites"
                )
            },
            label = { Text("Favorites") },
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Contacts"
                )
            },
            label = { Text("Contacts") },
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) }
        )
    }
}






