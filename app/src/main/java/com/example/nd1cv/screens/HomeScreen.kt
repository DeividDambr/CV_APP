package com.example.nd1cv.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoAlbum
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.ContactPage
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PhotoAlbum
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nd1cv.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    navController: NavController,
    settingsOn: Boolean) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { navController.navigate(Screen.SettingsScreen.route) }
            ){
                if(settingsOn){
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = stringResource(id = R.string.settings)
                    )
                }
                else{
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = stringResource(id = R.string.settings)
                    )
                }
            }
         },
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .padding(start = 80.dp),
                style = MaterialTheme.typography.headlineMedium
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun BottomAppBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavigationItem(
            title = stringResource(R.string.main),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Screen.MainScreen.route
        ),
        NavigationItem(
            title = stringResource(R.string.cv),
            selectedIcon = Icons.Filled.ContactPage,
            unselectedIcon = Icons.Outlined.ContactPage,
            route = Screen.CVScreen.route
        ),
        NavigationItem(
            title = stringResource(R.string.portfolio),
            selectedIcon = Icons.Filled.PhotoAlbum,
            unselectedIcon = Icons.Outlined.PhotoAlbum,
            route = Screen.PortfolioScreen.route
        ),
        NavigationItem(
            title = stringResource(R.string.contact_us),
            selectedIcon = Icons.Filled.AlternateEmail,
            unselectedIcon = Icons.Outlined.AlternateEmail,
            route = Screen.ContactUsScreen.route
        )
    )

    val backStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar {
        items.forEachIndexed {index, item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route)
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = false,
                icon = {
                    Icon(
                        imageVector =
                        if(selected){
                            item.selectedIcon
                        } else {
                            item.unselectedIcon
                        },
                        contentDescription = item.title
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController){
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
    ){innerPadding ->
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally){
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
            item {
                Row{
                    ImageMain(
                        name = stringResource(id = R.string.full_name),
                        job = stringResource(id = R.string.job_title),
                        imagePainter = painterResource(id = R.drawable.profile_pic)
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            item {
                Row{
                    ContactInfo(
                        phoneNum = stringResource(id = R.string.my_phone_num),
                        email = stringResource(id = R.string.my_email)
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ImageMain(
    name: String,
    job: String,
    imagePainter: Painter,
    modifier: Modifier = Modifier
){
    Column (
        modifier= modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(48.dp))
                .size(250.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = name,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = job,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun ContactInfo(
    phoneNum: String,
    email: String,
    modifier: Modifier = Modifier
){
    Column (
        modifier= modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                Icons.Default.Phone,
                contentDescription = "Phone icon"
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = phoneNum,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Email,
                contentDescription = "Email icon" ,

                )
            Spacer(Modifier.width(16.dp))
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}