package com.example.ui.screens.settings

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.BibleFontSize
import com.example.ui.viewmodel.LuzViewModel
import com.example.ui.viewmodel.ThemeMode

@Composable
fun SettingsScreen(
  viewModel: LuzViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val themeMode by viewModel.themeMode.collectAsState()
  val fontSize by viewModel.fontSize.collectAsState()
  val remindersEnabled by viewModel.dailyRemindersEnabled.collectAsState()

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
        text = "Configuración",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }

    LazyColumn(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      // Appearance & Theme
      item {
        Text(
          text = "APARIENCIA",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 1.sp
          )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "Modo de Tema",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              ThemeOptionPill(
                title = "Sistema",
                icon = Icons.Filled.PhoneAndroid,
                isSelected = themeMode == ThemeMode.SYSTEM,
                onClick = { viewModel.setThemeMode(ThemeMode.SYSTEM) },
                modifier = Modifier.weight(1f)
              )
              ThemeOptionPill(
                title = "Claro",
                icon = Icons.Filled.LightMode,
                isSelected = themeMode == ThemeMode.LIGHT,
                onClick = { viewModel.setThemeMode(ThemeMode.LIGHT) },
                modifier = Modifier.weight(1f)
              )
              ThemeOptionPill(
                title = "Oscuro",
                icon = Icons.Filled.DarkMode,
                isSelected = themeMode == ThemeMode.DARK,
                onClick = { viewModel.setThemeMode(ThemeMode.DARK) },
                modifier = Modifier.weight(1f)
              )
            }
          }
        }
      }

      // Font size for Bible Reading
      item {
        Text(
          text = "LECTURA BÍBLICA",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 1.sp
          )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Filled.FormatSize, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Tamaño de Fuente del Texto",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
              )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              BibleFontSize.values().forEach { sizeOption ->
                Box(
                  modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                      if (fontSize == sizeOption) MaterialTheme.colorScheme.primary
                      else MaterialTheme.colorScheme.surfaceVariant
                    )
                    .clickable { viewModel.setFontSize(sizeOption) }
                    .padding(vertical = 10.dp),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = sizeOption.label,
                    fontWeight = if (fontSize == sizeOption) FontWeight.Bold else FontWeight.Normal,
                    color = if (fontSize == sizeOption) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                  )
                }
              }
            }
          }
        }
      }

      // Notifications
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.weight(1f)
            ) {
              Icon(Icons.Filled.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "Recordatorios de Devocional y Racha",
                  style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                  text = "Notificar diariamente el versículo del día",
                  style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
              }
            }
            Switch(
              checked = remindersEnabled,
              onCheckedChange = { viewModel.toggleReminders(it) }
            )
          }
        }
      }

      // Legal & Bible Information
      item {
        Text(
          text = "ACERCA DE LA APLICACIÓN",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 1.sp
          )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = CardDefaults.outlinedCardBorder()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Text("✝️", fontSize = 18.sp)
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "Luz ✝️",
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                  text = "Conoce la Palabra. Fortalece tu fe.",
                  style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
                )
              }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
              text = "• Fuente Bíblica:\nUtiliza textos de la Reina-Valera (RVA 1909), traducción clásica de las Sagradas Escrituras en dominio público legalmente disponible.",
              style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp, color = MaterialTheme.colorScheme.onSurface)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = "• IA Teológica Pedagógica:\nLuz IA opera con modelos Gemini 3.5 Flash diseñados para el escrutinio pedagógico de las Escrituras. Diferencia estrictamente entre el texto bíblico original y las interpretaciones humanas, y no atribuye revelaciones divinas ni suplanta la voz de Dios.",
              style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp, color = MaterialTheme.colorScheme.onSurface)
            )
          }
        }
      }
    }
  }
}

@Composable
fun ThemeOptionPill(
  title: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(
        if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant
      )
      .clickable { onClick() }
      .padding(vertical = 10.dp),
    contentAlignment = Alignment.Center
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = title,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 12.sp
      )
    }
  }
}
