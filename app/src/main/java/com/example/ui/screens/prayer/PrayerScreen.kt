package com.example.ui.screens.prayer

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PrayerCategory
import com.example.data.model.PrayerItem
import com.example.ui.theme.FaithEmerald
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PrayerPurple
import com.example.ui.viewmodel.LuzViewModel

@Composable
fun PrayerScreen(
  viewModel: LuzViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val prayers by viewModel.prayers.collectAsState()
  var selectedCategory by remember { mutableStateOf<PrayerCategory?>(null) }
  var showAddDialog by remember { mutableStateOf(false) }
  var activePrayerForAnswer by remember { mutableStateOf<PrayerItem?>(null) }
  var answeredNoteText by remember { mutableStateOf("") }

  // Form states for new prayer
  var newTitle by remember { mutableStateOf("") }
  var newDesc by remember { mutableStateOf("") }
  var newCat by remember { mutableStateOf(PrayerCategory.FAMILIA) }

  Scaffold(
    modifier = modifier.fillMaxSize(),
    floatingActionButton = {
      FloatingActionButton(
        onClick = { showAddDialog = true },
        containerColor = MaterialTheme.colorScheme.primary,
        modifier = Modifier.testTag("add_prayer_fab")
      ) {
        Icon(Icons.Filled.Add, contentDescription = "Nueva petición de oración")
      }
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 16.dp)
    ) {
      // Top Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(onClick = onBack) {
          Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "Altar de Oración 🙏",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      }

      // Inspirational Scripture Banner
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = PrayerPurple.copy(alpha = 0.1f)),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "«Por nada estéis afanosos, sino sean conocidas vuestras peticiones delante de Dios en toda oración y ruego, con acción de gracias.»",
            style = MaterialTheme.typography.bodySmall.copy(
              fontStyle = FontStyle.Italic,
              lineHeight = 18.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "— Filipenses 4:6",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = PrayerPurple
            )
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Category filters
      LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        item {
          PrayerCategoryFilterChip(
            label = "Todas",
            isSelected = selectedCategory == null,
            onClick = { selectedCategory = null }
          )
        }
        items(PrayerCategory.values()) { cat ->
          PrayerCategoryFilterChip(
            label = "${cat.emoji} ${cat.label}",
            isSelected = selectedCategory == cat,
            onClick = { selectedCategory = cat }
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      val filteredPrayers = if (selectedCategory == null) prayers else prayers.filter { it.category == selectedCategory }

      if (filteredPrayers.isEmpty()) {
        Box(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
          contentAlignment = Alignment.Center
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("🕊️", fontSize = 42.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "No tienes peticiones registradas",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Toca el botón '+' para elevar una nueva oración",
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          }
        }
      } else {
        LazyColumn(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
          items(filteredPrayers) { prayer ->
            PrayerItemCard(
              prayer = prayer,
              onToggleAnswered = {
                if (!prayer.isAnswered) {
                  activePrayerForAnswer = prayer
                  answeredNoteText = ""
                } else {
                  viewModel.togglePrayerAnswered(prayer)
                }
              },
              onDelete = { viewModel.deletePrayer(prayer.id) }
            )
          }
          item {
            Spacer(modifier = Modifier.height(60.dp))
          }
        }
      }
    }
  }

  // Add Prayer Dialog
  if (showAddDialog) {
    AlertDialog(
      onDismissRequest = { showAddDialog = false },
      title = { Text("Nueva Petición de Oración") },
      text = {
        Column(modifier = Modifier.fillMaxWidth()) {
          OutlinedTextField(
            value = newTitle,
            onValueChange = { newTitle = it },
            label = { Text("Motivo / Título") },
            placeholder = { Text("Ej: Salud de mi abuela, Paz en mi trabajo...") },
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = newDesc,
            onValueChange = { newDesc = it },
            label = { Text("Detalles y clamor") },
            placeholder = { Text("Escribe tus pensamientos o promesa bíblica...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
          )

          Spacer(modifier = Modifier.height(12.dp))

          Text("Categoría:", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
          Spacer(modifier = Modifier.height(6.dp))

          LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            items(PrayerCategory.values()) { cat ->
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(
                    if (newCat == cat) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant
                  )
                  .clickable { newCat = cat }
                  .padding(horizontal = 10.dp, vertical = 6.dp)
              ) {
                Text(
                  text = "${cat.emoji} ${cat.label}",
                  fontSize = 11.sp,
                  color = if (newCat == cat) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      },
      confirmButton = {
        Button(
          onClick = {
            if (newTitle.isNotBlank()) {
              viewModel.addPrayer(newTitle, newDesc, newCat)
              newTitle = ""
              newDesc = ""
              showAddDialog = false
            }
          },
          enabled = newTitle.isNotBlank()
        ) {
          Text("Guardar en mi Altar")
        }
      },
      dismissButton = {
        TextButton(onClick = { showAddDialog = false }) {
          Text("Cancelar")
        }
      }
    )
  }

  // Mark Answered Dialog
  if (activePrayerForAnswer != null) {
    val prayer = activePrayerForAnswer!!
    AlertDialog(
      onDismissRequest = { activePrayerForAnswer = null },
      title = { Text("¡Alabado sea Dios! 🙌") },
      text = {
        Column {
          Text(
            text = "Escribe tu testimonio o cómo respondió el Señor a esta oración:",
            style = MaterialTheme.typography.bodySmall
          )
          Spacer(modifier = Modifier.height(10.dp))
          OutlinedTextField(
            value = answeredNoteText,
            onValueChange = { answeredNoteText = it },
            placeholder = { Text("Dios obró paz y sanidad conforme a su voluntad...") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
          )
        }
      },
      confirmButton = {
        Button(
          onClick = {
            val note = if (answeredNoteText.isNotBlank()) answeredNoteText else "¡Dios respondió con gracia y fidelidad!"
            viewModel.togglePrayerAnswered(prayer, note)
            activePrayerForAnswer = null
          }
        ) {
          Text("Marcar Respondida (+40 XP)")
        }
      },
      dismissButton = {
        TextButton(onClick = { activePrayerForAnswer = null }) {
          Text("Cancelar")
        }
      }
    )
  }
}

@Composable
fun PrayerItemCard(
  prayer: PrayerItem,
  onToggleAnswered: () -> Unit,
  onDelete: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (prayer.isAnswered) FaithEmerald.copy(alpha = 0.08f)
      else MaterialTheme.colorScheme.surface
    ),
    border = CardDefaults.outlinedCardBorder()
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = prayer.category.emoji, fontSize = 18.sp)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = prayer.title,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
        }

        IconButton(
          onClick = onDelete,
          modifier = Modifier.size(28.dp)
        ) {
          Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f), modifier = Modifier.size(18.dp))
        }
      }

      if (prayer.description.isNotBlank()) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = prayer.description,
          style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
          )
        )
      }

      if (prayer.isAnswered && !prayer.answeredNote.isNullOrBlank()) {
        Spacer(modifier = Modifier.height(8.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(FaithEmerald.copy(alpha = 0.15f))
            .padding(10.dp)
        ) {
          Column {
            Text(
              text = "Testimonio / Respuesta:",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = FaithEmerald)
            )
            Text(
              text = prayer.answeredNote,
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurface)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = prayer.dateCreated,
          fontSize = 11.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
          modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
              if (prayer.isAnswered) FaithEmerald.copy(alpha = 0.2f)
              else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable { onToggleAnswered() }
            .padding(horizontal = 10.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = if (prayer.isAnswered) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircleOutline,
            contentDescription = null,
            tint = if (prayer.isAnswered) FaithEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (prayer.isAnswered) "Respondida" else "Marcar como respondida",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (prayer.isAnswered) FaithEmerald else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }
  }
}

@Composable
fun PrayerCategoryFilterChip(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(16.dp))
      .background(
        if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant
      )
      .clickable { onClick() }
      .padding(horizontal = 12.dp, vertical = 6.dp)
  ) {
    Text(
      text = label,
      fontSize = 12.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
      color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}
