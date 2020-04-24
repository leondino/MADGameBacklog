package com.example.gamebacklog.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gamebacklog.database.GameRepository
import com.example.gamebacklog.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AddActivityViewModel(application: Application) :  AndroidViewModel(application){
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val gameRepository = GameRepository(application.applicationContext)

    val game = MutableLiveData<Game?>()
    val error = MutableLiveData<String?>()
    val success = MutableLiveData<Boolean>()

    fun isGameValid(): Boolean {
        return when {
            game.value == null -> {
                error.value = "Game must not be null"
                false
            }
            game.value!!.title.isBlank() -> {
                error.value = "Please fill in a title"
                false
            }
            game.value!!.platform.isBlank() -> {
                error.value = "Please fill in a platform"
                false
            }
            game.value!!.releaseDate.day <=0 || game.value!!.releaseDate.day >=32
                    || game.value!!.releaseDate.month <=0 || game.value!!.releaseDate.month >=13
                    || game.value!!.releaseDate.year <= 1900 || game.value!!.releaseDate == null -> {
                error.value = "Please fill in a valid date"
                false
            }
            else -> {
                success.value = true
                true
            }
        }
    }

}