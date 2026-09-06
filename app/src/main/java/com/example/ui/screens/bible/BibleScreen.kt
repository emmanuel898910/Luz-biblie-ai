package com.example.ui.screens.bible

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BibleBook
import com.example.data.model.BibleVerse
import com.example.data.model.Testament
import com.example.ui.theme.GoldPrimary
import com.example.ui.viewmodel.BibleFontSize
import com.example.ui.viewmodel.LuzViewModel

@Composable
fun BibleScreen(
  viewModel: LuzViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val selectedBook by viewModel.selectedBook.collectAsState()
  val selectedChapterNum by viewModel.selectedChapterNum.collectAsState()
  val currentChapter by viewModel.currentChapter.collectAsState()
  val fontSize by viewModel.fontSize.collectAsState()
  val bookmarks by viewModel.bookmarks.collectAsState()
  val bibleNotes by viewModel.bibleNotes.collectAsState()
  val searchQuery by viewModel.bibleSearchQuery.collectAsState()
  val searchResults by viewModel.searchResults.collectAsState()

  var selectedTestament by remember { mutableStateOf(selectedBook.testament) }
  var showBookPicker by remember { mutableStateOf(false) }
  var showSearchDialog by remember { mutableStateOf(false) }
  var showNotesView by remember { mutableStateOf(false) }
  var activeVerseForNote by remember { mutableStateOf<BibleVerse?>(null) }
  var noteInputText by remember { mutableStateOf("") }
  var showFontSizeMenu by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
  ) {
    // Header Bar with Book Selector, Font Size, Search, and Bookmarks
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Book & Chapter Trigger
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(14.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
          .clickable { showBookPicker = true }
          .padding(horizontal = 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${selectedBook.name} $selectedChapterNum",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
        Text(
          text = " ▾",
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.Bold
        )
      }

      // Actions: Search, Font size, Notes & Bookmarks
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
          onClick = { showSearchDialog = true },
          modifier = Modifier.testTag("bible_search_button")
        ) {
          Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Buscar en la Biblia",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }

        Box {
          IconButton(onClick = { showFontSizeMenu = true }) {
            Icon(
              imageVector = Icons.Filled.FormatSize,
              contentDescription = "Tamaño de letra",
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          DropdownMenu(
            expanded = showFontSizeMenu,
            onDismissRequest = { showFontSizeMenu = false }
          ) {
            BibleFontSize.values().forEach { size ->
              DropdownMenuItem(
                text = {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    if (fontSize == size) {
                      Icon(Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                      Spacer(modifier = Modifier.width(6.dp))
                    }
                    Text(size.label)
                  }
                },
                onClick = {
                  viewModel.setFontSize(size)
                  showFontSizeMenu = false
                }
              )
            }
          }
        }

        IconButton(onClick = { showNotesView = !showNotesView }) {
          Icon(
            imageVector = if (showNotesView) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
            contentDescription = "Notas y Marcadores",
            tint = if (showNotesView) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    // Chapters Row
    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      items(selectedBook.totalChapters) { index ->
        val chapter = index + 1
        val isSelected = chapter == selectedChapterNum
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(
              if (isSelected) MaterialTheme.colorScheme.primary
              else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable { viewModel.selectChapter(chapter) },
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "$chapter",
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Content: Either Scripture Reader or Saved Bookmarks/Notes view
    if (showNotesView) {
      SavedBookmarksAndNotesView(
        bookmarks = bookmarks,
        notes = bibleNotes,
        onDeleteNote = { viewModel.deleteNote(it) },
        onSelectBookmark = { b ->
          val book = viewModel.bibleRepo.books.firstOrNull { it.id == b.bookId }
          if (book != null) {
            viewModel.selectBook(book)
            viewModel.selectChapter(b.chapter)
            showNotesView = false
          }
        }
      )
    } else {
      // Scripture Reader List
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        // Book summary banner
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = "${selectedBook.name} — ${selectedBook.keyTheme}",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
              )
              Text(
                text = selectedBook.summary,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              )
            }
          }
        }

        val verses = currentChapter?.verses.orEmpty()
        items(verses) { verse ->
          val isBookmarked = bookmarks.any { it.bookId == verse.bookId && it.chapter == verse.chapter && it.verse == verse.verse }
          val note = bibleNotes.firstOrNull { it.bookId == verse.bookId && it.chapter == verse.chapter && it.verse == verse.verse }

          VerseItemRow(
            verse = verse,
            isBookmarked = isBookmarked,
            noteText = note?.noteText,
            fontSize = fontSize,
            onToggleBookmark = { viewModel.toggleBookmark(verse, isBookmarked) },
            onAddNote = {
              activeVerseForNote = verse
              noteInputText = note?.noteText.orEmpty()
            },
            onShare = {
              val shareText = "«${verse.text}»\n— ${verse.reference} (RVA)\n\nCompartido desde Luz ✝️: Conoce la Palabra. Fortalece tu fe."
              val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
              }
              context.startActivity(Intent.createChooser(sendIntent, "Compartir versículo"))
            }
          )
        }

        // Chapter Finished Button
        item {
          Spacer(modifier = Modifier.height(12.dp))
          Button(
            onClick = { viewModel.markChapterFinished() },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("finish_chapter_button"),
            shape = RoundedCornerShape(14.dp)
          ) {
            Icon(Icons.Filled.Check, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Marcar capítulo como leído (+25 XP)")
          }
          Spacer(modifier = Modifier.height(40.dp))
        }
      }
    }
  }

  // Book Selection Dialog
  if (showBookPicker) {
    AlertDialog(
      onDismissRequest = { showBookPicker = false },
      title = {
        Text(
          text = "Selecciona un Libro Bíblico",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      },
      text = {
        Column(modifier = Modifier.fillMaxWidth()) {
          TabRow(selectedTabIndex = if (selectedTestament == Testament.ANTIGUO) 0 else 1) {
            Tab(
              selected = selectedTestament == Testament.ANTIGUO,
              onClick = { selectedTestament = Testament.ANTIGUO },
              text = { Text("Antiguo (${viewModel.bibleRepo.books.count { it.testament == Testament.ANTIGUO }})") }
            )
            Tab(
              selected = selectedTestament == Testament.NUEVO,
              onClick = { selectedTestament = Testament.NUEVO },
              text = { Text("Nuevo (${viewModel.bibleRepo.books.count { it.testament == Testament.NUEVO }})") }
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          LazyColumn(
            modifier = Modifier
              .fillMaxWidth()
              .height(350.dp)
          ) {
            val filteredBooks = viewModel.bibleRepo.books.filter { it.testament == selectedTestament }
            items(filteredBooks) { book ->
              val isCurrent = book.id == selectedBook.id
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isCurrent) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else Color.Transparent)
                  .clickable {
                    viewModel.selectBook(book)
                    showBookPicker = false
                  }
                  .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = book.name,
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                    color = if (isCurrent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                  )
                  Text(
                    text = "${book.totalChapters} capítulos • ${book.keyTheme}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
                Text(
                  text = book.abbreviation,
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      },
      confirmButton = {
        TextButton(onClick = { showBookPicker = false }) {
          Text("Cerrar")
        }
      }
    )
  }

  // Note Dialog
  if (activeVerseForNote != null) {
    val verse = activeVerseForNote!!
    AlertDialog(
      onDismissRequest = { activeVerseForNote = null },
      title = {
        Text("Nota en ${verse.reference}")
      },
      text = {
        Column {
          Text(
            text = "«${verse.text}»",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
          Spacer(modifier = Modifier.height(12.dp))
          OutlinedTextField(
            value = noteInputText,
            onValueChange = { noteInputText = it },
            placeholder = { Text("Escribe tu reflexión o apunte aquí...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            viewModel.addVerseNote(verse, noteInputText)
            activeVerseForNote = null
          }
        ) {
          Text("Guardar Nota")
        }
      },
      dismissButton = {
        TextButton(onClick = { activeVerseForNote = null }) {
          Text("Cancelar")
        }
      }
    )
  }

  // Search Dialog
  if (showSearchDialog) {
    AlertDialog(
      onDismissRequest = { showSearchDialog = false },
      title = {
        Text("Buscar en las Escrituras")
      },
      text = {
        Column(modifier = Modifier.fillMaxWidth()) {
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.searchBible(it) },
            placeholder = { Text("Ej: amor, pastor, luz, David...") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          LazyColumn(
            modifier = Modifier
              .fillMaxWidth()
              .height(300.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            items(searchResults) { result ->
              Card(
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable {
                    val book = viewModel.bibleRepo.books.firstOrNull { it.id == result.bookId }
                    if (book != null) {
                      viewModel.selectBook(book)
                      viewModel.selectChapter(result.chapter)
                      showSearchDialog = false
                    }
                  },
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
              ) {
                Column(modifier = Modifier.padding(10.dp)) {
                  Text(
                    text = result.reference,
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.primary
                    )
                  )
                  Text(
                    text = result.text,
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = MaterialTheme.colorScheme.onSurface
                    )
                  )
                }
              }
            }

            if (searchQuery.isNotBlank() && searchResults.isEmpty()) {
              item {
                Text(
                  text = "No se encontraron versículos que coincidan con la búsqueda.",
                  style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                  modifier = Modifier.padding(16.dp)
                )
              }
            }
          }
        }
      },
      confirmButton = {
        TextButton(onClick = { showSearchDialog = false }) {
          Text("Cerrar")
        }
      }
    )
  }
}

@Composable
fun VerseItemRow(
  verse: BibleVerse,
  isBookmarked: Boolean,
  noteText: String?,
  fontSize: BibleFontSize,
  onToggleBookmark: () -> Unit,
  onAddNote: () -> Unit,
  onShare: () -> Unit,
  modifier: Modifier = Modifier
) {
  val baseSp = 16.sp * fontSize.spScale
  val lineSpacingSp = 24.sp * fontSize.spScale

  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isBookmarked) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
      else MaterialTheme.colorScheme.surface
    ),
    border = CardDefaults.outlinedCardBorder()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        // Verse Number
        Box(
          modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "${verse.verse}",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        }

        // Action icons
        Row {
          IconButton(
            onClick = onToggleBookmark,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
              contentDescription = "Guardar marcador",
              tint = if (isBookmarked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(18.dp)
            )
          }

          IconButton(
            onClick = onAddNote,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = if (!noteText.isNullOrBlank()) Icons.Filled.EditNote else Icons.Outlined.Edit,
              contentDescription = "Nota",
              tint = if (!noteText.isNullOrBlank()) GoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(18.dp)
            )
          }

          IconButton(
            onClick = onShare,
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Filled.Share,
              contentDescription = "Compartir versículo",
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(18.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = verse.text,
        fontSize = baseSp,
        lineHeight = lineSpacingSp,
        color = MaterialTheme.colorScheme.onSurface,
        fontFamily = FontFamily.Serif
      )

      if (!noteText.isNullOrBlank()) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(GoldPrimary.copy(alpha = 0.1f))
            .padding(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Filled.EditNote,
            contentDescription = null,
            tint = GoldPrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "Tu nota: $noteText",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        }
      }
    }
  }
}

@Composable
fun SavedBookmarksAndNotesView(
  bookmarks: List<com.example.data.local.BookmarkEntity>,
  notes: List<com.example.data.local.BibleNoteEntity>,
  onDeleteNote: (Long) -> Unit,
  onSelectBookmark: (com.example.data.local.BookmarkEntity) -> Unit
) {
  LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Text(
        text = "MARCADORES GUARDADOS (${bookmarks.size})",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        )
      )
    }

    if (bookmarks.isEmpty()) {
      item {
        Text(
          text = "No tienes versículos marcados como favoritos aún.",
          style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }
    } else {
      items(bookmarks) { b ->
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelectBookmark(b) },
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Text(
              text = "${b.bookName} ${b.chapter}:${b.verse}",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = b.text,
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Guardado el ${b.dateAdded}",
              fontSize = 10.sp,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = "TUS NOTAS BÍBLICAS (${notes.size})",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        )
      )
    }

    if (notes.isEmpty()) {
      item {
        Text(
          text = "No tienes notas escritas en los versículos.",
          style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
      }
    } else {
      items(notes) { n ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder()
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "${n.bookName} ${n.chapter}:${n.verse}",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
              )
              IconButton(
                onClick = { onDeleteNote(n.id) },
                modifier = Modifier.size(24.dp)
              ) {
                Icon(Icons.Filled.Close, contentDescription = "Eliminar nota", tint = MaterialTheme.colorScheme.error)
              }
            }
            Text(
              text = "«${n.verseText}»",
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = n.noteText,
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
          }
        }
      }
    }
  }
}
