package com.example.nd1cv.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nd1cv.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(navController: NavController){

    var messageFrom by remember{
        mutableStateOf("d.dambrauskas@stud.vilniustech.lt")
    }
    var messageTheme by remember{
        mutableStateOf("")
    }
    var message by remember{
        mutableStateOf("")
    }

    var sendCheck: Boolean

    val context = LocalContext.current

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
    ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    MessageFrom(
                        value = stringResource(R.string.my_email),
                        onValueChanged = { messageFrom = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }
                item {
                    MessageTheme(
                        value = messageTheme,
                        onValueChanged = { messageTheme = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                    )
                }
                item{
                    Message(
                        value = message,
                        onValueChanged = { message = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(20.dp)
                    )
                }
                item {
                    sendCheck = messageTheme.isNotBlank() && message.isNotBlank()
                    ElevatedButton(enabled = sendCheck,
                    onClick = {
                        sendEmail(context, messageFrom, messageTheme, message)
                    }) {
                        Text(
                            text = stringResource(R.string.send),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .width(50.dp)
                        )
                    }
                }
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageFrom(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier
) {
    OutlinedTextField(
        label = {
            Text(text = stringResource(R.string.send_email_to))
        },
        enabled = false,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text) ,
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageTheme(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier
) {
    OutlinedTextField(
        label = {
            Text(text = stringResource(R.string.email_subject))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text) ,
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Message(
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier
) {
    OutlinedTextField(
        label = {
            Text(text = stringResource(R.string.email_content))
        },
        textStyle = TextStyle(baselineShift = BaselineShift(-0.2f)),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text) ,
        singleLine = false,
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged
    )
}

private fun sendEmail(context: Context, from: String, theme: String, message: String) {
    val emailIntent = Intent(Intent.ACTION_SEND)
    emailIntent.type = "text/plain"
    emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(from))
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, theme)
    emailIntent.putExtra(Intent.EXTRA_TEXT, message)
    try {
        context.startActivity(Intent.createChooser(emailIntent, "Siųsti laišką"))
    } catch (e: ActivityNotFoundException) {
        Log.e("Error", context.getString(R.string.email_error))
    }
}

@Preview(showBackground = true)
@Composable
fun ContactScreenPreview() {
        ContactScreen(navController = rememberNavController())
}