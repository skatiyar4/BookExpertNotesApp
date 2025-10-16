package com.example.bookexpertnotesapp.navigation



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













