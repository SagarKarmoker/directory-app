package com.sagar.ewudirectory

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sagar.ewudirectory.ui.theme.EWUDirectoryTheme
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EWUDirectoryTheme {
                var selectedTab by remember { mutableStateOf(0) } // Manage tab selection

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(selectedTab = selectedTab) { tab ->
                            selectedTab = tab
                        }
                    }
                ) { innerPadding ->
                    when (selectedTab) {
                        0 -> FavoritesScreen(innerPadding) // Show the Favorites screen
                        1 -> ContactsScreen(innerPadding)  // Show the Contacts screen
                    }
                }
            }
        }
    }
}

sealed class BottomNavigationTab(val index: Int, val title: String, val icon: ImageVector) {object Favorites : BottomNavigationTab(0, "Favorites", Icons.Default.Favorite)
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


@Composable
fun FavoritesScreen(innerPadding: PaddingValues) {
    // Dummy list of favorite faculty members
    LazyColumn(
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(2) {
            Faculty(
                name = "Dr. Jane Smith",
                department = "Physics",
                email = "janesmith@university.edu",
                phoneNumber = "+987654321",
                imageResId = R.drawable.ic_launcher_background
            )
        }
    }
}

@Composable
fun ContactsScreen(innerPadding: PaddingValues) {
    // List of all faculty members
    LazyColumn(
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(10) {
            Faculty(
                name = "Dr. John Doe",
                department = "Computer Science",
                email = "johndoe@university.edu",
                phoneNumber = "+123456789",
                imageResId = R.drawable.ic_launcher_background
            )
        }
    }
}


@Composable
fun Faculty(
    name: String,
    department: String,
    email: String,
    phoneNumber: String,
    imageResId: Int
) {
    val context = LocalContext.current // Access context to start activities

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable { },
        shape = RoundedCornerShape(12.dp), // Modern rounded corners

    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically, // Align contents vertically centered
            horizontalArrangement = Arrangement.SpaceBetween // Ensure spacing between content
        ) {
            // Avatar (Image) with rounded corners
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Faculty Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp) // Size of the avatar
                    .clip(RoundedCornerShape(12.dp)) // Rounded avatar corners
            )

            Spacer(modifier = Modifier.width(16.dp)) // Space between avatar and details

            // Column for faculty details
            Column(
                modifier = Modifier.weight(1f) // Allow text column to take remaining space
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge, // Using titleLarge for the name
                    color = MaterialTheme.colorScheme.primary // Primary color for name text
                )
                Text(
                    text = department,
                    style = MaterialTheme.typography.bodyMedium, // Department text
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f) // Softer text color for department
                )
                Spacer(modifier = Modifier.height(4.dp)) // Spacing between department and email
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium, // Email text
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f) // Softer text color for email
                )
                Text(
                    text = phoneNumber,
                    style = MaterialTheme.typography.bodyMedium, // Phone number text
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f) // Softer text color for phone number
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Space between details and buttons

            // Column for call and email buttons, stacked vertically
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Center buttons horizontally
                verticalArrangement = Arrangement.Center
            ) {
                // Call Button
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_DIAL, android.net.Uri.parse("tel:$phoneNumber"))
                    context.startActivity(intent)
                }) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call Faculty",
                        tint = MaterialTheme.colorScheme.primary // Use primary color for icon
                    )
                }

                // Email Button
                IconButton(onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = android.net.Uri.parse("mailto:$email")
                    }
                    context.startActivity(emailIntent)
                }) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email Faculty",
                        tint = MaterialTheme.colorScheme.primary // Use primary color for icon
                    )
                }
            }
        }
    }
}