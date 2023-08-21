package com.ajay.bulksms.components


sealed class Screen(val route: String) {
    object MainView: Screen("MainView")
    object selectContactView: Screen("selectContactView")
    object testView: Screen("testView")
    object CSVView: Screen("CSVView")

}