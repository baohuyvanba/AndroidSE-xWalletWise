package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.tyzen.xwalletwise.ui.theme.WalletWiseTheme

@Preview(showBackground = false)
@Composable
fun PreviewDropDownMenu() {
    WalletWiseTheme {
        Column {
            val options = listOf("Option 1", "Option 2", "Option 3")
            var value by remember { mutableStateOf("") }

//            DropDownMenu(
//                label = "Select an option",
//                options = options,
//                value = value,
//                onOptionSelected = {value = it}, )

            DropDownMenu(
                label = "Select an option",
                options = listOf("Option 1", "Option 2", "Option 3"),
                value = "Option 1",
                onOptionSelected = { /* Handle selection */ },
                focusedBorderColor   = Color.White,
                unfocusedBorderColor = Color.White,
                focusedLabelColor    = Color.White,
                unfocusedLabelColor  = Color.White,
                textStyle  = MaterialTheme.typography.bodyMedium,
                labelStyle = MaterialTheme.typography.bodyMedium,
                dropdownTextItemColor = Color.White,
                backgroundColor = Color.Transparent,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
    label: String = "Select an option",
    options: List<String> = listOf(),
    value: String = "",
    onOptionSelected: (String) -> Unit = {},
    expanded: Boolean = false,
    //
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.outline,
    disabledBorderColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    //
    focusedLabelColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedLabelColor: Color = MaterialTheme.colorScheme.primary,
    disabledLabelColor: Color = MaterialTheme.colorScheme.onSurface,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    //
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    labelStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    trailingIconColor: Color = MaterialTheme.colorScheme.primary,
    //
    dropdownTextItemColor: Color = MaterialTheme.colorScheme.onSurface,
    backgroundColor: Color = Color.Transparent,
) {
    var isExpanded by remember { mutableStateOf(expanded) }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded })
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor)
                .clip(RoundedCornerShape(4.dp)),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center, )
        {
            OutlinedTextField(
                value = value,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(
                        text = label,
                        style = labelStyle.copy(color =
                            if (isExpanded)
                                focusedLabelColor
                            else
                                unfocusedLabelColor)
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        tint = trailingIconColor
                    )
                },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    //Border color
                    focusedBorderColor = focusedBorderColor,
                    unfocusedBorderColor = unfocusedBorderColor,
                    disabledBorderColor = disabledBorderColor,
                    //Label color
                    focusedLabelColor = focusedLabelColor,
                    unfocusedLabelColor = unfocusedLabelColor,
                    disabledLabelColor = disabledLabelColor,
                    //Cursor color
                    cursorColor = cursorColor,
                ),
                textStyle = textStyle,
                shape = RoundedCornerShape(4.dp)
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier
                    .exposedDropdownSize()
                    .background(backgroundColor)
            ) {
                options.forEach { option ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(backgroundColor) // Background for each item
                            .clickable {
                                onOptionSelected(option)
                                isExpanded = false
                            }
                            .padding(8.dp) // Padding inside each item
                    ) {
                        Text(
                            text = option,
                            color = dropdownTextItemColor, // Text color
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
//                    DropdownMenuItem(
//                        text = {
//                            Text(
//                                text = option,
//                                color = dropdownTextItemColor
//                            )
//                        },
//                        onClick = {
//                            onOptionSelected(option)
//                            isExpanded = false
//                        }
//                    )
                }
            }
        }
    }
}