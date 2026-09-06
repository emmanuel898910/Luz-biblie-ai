package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ui.screens.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.LuzViewModel
import com.example.ui.viewmodel.ThemeMode

class MainActivity : ComponentActivity() {

  private val viewModel: LuzViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      val themeMode by viewModel.themeMode.collectAsState()
      val systemInDark = isSystemInDarkTheme()
      val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> systemInDark
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
      }

      MyApplicationTheme(darkTheme = isDark) {
        Surface(modifier = Modifier.fillMaxSize()) {
          MainScreen(viewModel = viewModel)
        }
      }
    }
  }
}
