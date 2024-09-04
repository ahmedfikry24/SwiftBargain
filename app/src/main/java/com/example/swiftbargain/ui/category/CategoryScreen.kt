package com.example.swiftbargain.ui.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.swiftbargain.ui.category.view_model.CategoryInteractions
import com.example.swiftbargain.ui.category.view_model.CategoryUiState
import com.example.swiftbargain.ui.category.view_model.CategoryViewModel
import com.example.swiftbargain.ui.composable.ContentError
import com.example.swiftbargain.ui.composable.ContentLoading
import com.example.swiftbargain.ui.composable.ContentVisibility
import com.example.swiftbargain.ui.composable.ScrollToFirstItemFab
import com.example.swiftbargain.ui.theme.spacing
import com.example.swiftbargain.ui.utils.ContentStatus
import com.example.swiftbargain.ui.utils.EventHandler
import kotlinx.coroutines.launch

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    EventHandler(effects = viewModel.event) { event, _ ->

    }
    CategoryContent(state = state, interactions = viewModel)
}


@Composable
private fun CategoryContent(
    state: CategoryUiState,
    interactions: CategoryInteractions
) {
    ContentLoading(isVisible = state.contentStatus == ContentStatus.LOADING)
    ContentVisibility(isVisible = state.contentStatus == ContentStatus.VISIBLE) {
        val scrollState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        ScrollToFirstItemFab(
            modifier = Modifier.fillMaxSize(),
            isFabVisible = scrollState.canScrollBackward,
            onClickFab = { scope.launch { scrollState.animateScrollToItem(0) } }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollState,
                contentPadding = PaddingValues(vertical = MaterialTheme.spacing.space16)
            ) {

            }
        }
    }
    ContentError(
        isVisible = state.contentStatus == ContentStatus.FAILURE,
        onTryAgain = {}
    )
}
