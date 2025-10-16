package com.example.bookexpertnotesapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import bookexpertnotesapp.composeapp.generated.resources.Res
import bookexpertnotesapp.composeapp.generated.resources.back_btn
import bookexpertnotesapp.composeapp.generated.resources.create_note_lbl
import bookexpertnotesapp.composeapp.generated.resources.outline_add_24
import com.example.bookexpertnotesapp.domain.model.Note
import com.example.bookexpertnotesapp.navigation.NoteScreen
import com.example.bookexpertnotesapp.ui.HomeScreen
import com.example.bookexpertnotesapp.ui.NoteEditorScreen
import com.example.bookexpertnotesapp.ui.NotesScreen
import com.example.bookexpertnotesapp.ui.PDFViewScreen
import com.example.bookexpertnotesapp.ui.ViewNoteScreen
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    val navController: NavHostController = rememberNavController()
    var isFloatingButtonShow by rememberSaveable { mutableStateOf(true) }
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = backStackEntry?.destination?.route
    MaterialTheme {
        Scaffold(
            topBar = {
                NotesAppBar(
                    currentScreen = currentScreen ?: "",
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            },
            floatingActionButton = {
                if (isFloatingButtonShow) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate(NoteScreen.NoteEditor.route)
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50)
                    ) {
                        /* Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(
                         Res.string.create_note_lbl))*/

                        Icon(
                            painter = painterResource(Res.drawable.outline_add_24),
                            contentDescription = stringResource(
                                Res.string.create_note_lbl
                            )
                        )
                    }
                }
            }) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = NoteScreen.Home.route,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(route = NoteScreen.Home.route) {
                    isFloatingButtonShow = true
                    HomeScreen(
                        pdfViewClick = {
                            navController.navigate(NoteScreen.PDFView.route)
                        },
                        notesClick = {
                            navController.navigate(NoteScreen.Notes.route)
                        },
                    )
                }
                composable(route = NoteScreen.PDFView.route) {
                    isFloatingButtonShow = false
                    PDFViewScreen()
                }
                composable(route = NoteScreen.Notes.route) {
                    isFloatingButtonShow = true
                    NotesScreen { note ->
                        val noteJson: String? = Json.encodeToString(note)
                        navController.currentBackStackEntry?.savedStateHandle?.set("note", noteJson)
                        navController.navigate(NoteScreen.ViewNote.route)
                    }
                }
                composable(route = NoteScreen.ViewNote.route) { backStackEntry ->
                    isFloatingButtonShow = false

                    val noteJson: String? = navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<String>("note")
                    noteJson?.let {
                        val note: Note = Json.decodeFromString<Note>(noteJson)
                        ViewNoteScreen(note) {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "note",
                                noteJson
                            )
                            navController.navigate(NoteScreen.NoteEditor.route)
                        }
                    }

                }
                composable(route = NoteScreen.NoteEditor.route) {
                    isFloatingButtonShow = false
                    val noteJson = navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.get<String>("note")
                    val note: Note? = noteJson?.let {
                        Json.decodeFromString<Note>(noteJson)
                    }
                    NoteEditorScreen(existingNote = note) {
                        navController.popBackStack()
                        if (navController.currentBackStackEntry?.destination?.route != NoteScreen.Notes.route) {
                            navController.navigate(NoteScreen.Notes.route)
                        }
                    }
                }

            }
        }
    }
}

//@Composable
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesAppBar(
    currentScreen: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(currentScreen) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Unspecified,
            titleContentColor = Color.Unspecified,
            actionIconContentColor = Color.Unspecified
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(Res.string.back_btn)
                    )
                }
            }
        }
    )
}


