package com.example.swiftbargain.ui.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.swiftbargain.R
import com.example.swiftbargain.navigation.Account
import com.example.swiftbargain.navigation.Cart
import com.example.swiftbargain.navigation.Explore
import com.example.swiftbargain.navigation.Home
import com.example.swiftbargain.navigation.Offer
import com.example.swiftbargain.ui.theme.colors


private data class BottomNavItem(
    val label: String,
    val iconId: Int,
    val route: Any
)

@Composable
fun UserBottomNavigation(navController: NavController) {
    val screens = listOf(
        BottomNavItem("Home", R.drawable.ic_home, Home),
        BottomNavItem("Explore", R.drawable.ic_search, Explore),
        BottomNavItem("Cart", R.drawable.ic_cart, Cart()),
        BottomNavItem("Offer", R.drawable.ic_offer, Offer),
        BottomNavItem("Account", R.drawable.ic_profile, Account),
    )
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val isNavBarVisible = screens.any { currentDestination.checkCurrentDestination(it.route) }

    if (isNavBarVisible)
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colors.background
        ) {
            screens.forEach { screen ->
                this.NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colors.primary,
                        selectedTextColor = MaterialTheme.colors.primary,
                    ),
                    selected = currentDestination.checkCurrentDestination(screen.route),
                    label = {
                        Text(
                            text = screen.label,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colors.textGrey
                        )
                    },
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(Home) { saveState = true }
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(screen.iconId),
                            contentDescription = null
                        )
                    }
                )
            }
        }
}

private fun NavDestination?.checkCurrentDestination(route: Any): Boolean {
    return this?.hierarchy?.any {
        it.route?.substringAfterLast(".")?.substringBefore("?") ==
                route.javaClass.toString().substringAfterLast(".")
    } == true
}
