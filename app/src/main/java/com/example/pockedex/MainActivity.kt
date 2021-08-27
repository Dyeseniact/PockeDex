package com.example.pockedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pockedex.databinding.ActivityMainBinding
import com.example.pockedex.model.Pokemon
import com.example.pockedex.retrofit.WebServices
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val baseUrl = "https://pokeapi.co/api/v2/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSearch.setOnClickListener {
            searchPokemon()
        }
    }

    private fun searchPokemon(){
        val pokeName = binding.etPokemon.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pokemonServices = retrofit.create(WebServices::class.java)

        val call = pokemonServices.getPokemon(pokeName)

        call?.enqueue(object : Callback<Pokemon>{
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if(response.code()==200){
                    val pokemon = response.body()
                    Log.d("Response",response.body().toString())

                    binding.tvPokemon.text = pokemon?.name
                    binding.tvWeight.text = "Peso: ${pokemon?.weight}"
                    Picasso.get().load(pokemon?.sprites?.photoUrl).into(binding.pokemon)
                }



            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                Log.e("error","Error: $t")

            }

        })
    }
}