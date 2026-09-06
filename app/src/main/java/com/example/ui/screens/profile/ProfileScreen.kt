package com.example.ui.screens.profile

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.data.model.Achievement
import com.example.data.model.LevelSystem
import com.example.ui.components.XpProgressBar
import com.example.ui.theme.FaithEmerald
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PrayerPurple
import com.example.ui.viewmodel.LuzViewModel
import com.example.ui.viewmodel.SubScreen

@Composable
fun ProfileScreen(
  viewModel: LuzViewModel,
  modifier: Modifier = Modifier
) {
  val userStats by viewModel.userStats.collectAsState()
  val currentLevel by viewModel.currentLevel.collectAsState()
  val achievements = viewModel.userDataRepo.calculateAchievements(userStats)

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // User Identity Card
    item {
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
          // Glow Avatar Circle
          Box(
            modifier = Modifier
              .size(76.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
              .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = currentLevel.badgeEmoji,
              fontSize = 36.sp
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = currentLevel.title,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )

          Text(
            text = "Nivel ${currentLevel.levelNumber} • ${userStats.xp} XP totales acumulados",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary)
          )

          Spacer(modifier = Modifier.height(16.dp))

          XpProgressBar(currentXp = userStats.xp, currentLevel = currentLevel)
        }
      }
    }

    // High Level Metrics Grid (Capítulos, Versículos, Quizzes, Oraciones)
    item {
      Text(
        text = "ESTADÍSTICAS ESPIRITUALES",
        style = MaterialTheme.typography.labelMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          letterSpacing = 1.sp
        )
      )

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        ProfileMetricCard(
          title = "Capítulos Leídos",
          value = "${userStats.chaptersRead}",
          emoji = "📖",
          accentColor = MaterialTheme.colorScheme.primary,
          modifier = Modifier.weight(1f)
        )
        ProfileMetricCard(
          title = "Racha Actual",
          value = "${userStats.streakDays} días",
          emoji = "🔥",
          accentColor = FlameOrange,
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        ProfileMetricCard(
          title = "Memorizados",
          value = "${userStats.versesMemorized}",
          emoji = "💎",
          accentColor = FaithEmerald,
          modifier = Modifier.weight(1f)
        )
        ProfileMetricCard(
          title = "Quizzes Superados",
          value = "${userStats.quizzesCompleted}",
          emoji = "🧠",
          accentColor = PrayerPurple,
          modifier = Modifier.weight(1f)
        )
      }
    }

    // Achievements (Logros) Section
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "LOGROS Y RECOMPENSAS",
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            letterSpacing = 1.sp
          )
        )
        Text(
          text = "${achievements.count { it.isUnlocked }} / ${achievements.size} desbloqueados",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
          )
        )
      }
    }

    items(achievements) { ach ->
      AchievementCard(achievement = ach)
    }

    // Settings Quick Link
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { viewModel.navigateToSubScreen(SubScreen.CONFIGURACION) }
          .testTag("profile_settings_link"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Filled.Settings,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
              text = "Configuración y Personalización",
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
          }
          Icon(
            imageVector = Icons.Filled.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun ProfileMetricCard(
  title: String,
  value: String,
  emoji: String,
  accentColor: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = CardDefaults.outlinedCardBorder()
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(text = emoji, fontSize = 20.sp)
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = accentColor
        )
      )
      Text(
        text = title,
        style = MaterialTheme.typography.bodySmall.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 11.sp
        )
      )
    }
  }
}

@Composable
fun AchievementCard(
  achievement: Achievement,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (achievement.isUnlocked) MaterialTheme.colorScheme.surface
      else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
    ),
    border = CardDefaults.outlinedCardBorder()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(
            if (achievement.isUnlocked) GoldPrimary.copy(alpha = 0.15f)
            else MaterialTheme.colorScheme.surfaceVariant
          ),
        contentAlignment = Alignment.Center
      ) {
        if (achievement.isUnlocked) {
          Text(text = achievement.iconEmoji, fontSize = 22.sp)
        } else {
          Icon(
            imageVector = Icons.Filled.Lock,
            contentDescription = "Bloqueado",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
          )
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
            text = achievement.title,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.Bold,
              color = if (achievement.isUnlocked) MaterialTheme.colorScheme.onSurface
              else MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
          Text(
            text = if (achievement.isUnlocked) "Desbloqueado ✓" else "${achievement.currentProgress}/${achievement.requiredCount}",
            style = MaterialTheme.typography.labelSmall.copy(
              color = if (achievement.isUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
              fontWeight = FontWeight.Bold
            )
          )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = achievement.description,
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 11.sp
          )
        )

        if (!achievement.isUnlocked) {
          Spacer(modifier = Modifier.height(6.dp))
          val progress = (achievement.currentProgress.toFloat() / achievement.requiredCount.toFloat()).coerceIn(0f, 1f)
          LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
              .fillMaxWidth()
              .height(4.dp)
              .clip(RoundedCornerShape(2.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
          )
        }
      }
    }
  }
}
