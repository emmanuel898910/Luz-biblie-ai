package com.example.ui.screens.study

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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.data.model.StudyTopic
import com.example.ui.theme.GoldPrimary
import com.example.ui.viewmodel.LuzViewModel

@Composable
fun StudyScreen(
  viewModel: LuzViewModel,
  modifier: Modifier = Modifier
) {
  val selectedTopic by viewModel.selectedStudyTopic.collectAsState()
  val customStudyContent by viewModel.customStudyContent.collectAsState()
  val isGeneratingStudy by viewModel.isGeneratingStudy.collectAsState()
  var customTopicPrompt by remember { mutableStateOf("") }
  var showAiGenerator by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header & AI Generator Toggle Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp)),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        ),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(34.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Filled.AutoAwesome,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.primary,
                  modifier = Modifier.size(18.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "Generador de Estudio Bíblico",
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                  text = "Estudio teológico automático con IA",
                  style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = customTopicPrompt,
            onValueChange = { customTopicPrompt = it },
            placeholder = { Text("Ej: Cómo vencer la tentación, La gracia inmerecida...") },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("study_ai_input"),
            shape = RoundedCornerShape(12.dp),
            trailingIcon = {
              if (isGeneratingStudy) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
              } else {
                IconButton(
                  onClick = {
                    if (customTopicPrompt.isNotBlank()) {
                      viewModel.generateCustomStudy(customTopicPrompt)
                    }
                  }
                ) {
                  Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "Generar",
                    tint = MaterialTheme.colorScheme.primary
                  )
                }
              }
            }
          )
        }
      }
    }

    // If Custom Study Content is present, show it prominently
    if (customStudyContent != null) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = "ESTUDIO GENERADO",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
              )
              Text(
                text = "Luz IA • Bíblico",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = customStudyContent.orEmpty(),
              style = MaterialTheme.typography.bodyMedium.copy(
                lineHeight = 22.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
          }
        }
      }
    }

    // Topics Selector Carousel
    item {
      Text(
        text = "TEMAS DE ESTUDIO BÍBLICO",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 1.sp
        )
      )
      Spacer(modifier = Modifier.height(8.dp))
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(viewModel.studyRepo.studyTopics) { topic ->
          val isSelected = topic.id == selectedTopic.id && customStudyContent == null
          Card(
            modifier = Modifier
              .clickable { viewModel.selectStudyTopic(topic) }
              .testTag("study_topic_${topic.id}"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
              else MaterialTheme.colorScheme.surface
            ),
            border = CardDefaults.outlinedCardBorder()
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(text = topic.iconEmoji, fontSize = 20.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = topic.title,
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
              )
            }
          }
        }
      }
    }

    // Active Study Topic Deep Dive
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          // Topic Title & Description
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = selectedTopic.iconEmoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = selectedTopic.title,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
              Text(
                text = selectedTopic.description,
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // 1. Key Verses Section
          StudySectionHeader(title = "1. Versículos Clave de la Escritura", icon = Icons.Filled.MenuBook)
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            selectedTopic.verses.forEach { (reference, text) ->
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(10.dp))
                  .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                  .padding(12.dp)
              ) {
                Column {
                  Text(
                    text = reference,
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.primary
                    )
                  )
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontStyle = FontStyle.Italic,
                      lineHeight = 18.sp
                    )
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // 2. Biblical Context Section
          StudySectionHeader(title = "2. Contexto Bíblico e Histórico", icon = Icons.Filled.BookmarkBorder)
          Text(
            text = selectedTopic.biblicalContext,
            style = MaterialTheme.typography.bodyMedium.copy(
              lineHeight = 22.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          )

          Spacer(modifier = Modifier.height(16.dp))

          // 3. Theological Explanation (Distinguishing Text vs Interpretation)
          StudySectionHeader(title = "3. Explicación y Doctrina Bíblica", icon = Icons.Outlined.Lightbulb)
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
              .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(12.dp))
              .padding(12.dp)
          ) {
            Column {
              Text(
                text = "Diferenciación entre texto e interpretación teológica:",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary
                )
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = selectedTopic.theologicalExplanation,
                style = MaterialTheme.typography.bodyMedium.copy(
                  lineHeight = 22.sp,
                  color = MaterialTheme.colorScheme.onSurface
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // 4. Reflection Questions
          StudySectionHeader(title = "4. Preguntas de Reflexión Personal", icon = Icons.Filled.QuestionMark)
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            selectedTopic.reflectionQuestions.forEachIndexed { idx, q ->
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
              ) {
                Text(
                  text = "${idx + 1}.",
                  fontWeight = FontWeight.Bold,
                  color = MaterialTheme.colorScheme.primary,
                  modifier = Modifier.width(20.dp)
                )
                Text(
                  text = q,
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    lineHeight = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                  )
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // 5. Practical Application
          StudySectionHeader(title = "5. Aplicación Práctica", icon = Icons.Outlined.TaskAlt)
          Text(
            text = selectedTopic.practicalApplication,
            style = MaterialTheme.typography.bodyMedium.copy(
              lineHeight = 20.sp,
              color = MaterialTheme.colorScheme.onSurface
            )
          )

          Spacer(modifier = Modifier.height(16.dp))

          // 6. Closing Prayer
          StudySectionHeader(title = "6. Oración de Consagración", icon = Icons.Filled.VolunteerActivism)
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(GoldPrimary.copy(alpha = 0.1f))
              .padding(12.dp)
          ) {
            Text(
              text = selectedTopic.closingPrayer,
              style = MaterialTheme.typography.bodySmall.copy(
                fontStyle = FontStyle.Italic,
                lineHeight = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
              )
            )
          }
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun StudySectionHeader(
  title: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(bottom = 6.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = MaterialTheme.colorScheme.primary,
      modifier = Modifier.size(16.dp)
    )
    Spacer(modifier = Modifier.width(6.dp))
    Text(
      text = title,
      style = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
      )
    )
  }
}
