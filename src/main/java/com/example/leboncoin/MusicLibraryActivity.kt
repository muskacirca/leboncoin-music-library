package com.example.leboncoin

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.leboncoin.databinding.ActivityMainBinding
import com.example.leboncoin.model.AlbumRecyclerAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MusicLibraryActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var viewModel: MusicLibraryViewModel

    @BindView(R.id.albums_count_text)
    lateinit var albumsCount: TextView

    @BindView(R.id.albums_recycler_view)
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AlbumRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        (applicationContext as MusicLibraryApplication).appComponent.inject(this)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ButterKnife.bind(this);
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)

        setUpAlbumRecyclerView()

        viewModel.uiState
            .onEach { updateUI(it) }
            .launchIn(MainScope())
    }

    private fun updateUI(state: MusicLibraryState) {

        if (state.isLoading) {

            recyclerView.visibility = View.GONE
            this.albumsCount.text = getString(R.string.albums_loading)

        } else {

            this.albumsCount.visibility = View.GONE
            this.recyclerView.visibility = View.VISIBLE

            adapter.updateData(state.items.toTypedArray())
        }
    }

    private fun setUpAlbumRecyclerView() {
        this.recyclerView.layoutManager = LinearLayoutManager(this)
        this.adapter = AlbumRecyclerAdapter(emptyArray())
        this.recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}