package com.example.ui.screens.home

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.DailyChallengeCard
import com.example.ui.components.XpProgressBar
import com.example.ui.theme.FaithEmerald
import com.example.ui.theme.FlameOrange
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.PrayerPurple
import com.example.ui.viewmodel.LuzViewModel
import com.example.ui.viewmodel.SubScreen

@Composable
fun HomeScreen(
  viewModel: LuzViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val userStats by viewModel.userStats.collectAsState()
  val currentLevel by viewModel.currentLevel.collectAsState()
  val dailyChallenges by viewModel.dailyChallenges.collectAsState()
  val todayVerse = viewModel.todayVerse

  var showReflection by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Top Hero Card with Scripture Artwork
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
        ) {
          Image(
            painter = painterResource(id = R.drawable.banner_scripture_light),
            contentDescription = "Luz de las Escrituras",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(
                Brush.verticalGradient(
                  listOf(
                    Color.Transparent,
                    Color(0xFF0A0F1D).copy(alpha = 0.85f)
                  )
                )
              )
          )
          Column(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .padding(16.dp)
          ) {
            Text(
              text = "Conoce la Palabra. Fortalece tu fe.",
              color = Color.White,
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp
            )
            Text(
              text = "Lectura y reflexión diaria guiada por el Espíritu",
              color = Color.White.copy(alpha = 0.8f),
              fontSize = 12.sp
            )
          }
        }
      }
    }

    // Verse of the Day Card
    item {
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("verse_of_day_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(28.dp)
                  .clip(CircleShape)
                  .background(GoldPrimary.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Text("✨", fontSize = 14.sp)
              }
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "VERSÍCULO DEL DÍA",
                style = MaterialTheme.typography.labelMedium.copy(
                  color = MaterialTheme.colorScheme.primary,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 1.sp
                )
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text(
                text = todayVerse.theme,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = todayVerse.text,
            style = MaterialTheme.typography.titleMedium.copy(
              fontStyle = FontStyle.Italic,
              lineHeight = 26.sp,
              color = MaterialTheme.colorScheme.onSurface,
              fontWeight = FontWeight.Medium
            )
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "— ${todayVerse.reference} (RVA 1909)",
            style = MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.Bold
            )
          )

          // Reflection block
          AnimatedVisibility(visible = showReflection) {
            Column(
              modifier = Modifier
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                .padding(12.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Outlined.Lightbulb,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.primary,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Reflexión devocional",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                  )
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = todayVerse.reflection,
                style = MaterialTheme.typography.bodySmall.copy(
                  color = MaterialTheme.colorScheme.onSurfaceVariant,
                  lineHeight = 18.sp
                )
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            OutlinedButton(
              onClick = { showReflection = !showReflection },
              shape = RoundedCornerShape(12.dp)
            ) {
              Text(
                text = if (showReflection) "Ocultar reflexión" else "Ver reflexión",
                fontSize = 12.sp
              )
            }

            Row {
              IconButton(
                onClick = {
                  val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                      Intent.EXTRA_TEXT,
                      "«${todayVerse.text}»\n— ${todayVerse.reference}\n\nCompartido desde Luz ✝️: Conoce la Palabra. Fortalece tu fe."
                    )
                    type = "text/plain"
                  }
                  context.startActivity(Intent.createChooser(sendIntent, "Compartir versículo"))
                }
              ) {
                Icon(
                  imageVector = Icons.Filled.Share,
                  contentDescription = "Compartir",
                  tint = MaterialTheme.colorScheme.primary
                )
              }
            }
          }
        }
      }
    }

    // Level & Progress Bar Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = CardDefaults.outlinedCardBorder()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          XpProgressBar(currentXp = userStats.xp, currentLevel = currentLevel)
        }
      }
    }

    // Quick Action Pillars Grid (Quiz, Camino, Oración, Memorización)
    item {
      Column {
        Text(
          text = "ACTIVIDADES DE CRECIMIENTO",
          style = MaterialTheme.typography.labelMedium.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          ),
          modifier = Modifier.padding(bottom = 10.dp)
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          FeatureActionTile(
            title = "Quiz Bíblico",
            subtitle = "Preguntas & XP",
            icon = Icons.Filled.Psychology,
            accentColor = FlameOrange,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.navigateToSubScreen(SubScreen.QUIZ) }
          )

          FeatureActionTile(
            title = "Camino",
            subtitle = "Ruta de la Fe",
            icon = Icons.Filled.Map,
            accentColor = FaithEmerald,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.navigateToSubScreen(SubScreen.CAMINO) }
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          FeatureActionTile(
            title = "Oración",
            subtitle = "Altar Personal",
            icon = Icons.Filled.VolunteerActivism,
            accentColor = PrayerPurple,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.navigateToSubScreen(SubScreen.ORACION) }
          )

          FeatureActionTile(
            title = "Memorización",
            subtitle = "Retos de versículos",
            icon = Icons.Filled.Extension,
            accentColor = GoldPrimary,
            modifier = Modifier.weight(1f),
            onClick = { viewModel.navigateToSubScreen(SubScreen.MEMORIZACION) }
          )
        }
      }
    }

    // Daily Challenges Section
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "DESAFÍOS DIARIOS",
          style = MaterialTheme.typography.labelMedium.copy(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
        )
        Text(
          text = "${dailyChallenges.count { it.isCompleted }} / ${dailyChallenges.size} listos",
          style = MaterialTheme.typography.bodySmall.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
          )
        )
      }
    }

    items(dailyChallenges) { challenge ->
      DailyChallengeCard(
        challenge = challenge,
        onComplete = { viewModel.completeChallenge(challenge) }
      )
    }

    item {
      Spacer(modifier = Modifier.height(20.dp))
    }
  }
}

@Composable
fun FeatureActionTile(
  title: String,
  subtitle: String,
  icon: ImageVector,
  accentColor: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .clickable { onClick() }
      .testTag("feature_tile_${title.lowercase()}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    border = CardDefaults.outlinedCardBorder()
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(accentColor.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = accentColor,
          modifier = Modifier.size(20.dp)
        )
      }
      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.bodyMedium.copy(
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
      )
      Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall.copy(
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 11.sp
        )
      )
    }
  }
}
