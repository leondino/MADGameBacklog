package com.example.gamebacklog.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.gamebacklog.model.Game

class GameRepository(context: Context) {
    private var gameDao: GameDao

    init{
        val gameRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDao = gameRoomDatabase!!.gameDao()
    }

    fun getAllGames(): LiveData<List<Game>> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game){
        return gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game){
        return gameDao.deleteGame(game)
    }

    suspend fun deleteAllGames(){
        return gameDao.deleteAllGames()
    }

}