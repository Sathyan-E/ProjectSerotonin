package com.example.projectserotonin.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

/**
This Component should be used on all places for image loading. In Future if we change any library(image loading) it can be done easily.
 **/
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AsyncImage(url:String, contentScale: ContentScale = ContentScale.Fit, modifier: Modifier) {
   Log.i("AsyncImage", "AsyncImage: $url")
    GlideImage(
        model = url,
        contentDescription = null,
        contentScale = contentScale,
    )
}