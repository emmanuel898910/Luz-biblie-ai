package com.example.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DailyChallenge
import com.example.data.model.LevelSystem
import com.example.data.model.UserLevel
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.GoldPrimary
import com.example.ui.viewmodel.LuzNavigationTab

@Composable
fun LuzTopBar(
  xp: Int,
  streak: Int,
  level: UserLevel,
  onOpenSettings: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 10.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // App Brand Logo & Slogan
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.weight(1f)
    ) {
      Box(
        modifier = Modifier
          .size(38.dp)
          .clip(CircleShape)
          .background(
            Brush.linearGradient(
              listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.secondary
              )
            )
          ),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = "✝️",
          fontSize = 18.sp
        )
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(
          text = "Luz",
          style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
          )
        )
        Text(
          text = level.title,
          style = MaterialTheme.typography.labelSmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
          )
        )
      }
    }

    // Streak & XP Chips
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      // Streak Pill
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(FlameOrange.copy(alpha = 0.15f))
          .border(1.dp, FlameOrange.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
          .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Filled.LocalFireDepartment,
          contentDescription = "Racha",
          tint = FlameOrange,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "$streak d",
          color = FlameOrange,
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp
        )
      }

      // XP Pill
      Row(
        modifier = Modifier
          .clip(RoundedCornerShape(20.dp))
          .background(GoldPrimary.copy(alpha = 0.15f))
          .border(1.dp, GoldPrimary.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
          .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Filled.EmojiEvents,
          contentDescription = "XP",
          tint = GoldPrimary,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "$xp XP",
          color = GoldPrimary,
          fontWeight = FontWeight.Bold,
          fontSize = 13.sp
        )
      }

      // Settings Icon
      IconButton(
        onClick = onOpenSettings,
        modifier = Modifier
          .size(36.dp)
          .testTag("settings_button")
      ) {
        Icon(
          imageVector = Icons.Filled.Settings,
          contentDescription = "Configuración",
          tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

@Composable
fun LuzBottomNavBar(
  currentTab: LuzNavigationTab,
  onTabSelected: (LuzNavigationTab) -> Unit,
  modifier: Modifier = Modifier
) {
  NavigationBar(
    modifier = modifier,
    containerColor = MaterialTheme.colorScheme.surface,
    tonalElevation = 8.dp
  ) {
    LuzNavigationTab.values().forEach { tab ->
      val isSelected = tab == currentTab
      val icon = when (tab) {
        LuzNavigationTab.INICIO -> if (isSelected) Icons.Filled.Home else Icons.Outlined.Home
        LuzNavigationTab.BIBLIA -> if (isSelected) Icons.Filled.MenuBook else Icons.Outlined.MenuBook
        LuzNavigationTab.ESTUDIO -> if (isSelected) Icons.Filled.School else Icons.Outlined.School
        LuzNavigationTab.IA -> if (isSelected) Icons.Filled.SmartToy else Icons.Outlined.SmartToy
        LuzNavigationTab.PERFIL -> if (isSelected) Icons.Filled.Person else Icons.Outlined.Person
      }

      NavigationBarItem(
        selected = isSelected,
        onClick = { onTabSelected(tab) },
        icon = {
          Icon(
            imageVector = icon,
            contentDescription = tab.label,
            modifier = Modifier.size(24.dp)
          )
        },
        label = {
          Text(
            text = tab.label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          selectedTextColor = MaterialTheme.colorScheme.primary,
          unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
          unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
          indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        ),
        modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
      )
    }
  }
}

@Composable
fun XpProgressBar(
  currentXp: Int,
  currentLevel: UserLevel,
  modifier: Modifier = Modifier
) {
  val nextLevel = LevelSystem.getNextLevel(currentLevel)
  val progress = if (nextLevel != null) {
    val range = (nextLevel.minXp - currentLevel.minXp).toFloat()
    if (range > 0) ((currentXp - currentLevel.minXp) / range).coerceIn(0f, 1f) else 1f
  } else 1f

  Column(modifier = modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = "Nivel ${currentLevel.levelNumber}: ${currentLevel.title}",
        style = MaterialTheme.typography.bodySmall.copy(
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )
      Text(
        text = if (nextLevel != null) "$currentXp / ${nextLevel.minXp} XP" else "$currentXp XP (Máx)",
        style = MaterialTheme.typography.bodySmall.copy(
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.Bold
        )
      )
    }
    Spacer(modifier = Modifier.height(6.dp))
    LinearProgressIndicator(
      progress = { progress },
      modifier = Modifier
        .fillMaxWidth()
        .height(8.dp)
        .clip(RoundedCornerShape(4.dp)),
      color = MaterialTheme.colorScheme.primary,
      trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
  }
}

@Composable
fun DailyChallengeCard(
  challenge: DailyChallenge,
  onComplete: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .animateContentSize(),
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (challenge.isCompleted)
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
      else MaterialTheme.colorScheme.surface
    ),
    border = CardDefaults.outlinedCardBorder()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Icon(
          imageVector = if (challenge.isCompleted) Icons.Filled.CheckCircle else Icons.Filled.CheckCircleOutline,
          contentDescription = null,
          tint = if (challenge.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier
            .size(24.dp)
            .clickable(enabled = !challenge.isCompleted) { onComplete() }
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
          Text(
            text = challenge.title,
            style = MaterialTheme.typography.bodyMedium.copy(
              fontWeight = FontWeight.SemiBold,
              color = MaterialTheme.colorScheme.onSurface
            )
          )
          Text(
            text = challenge.description,
            style = MaterialTheme.typography.bodySmall.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          )
        }
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(
            if (challenge.isCompleted) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            else GoldPrimary.copy(alpha = 0.15f)
          )
          .padding(horizontal = 8.dp, vertical = 4.dp)
      ) {
        Text(
          text = if (challenge.isCompleted) "Listo ✓" else "+${challenge.xpReward} XP",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = if (challenge.isCompleted) MaterialTheme.colorScheme.primary else GoldPrimary
        )
      }
    }
  }
}
