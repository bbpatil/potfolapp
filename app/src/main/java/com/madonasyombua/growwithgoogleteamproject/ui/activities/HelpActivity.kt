/*Copyright (c) 2018 Madona Syombua

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package com.madonasyombua.growwithgoogleteamproject.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.madonasyombua.growwithgoogleteamproject.R
import com.madonasyombua.growwithgoogleteamproject.databinding.ActivityHelpSearchviewBinding
import com.madonasyombua.growwithgoogleteamproject.ui.SharedPref
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.miguelcatalan.materialsearchview.MaterialSearchView.SearchViewListener

class HelpActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {
    private var searchView: MaterialSearchView? = null

    private lateinit var sharedPref: SharedPref
    private var prevState = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHelpSearchviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPref(this)

        if (sharedPref.loadNightModeState()) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
        prevState = sharedPref.loadNightModeState()


        setCorrectTheme()
        setSupportActionBar(binding.tbHelp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Help"

        binding.tbHelp.setNavigationOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    MainActivity::class.java
                )
            )
        }
        val searchView = findViewById<MaterialSearchView>(R.id.search_view)
        searchView.setVoiceSearch(true)
        searchView.setCursorDrawable(R.drawable.color_cursor_white)
        searchView.setSuggestions(resources.getStringArray(R.array.search_suggestions))
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Snackbar.make(findViewById(R.id.container), "Query: $query", Snackbar.LENGTH_LONG)
                    .show()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView.setOnSearchViewListener(object : SearchViewListener {
            override fun onSearchViewShown() { // Do something something
            }

            override fun onSearchViewClosed() { // Do something something
            }
        })
        binding.faq.setOnClickListener { openFaq() }
        binding.contact.setOnClickListener { openContact() }
        binding.terms.setOnClickListener { openTerms() }
    }

    private fun openContact() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse("https://www.madonahsyombua.com/contact")
        startActivity(intent)
    }

    private fun openTerms() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse("https://www.madonahsyombua.com/privacy-policy")
        startActivity(intent)
    }

    private fun openFaq() {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.data = Uri.parse("https://www.madonahsyombua.com/faqs")
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this)
            .unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun setCorrectTheme() {
        val sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPref.setNightModeState(sharedPreference.getBoolean("enable_dark_mode", false))
        sharedPreference.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()
        //Activities must be started again to show the theme change,
        if (prevState != sharedPref.loadNightModeState()) {
            startActivity(Intent(this, this.javaClass))
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.help_menu, menu)
        val item = menu.findItem(R.id.action_search)
        // searchView!!.setMenuItem(item)
        return true
    }

    override fun onBackPressed() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            val matches = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0) {
                val searchWord = matches[0]
                if (!TextUtils.isEmpty(searchWord)) {
                    searchView?.setQuery(searchWord, false)
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "enable_dark_mode") {
            sharedPref.setNightModeState(sharedPreferences.getBoolean(key, false))
        }
    }
}