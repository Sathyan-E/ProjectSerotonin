package com.example.projectserotonin.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projectserotonin.R
import com.example.projectserotonin.data.Item
import com.example.projectserotonin.ui.theme.BodyText
import com.example.projectserotonin.ui.theme.DescriptionSmall
import com.example.projectserotonin.ui.theme.LabelText
import com.example.projectserotonin.ui.theme.SRTheme
/**
This is custom Toast View for Supplement consumed action
 **/
@Composable
fun SupplementToast(item: Item?, onTap: (Item?) -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()
        .clip(RoundedCornerShape(SRTheme.dimens.space5))
            .background(SRTheme.colors.toastBackgroundColor)
        .debounceClick {
            onTap(item)
        }
        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(SRTheme.dimens.space6)
        ) {

            Surface(
                Modifier
                    .width(SRTheme.dimens.space70)
                    .height(SRTheme.dimens.space120)
                    .clip(RoundedCornerShape(SRTheme.dimens.space40))
                    .background(Color.White)
                    .weight(0.25f)
                    .shadow(
                        5.dp,
                        shape = RoundedCornerShape(SRTheme.dimens.space40),
                        clip = true,
                        ambientColor = Color.Red,
                        spotColor = DefaultShadowColor
                    )
            ) {
                Box(
                    modifier = Modifier
                        .width(SRTheme.dimens.space50)
                        .height(SRTheme.dimens.space50)
                        .padding(SRTheme.dimens.space10)

                ) {
                    AsyncImage(
                        url = item?.product?.image ?: "", contentScale = ContentScale.FillHeight,
                        modifier = Modifier.fillMaxSize()

                    )
                }
            }
            Spacer(modifier = Modifier.width(SRTheme.dimens.space10))
            Column(modifier = Modifier.align(Alignment.CenterVertically).weight(0.65f)) {
                LabelText(text = "Got your ${item?.product?.name ?: ""}", maxLines = 3, color = SRTheme.colors.onPrimary)
                Spacer(modifier = Modifier.height(SRTheme.dimens.space2))
                DescriptionSmall(text =  "TIP: ${item?.product?.regimen?.description ?: ""}",  maxLines = 3,color = SRTheme.colors.onPrimary)
            }

            Column(modifier = Modifier.size(SRTheme.dimens.space24).align(Alignment.CenterVertically).weight(0.1f)) {
                Image(painter = painterResource(id = R.drawable.icon_next_arrow), contentDescription =null )
            }
        }
    }
}
