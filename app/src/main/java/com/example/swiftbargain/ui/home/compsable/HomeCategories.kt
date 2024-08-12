package com.example.swiftbargain.ui.home.compsable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.composable.CategoryItem
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.CategoryUiSate

@Composable
fun HomeCategories(
    modifier: Modifier = Modifier,
    categories: List<CategoryUiSate>
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
    ) {
        Text(
            text = stringResource(R.string.category),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colors.text
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
        ) {
            items(categories) { category ->
                CategoryItem(categoryItem = category)
            }
        }
    }
}
