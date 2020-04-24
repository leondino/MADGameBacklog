package com.example.gamebacklog.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gamebacklog.database.GameRepository
import com.example.gamebacklog.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BacklogActivityViewModel(application: Application): AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val gameRepository = GameRepository(application.applicationContext)

    val games: LiveData<List<Game>> = gameRepository.getAllGames()

    // Move to Edit activity for optional steps!! (Maybe not)
    fun insertReminder(game: Game){
        ioScope.launch {
            gameRepository.insertGame(game)
        }
    }

    fun deleteReminder(game: Game){
        ioScope.launch {
            gameRepository.deleteGame(game)
        }
    }
}