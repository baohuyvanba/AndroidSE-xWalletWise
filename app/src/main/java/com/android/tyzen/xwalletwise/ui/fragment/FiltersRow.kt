package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Filter row -------------------------------------------------------------------------------------
 */
@Composable
fun FilterRow(
    filterOptions: List<String>,
    selectedFilter: String,
    onFilterSelected: (String) -> Unit, )
{
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp), )
    {
        items(filterOptions) { filter ->
            FilterChip(
                selected = (filter == selectedFilter),
                onClick  = {
                    if (filter == selectedFilter) {
                        onFilterSelected("")
                    } else {
                        onFilterSelected(filter)
                    }
                },
                label    = { Text(text = filter) }
            )
        }
    }
}