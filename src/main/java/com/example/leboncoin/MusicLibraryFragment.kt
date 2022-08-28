package com.example.leboncoin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.leboncoin.databinding.MusicLibraryFragmentBinding
import com.example.leboncoin.model.AlbumRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MusicLibraryFragment : Fragment() {

    private var _binding: MusicLibraryFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: MusicLibraryViewModel

    @BindView(R.id.albums_count_text)
    lateinit var albumsCount: TextView

    @BindView(R.id.albums_recycler_view)
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: AlbumRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedState: Bundle?): View {
        _binding = MusicLibraryFragmentBinding.inflate(inflater, container, false)
        _binding?.root?.let { ButterKnife.bind(this, it) }
        setUpAlbumRecyclerView()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        this.recyclerView.layoutManager = LinearLayoutManager(this.context)
        this.adapter = AlbumRecyclerAdapter(emptyArray())
        this.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}