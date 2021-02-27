package com.example.simpleweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.work.*
import com.example.simpleweather.utils.Constants
import com.example.simpleweather.utils.setupWithNavController
import com.example.simpleweather.utils.worker.BackgroundUpdateWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null
    private lateinit var workManager : WorkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
            workManager = WorkManager.getInstance(applicationContext)
            doBackgroundRefresh()
        }
        // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val navGraphIds = listOf(
            R.navigation.nav_graph_nearby,
            R.navigation.nav_graph_home,
            R.navigation.nav_graph_settings
        )

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds,
            supportFragmentManager,
            R.id.fragment_container,
            intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer {
            setupActionBarWithNavController(it)
        })
        currentNavController = controller

    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun doBackgroundRefresh() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicRequest = PeriodicWorkRequest
            .Builder(BackgroundUpdateWorker::class.java, 1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            Constants.REFRESH_CONDITIONS_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest
        )
    }

}