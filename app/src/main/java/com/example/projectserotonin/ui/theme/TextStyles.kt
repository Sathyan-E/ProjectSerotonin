package com.example.projectserotonin.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun SubTitleText2(text: String, color : Color = SRTheme.colors.contentPrimary, maxLines : Int = 2, modifier: Modifier = Modifier,textAlign: TextAlign?= null){
    Text(
        text = text,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = SRTheme.typography.titleSmall,
        modifier = modifier,
        textAlign = textAlign
    )
}


@Composable
fun BodyText(text: String, color : Color = SRTheme.colors.contentPrimary,maxLines : Int = 2, modifier: Modifier = Modifier,textAlign: TextAlign?= null){
    Text(
        text = text,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = SRTheme.typography.bodyLarge,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun LabelText(text: String, color : Color = SRTheme.colors.contentPrimary, maxLines : Int = 1, modifier: Modifier = Modifier,textAlign: TextAlign?= null){
    Text(
        text = text,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = SRTheme.typography.bodyMedium,
        modifier = modifier,
        textAlign = textAlign
    )
}

@Composable
fun DescriptionSmall(modifier: Modifier = Modifier,text: String, color : Color = SRTheme.colors.contentPrimary, maxLines : Int = 1) {
    Text(
        text = text,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        style = SRTheme.typography.labelMedium,
        modifier = modifier
    )
}

@Composable
fun HeaderText(text: String, color : Color = SRTheme.colors.contentPrimary, maxLines : Int = 1, modifier: Modifier = Modifier,overflow: TextOverflow  = TextOverflow.Ellipsis,textAlign: TextAlign?= null) {
    Text(
        text = text,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
        style = SRTheme.typography.titleMedium,
        modifier = modifier,
        textAlign = textAlign
    )
}
