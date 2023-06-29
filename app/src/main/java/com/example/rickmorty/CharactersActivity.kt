package com.example.rickmorty

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.characters.CharactersListPagingEpoxyController
import com.example.rickmorty.characters.CharactersViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersActivity : AppCompatActivity() {
    val epoxyController = CharactersListPagingEpoxyController(
        ::onCharacterClicked
    )


    val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                epoxyController.submitData(it)

                if (it == null) {
                    Toast.makeText(this@CharactersActivity, "Unsuccessful Request", Toast.LENGTH_SHORT).show()
                    return@collectLatest
                }
            }
        }

        findViewById<EpoxyRecyclerView>(R.id.epoxy_character_recycler_view).setController(epoxyController)
    }

    private fun onCharacterClicked(characterId: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("ID", characterId)
        startActivity(intent)
    }

}