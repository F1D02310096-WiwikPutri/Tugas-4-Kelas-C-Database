package com.example.mobile4t

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity :
    AppCompatActivity() {

    private lateinit var bottomNav:
            BottomNavigationView

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(
            R.layout.activity_main
        )

        bottomNav =
            findViewById(R.id.bottomNav)

        if (savedInstanceState == null) {

            replaceFragment(
                HomeFragment()
            )
        }

        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {

        bottomNav
            .setOnItemSelectedListener {

                when (it.itemId) {

                    R.id.menu_home -> {

                        replaceFragment(
                            HomeFragment()
                        )

                        true
                    }

                    R.id.menu_search -> {

                        replaceFragment(
                            NotesFragment()
                        )

                        true
                    }

                    R.id.menu_profile -> {

                        replaceFragment(
                            ProfileFragment()
                        )

                        true
                    }

                    else -> false
                }
            }
    }

    private fun replaceFragment(
        fragment: Fragment
    ) {

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.frameContainer,
                fragment
            )
            .commit()
    }
}