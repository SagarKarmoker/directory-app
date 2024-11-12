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
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sagar.ewudirectory.data.Faculty
import com.sagar.ewudirectory.navigation.BottomNavigationBar
import com.sagar.ewudirectory.ui.screens.ContactsScreen
import com.sagar.ewudirectory.ui.screens.FacultyDetailScreen
import com.sagar.ewudirectory.ui.screens.FavoritesScreen
import com.sagar.ewudirectory.ui.theme.EWUDirectoryTheme
import com.sagar.ewudirectory.viewmodel.FacultyViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EWUDirectoryTheme {
                // Get a ViewModel instance
                val viewModel: FacultyViewModel = viewModel()

                // Observe state from the ViewModel
                val selectedTab by viewModel.selectedTab.observeAsState(0)
                val query by viewModel.query.observeAsState("")
                val facultyList by viewModel.facultyList.observeAsState(emptyList())
                val isSearchBarOpen by viewModel.isSearchBarActive.observeAsState()

                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        isSearchBarOpen?.let {
                            SearchBarWithQuery(query, onQueryChange = { viewModel.updateQuery(it) },
                                it
                            )
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigationBar(selectedTab) { tab ->
                            viewModel.selectTab(tab)
                            // Navigate to the appropriate screen based on the selected tab
                            when (tab) {
                                0 -> navController.navigate("home") { launchSingleTop = true }
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
                                0 -> FavoritesScreen(innerPadding, navController, facultyList)
                                1 -> ContactsScreen(innerPadding, navController, facultyList)
                            }
                        }

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
                                department = backStackEntry.arguments?.getString("department") ?: "",
                                email = backStackEntry.arguments?.getString("email") ?: "",
                                phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: "",
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWithQuery(query: String, onQueryChange: (String) -> Unit, isSearchBarOpen: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 8.dp)
    ) {
        SearchBar(
            query = query,
            placeholder = { Text(text = "Enter Name") },
            onQueryChange = onQueryChange,
            onSearch = {
                println("Search triggered with query: $query")
            },
            active = isSearchBarOpen,
            onActiveChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search Icon"
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            // Optional: Provide search suggestions or other content here when active
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Suggested Item 1")
                Text("Suggested Item 2")
                Text("Suggested Item 3")
            }
        }
    }
}
