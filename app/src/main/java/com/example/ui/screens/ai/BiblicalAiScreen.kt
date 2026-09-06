package com.example.ui.screens.ai

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.remote.ChatMessage
import com.example.ui.theme.GoldPrimary
import com.example.ui.viewmodel.LuzViewModel

@Composable
fun BiblicalAiScreen(
  viewModel: LuzViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val messages by viewModel.chatMessages.collectAsState()
  val isThinking by viewModel.isAiThinking.collectAsState()
  var inputPrompt by remember { mutableStateOf("") }
  val listState = rememberLazyListState()

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      listState.animateScrollToItem(messages.size - 1)
    }
  }

  val suggestedPrompts = listOf(
    "¿Qué enseña la Biblia sobre la gracia?",
    "Explícame el significado de Romanos 8:28",
    "¿Quién fue el rey David y qué lecciones nos deja?",
    "¿Cómo encontrar paz en medio de la tormenta?",
    "Diferencia entre justificación y santificación"
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp)
  ) {
    // Safety & Ethical Guide Banner
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp),
      shape = RoundedCornerShape(12.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
      border = CardDefaults.outlinedCardBorder()
    ) {
      Row(
        modifier = Modifier.padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Filled.Info,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Luz IA es un asistente pedagógico para el estudio bíblico. Diferencia entre texto explícito e interpretación teológica. No sustituye la oración ni pretende emitir revelaciones divinas.",
          style = MaterialTheme.typography.bodySmall.copy(
            fontSize = 11.sp,
            lineHeight = 15.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        )
      }
    }

    // Quick Suggestions Carousel
    LazyRow(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      items(suggestedPrompts) { prompt ->
        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            .clickable {
              viewModel.sendAiMessage(prompt)
            }
            .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
          Text(
            text = prompt,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Medium
          )
        }
      }
    }

    // Chat Message Stream
    LazyColumn(
      state = listState,
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(messages) { msg ->
        ChatMessageBubble(
          message = msg,
          onCopy = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("Mensaje Luz", msg.text))
            Toast.makeText(context, "Texto copiado al portapapeles", Toast.LENGTH_SHORT).show()
          }
        )
      }

      if (isThinking) {
        item {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Box(
              modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Text("✝️", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Card(
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                CircularProgressIndicator(
                  modifier = Modifier.size(16.dp),
                  strokeWidth = 2.dp,
                  color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Consultando las Escrituras...",
                  fontSize = 12.sp,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
                )
              }
            }
          }
        }
      }
    }

    // Input Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      OutlinedTextField(
        value = inputPrompt,
        onValueChange = { inputPrompt = it },
        placeholder = { Text("Haz una pregunta bíblica a Luz IA...") },
        modifier = Modifier
          .weight(1f)
          .testTag("ai_chat_input"),
        shape = RoundedCornerShape(24.dp),
        maxLines = 4
      )

      Spacer(modifier = Modifier.width(8.dp))

      IconButton(
        onClick = {
          if (inputPrompt.isNotBlank() && !isThinking) {
            viewModel.sendAiMessage(inputPrompt)
            inputPrompt = ""
          }
        },
        enabled = inputPrompt.isNotBlank() && !isThinking,
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(
            if (inputPrompt.isNotBlank() && !isThinking) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.surfaceVariant
          )
          .testTag("ai_send_button")
      ) {
        Icon(
          imageVector = Icons.Filled.Send,
          contentDescription = "Enviar",
          tint = if (inputPrompt.isNotBlank() && !isThinking) MaterialTheme.colorScheme.onPrimary
          else MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

@Composable
fun ChatMessageBubble(
  message: ChatMessage,
  onCopy: () -> Unit
) {
  val isUser = message.isUser

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
    verticalAlignment = Alignment.Top
  ) {
    if (!isUser) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(GoldPrimary.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
      ) {
        Text("✝️", fontSize = 14.sp)
      }
      Spacer(modifier = Modifier.width(8.dp))
    }

    Card(
      shape = RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp,
        bottomStart = if (isUser) 16.dp else 4.dp,
        bottomEnd = if (isUser) 4.dp else 16.dp
      ),
      colors = CardDefaults.cardColors(
        containerColor = if (isUser) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surface
      ),
      border = if (!isUser) CardDefaults.outlinedCardBorder() else null,
      modifier = Modifier.widthIn(max = 300.dp)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Text(
          text = message.text,
          style = MaterialTheme.typography.bodyMedium.copy(
            color = if (isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
            lineHeight = 20.sp
          )
        )

        if (!isUser) {
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
          ) {
            IconButton(
              onClick = onCopy,
              modifier = Modifier.size(24.dp)
            ) {
              Icon(
                imageVector = Icons.Filled.ContentCopy,
                contentDescription = "Copiar texto",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(14.dp)
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun Modifier.widthIn(max: androidx.compose.ui.unit.Dp): Modifier {
  return this.then(Modifier.fillMaxWidth(0.85f))
}
