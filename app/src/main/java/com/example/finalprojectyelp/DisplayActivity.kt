package com.example.finalprojectyelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "o_FjalluFGS5VhvF0YH2RR4_ZiW1ES6X6mQWPlQP2J2BR5r9mLEMdQ7JI7VDNG99A6MoSGUpncvIcGHfF-xz2fLAYdne3EsJdlORPtMRdueGDCbEgYXVQGi-I6OyX3Yx"
class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        initializeUI()
        val getText = intent.getStringExtra("searchKey").toString()


        val businesses = mutableListOf<YelpBusiness>()
        val adapter = BusinessesAdapter(this, businesses)
        findViewById<RecyclerView>(R.id.rvBusinesses).adapter = adapter
        findViewById<RecyclerView>(R.id.rvBusinesses).layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val yelpService = retrofit.create(YelpService::class.java)
        yelpService.searchRestaurants("Bearer $API_KEY",getText, "New York").enqueue(object : Callback<YelpSearchResult> {
            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                    return
                }
                businesses.addAll(body.businesses)
                adapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }
        })

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_results
        bottomNavigationView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.nav_results -> return true
                    R.id.nav_fav -> {
                        startActivity(Intent(applicationContext, FavoritesActivity::class.java))
                        overridePendingTransition(0, 0)
                        return true
                    }
                    R.id.nav_home -> {
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                        overridePendingTransition(0, 0)
                        return true
                    }
                    R.id.nav_logout -> {
                        logout()
                        overridePendingTransition(0, 0)
                        return true
                    }
                }
                return false
            }
        })
    }
    private fun initializeUI() {
        val signOut =
            findViewById<Button>(R.id.signOut)
        signOut.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        startActivity(MainActivity.getLaunchIntent(this))
        FirebaseAuth.getInstance().signOut();
    }
}