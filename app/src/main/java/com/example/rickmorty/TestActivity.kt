package com.example.rickmorty

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.rickmorty.characters.CharactersRepository
import kotlinx.coroutines.launch

class TestActivity : AppCompatActivity() {

    val repository = CharactersRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        lifecycleScope.launch {
            val resultPage = repository.getCharactersPage(2)
            val result = resultPage?.results

            if (resultPage == null) {
                Toast.makeText(this@TestActivity, "NOT SUCCESS| result page is ${resultPage}", Toast.LENGTH_SHORT).show()
            }

            if (result!!.isEmpty()) {
                Toast.makeText(this@TestActivity, "NOT SUCCESS| result is ${result}", Toast.LENGTH_SHORT).show()
            }

            findViewById<TextView>(R.id.test_textView).text = result.toString()
            findViewById<TextView>(R.id.test2_textView).text = resultPage.toString()
        }
    }

}