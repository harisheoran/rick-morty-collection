package com.example.rickmorty

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView

class MainActivity : AppCompatActivity() {
    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private val epoxyController = CharacterDetailsEpoxyController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.characterByIdLiveData.observe(this) {
            //update the data that is in epoxy controller
            epoxyController.characterResponse = it

            if (it == null) {
                Toast.makeText(this@MainActivity, "Unsuccessful Request", Toast.LENGTH_SHORT).show()
                return@observe
            }
        }

        viewModel.refreshCharacter(characterId = intent.getIntExtra("ID", 1))
        val epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}