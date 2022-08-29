package com.example.leboncoin.music.library.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leboncoin.R
import com.example.leboncoin.databinding.MusicLibraryFragmentBinding
import com.example.leboncoin.music.library.MusicLibraryState
import com.example.leboncoin.music.library.MusicLibraryViewModel
import com.example.leboncoin.music.library.model.Album
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
    lateinit var adapter: AlbumRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedState: Bundle?): View {
        _binding = MusicLibraryFragmentBinding.inflate(inflater, container, false)
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

            this.binding.albumsRecyclerView.visibility = View.GONE
            this.binding.userMessage.visibility = View.VISIBLE
            this.binding.userMessage.text = getString(R.string.albums_loading)

        } else if (state.userMessage != null) {

            this.binding.albumsRecyclerView.visibility = View.GONE
            this.binding.userMessage.visibility = View.VISIBLE
            this.binding.userMessage.text = state.userMessage

        } else {

            this.binding.userMessage.visibility = View.GONE
            this.binding.albumsRecyclerView.visibility = View.VISIBLE

            adapter.updateData(state.items.toTypedArray())
        }
    }

    private fun setUpAlbumRecyclerView() {
        this.binding.albumsRecyclerView.layoutManager = LinearLayoutManager(this.context)
        this.adapter = AlbumRecyclerAdapter(emptyArray(), onAlbumClick())
        this.binding.albumsRecyclerView.adapter = adapter
    }

    private fun onAlbumClick(): OnItemClickListener<Album> {
        return object : OnItemClickListener<Album> {
            override fun onItemClick(view: View, item: Album) {
                Navigation.findNavController(view)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, Bundle())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}