package com.example.gesports.ui.login.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LogoIcon(
    logoRes: Int,
    contentDescription: String,
    onClick: () -> Unit = {}
) {
    Image(
        painter = painterResource(id = logoRes),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(60.dp)
            .padding(horizontal = 10.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Fit
    )
}
