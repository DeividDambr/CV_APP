package com.example.nd1cv.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.nd1cv.R
import com.example.nd1cv.WindowInfo
import com.example.nd1cv.data.DataSource
import com.example.nd1cv.data.Photos
import com.example.nd1cv.rememberWindowInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(navController: NavController) {
    val itemsList: List<Photos> = DataSource().loadPhotos()

    val windowInfo = rememberWindowInfo()

    Scaffold(
        topBar = {
            TopAppBar(navController = navController, false)
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {
                GridCells.Fixed(2)
            } else {
                GridCells.Fixed(3)
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            items(itemsList) { item ->
                ItemCard(item)
            }
        }
    }
}

@Composable
fun ItemCard(item: Photos) {

    var showDialog by remember { mutableStateOf(false) }

    Image(
        painter = painterResource(id = item.imageResourceId),
        contentDescription = null,
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .padding(5.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable {
                showDialog = true
            },
        contentScale = ContentScale.Crop
    )

    if (showDialog) {
        DialogWithImage(
            painter = painterResource(id = item.imageResourceId),
            imageDescription = stringResource(id = item.stringResourceId),
            onDismissRequest = { showDialog = false }
        )
    }
}

@Composable
fun DialogWithImage(
    painter: Painter,
    imageDescription: String,
    onDismissRequest: () -> Unit
) {
    val dismissDialog = remember { mutableStateOf(false) }

    if(dismissDialog.value) return

    Dialog(
        onDismissRequest = {
            dismissDialog.value = true
            onDismissRequest()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            securePolicy = SecureFlagPolicy.SecureOff
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { onDismissRequest() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close)
                            )
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                        )
                    }
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                    ) {
                        Text(text = imageDescription)
                        Spacer(modifier = Modifier.height(48.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PortfolioScreenPreviewPreview() {
        PortfolioScreen(navController = rememberNavController())
}