package com.example.swiftbargain.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swiftbargain.ui.composable.PrimarySnackBar
import com.example.swiftbargain.ui.composable.UserBottomNavigation
import com.example.swiftbargain.ui.home.HomeScreen
import com.example.swiftbargain.ui.product_details.ProductDetailsScreen
import com.example.swiftbargain.ui.reviews.ReviewsScreen
import com.example.swiftbargain.ui.sale.SaleScreen
import com.example.swiftbargain.ui.theme.colors

@Composable
fun UserNavGraph(mainNavController: NavController) {
    val navController = rememberNavController()
    Scaffold(
        snackbarHost = { PrimarySnackBar() }, bottomBar = { UserBottomNavigation(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(innerPadding)
        ) {
            NavHost(navController = navController, startDestination = Home) {
                composable<Home> { HomeScreen(navController) }
                composable<Explore> {}
                composable<Cart> {}
                composable<Offer> {}
                composable<Account> {}
                composable<Sale> { SaleScreen(navController) }
                composable<ProductDetails> { ProductDetailsScreen(navController) }
                composable<ProductReviews> { ReviewsScreen(navController = navController) }
            }
        }
    }
}