package com.example.projectserotonin.components

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.projectserotonin.ui.theme.SRTheme

@Composable
fun ProgressIndicator(modifier: Modifier = Modifier.width(SRTheme.dimens.space48)) {
    CircularProgressIndicator(
        modifier = modifier,
        color = Color.White,
        trackColor = Color.Blue,
    )
}