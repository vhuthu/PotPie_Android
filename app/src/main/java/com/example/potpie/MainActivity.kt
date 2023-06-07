package com.example.potpie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.potpie.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import android.R.*
import android.widget.Toast
import java.util.Collections.list

class MainActivity : AppCompatActivity() {

    lateinit var rvMain : RecyclerView
    lateinit var myAdapter: MyAdapter
    private lateinit var list: ArrayList<UsersItem>

    private lateinit var binding : ActivityMainBinding

    var Base_Url = "https://fakestoreapi.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        replaceFragment(HOME())
        binding.bottomNavigationView.setOnItemReselectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HOME())
                R.id.Cart -> replaceFragment(CART())
                else -> {

                }
            }
            true
        }

        //setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.recycler_view)

        rvMain.layoutManager = LinearLayoutManager(this)

        getAllData()

    }

    private fun getAllData() {

        var retrofit = Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        var retroData = retrofit.getData()

        retroData.enqueue(object: Callback<List<UsersItem>>{
            override fun onResponse(
                call: Call<List<UsersItem>>,
                response: Response<List<UsersItem>>
            ) {
              var data = response.body()!!

                myAdapter = MyAdapter(baseContext,data)
                rvMain.adapter = myAdapter
                Log.d("data",data.toString())

                myAdapter.setOnItemClickListener(object : MyAdapter.onItemClickListener{
                    override fun onItemClickListener(position: Int) {
                      Toast.makeText(this@MainActivity,"You clicked me $position ", Toast.LENGTH_LONG).show()
                    }
                })
            }

            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {

            }

        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction =fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}