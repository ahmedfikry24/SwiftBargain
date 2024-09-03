package com.example.swiftbargain.ui.explore.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.swiftbargain.ui.composable.CategoryItem
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.shared_ui_state.CategoryUiSate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExploreCategorySection(
    modifier: Modifier = Modifier,
    title: String,
    categoryList: List<CategoryUiSate>,
    onClickCategory: (String, String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.space16),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colors.text
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space16),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space20)
        ) {
            categoryList.forEach { category ->
                CategoryItem(
                    categoryItem = category,
                    onCLick = { onClickCategory(it, category.label) }
                )
            }
        }
    }
}
