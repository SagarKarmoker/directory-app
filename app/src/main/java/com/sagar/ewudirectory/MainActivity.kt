package com.sagar.ewudirectory

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sagar.ewudirectory.ui.theme.EWUDirectoryTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EWUDirectoryTheme {
                var selectedTab by remember { mutableIntStateOf(0) } // Manage tab selection
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        var query by remember { mutableStateOf("") }
                        var active by remember { mutableStateOf(false) }

                        SearchBar(
                            query = query,
                            placeholder = {
                                Text(text = "Enter Name")
                            },
                            onQueryChange = { newQuery ->
                                query = newQuery // Update the search query
                            },
                            onSearch = {
                                // Perform the search action here when the search is triggered
                                println("Search triggered with query: $query")
                            },
                            active = active,
                            onActiveChange = { newActiveState ->
                                active =
                                    newActiveState // Update the active state (open/close search)
                            },
                            leadingIcon = {
                                IconButton(
                                    onClick = { navController.popBackStack() }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "Back"
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth(1f)
                                .padding(bottom = 10.dp)
                        ) {
                            // Optional: Provide search suggestions or other content here when active
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Suggested Item 1")
                                Text("Suggested Item 2")
                                Text("Suggested Item 3")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(selectedTab = selectedTab) { tab ->
                            selectedTab = tab
                            // Navigate to the appropriate screen based on the selected tab
                            when (tab) {
                                0 -> navController.navigate("home") {
                                    launchSingleTop = true
                                } // Ensure navigating to home only once
                                1 -> navController.navigate("home") { launchSingleTop = true }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            when (selectedTab) {
                                0 -> FavoritesScreen(innerPadding, navController)
                                1 -> ContactsScreen(innerPadding, navController)
                            }
                        }

                        // Detail screen route
                        composable(
                            "facultyDetail/{name}/{department}/{email}/{phoneNumber}",
                            arguments = listOf(
                                navArgument("name") { type = NavType.StringType },
                                navArgument("department") { type = NavType.StringType },
                                navArgument("email") { type = NavType.StringType },
                                navArgument("phoneNumber") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            FacultyDetailScreen(
                                name = backStackEntry.arguments?.getString("name") ?: "",
                                department = backStackEntry.arguments?.getString("department")
                                    ?: "",
                                email = backStackEntry.arguments?.getString("email") ?: "",
                                phoneNumber = backStackEntry.arguments?.getString("phoneNumber")
                                    ?: "",
                                imageResId = R.drawable.ic_launcher_background,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}

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


@Composable
fun FavoritesScreen(innerPadding: PaddingValues, navController: NavController) {
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
                imageResId = R.drawable.ic_launcher_background,
                navController = navController
            )
        }
    }
}

@Composable
fun ContactsScreen(innerPadding: PaddingValues, navController: NavController) {
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
                imageResId = R.drawable.ic_launcher_background,
                navController = navController
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
    imageResId: Int,
    navController: NavController
) {
    val context = LocalContext.current // Access context to start activities

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable {
                navController.navigate("facultyDetail/$name/$department/$email/$phoneNumber")
            }, // Navigate to detail screen when card is clicked
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Faculty Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = department,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Text(
                    text = phoneNumber,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = {
                    val intent =
                        Intent(Intent.ACTION_DIAL, android.net.Uri.parse("tel:$phoneNumber"))
                    context.startActivity(intent)
                }) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "Call Faculty",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(onClick = {
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = android.net.Uri.parse("mailto:$email")
                    }
                    context.startActivity(emailIntent)
                }) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email Faculty",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacultyDetailScreen(
    name: String,
    department: String,
    email: String,
    phoneNumber: String,
    imageResId: Int,
    onBackClick: () -> Unit // Callback to handle back button click
) {
    Scaffold(
        topBar = {
            // Top AppBar with back button and title
            TopAppBar(
                title = {
                    Text(
                        text = "Faculty Details",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp), // Add padding for content
            horizontalAlignment = Alignment.CenterHorizontally // Center content horizontally
        ) {
            // Faculty image with modern styling
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Faculty Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp)) // Rounded corners for a modern look
            )

            Spacer(modifier = Modifier.height(24.dp)) // Spacing between the image and details

            // Faculty name with large title style
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Department details with softer color
            Text(
                text = department,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Email section with a clickable row (for future email intent)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Phone section with a clickable row (for future call intent)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Phone",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = phoneNumber,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun FacultyDetailScreenPreview(){
//    FacultyDetailScreen(
//        name = "Dr. John Doe",
//        department = "Computer Science",
//        email = "johndoe@university.edu",
//        phoneNumber = "+123456789",
//        imageResId = R.drawable.ic_launcher_background
//    )
//}
