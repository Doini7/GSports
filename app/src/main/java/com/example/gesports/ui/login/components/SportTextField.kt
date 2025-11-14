package com.example.gesports.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun SportTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable (() -> Unit))? = null,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedPlaceholderColor = Color.White,
        unfocusedPlaceholderColor = Color.White,
        focusedContainerColor = Color(0xFF7BB8FD),
        unfocusedContainerColor = Color(0xFF7BB8FD),
        focusedBorderColor = Color(0xFF0059FF),
        unfocusedBorderColor = Color(0xFF0059FF),
        cursorColor = Color.White
    )
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        trailingIcon = trailingIcon,
        modifier = modifier
            .width(300.dp)
            .height(54.dp),
        colors = colors,
        shape = RoundedCornerShape(15.dp),
        singleLine = singleLine,
        visualTransformation = visualTransformation
    )
}
