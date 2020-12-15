package com.example.finalprojectyelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class FavoritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        initializeUI()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_fav
        bottomNavigationView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.nav_fav -> return true
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