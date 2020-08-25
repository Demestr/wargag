package com.lipssoftware.wargag.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.lipssoftware.wargag.R
import com.lipssoftware.wargag.WargagApplication
import com.lipssoftware.wargag.databinding.FragmentSettingsBinding
import com.lipssoftware.wargag.utils.DARK_MODE_PREF
import com.lipssoftware.wargag.utils.Util

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val preferences = Util(requireContext())
        binding.darkModeSwitch.isChecked = preferences.prefs.getBoolean(
            DARK_MODE_PREF, false)
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                preferences.prefs.edit()
                    .putBoolean(DARK_MODE_PREF, isChecked).apply()
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                preferences.prefs.edit()
                    .putBoolean(DARK_MODE_PREF, isChecked).apply()
            }
        }
        return binding.root
    }
}