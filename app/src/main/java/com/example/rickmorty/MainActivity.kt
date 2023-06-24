package com.example.rickmorty

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameView = findViewById<TextView>(R.id.text_view)
        val genderView = findViewById<ImageView>(R.id.gender_image_view)
        val aliveView = findViewById<TextView>(R.id.alive_text_view)
        val originView = findViewById<TextView>(R.id.origin_name_text_view)
        val speciesView = findViewById<TextView>(R.id.species_name_text_view)
        val characterView = findViewById<ImageView>(R.id.character_image_view)


        viewModel.refreshCharacter(1)

        viewModel.characterByIdLiveData.observe(this) {
            if (it == null) {
                Toast.makeText(this@MainActivity, "Unsuccessful Request", Toast.LENGTH_SHORT).show()
                return@observe
            }
            val body = it
            val name = body.name
            val alive = body.status
            val species = body.species
            val originName = body.origin.name
            val characterImage = body.image
            val gender = body.gender

            if (gender.equals("Male", ignoreCase = true)) {
                genderView.setImageResource(R.drawable.male_24)
            } else {
                genderView.setImageResource(R.drawable.female_24)
            }

            nameView.setText(name)
            aliveView.setText(alive)
            speciesView.setText(species)
            originView.setText(originName)
            Picasso.get().load(characterImage).into(characterView);

        }


    }


}