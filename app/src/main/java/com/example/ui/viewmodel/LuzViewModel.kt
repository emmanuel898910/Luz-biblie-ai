package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BibleNoteEntity
import com.example.data.local.BookmarkEntity
import com.example.data.local.LuzDatabase
import com.example.data.local.UserStatsEntity
import com.example.data.model.BibleBook
import com.example.data.model.BibleChapter
import com.example.data.model.BibleVerse
import com.example.data.model.CaminoStatus
import com.example.data.model.CaminoStep
import com.example.data.model.DailyChallenge
import com.example.data.model.LevelSystem
import com.example.data.model.MemorizationVerse
import com.example.data.model.PrayerCategory
import com.example.data.model.PrayerItem
import com.example.data.model.QuizCategory
import com.example.data.model.QuizQuestion
import com.example.data.model.StudyTopic
import com.example.data.model.UserLevel
import com.example.data.model.VerseOfTheDay
import com.example.data.remote.ChatMessage
import com.example.data.remote.GeminiService
import com.example.data.repository.BibleRepository
import com.example.data.repository.CaminoRepository
import com.example.data.repository.MemoryRepository
import com.example.data.repository.QuizRepository
import com.example.data.repository.StudyRepository
import com.example.data.repository.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class LuzNavigationTab(val label: String, val iconName: String) {
  INICIO("Inicio", "home"),
  BIBLIA("Biblia", "menu_book"),
  ESTUDIO("Estudio", "school"),
  IA("IA Bíblica", "smart_toy"),
  PERFIL("Perfil", "person")
}

enum class SubScreen {
  NONE,
  QUIZ,
  CAMINO,
  MEMORIZACION,
  ORACION,
  CONFIGURACION,
  BOOKMARKS_AND_NOTES
}

enum class ThemeMode {
  SYSTEM,
  LIGHT,
  DARK
}

enum class BibleFontSize(val spScale: Float, val label: String) {
  SMALL(0.85f, "Pequeño"),
  NORMAL(1.0f, "Normal"),
  LARGE(1.25f, "Grande")
}

class LuzViewModel(application: Application) : AndroidViewModel(application) {

  private val db = LuzDatabase.getInstance(application)
  val bibleRepo = BibleRepository(db.luzDao())
  val studyRepo = StudyRepository()
  val quizRepo = QuizRepository()
  val caminoRepo = CaminoRepository()
  val memoryRepo = MemoryRepository()
  val userDataRepo = UserDataRepository(db.luzDao())
  val geminiService = GeminiService()

  // Navigation State
  private val _currentTab = MutableStateFlow(LuzNavigationTab.INICIO)
  val currentTab: StateFlow<LuzNavigationTab> = _currentTab.asStateFlow()

  private val _activeSubScreen = MutableStateFlow(SubScreen.NONE)
  val activeSubScreen: StateFlow<SubScreen> = _activeSubScreen.asStateFlow()

  // Settings
  private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
  val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

  private val _fontSize = MutableStateFlow(BibleFontSize.NORMAL)
  val fontSize: StateFlow<BibleFontSize> = _fontSize.asStateFlow()

  private val _dailyRemindersEnabled = MutableStateFlow(true)
  val dailyRemindersEnabled: StateFlow<Boolean> = _dailyRemindersEnabled.asStateFlow()

  // User Stats & Leveling
  val userStats: StateFlow<UserStatsEntity> = userDataRepo.userStatsFlow
    .stateIn(viewModelScope, SharingStarted.Eagerly, UserStatsEntity())

  val currentLevel: StateFlow<UserLevel> = MutableStateFlow(LevelSystem.levels.first()).apply {
    viewModelScope.launch {
      userStats.collect { stats ->
        value = LevelSystem.getLevelForXp(stats.xp)
      }
    }
  }

  // Daily verse & challenges
  val todayVerse: VerseOfTheDay = bibleRepo.getTodayVerse()

  private val _completedChallengeIds = userDataRepo.getCompletedChallengeIds()
    .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

