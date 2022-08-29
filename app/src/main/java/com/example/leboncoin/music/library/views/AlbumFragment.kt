package com.example.leboncoin.music.library.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.leboncoin.R
import com.example.leboncoin.databinding.AlbumPageFragementBinding
import com.example.leboncoin.music.library.MusicLibraryViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlbumFragment : Fragment() {

    @Inject
    lateinit var viewModel: MusicLibraryViewModel

    private var _binding: AlbumPageFragementBinding? = null
    private val binding get() = _binding!!

    private val args: AlbumFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = AlbumPageFragementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.albumPageTitle.text = args.album.title
        Picasso.get()
            .load(args.album.thumbnailUrl)
            .error(R.drawable.ic_img_not_found)
            .into(binding.albumCover)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}