package com.example.ui.screens.quiz

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.QuizCategory
import com.example.ui.theme.FaithEmerald
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.GoldPrimary
import com.example.ui.viewmodel.LuzViewModel

@Composable
fun QuizScreen(
  viewModel: LuzViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val questions by viewModel.currentQuizQuestions.collectAsState()
  val questionIndex by viewModel.quizQuestionIndex.collectAsState()
  val selectedCategory by viewModel.selectedQuizCategory.collectAsState()
  val selectedOptionIndex by viewModel.selectedOptionIndex.collectAsState()
  val isSubmitted by viewModel.isOptionSubmitted.collectAsState()
  val quizScore by viewModel.quizScore.collectAsState()
  val isCompleted by viewModel.quizCompleted.collectAsState()

  val currentQ = questions.getOrNull(questionIndex)

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
  ) {
    // Top Bar with Back Button and Score
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
        Text(
          text = "Quiz Bíblico",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
      }

      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(GoldPrimary.copy(alpha = 0.15f))
          .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Filled.EmojiEvents,
          contentDescription = null,
          tint = GoldPrimary,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "$quizScore pts",
          fontWeight = FontWeight.Bold,
          color = GoldPrimary,
          fontSize = 13.sp
        )
      }
    }

    if (isCompleted) {
      // Completed Screen
      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(GoldPrimary.copy(alpha = 0.15f)),
              contentAlignment = Alignment.Center
            ) {
              Text("🏆", fontSize = 36.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
              text = "¡Desafío Bíblico Completado!",
              style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = "Has acumulado $quizScore puntos de experiencia para fortalecer tu conocimiento de la Palabra de Dios.",
              style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
              )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
              onClick = { viewModel.resetQuiz() },
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp)
            ) {
              Icon(Icons.Filled.Refresh, contentDescription = null)
              Spacer(modifier = Modifier.width(8.dp))
              Text("Jugar de nuevo")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
              onClick = onBack,
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp)
            ) {
              Text("Regresar al Inicio")
            }
          }
        }
      }
    } else if (currentQ != null) {
      // Category Pills
      LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        item {
          CategoryChip(
            label = "Todos",
            isSelected = selectedCategory == null,
            onClick = { viewModel.filterQuizCategory(null) }
          )
        }
        items(QuizCategory.values()) { cat ->
          CategoryChip(
            label = cat.title,
            isSelected = selectedCategory == cat,
            onClick = { viewModel.filterQuizCategory(cat) }
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Progress bar
      val progress = (questionIndex.toFloat() + 1f) / questions.size.toFloat()
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(
            text = "Pregunta ${questionIndex + 1} de ${questions.size}",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
          )
          Text(
            text = "${currentQ.difficulty.name} (+${currentQ.difficulty.points} XP)",
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
          )
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
          progress = { progress },
          modifier = Modifier
            .fillMaxWidth()
            .height(6.dp)
            .clip(RoundedCornerShape(3.dp)),
          color = MaterialTheme.colorScheme.primary,
          trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      LazyColumn(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Question Card
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = CardDefaults.outlinedCardBorder()
          ) {
            Column(modifier = Modifier.padding(18.dp)) {
              Text(
                text = currentQ.question,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  lineHeight = 24.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
            }
          }
        }

        // Options List
        items(currentQ.options.size) { idx ->
          val optionText = currentQ.options[idx]
          val isSelected = selectedOptionIndex == idx
          val isCorrect = idx == currentQ.correctOptionIndex

          val backgroundColor = when {
            isSubmitted && isCorrect -> FaithEmerald.copy(alpha = 0.2f)
            isSubmitted && isSelected && !isCorrect -> MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
            isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            else -> MaterialTheme.colorScheme.surface
          }

          val borderColor = when {
            isSubmitted && isCorrect -> FaithEmerald
            isSubmitted && isSelected && !isCorrect -> MaterialTheme.colorScheme.error
            isSelected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
          }

          Card(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(14.dp))
              .clickable(enabled = !isSubmitted) { viewModel.selectQuizOption(idx) }
              .testTag("quiz_option_$idx"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            border = CardDefaults.outlinedCardBorder().copy(
              brush = androidx.compose.ui.graphics.SolidColor(borderColor)
            )
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                Box(
                  modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(borderColor.copy(alpha = 0.2f)),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "${('A'.code + idx).toChar()}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                  text = optionText,
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                )
              }

              if (isSubmitted) {
                if (isCorrect) {
                  Icon(Icons.Filled.CheckCircle, contentDescription = "Correcto", tint = FaithEmerald)
                } else if (isSelected) {
                  Icon(Icons.Filled.Close, contentDescription = "Incorrecto", tint = MaterialTheme.colorScheme.error)
                }
              }
            }
          }
        }

        // Explanation Card once submitted
        if (isSubmitted) {
          item {
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
              )
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Filled.MenuBook,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(
                    text = "Referencia: ${currentQ.biblicalReference}",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.primary
                    )
                  )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                  text = currentQ.explanation,
                  style = MaterialTheme.typography.bodySmall.copy(
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                )
              }
            }
          }
        }
      }

      // Bottom Action Button
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp)
      ) {
        if (!isSubmitted) {
          Button(
            onClick = { viewModel.submitQuizAnswer() },
            enabled = selectedOptionIndex != null,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("quiz_submit_button"),
            shape = RoundedCornerShape(14.dp)
          ) {
            Text("Comprobar respuesta")
          }
        } else {
          Button(
            onClick = { viewModel.nextQuizQuestion() },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("quiz_next_button"),
            shape = RoundedCornerShape(14.dp)
          ) {
            Text(if (questionIndex + 1 < questions.size) "Siguiente pregunta →" else "Ver resultados")
          }
        }
      }
    }
  }
}

@Composable
fun CategoryChip(
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
