package com.android.tyzen.xwalletwise.ui.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.model.category.Category
import com.android.tyzen.xwalletwise.util.categoriesList
import com.android.tyzen.xwalletwise.util.categoryIconsList

/**
 * Icons bottom sheet ------------------------------------------------------------------------------
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconsBottomSheet(
    onIconSelected: (String) -> Unit,
    onDismissRequest: () -> Unit, )
{
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface)
    {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Choose Category Icon",
                style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 64.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp), )
            {
                items(categoriesList.size) { index ->
                    IconButton(
                        onClick = { onIconSelected(categoriesList[index]) },
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                shape = CircleShape
                            ), )
                    {
                        Image(
                            painter = painterResource(id = categoryIconsList[categoriesList[index]] ?: R.drawable.ic_category_other),
                            contentDescription = "Category Icon: $categoriesList[index]", )
                    }

                }
            }
        }
    }
}

/**
 * Category Bottom Sheet ---------------------------------------------------------------------------
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryBottomSheet(
    categories: List<Category>,
    onCategorySelected: (Int) -> Unit,
    onDismissRequest: () -> Unit )
{
    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface)
    {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp),)
        {
            items(categories.size) { index ->
                CategoryChip(
                    category = categories[index],
                    onCategorySelected = onCategorySelected)
            }
        }
    }
}

@Composable
fun CategoryChip(
    category: Category,
    onCategorySelected: (Int) -> Unit)
{
    AssistChip(
        onClick = { onCategorySelected(category.id) },
        label = { Text(text = category.title) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = categoryIconsList[category.icon] ?: R.drawable.ic_category_other),
                contentDescription = "Category Icon: ${category.title}", )
        },
        modifier = Modifier.padding(4.dp)
    )
}