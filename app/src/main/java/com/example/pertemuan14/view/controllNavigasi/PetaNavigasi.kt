package com.example.pertemuan14.view.controllNavigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pertemuan14.view.DetailSiswaScreen
import com.example.pertemuan14.view.EditSiswaScreen
import com.example.pertemuan14.view.EntrySiswaScreen
import com.example.pertemuan14.view.HomeScreen
import com.example.pertemuan14.view.route.DestinasiDetail
import com.example.pertemuan14.view.route.DestinasiEdit
import com.example.pertemuan14.view.route.DestinasiEntry
import com.example.pertemuan14.view.route.DestinasiHome

@Composable
fun DataSiswaApp(navController: NavHostController = rememberNavController(), modifier:
Modifier){
    HostNavigasi(navController = navController)
}

@Composable
fun HostNavigasi(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    NavHost(navController = navController, startDestination = DestinasiHome.route, modifier
    = Modifier ){
        composable(DestinasiHome.route) {
            HomeScreen(navigateToItemEntry = { navController.navigate(DestinasiEntry.route)
            },
                navigateToItemUpdate = {
                    navController.navigate("${DestinasiDetail.route}/${it}")})
        }
        composable(DestinasiEntry.route){
            EntrySiswaScreen(navigateBack = { navController.navigate(DestinasiHome.route) }
            )
        }
        composable(DestinasiDetail.routeWithArgs,arguments = listOf(navArgument
            (DestinasiDetail.itemIdArg) {
            type = NavType.StringType })
        ){
            DetailSiswaScreen(navigateToEditItem = {navController.navigate("${DestinasiEdit
                .route}/${it}")},
                navigateBack = { navController.navigate(DestinasiHome.route) })
        }
        composable(
            DestinasiEdit.routeWithArgs, arguments = listOf(navArgument
            (DestinasiEdit.itemIdArg){
            type= NavType.StringType})){
            EditSiswaScreen(navigateBack = { navController.navigate(DestinasiHome.route) },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}
