package com.example.finalprojectyelp




import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuItemView
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initializeUI()


        val searchButton = findViewById<Button>(R.id.searchButton)
        val searchText = findViewById<EditText>(R.id.searchText)


        searchButton.setOnClickListener {

            // Getting the user input
            val text = searchText.text.toString()
            val display = Intent(this@HomeActivity, DisplayActivity::class.java)
            display.putExtra("searchKey", text)
            startActivity(display)

        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_home
        bottomNavigationView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.nav_home -> return true
                    R.id.nav_fav -> {
                        val fav = Intent(this@HomeActivity, FavoritesActivity::class.java)
                        startActivity(fav)
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