package com.example.nd1cv.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nd1cv.R
import com.example.nd1cv.preferences.Preferences


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(
    navController: NavController,
    deletedId: Int
)
{
    var savedTitle by remember { mutableStateOf("") }
    var savedText by remember { mutableStateOf("") }

    val context = LocalContext.current
    val urlManager = Preferences(context)

    var textId by remember { mutableIntStateOf(urlManager.getTextId()) }

    Scaffold(
        topBar = {
            TopAppBar(navController, false)
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                modifier = Modifier
            )
        }
    ) {innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding(), start = innerPadding.calculateStartPadding(LayoutDirection.Ltr), bottom = 20.dp)
            .padding(top = 20.dp)
        ){
            item{
                Text(
                    text = stringResource(id = R.string.add_entry),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontSize = 24.sp
                )
            }
            item{
                AddEntry(
                    savedTitle = savedTitle,
                    savedText = savedText,
                    onTitleChanged = { savedTitle = it },
                    onTextChanged = { savedText = it },
                    modifier = Modifier,
                    onAddClick = {
                        if(deletedId == -1) {
                            urlManager.saveText(textId, savedTitle, savedText)
                            textId++
                            urlManager.saveTextId(textId)
                            Log.d(
                                "CVScreen",
                                "Saved: textId=$textId, title=$savedTitle, text=$savedText"
                            )
                        }
                        else{
                            urlManager.saveText(deletedId, savedTitle, savedText)
                            Log.d(
                                "tester",
                                "$deletedId"
                            )
                        }
                        navController.navigate(Screen.CVScreen.route)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntry(
    savedTitle: String,
    savedText: String,
    onTitleChanged: (String) -> Unit,
    onTextChanged: (String) -> Unit,
    modifier: Modifier,
    onAddClick: () -> Unit,
) {
    var sendCheck: Boolean

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 48.dp)
        ) {
            OutlinedTextField(
                placeholder = {
                    Text(text = stringResource(R.string.enter_para_title))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 20.dp),
                value = savedTitle,
                onValueChange = onTitleChanged
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                placeholder = {
                    Text(text = stringResource(R.string.enter_para_text))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = false,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 20.dp),
                value = savedText,
                onValueChange = onTextChanged,
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            sendCheck = savedText.isNotBlank() && savedTitle.isNotBlank()
            ElevatedButton(enabled = sendCheck,
                onClick = { onAddClick() }) {
                Text(text = stringResource(id = R.string.add_entry))
            }
        }
    }
}

@Preview
@Composable
fun EntryPreview(){
    AddEntryScreen(navController = rememberNavController(), -1)
}