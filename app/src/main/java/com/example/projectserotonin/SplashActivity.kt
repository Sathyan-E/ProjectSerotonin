package com.example.projectserotonin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.projectserotonin.dashboard.DashboardActivity
import com.example.projectserotonin.ui.theme.SRTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SRTheme {
                SplashView()
            }
        }
    }

    private suspend fun moveToDashboardScreen() {
        delay(1500)
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Composable
    fun SplashView() {
        LaunchedEffect(Unit) {
            moveToDashboardScreen()
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
           Image(painter = painterResource(id = R.drawable.icon_sr) , contentDescription = null, modifier = Modifier.size(SRTheme.dimens.space200))
        }
    }
}