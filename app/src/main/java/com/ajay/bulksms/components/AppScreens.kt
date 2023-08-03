package com.ajay.bulksms.components


sealed class Screen(val route: String) {
    object MainView: Screen("MainView")
    object detailView: Screen("detailView")
}