package com.sagar.ewudirectory.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun FacultyCard(
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
                .padding(vertical = 8.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Faculty Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
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
//                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Professor",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
//                Text(
//                    text = phoneNumber,
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
//                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
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