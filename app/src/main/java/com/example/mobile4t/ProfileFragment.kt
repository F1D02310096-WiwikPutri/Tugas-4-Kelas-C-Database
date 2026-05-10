package com.example.mobile4t

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment :
    Fragment(R.layout.fragment_profile) {

    private lateinit var sharedPref:
            SharedPreferences

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(
            view,
            savedInstanceState
        )

        sharedPref =

            requireActivity()
                .getSharedPreferences(

                    "SETTING_PREF",

                    android.content.Context.MODE_PRIVATE
                )

        initializeView(view)
    }

    private fun initializeView(
        view: View
    ) {

        val tvProfileName =
            view.findViewById<TextView>(
                R.id.tvProfileName
            )

        val switchDark =
            view.findViewById<Switch>(
                R.id.switchDark
            )

        val switchNotif =
            view.findViewById<Switch>(
                R.id.switchNotif
            )

        val btnLogout =
            view.findViewById<Button>(
                R.id.btnLogout
            )

        tvProfileName.text =
            "Administrator"

        switchDark.isChecked =
            sharedPref.getBoolean(
                "DARK_MODE",
                false
            )

        switchNotif.isChecked =
            sharedPref.getBoolean(
                "NOTIFICATION",
                true
            )

        switchDark
            .setOnCheckedChangeListener {

                    _,
                    isChecked ->

                sharedPref.edit()

                    .putBoolean(
                        "DARK_MODE",
                        isChecked
                    )

                    .apply()
            }

        switchNotif
            .setOnCheckedChangeListener {

                    _,
                    isChecked ->

                sharedPref.edit()

                    .putBoolean(
                        "NOTIFICATION",
                        isChecked
                    )

                    .apply()
            }

        btnLogout.setOnClickListener {

            logoutAccount()
        }
    }

    private fun logoutAccount() {

        val loginPref =

            requireActivity()
                .getSharedPreferences(

                    "LOGIN_PREF",

                    android.content.Context.MODE_PRIVATE
                )

        loginPref.edit()
            .clear()
            .apply()

        startActivity(

            Intent(

                requireContext(),

                LoginActivity::class.java
            )
        )

        requireActivity().finish()
    }
}