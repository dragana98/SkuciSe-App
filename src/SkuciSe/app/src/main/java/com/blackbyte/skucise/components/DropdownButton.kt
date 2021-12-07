package com.blackbyte.skucise.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.ui.theme.LightPurple
import com.blackbyte.skucise.ui.theme.Purple200

@Composable
fun DropdownButton(
    hintText: String,
    items: List<String>,
    disabled: List<String>,
    onSelectedIndex: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex: Int by remember { mutableStateOf(0) }
    var isSet by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        OutlinedButton(
            onClick = {
                expanded = true
            }, modifier = Modifier
                .height(46.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (!isSet) hintText else items[selectedIndex])
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "registration icon"
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colors.surface
                )

        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    isSet = true
                    onSelectedIndex(selectedIndex)
                }) {
                    val disabledText = if (disabled.contains(s)) {
                        " (Disabled)"
                    } else {
                        ""
                    }
                    Text(text = s + disabledText)
                }
            }
        }
    }
}
