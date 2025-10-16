package com.example.bookexpertnotesapp.navigation

import bookexpertnotesapp.composeapp.generated.resources.Res
import bookexpertnotesapp.composeapp.generated.resources.create_note_lbl
import bookexpertnotesapp.composeapp.generated.resources.notes_lbl
import bookexpertnotesapp.composeapp.generated.resources.pdf_lbl
import bookexpertnotesapp.composeapp.generated.resources.view_note_lbl
import com.example.bookexpertnotesapp.domain.model.Note


/*enum class NoteScreen(val title: String) {
    Home(title = Res.string.notes_lbl),
    PDFView(title = Res.string.pdf_lbl),
    Notes(title = Res.string.notes_lbl),
    ViewNote(title = Res.string.view_note_lbl),
    NoteEditor(title = Res.string.create_note_lbl)
}*/

const val HomeScreen : String= "Home"
const val PDFViewScreen : String= "PDFView"
const val NotesScreen : String= "Notes"
const val ViewNoteScreen : String= "ViewNote"
const val NoteEditorScreen : String= "NoteEditor"
sealed class NoteScreen(val route: String) {
    object Home : NoteScreen(HomeScreen)
    object PDFView : NoteScreen(PDFViewScreen)
    object Notes : NoteScreen(NotesScreen)
    object ViewNote : NoteScreen(ViewNoteScreen)
    object NoteEditor : NoteScreen(NoteEditorScreen)
}

/*

@Composable
internal fun NavGraph(navController: NavHostController = rememberNavController())
{
// Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = NoteScreen.valueOf(
        backStackEntry?.destination?.route ?: NoteScreen.Start.name
    )
    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = Modifier
            .fillMaxSize()
            */
/*.padding(innerPadding)*//*
,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeView(onNavigate = onNavigate)
        }
        composable(route = BottomBarScreen.Reels.route) {
            ReelsView(onNavigate = onNavigate)
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileView(onNavigate = onNavigate)
        }
        composable(route = AppScreen.Detail.route) {
            DetailsView(onNavigate = onNavigate)
        }
    }


}

@Composable
fun NavHostMain(
    navController: NavHostController = rememberNavController(),
    onNavigate: (rootName: String) -> Unit,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination

    Scaffold(
        topBar = {
            val title = getTitle(currentScreen)
            TopBar(
                title = title,
                canNavigateBack = currentScreen?.route == AppScreen.Detail.route,
                navigateUp = { navController.navigateUp() }
            )
        },
    ) { innerPadding ->

    }
}
*/











