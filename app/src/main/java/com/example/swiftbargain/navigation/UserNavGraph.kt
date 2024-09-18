package com.example.swiftbargain.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swiftbargain.ui.cart.CartScreen
import com.example.swiftbargain.ui.cart_check_out.CartCheckOutScreen
import com.example.swiftbargain.ui.category.CategoryScreen
import com.example.swiftbargain.ui.composable.PrimarySnackBar
import com.example.swiftbargain.ui.composable.UserBottomNavigation
import com.example.swiftbargain.ui.explore.ExploreScreen
import com.example.swiftbargain.ui.favorites.FavoritesScreen
import com.example.swiftbargain.ui.home.HomeScreen
import com.example.swiftbargain.ui.offer.OfferScreen
import com.example.swiftbargain.ui.product_details.ProductDetailsScreen
import com.example.swiftbargain.ui.reviews.ReviewsScreen
import com.example.swiftbargain.ui.sale.SaleScreen
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.utils.SnackBarManager
import com.example.swiftbargain.ui.utils.SnackBarManager.showError
import com.example.swiftbargain.ui.utils.UiConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun UserNavGraph(mainNavController: NavController) {
    val navController = rememberNavController()
    val snackBar = SnackBarManager.init()
    val scope = rememberCoroutineScope()
    val unAuthorizedLogin = { logOut(mainNavController, snackBar, scope) }
    Scaffold(
        snackbarHost = { PrimarySnackBar() },
        bottomBar = { UserBottomNavigation(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(innerPadding)
        ) {
            NavHost(navController = navController, startDestination = Home) {
                composable<Home> { HomeScreen(navController) }
                composable<Explore> { ExploreScreen(navController) }
                composable<Cart> { CartScreen(navController) }
                composable<CartCheckOut> { CartCheckOutScreen(navController, unAuthorizedLogin) }
                composable<Offer> { OfferScreen(navController) }
                composable<Account> {}
                composable<Sale> { SaleScreen(navController) }
                composable<ProductDetails> { ProductDetailsScreen(navController) }
                composable<ProductReviews> { ReviewsScreen(navController) }
                composable<Favorites> { FavoritesScreen(navController) }
                composable<Category> { CategoryScreen(navController) }
            }
        }
    }
}

private fun logOut(
    navController: NavController,
    snackBar: SnackbarHostState,
    scope: CoroutineScope
) {
    scope.launch {
        snackBar.showError(UiConstants.UNAUTHORIZED_TO_ACCESS, this) {
            navController.navigate(Authentication) { popUpTo(Authentication) { inclusive = false } }
        }
    }
}