  val dailyChallenges: StateFlow<List<DailyChallenge>> = combine(
    _completedChallengeIds,
    userStats
  ) { completedIds, _ ->
    userDataRepo.getDailyChallenges(completedIds)
  }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

  // Bible State
  private val _selectedBook = MutableStateFlow(bibleRepo.books.first { it.id == "SAL" })
  val selectedBook: StateFlow<BibleBook> = _selectedBook.asStateFlow()

  private val _selectedChapterNum = MutableStateFlow(23)
  val selectedChapterNum: StateFlow<Int> = _selectedChapterNum.asStateFlow()

  private val _currentChapter = MutableStateFlow<BibleChapter?>(null)
  val currentChapter: StateFlow<BibleChapter?> = _currentChapter.asStateFlow()

  private val _bibleSearchQuery = MutableStateFlow("")
  val bibleSearchQuery: StateFlow<String> = _bibleSearchQuery.asStateFlow()

  private val _searchResults = MutableStateFlow<List<BibleVerse>>(emptyList())
  val searchResults: StateFlow<List<BibleVerse>> = _searchResults.asStateFlow()

  val bookmarks: StateFlow<List<BookmarkEntity>> = bibleRepo.getBookmarks()
    .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

  val bibleNotes: StateFlow<List<BibleNoteEntity>> = bibleRepo.getNotes()
    .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

