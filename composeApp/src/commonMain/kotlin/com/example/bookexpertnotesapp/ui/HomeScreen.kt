package com.example.bookexpertnotesapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bookexpertnotesapp.composeapp.generated.resources.Res
import bookexpertnotesapp.composeapp.generated.resources.notes_lbl
import bookexpertnotesapp.composeapp.generated.resources.pdf_lbl
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
internal fun HomeScreen(pdfViewClick: ()-> Unit,
                        notesClick : () -> Unit,) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = pdfViewClick) {
            Text(stringResource(Res.string.pdf_lbl))
        }


        Button(onClick = notesClick) {
            Text(stringResource(Res.string.notes_lbl))
        }


    }


}