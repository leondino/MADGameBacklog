package com.example.gamebacklog.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gamebacklog.R
import com.example.gamebacklog.model.Game

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var addActivityViewModel: AddActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_activity_add)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()
    }

    private fun initViews(){
        fab.setOnClickListener { view ->
            onSaveClick()
        }
    }

    private fun initViewModel(){
        addActivityViewModel = ViewModelProvider(this).get(AddActivityViewModel::class.java)

        addActivityViewModel.error.observe(this, Observer { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })

        addActivityViewModel.success.observe(this, Observer { success ->
            if (success) {
                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_GAME, addActivityViewModel.game.value)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        })
    }

    private fun onSaveClick(){
        addActivityViewModel.game.value = Game(
            title = etTitle.text.toString(),
            releaseDate = Date(),
            platform = etPlatform.text.toString()
        )
        // Check date on numbers (Not in ViewModel because of problems and structure)
        if((etDay.text.toString()+etMonth.text.toString()+etYear.text.toString())
                .matches("[0-9]+".toRegex()) &&
            !(etDay.text.toString().toInt() <=0 || etDay.text.toString().toInt() >=32
                    || etMonth.text.toString().toInt() <=0 || etMonth.text.toString().toInt() >=13
                    || etYear.text.toString().toInt() <= 1900)) {
            addActivityViewModel.game.value?.releaseDate =
                Date(
                    etYear.text.toString().toInt(),
                    etMonth.text.toString().toInt(), etDay.text.toString().toInt()
                )
        }
        addActivityViewModel.isGameValid()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> { // Used to identify when the user has clicked the back button
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_GAME = "EXTRA_GAME"
    }

}
