package com.lipssoftware.wargag.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.lipssoftware.wargag.databinding.FragmentContentBinding
import com.lipssoftware.wargag.databinding.FragmentFavoritesBinding
import com.lipssoftware.wargag.ui.feed.FeedAdapter
import com.lipssoftware.wargag.ui.feed.FeedFragmentDirections
import com.lipssoftware.wargag.ui.feed.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val favoritesVM: FavoritesViewModel by viewModels()
    private lateinit var adapter: FeedAdapter
    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        setupRecyclerView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        favoritesVM.favorites.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addItems(it)
                (view?.parent as? ViewGroup)?.doOnPreDraw {
                    startPostponedEnterTransition()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.favoritesList.apply {
            this@FavoritesFragment.adapter = FeedAdapter({ post, extras ->
                post?.let {
                    findNavController().navigate(
                        FavoritesFragmentDirections.actionNavFavoritesToNavDetail(
                            post
                        ), extras
                    )
                }
            }, { post ->
                favoritesVM.updatePost(post)
            }) {
                //favoritesVM.refreshDataFromRepository()
            }
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            adapter = this@FavoritesFragment.adapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            setItemViewCacheSize(25)
        }
    }
}