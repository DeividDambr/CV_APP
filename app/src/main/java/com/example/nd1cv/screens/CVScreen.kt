package com.example.nd1cv.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nd1cv.R
import com.example.nd1cv.data.DataSource
import com.example.nd1cv.preferences.Preferences

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CVScreen(navController: NavController) {

        val context = LocalContext.current
        val urlManager = Preferences(context)

        val textId by remember { mutableIntStateOf(urlManager.getTextId()) }

        val dataSource = DataSource()

        urlManager.saveDeletedId(-1)

        Scaffold(
                topBar = {
                        TopAppBar(
                                navController,
                                settingsOn = false
                        )
                },
                bottomBar = {
                        BottomAppBar(
                                navController = navController,
                                modifier = Modifier
                        )
                }
        ){innerPadding ->
                LazyColumn(
                        modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                ){
                        item{
                                Row(
                                        modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 36.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                ){
                                        Text(
                                                text = stringResource(id = R.string.about_me),
                                                style = MaterialTheme.typography.headlineMedium,
                                        )
                                }
                                Row(
                                        modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 36.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                ){
                                        Text(
                                                text = stringResource(id = R.string.about_me_desc),
                                                style = MaterialTheme.typography.bodyMedium,
                                                textAlign = TextAlign.Justify
                                        )
                                }

                        }
                        item {
                                Row(
                                        modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 36.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        Text(
                                                text = stringResource(id = R.string.skills),
                                                style = MaterialTheme.typography.headlineMedium,
                                        )
                                }
                        }
                        item {
                                Row(
                                        modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 36.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        LazyRow {
                                                items(dataSource.loadSkills()) { skill ->
                                                        Text(
                                                                text = stringResource(id = skill.stringResourceId),
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                modifier = Modifier.padding(8.dp)
                                                        )
                                                }
                                        }
                                }
                        }
                        item {
                                Row(
                                        modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 36.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        Text(
                                                text = stringResource(id = R.string.additional_skills),
                                                style = MaterialTheme.typography.headlineMedium,
                                        )
                                }
                        }
                        item{
                                Row(
                                        modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 36.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                ) {
                                        LazyRow {
                                                items(dataSource.loadAdditionalSkills()) { skill ->
                                                        Text(
                                                                text = stringResource(id = skill.stringResourceId),
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                modifier = Modifier.padding(8.dp)
                                                        )
                                                }
                                        }
                                }
                        }
                        item{
                                Row(
                                        modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 36.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                ){
                                        Column {
                                                Text(
                                                        text = stringResource(id = R.string.extra_entries),
                                                        style = MaterialTheme.typography.headlineMedium,
                                                )
                                        }
                                        Column(
                                                modifier = Modifier
                                                        .fillMaxWidth(),
                                                horizontalAlignment = Alignment.End
                                        ) {
                                                IconButton(onClick = {
                                                        navController.navigate(Screen.AddEntryScreen.route)
                                                }) {
                                                        Icon(
                                                                imageVector = Icons.Filled.AddCircleOutline,
                                                                contentDescription = stringResource(id = R.string.add_entry),
                                                                modifier = Modifier
                                                                        .size(40.dp),
                                                                tint = Color(0, 185, 0, 255)
                                                        )
                                                }
                                        }
                                }
                        }
                        for (i in 0 until textId) {

                                val title = urlManager.getCVParaTitle(i)
                                val text = urlManager.getCVParaText(i)

                                if (title.isNotEmpty() || text.isNotEmpty()) {
                                        item {
                                                Row {
                                                        Spacer(modifier = Modifier.height(10.dp))
                                                        Column {
                                                                Text(
                                                                        text = title,
                                                                        style = MaterialTheme.typography.headlineMedium,
                                                                )
                                                        }
                                                        Column(
                                                                modifier = Modifier
                                                                        .fillMaxWidth(),
                                                                horizontalAlignment = Alignment.End
                                                        ) {
                                                                IconButton(onClick = {
                                                                        urlManager.deleteText(i)
                                                                        urlManager.saveDeletedId(i)
                                                                        Log.d(
                                                                                "tester",
                                                                                "$i"
                                                                        )
                                                                }) {
                                                                        Icon(
                                                                                imageVector = Icons.Filled.RemoveCircleOutline,
                                                                                contentDescription = stringResource(
                                                                                        id = R.string.add_entry
                                                                                ),
                                                                                modifier = Modifier
                                                                                        .size(40.dp),
                                                                                tint = Color(
                                                                                        185,
                                                                                        0,
                                                                                        0,
                                                                                        255
                                                                                )
                                                                        )
                                                                }
                                                        }
                                                }
                                                Row(modifier = Modifier
                                                        .fillMaxWidth(),
                                                ){
                                                        Spacer(modifier = Modifier.height(6.dp))
                                                        Text(
                                                                textAlign = TextAlign.Justify,
                                                                modifier = Modifier.padding(8.dp),
                                                                text = text,
                                                                style = MaterialTheme.typography.bodyMedium
                                                        )
                                                }
                                        }
                                }
                                else{
                                        urlManager.saveDeletedId(i)
                                }
                        }
                }
        }
}

@Preview(showBackground = true)
@Composable
fun CVScreenPreviewPreview() {
        CVScreen(navController = rememberNavController())
}