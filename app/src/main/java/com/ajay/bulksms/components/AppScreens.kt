package com.ajay.bulksms.components


sealed class Screen(val route: String) {
    object MainView: Screen("MainView")
    object SelectContactView: Screen("SelectContactView")
    object TestView: Screen("TestView")
    object CSVView: Screen("CSVView")

}