package com.codepath.articlesearch

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox

class SettingsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val fontCheckBox: CheckBox = view.findViewById(R.id.darkmodeCheckBox)
        val prefs = view.context.getSharedPreferences("com.codepath.articlesearch", Context.MODE_PRIVATE)
        val setSpecialFont = prefs.getBoolean(getString(R.string.font_pref), false)
        fontCheckBox.isChecked = setSpecialFont
            if(setSpecialFont) {
            fontCheckBox.typeface = Typeface.DEFAULT_BOLD
        }
        else {
            fontCheckBox.typeface = Typeface.DEFAULT
        }
        fontCheckBox.setOnCheckedChangeListener { compoundBtn, isChecked ->
            if(isChecked) {
                with(prefs.edit()) {
                    putBoolean(getString(R.string.font_pref), true)
                    apply()
                }
                fontCheckBox.typeface = Typeface.DEFAULT_BOLD
            }
            else {
                with(prefs.edit()) {
                    putBoolean(getString(R.string.font_pref), false)
                    apply()
                }
                fontCheckBox.typeface = Typeface.DEFAULT
            }
        }
        return view
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}