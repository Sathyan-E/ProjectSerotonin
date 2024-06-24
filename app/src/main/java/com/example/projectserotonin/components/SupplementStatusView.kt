package com.example.projectserotonin.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectserotonin.R
import com.example.projectserotonin.ui.theme.SRTheme
import com.example.projectserotonin.utils.TagUtils
/**
This is used in Dashboard screen to show the status of supplement
 **/
@Composable
fun SupplementStatusView(isConsumed: Boolean, isPaused: Boolean) {
    Box(modifier = Modifier.background(Color.Transparent)) {
        if (isConsumed) {
            Image(
                painter = painterResource(id = R.drawable.icon_tick),
                contentDescription = null,
                modifier = Modifier
                    .size(SRTheme.dimens.space35)
                    .clip(CircleShape)
                    .border(SRTheme.dimens.space3, Color.White, CircleShape)

            )
        } else if (isPaused){
            Image(
                painter = painterResource(id = R.drawable.icon_pause),
                contentDescription = null,
                modifier = Modifier
                    .size(SRTheme.dimens.space35)
                    .clip(CircleShape)
                    .border(SRTheme.dimens.space3, Color.White, CircleShape)

            )

        }
    }

}