package com.example.nd1cv.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nd1cv.R
import com.example.nd1cv.languageFiles.DataStorePreferenceRepository
import com.example.nd1cv.languageFiles.DataStoreViewModelFactory
import com.example.nd1cv.languageFiles.LanguageViewModel
import kotlinx.coroutines.launch
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: LanguageViewModel = viewModel(
        factory = DataStoreViewModelFactory(DataStorePreferenceRepository(LocalContext.current))
    )
){
    var isExpanded by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val currentLanguage = viewModel.language.observeAsState().value

    val lang = stringResource(id = R.string.language)
    val langEn = stringResource(id = R.string.language_en)
    val langLt = stringResource(id = R.string.language_lt)
    val settings = stringResource(id = R.string.settings)

    val selectedLang = if(currentLanguage == 1){
        langEn
    }
    else{
        langLt
    }

    SetLanguage(position = currentLanguage!!)

    Scaffold(
        topBar = {
            TopAppBar(navController, true)
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                modifier = Modifier
            )
        }
    ) {innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(top = 16.dp),
        ){
            Text(
                text = settings,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontSize = 24.sp
            )
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start) {
            item {
                Spacer(modifier = Modifier.height(64.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    ExposedDropdownMenuBox(
                        expanded = isExpanded,
                        onExpandedChange = { isExpanded = it }
                    ) {
                        TextField(
                            value = "$lang: $selectedLang",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                TrailingIcon(expanded = isExpanded)
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = {
                                isExpanded = false
                            }
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(text = langEn)
                                },
                                onClick = {
                                    navController.navigate(Screen.SettingsScreen.route)
                                    isExpanded = false
                                    scope.launch {
                                        viewModel.saveLanguage(1)
                                    }

                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(text = langLt)
                                },
                                onClick = {
                                    navController.navigate(Screen.SettingsScreen.route)
                                    isExpanded = false
                                    scope.launch {
                                        viewModel.saveLanguage(0)
                                    }

                                }
                            )
                        }
                    }
                }
            }
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.qrcode),
                        contentDescription = stringResource(id = R.string.qr_code),
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                    )
                }
            }
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = stringResource(id = R.string.author_content) + " " + stringResource(id = R.string.full_name) + " " + stringResource(
                            id = R.string.student_group)
                    )
                }
            }
        }
    }
}

@Composable
private fun SetLanguage(position: Int) {
    val locale = Locale(if (position == 1) "en" else "lt")
    val configuration = LocalConfiguration.current
    configuration.setLocale(locale)
    val resources = LocalContext.current.resources
    resources.updateConfiguration(configuration, resources.displayMetrics)
}

@Preview
@Composable
fun SettingsPreview(){
    SettingsScreen(navController = rememberNavController())
}