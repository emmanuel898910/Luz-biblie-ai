package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.ui.components.LuzBottomNavBar
import com.example.ui.components.LuzTopBar
import com.example.ui.screens.ai.BiblicalAiScreen
import com.example.ui.screens.bible.BibleScreen
import com.example.ui.screens.camino.CaminoScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.memory.MemoryScreen
import com.example.ui.screens.prayer.PrayerScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.quiz.QuizScreen
import com.example.ui.screens.settings.SettingsScreen
import com.example.ui.screens.study.StudyScreen
import com.example.ui.viewmodel.LuzNavigationTab
import com.example.ui.viewmodel.LuzViewModel
import com.example.ui.viewmodel.SubScreen

@Composable
fun MainScreen(
  viewModel: LuzViewModel,
  modifier: Modifier = Modifier
) {
  val currentTab by viewModel.currentTab.collectAsState()
  val activeSubScreen by viewModel.activeSubScreen.collectAsState()
  val userStats by viewModel.userStats.collectAsState()
  val currentLevel by viewModel.currentLevel.collectAsState()

  // Handle Android system back button when inside a subscreen
  BackHandler(enabled = activeSubScreen != SubScreen.NONE) {
    viewModel.closeSubScreen()
  }

  if (activeSubScreen != SubScreen.NONE) {
    // Render the active subscreen overlay
    Box(
      modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
    ) {
      when (activeSubScreen) {
        SubScreen.QUIZ -> QuizScreen(viewModel = viewModel, onBack = { viewModel.closeSubScreen() })
        SubScreen.CAMINO -> CaminoScreen(viewModel = viewModel, onBack = { viewModel.closeSubScreen() })
        SubScreen.MEMORIZACION -> MemoryScreen(viewModel = viewModel, onBack = { viewModel.closeSubScreen() })
        SubScreen.ORACION -> PrayerScreen(viewModel = viewModel, onBack = { viewModel.closeSubScreen() })
        SubScreen.CONFIGURACION -> SettingsScreen(viewModel = viewModel, onBack = { viewModel.closeSubScreen() })
        else -> Unit
      }
    }
  } else {
    // Primary scaffold with top header and bottom navigation bar
    Scaffold(
      modifier = modifier
        .fillMaxSize()
        .testTag("main_scaffold"),
      topBar = {
        LuzTopBar(
          xp = userStats.xp,
          streak = userStats.streakDays,
          level = currentLevel,
          onOpenSettings = { viewModel.navigateToSubScreen(SubScreen.CONFIGURACION) }
        )
      },
      bottomBar = {
        LuzBottomNavBar(
          currentTab = currentTab,
          onTabSelected = { tab -> viewModel.setTab(tab) }
        )
      }
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
          .background(MaterialTheme.colorScheme.background)
      ) {
        Crossfade(targetState = currentTab, label = "tab_crossfade") { tab ->
          when (tab) {
            LuzNavigationTab.INICIO -> HomeScreen(viewModel = viewModel)
            LuzNavigationTab.BIBLIA -> BibleScreen(viewModel = viewModel)
            LuzNavigationTab.ESTUDIO -> StudyScreen(viewModel = viewModel)
            LuzNavigationTab.IA -> BiblicalAiScreen(viewModel = viewModel)
            LuzNavigationTab.PERFIL -> ProfileScreen(viewModel = viewModel)
          }
        }
      }
    }
  }
}