  // AI Chat State
  private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
    listOf(
      ChatMessage(
        text = "¡Paz y gracia de Dios contigo! ✝️ Soy tu asistente bíblico Luz. Puedo ayudarte a escudriñar las Sagradas Escrituras, explicar versículos, profundizar en la historia bíblica y clarificar diferencias entre el texto sagrado y sus interpretaciones teológicas. ¿Qué deseas explorar hoy?",
        isUser = false
      )
    )
  )
  val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

  private val _isAiThinking = MutableStateFlow(false)
  val isAiThinking: StateFlow<Boolean> = _isAiThinking.asStateFlow()

  // Study Mode State
  private val _selectedStudyTopic = MutableStateFlow(studyRepo.studyTopics.first())
  val selectedStudyTopic: StateFlow<StudyTopic> = _selectedStudyTopic.asStateFlow()

  private val _customStudyInput = MutableStateFlow("")
  val customStudyInput: StateFlow<String> = _customStudyInput.asStateFlow()

  private val _customStudyContent = MutableStateFlow<String?>(null)
  val customStudyContent: StateFlow<String?> = _customStudyContent.asStateFlow()

  private val _isGeneratingStudy = MutableStateFlow(false)
  val isGeneratingStudy: StateFlow<Boolean> = _isGeneratingStudy.asStateFlow()

  // Quiz State
  private val _selectedQuizCategory = MutableStateFlow<QuizCategory?>(null)
  val selectedQuizCategory: StateFlow<QuizCategory?> = _selectedQuizCategory.asStateFlow()

  private val _currentQuizQuestions = MutableStateFlow(quizRepo.questions)
  val currentQuizQuestions: StateFlow<List<QuizQuestion>> = _currentQuizQuestions.asStateFlow()

  private val _quizQuestionIndex = MutableStateFlow(0)
  val quizQuestionIndex: StateFlow<Int> = _quizQuestionIndex.asStateFlow()

  private val _selectedOptionIndex = MutableStateFlow<Int?>(null)
  val selectedOptionIndex: StateFlow<Int?> = _selectedOptionIndex.asStateFlow()

  private val _isOptionSubmitted = MutableStateFlow(false)
  val isOptionSubmitted: StateFlow<Boolean> = _isOptionSubmitted.asStateFlow()

  private val _quizScore = MutableStateFlow(0)
  val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

  private val _quizCompleted = MutableStateFlow(false)
  val quizCompleted: StateFlow<Boolean> = _quizCompleted.asStateFlow()

  // Prayers State
  val prayers: StateFlow<List<PrayerItem>> = userDataRepo.prayersFlow
    .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

  // Memorization State
  private val _currentMemoryVerseIndex = MutableStateFlow(0)
  val currentMemoryVerseIndex: StateFlow<Int> = _currentMemoryVerseIndex.asStateFlow()

  val memoryVerses = memoryRepo.versesToMemorize

  private val _revealedWords = MutableStateFlow(false)
  val revealedWords: StateFlow<Boolean> = _revealedWords.asStateFlow()

  private val _userArrangedWords = MutableStateFlow<List<String>>(emptyList())
  val userArrangedWords: StateFlow<List<String>> = _userArrangedWords.asStateFlow()

  private val _availableScrambledWords = MutableStateFlow<List<String>>(emptyList())
  val availableScrambledWords: StateFlow<List<String>> = _availableScrambledWords.asStateFlow()

  private val _memoryExerciseCompleted = MutableStateFlow(false)
  val memoryExerciseCompleted: StateFlow<Boolean> = _memoryExerciseCompleted.asStateFlow()

  // Camino State
  private val _caminoSteps = MutableStateFlow(caminoRepo.steps)
  val caminoSteps: StateFlow<List<CaminoStep>> = _caminoSteps.asStateFlow()

  private val _activeCaminoStep = MutableStateFlow<CaminoStep?>(null)
  val activeCaminoStep: StateFlow<CaminoStep?> = _activeCaminoStep.asStateFlow()

  private val _caminoCheckpointSelected = MutableStateFlow<Int?>(null)
  val caminoCheckpointSelected: StateFlow<Int?> = _caminoCheckpointSelected.asStateFlow()

  private val _caminoCheckpointAnswered = MutableStateFlow(false)
  val caminoCheckpointAnswered: StateFlow<Boolean> = _caminoCheckpointAnswered.asStateFlow()

  init {
    viewModelScope.launch {
      userDataRepo.ensureInitialized()
      loadChapter(_selectedBook.value, _selectedChapterNum.value)
      initMemorizationVerse(0)
      loadCaminoProgress()
    }
  }

  fun setTab(tab: LuzNavigationTab) {
    _currentTab.value = tab
    _activeSubScreen.value = SubScreen.NONE
  }

  fun navigateToSubScreen(subScreen: SubScreen) {
    _activeSubScreen.value = subScreen
  }

  fun closeSubScreen() {
    _activeSubScreen.value = SubScreen.NONE
  }

  fun setThemeMode(mode: ThemeMode) {
    _themeMode.value = mode
  }

  fun setFontSize(size: BibleFontSize) {
    _fontSize.value = size
  }

  fun toggleReminders(enabled: Boolean) {
    _dailyRemindersEnabled.value = enabled
  }

  // --- Bible Logic ---
  fun selectBook(book: BibleBook) {
    _selectedBook.value = book
    _selectedChapterNum.value = 1
    loadChapter(book, 1)
  }

  fun selectChapter(chapterNum: Int) {
    _selectedChapterNum.value = chapterNum
    loadChapter(_selectedBook.value, chapterNum)
  }

  private fun loadChapter(book: BibleBook, chapterNum: Int) {
    val chapter = bibleRepo.getChapterVerses(book, chapterNum)
    _currentChapter.value = chapter
  }

  fun searchBible(query: String) {
    _bibleSearchQuery.value = query
    if (query.isBlank()) {
      _searchResults.value = emptyList()
    } else {
      _searchResults.value = bibleRepo.searchVerses(query)
    }
  }

  fun markChapterFinished() {
    viewModelScope.launch {
      userDataRepo.incrementChaptersRead()
      userDataRepo.completeChallenge("ch_read", 30)
    }
  }

  fun toggleBookmark(verse: BibleVerse, isBookmarked: Boolean) {
    viewModelScope.launch {
      bibleRepo.toggleBookmark(_selectedBook.value, verse.chapter, verse.verse, verse.text, isBookmarked)
    }
  }

  fun addVerseNote(verse: BibleVerse, noteText: String) {
    if (noteText.isBlank()) return
    viewModelScope.launch {
      bibleRepo.saveNote(_selectedBook.value, verse.chapter, verse.verse, verse.text, noteText)
    }
  }

  fun deleteNote(noteId: Long) {
    viewModelScope.launch {
      bibleRepo.deleteNote(noteId)
    }
  }

  // --- AI Chat Logic ---
  fun sendAiMessage(userText: String) {
    if (userText.isBlank()) return
    val userMsg = ChatMessage(text = userText.trim(), isUser = true)
    _chatMessages.value = _chatMessages.value + userMsg
    _isAiThinking.value = true

    viewModelScope.launch {
      val result = geminiService.askBibleAi(userText.trim(), _chatMessages.value)
      val aiResponse = result.getOrElse { "Lo siento, ha ocurrido un error de conexión al consultar la IA bíblica." }
      _chatMessages.value = _chatMessages.value + ChatMessage(text = aiResponse, isUser = false)
      _isAiThinking.value = false
      userDataRepo.addXp(10)
    }
  }

  // --- Study Logic ---
  fun selectStudyTopic(topic: StudyTopic) {
    _selectedStudyTopic.value = topic
    _customStudyContent.value = null
  }

  fun generateCustomStudy(customPrompt: String) {
    if (customPrompt.isBlank()) return
    _isGeneratingStudy.value = true
    _customStudyContent.value = null

    viewModelScope.launch {
      val prompt = """
        Genera un estudio bíblico estructurado y devocional sobre el siguiente tema: "$customPrompt".
        Incluye:
        1. Versículos clave (con cita exacta y texto bíblico).
        2. Contexto bíblico e histórico del pasaje.
        3. Explicación teológica clara (distinguiendo entre texto e interpretación).
        4. Tres preguntas de reflexión personal.
        5. Aplicación práctica para la vida diaria.
        6. Una oración final de consagración.
      """.trimIndent()

      val result = geminiService.askBibleAi(prompt)
      _customStudyContent.value = result.getOrNull() ?: "No se pudo generar el estudio en este momento."
      _isGeneratingStudy.value = false
      userDataRepo.addXp(25)
    }
  }

  // --- Quiz Logic ---
  fun filterQuizCategory(category: QuizCategory?) {
    _selectedQuizCategory.value = category
    _currentQuizQuestions.value = quizRepo.getQuestionsByCategory(category)
    resetQuiz()
  }

  fun selectQuizOption(index: Int) {
    if (_isOptionSubmitted.value) return
    _selectedOptionIndex.value = index
  }

  fun submitQuizAnswer() {
    val q = _currentQuizQuestions.value.getOrNull(_quizQuestionIndex.value) ?: return
    val selected = _selectedOptionIndex.value ?: return

    _isOptionSubmitted.value = true
    if (selected == q.correctOptionIndex) {
      val earned = q.difficulty.points
      _quizScore.value += earned
      viewModelScope.launch {
        userDataRepo.addXp(earned)
        userDataRepo.incrementQuizzes()
      }
    }
  }

  fun nextQuizQuestion() {
    if (_quizQuestionIndex.value + 1 < _currentQuizQuestions.value.size) {
      _quizQuestionIndex.value += 1
      _selectedOptionIndex.value = null
      _isOptionSubmitted.value = false
    } else {
      _quizCompleted.value = true
      viewModelScope.launch {
        userDataRepo.completeChallenge("ch_quiz", 25)
      }
    }
  }

  fun resetQuiz() {
    _quizQuestionIndex.value = 0
    _selectedOptionIndex.value = null
    _isOptionSubmitted.value = false
    _quizScore.value = 0
    _quizCompleted.value = false
  }

  // --- Prayers Logic ---
  fun addPrayer(title: String, description: String, category: PrayerCategory) {
    if (title.isBlank()) return
    viewModelScope.launch {
      userDataRepo.addPrayer(title.trim(), description.trim(), category)
      userDataRepo.completeChallenge("ch_prayer", 20)
    }
  }

  fun togglePrayerAnswered(prayer: PrayerItem, note: String = "¡Gracias a Dios por responder!") {
    viewModelScope.launch {
      val updated = prayer.copy(isAnswered = !prayer.isAnswered, answeredNote = note)
      userDataRepo.updatePrayer(updated)
      if (updated.isAnswered) {
        userDataRepo.markPrayerAnswered(prayer.id, note)
      }
    }
  }

  fun deletePrayer(prayerId: Long) {
    viewModelScope.launch {
      userDataRepo.deletePrayer(prayerId)
    }
  }

  // --- Memorization Logic ---
  fun initMemorizationVerse(index: Int) {
    _currentMemoryVerseIndex.value = index
    _revealedWords.value = false
    _memoryExerciseCompleted.value = false
    _userArrangedWords.value = emptyList()

    val verse = memoryVerses.getOrNull(index) ?: return
    val (_, scrambled) = memoryRepo.getScrambledWords(verse)
    _availableScrambledWords.value = scrambled
  }

  fun toggleRevealWords() {
    _revealedWords.value = !_revealedWords.value
  }

  fun pickWordInScramble(word: String) {
    _availableScrambledWords.value = _availableScrambledWords.value - word
    _userArrangedWords.value = _userArrangedWords.value + word

    val verse = memoryVerses[_currentMemoryVerseIndex.value]
    val (correctOrder, _) = memoryRepo.getScrambledWords(verse)

    if (_userArrangedWords.value.size == correctOrder.size) {
      val isAllCorrect = _userArrangedWords.value.map { it.lowercase() } == correctOrder.map { it.lowercase() }
      if (isAllCorrect) {
        _memoryExerciseCompleted.value = true
        viewModelScope.launch {
          userDataRepo.saveMemorizationProgress(verse.reference, verse.fullText, verse.theme, 100)
          userDataRepo.completeChallenge("ch_memory", 35)
        }
      }
    }
  }

  fun resetWordScramble() {
    val verse = memoryVerses[_currentMemoryVerseIndex.value]
    val (_, scrambled) = memoryRepo.getScrambledWords(verse)
    _userArrangedWords.value = emptyList()
    _availableScrambledWords.value = scrambled
    _memoryExerciseCompleted.value = false
  }

  // --- Camino Logic ---
  private fun loadCaminoProgress() {
    viewModelScope.launch {
      userDataRepo.getCaminoProgress().collect { progressList ->
        val completedIds = progressList.filter { it.isCompleted }.map { it.stepId }.toSet()
        val updated = caminoRepo.steps.map { step ->
          val status = when {
            completedIds.contains(step.id) -> CaminoStatus.COMPLETED
            step.id == 1 || completedIds.contains(step.id - 1) -> CaminoStatus.IN_PROGRESS
            else -> CaminoStatus.LOCKED
          }
          step.copy(status = status)
        }
        _caminoSteps.value = updated
      }
    }
  }

  fun openCaminoStep(step: CaminoStep) {
    _activeCaminoStep.value = step
    _caminoCheckpointSelected.value = null
    _caminoCheckpointAnswered.value = false
  }

  fun closeCaminoStep() {
    _activeCaminoStep.value = null
  }

  fun selectCaminoCheckpointOption(index: Int) {
    if (_caminoCheckpointAnswered.value) return
    _caminoCheckpointSelected.value = index
    _caminoCheckpointAnswered.value = true

    val step = _activeCaminoStep.value ?: return
    if (index == step.checkpointCorrectIndex) {
      viewModelScope.launch {
        userDataRepo.completeCaminoStep(step.id, step.rewardXp)
      }
    }
  }

  // --- Challenges ---
  fun completeChallenge(challenge: DailyChallenge) {
    viewModelScope.launch {
      userDataRepo.completeChallenge(challenge.id, challenge.xpReward)
    }
  }
}
