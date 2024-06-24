package com.example.projectserotonin.components

import androidx.compose.foundation.Image
import com.example.projectserotonin.R
import com.example.projectserotonin.ui.theme.LabelText
import com.example.projectserotonin.ui.theme.SRTheme
import com.example.projectserotonin.ui.theme.SubTitleText2
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
/**
This is Custom AlertDialog for the project. Can be customized further.
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SRAlertDialog(
    confirmButton: () -> Unit,
    dismissButton: () -> Unit,
    positiveText: String, negativeText: String
) {

    AlertDialog(
        onDismissRequest = dismissButton,
        title = {
                SubTitleText2(
                    text = stringResource(id = R.string.delete_consumption),
                    color = SRTheme.colors.contentPrimary,
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
                )
        },

        confirmButton = {
            Button(
                onClick = confirmButton,
                colors = ButtonDefaults.buttonColors(
                    contentColor = SRTheme.colors.contentPrimary,
                    containerColor = SRTheme.colors.contentPrimary
                )
            ) {
                LabelText(positiveText, color = SRTheme.colors.onPrimary,
                )
            }
        },
        dismissButton = {
            Button(onClick = dismissButton,
                colors = ButtonDefaults.buttonColors(
                    contentColor = SRTheme.colors.contentPrimary,
                    containerColor = SRTheme.colors.contentPrimary
                )
            ) {
                LabelText(negativeText, color = SRTheme.colors.onPrimary,
                )
            }
        },

    )

}