package com.example.projectserotonin.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projectserotonin.R
import com.example.projectserotonin.data.Item
import com.example.projectserotonin.ui.theme.SRTheme
import com.example.projectserotonin.utils.TagUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.exp
/**
This is used to update consumption status in regimen screen
 **/
@Composable
fun SupplementActionView(item: Item, isConsumed: Boolean, onAction: (String) -> Unit) {
    Box(modifier = Modifier.background(Color.Transparent)) {
        if (isConsumed) {
            Image(
                painter = painterResource(id = R.drawable.icon_tick),
                contentDescription = null,
                modifier = Modifier
                    .size(SRTheme.dimens.space40)
                    .clip(CircleShape)
                    .border(SRTheme.dimens.space3, Color.White, CircleShape)
                    .debounceClick {
                        onAction(TagUtils.deleteConsumption)
                    }
            )
        } else {
            val dullWhite1 = Color(0xFFF2F2F2)
            Column(modifier = Modifier
                .size(SRTheme.dimens.space40)
                .clip(CircleShape)
                .background(dullWhite1)
                .border(SRTheme.dimens.space3, Color.White, CircleShape)
                .debounceClick {
                    onAction(TagUtils.addConsumption)
                }) {
                Text(
                    text = item.product.regimen.servingSize.toString(),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        letterSpacing = 0.5.sp,
                        color = Color.Black
                    ),
                    color = Color.Black,
                    modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center))

            }

        }
    }

}