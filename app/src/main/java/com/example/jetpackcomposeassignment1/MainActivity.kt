package com.example.jetpackcomposeassignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposeassignment1.ui.theme.JetpackComposeAssignment1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAssignment1Theme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CourseListScreen(courses = sampleCourses)
                }
            }
        }
    }
}

data class Course(
    val title: String,
    val code: String,
    val creditHours: Int,
    val description: String,
    val prerequisites: String
)

val sampleCourses = listOf(
    Course("Calculus I", "MATH101", 3, "Introduction to calculus concepts and techniques.", "None"),
    Course("Introduction to Programming", "CS101", 4, "Learn the basics of programming using Kotlin.", "None"),
    Course("Data Structures", "CS201", 3, "In-depth study of stacks, queues, trees, and graphs.", "CS101"),
    Course("Discrete Mathematics", "MATH102", 3, "Explore mathematical structures and proofs.", "None"),
    Course("Operating Systems", "CS301", 4, "Study the design and implementation of modern operating systems.", "CS201"),
    Course("Mobile App Development", "CS350", 3, "Build modern Android apps using Jetpack Compose and Kotlin.", "CS201")
)

@Composable
fun CourseCard(course: Course) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val backgroundColor by animateColorAsState(
        targetValue = if (isExpanded) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer,
        animationSpec = tween(durationMillis = 400),
        label = "BackgroundColor"
    )

    val textColor = if (isExpanded) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer

    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "IconRotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded }
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = course.title,
                style = MaterialTheme.typography.headlineSmall,
                color = textColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = course.description.take(60) + if (course.description.length > 60) "..." else "",
                style = MaterialTheme.typography.bodySmall,
                color = textColor.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Code: ${course.code}", style = MaterialTheme.typography.bodyMedium, color = textColor)
                    Text("Credits: ${course.creditHours}", style = MaterialTheme.typography.bodyMedium, color = textColor)
                }

                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Expand/Collapse",
                    modifier = Modifier
                        .rotate(rotationAngle)
                        .padding(4.dp),
                    tint = textColor
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Description: ${course.description}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Prerequisites: ${course.prerequisites}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }
        }
    }
}

@Composable
fun CourseListScreen(courses: List<Course>) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(courses) { course ->
            CourseCard(course)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeAssignment1Theme {
        CourseListScreen(courses = sampleCourses)
    }
}
