package com.example.projectserotonin.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.projectserotonin.R
import com.example.projectserotonin.ui.theme.BodyText

/**
    Project App bar with customized Back Button
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectAppBar( canNavigateBack: Boolean = false,
                   navigateUp: () -> Unit) {
    TopAppBar(
        title = {
            BodyText(text = stringResource(id = R.string.app_name))
        }, colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = {
                    navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null // Provide a string resource ID
                    )
                }
            }
        }
    )
}