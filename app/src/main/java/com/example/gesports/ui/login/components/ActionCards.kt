package com.example.gesports.ui.login.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Action Card reusable component
 * Ideal for dashboards, menus de opciones, accesos rápidos, etc.
 */
@Composable
fun ActionCard(
    title: String,
    iconRes: Int,
    gradient: List<Color>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.verticalGradient(gradient)
            )
            .clickable { onClick() }
            .padding(18.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = iconRes),
                contentDescription = title,
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = title,
                color = Color.White,
                fontSize = 18.sp,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
