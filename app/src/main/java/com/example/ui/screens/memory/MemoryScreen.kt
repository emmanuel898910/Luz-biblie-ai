package com.example.ui.screens.memory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FaithEmerald
import com.example.ui.theme.GoldPrimary
import com.example.ui.viewmodel.LuzViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MemoryScreen(
  viewModel: LuzViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val currentVerseIndex by viewModel.currentMemoryVerseIndex.collectAsState()
  val revealed by viewModel.revealedWords.collectAsState()
  val userArrangedWords by viewModel.userArrangedWords.collectAsState()
  val availableWords by viewModel.availableScrambledWords.collectAsState()
  val isCompleted by viewModel.memoryExerciseCompleted.collectAsState()

  val currentVerse = viewModel.memoryVerses[currentVerseIndex]

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
  ) {
    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) {
          Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "Memorización Santa 💎",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      }
    }

    // Verses selector carousel
    LazyRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      itemsIndexed(viewModel.memoryVerses) { index, verse ->
        val isSelected = index == currentVerseIndex
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
              if (isSelected) MaterialTheme.colorScheme.primary
              else MaterialTheme.colorScheme.surfaceVariant
            )
            .clickable { viewModel.initMemorizationVerse(index) }
            .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
          Text(
            text = verse.reference,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Verse Flashcard
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      border = CardDefaults.outlinedCardBorder()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = currentVerse.reference,
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        )
        Text(
          text = "Tema: ${currentVerse.theme}",
          style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (revealed) {
          Text(
            text = "«${currentVerse.fullText}»",
            style = MaterialTheme.typography.bodyLarge.copy(
              fontStyle = FontStyle.Italic,
              textAlign = TextAlign.Center,
              lineHeight = 26.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
        } else {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
              .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "• • • [Texto oculto para memorizar] • • •",
              style = MaterialTheme.typography.bodyMedium.copy(
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedButton(
          onClick = { viewModel.toggleRevealWords() },
          shape = RoundedCornerShape(12.dp)
        ) {
          Icon(
            imageVector = if (revealed) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(text = if (revealed) "Ocultar texto" else "Revelar texto")
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Interactive Word Ordering Puzzle
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      border = CardDefaults.outlinedCardBorder()
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Filled.Extension,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Ordena las Palabras",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
          }

          IconButton(
            onClick = { viewModel.resetWordScramble() },
            modifier = Modifier.size(28.dp)
          ) {
            Icon(Icons.Filled.Refresh, contentDescription = "Reiniciar", tint = MaterialTheme.colorScheme.primary)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Target Board (where words are placed)
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
              if (isCompleted) FaithEmerald.copy(alpha = 0.15f)
              else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
            .padding(10.dp)
        ) {
          if (userArrangedWords.isEmpty()) {
            Text(
              text = "Toca las palabras de abajo en el orden correcto para reconstruir el versículo...",
              style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
          } else {
            FlowRow(
              horizontalArrangement = Arrangement.spacedBy(6.dp),
              verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              userArrangedWords.forEach { word ->
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                      if (isCompleted) FaithEmerald else MaterialTheme.colorScheme.primary
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                  Text(
                    text = word,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Available Words Bank
        Text(
          text = "Banco de palabras:",
          style = MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
        Spacer(modifier = Modifier.height(6.dp))

        FlowRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          availableWords.forEach { word ->
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .clickable { viewModel.pickWordInScramble(word) }
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text(
                text = word,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }

        if (isCompleted) {
          Spacer(modifier = Modifier.height(14.dp))
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(FaithEmerald.copy(alpha = 0.2f))
              .padding(12.dp),
            contentAlignment = Alignment.Center
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = FaithEmerald)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "¡Excelente! Versículo memorizado con éxito (+35 XP)",
                fontWeight = FontWeight.Bold,
                color = FaithEmerald,
                fontSize = 12.sp
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))
  }
}
