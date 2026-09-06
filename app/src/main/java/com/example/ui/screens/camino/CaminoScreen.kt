package com.example.ui.screens.camino

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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CaminoStatus
import com.example.data.model.CaminoStep
import com.example.ui.theme.FaithEmerald
import com.example.ui.theme.GoldPrimary
import com.example.ui.viewmodel.LuzViewModel

@Composable
fun CaminoScreen(
  viewModel: LuzViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val steps by viewModel.caminoSteps.collectAsState()
  val activeStep by viewModel.activeCaminoStep.collectAsState()
  val selectedCheckpointOpt by viewModel.caminoCheckpointSelected.collectAsState()
  val checkpointAnswered by viewModel.caminoCheckpointAnswered.collectAsState()

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
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
      }
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = "El Camino Bíblico 🗺️",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }

    // Intro Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      border = CardDefaults.outlinedCardBorder()
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "Tu sendero a través de las Escrituras",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
          )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Avanza hito a hito desde la Creación en Génesis hasta la Victoria Final en Apocalipsis. Completa la lección y el desafío de control para desbloquear cada etapa.",
          style = MaterialTheme.typography.bodySmall.copy(
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Vertical Timeline
    LazyColumn(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      itemsIndexed(steps) { index, step ->
        CaminoStepNode(
          step = step,
          isLast = index == steps.size - 1,
          onClick = {
            if (step.status != CaminoStatus.LOCKED) {
              viewModel.openCaminoStep(step)
            }
          }
        )
      }
      item {
        Spacer(modifier = Modifier.height(30.dp))
      }
    }
  }

  // Active Step Details & Checkpoint Dialog
  if (activeStep != null) {
    val step = activeStep!!
    AlertDialog(
      onDismissRequest = { viewModel.closeCaminoStep() },
      title = {
        Column {
          Text(
            text = "Hito #${step.order}: ${step.title}",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = step.subtitle,
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.primary)
          )
        }
      },
      text = {
        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          item {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .padding(10.dp)
            ) {
              Text(
                text = step.keyVerse,
                style = MaterialTheme.typography.bodySmall.copy(
                  fontStyle = FontStyle.Italic,
                  fontWeight = FontWeight.Medium,
                  color = MaterialTheme.colorScheme.primary
                )
              )
            }
          }

          item {
            Text(
              text = step.lessonText,
              style = MaterialTheme.typography.bodyMedium.copy(
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
          }

          item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "Desafío de Control:",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
              )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = step.checkpointQuestion,
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
            )
          }

          itemsIndexed(step.checkpointOptions) { idx, opt ->
            val isSelected = selectedCheckpointOpt == idx
            val isCorrect = idx == step.checkpointCorrectIndex

            val bg = when {
              checkpointAnswered && isCorrect -> FaithEmerald.copy(alpha = 0.2f)
              checkpointAnswered && isSelected && !isCorrect -> MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
              isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
              else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }

            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(bg)
                .clickable(enabled = !checkpointAnswered) {
                  viewModel.selectCaminoCheckpointOption(idx)
                }
                .padding(10.dp)
            ) {
              Text(
                text = "${('A'.code + idx).toChar()}. $opt",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }

          if (checkpointAnswered) {
            item {
              val isCorrect = selectedCheckpointOpt == step.checkpointCorrectIndex
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(8.dp))
                  .background(if (isCorrect) FaithEmerald.copy(alpha = 0.2f) else MaterialTheme.colorScheme.error.copy(alpha = 0.2f))
                  .padding(10.dp)
              ) {
                Text(
                  text = if (isCorrect) "¡Correcto! Has recibido +${step.rewardXp} XP y desbloqueado la siguiente etapa." else "Respuesta incorrecta. Revisa el texto e inténtalo de nuevo.",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (isCorrect) FaithEmerald else MaterialTheme.colorScheme.error
                )
              }
            }
          }
        }
      },
      confirmButton = {
        Button(onClick = { viewModel.closeCaminoStep() }) {
          Text("Cerrar")
        }
      }
    )
  }
}

@Composable
fun CaminoStepNode(
  step: CaminoStep,
  isLast: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val isCompleted = step.status == CaminoStatus.COMPLETED
  val isCurrent = step.status == CaminoStatus.IN_PROGRESS
  val isLocked = step.status == CaminoStatus.LOCKED

  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable(enabled = !isLocked) { onClick() }
      .testTag("camino_step_${step.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = when {
        isCompleted -> FaithEmerald.copy(alpha = 0.08f)
        isCurrent -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
        else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
      }
    ),
    border = CardDefaults.outlinedCardBorder()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Step Badge Circle
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(CircleShape)
          .background(
            when {
              isCompleted -> FaithEmerald
              isCurrent -> MaterialTheme.colorScheme.primary
              else -> MaterialTheme.colorScheme.surfaceVariant
            }
          ),
        contentAlignment = Alignment.Center
      ) {
        if (isCompleted) {
          Icon(Icons.Filled.Check, contentDescription = "Completado", tint = Color.White)
        } else if (isCurrent) {
          Icon(Icons.Filled.PlayArrow, contentDescription = "En curso", tint = Color.White)
        } else {
          Icon(Icons.Filled.Lock, contentDescription = "Bloqueado", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Paso ${step.order}: ${step.title}",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = if (isLocked) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
            )
          )
          Text(
            text = "+${step.rewardXp} XP",
            style = MaterialTheme.typography.labelSmall.copy(
              color = GoldPrimary,
              fontWeight = FontWeight.Bold
            )
          )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = step.subtitle,
          style = MaterialTheme.typography.labelSmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
          )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = step.summary,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp
          )
        )
      }
    }
  }
}
