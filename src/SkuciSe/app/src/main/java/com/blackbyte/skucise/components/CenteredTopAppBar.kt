package com.blackbyte.skucise.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CenteredTopAppBar(
    leadingAction: @Composable ()->Unit = {},
    centerContent: @Composable ()->Unit = {},
    trailingAction: @Composable ()->Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 1.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        //TopAppBar Content
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Box(
                Modifier.width(50.dp),
                contentAlignment = Alignment.Center
            ){
                leadingAction
            }
            Box(
                Modifier.wrapContentWidth(),
                contentAlignment = Alignment.Center
            ){
                centerContent
            }
            Box(
                Modifier.width(50.dp),
                contentAlignment = Alignment.Center
            )
            {
                trailingAction
            }
        }
    }
}