package com.lipssoftware.wargag.ui.feed

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
import androidx.recyclerview.widget.RecyclerView
import com.lipssoftware.wargag.databinding.FragmentContentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private val feedViewModel: FeedViewModel by viewModels()
    private lateinit var adapter: FeedAdapter
    private lateinit var binding: FragmentContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentBinding.inflate(inflater)
        setupRecyclerView()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        feedViewModel.content.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addItems(it)
                (view?.parent as? ViewGroup)?.doOnPreDraw {
                    startPostponedEnterTransition()
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.contentList.apply {
            this@FeedFragment.adapter = FeedAdapter({ post, extras ->
                post?.let {
                    findNavController().navigate(
                        FeedFragmentDirections.actionNavContentToNavDetail(
                            post
                        ), extras
                    )
                }
            }, { post ->
                feedViewModel.updatePost(post)
            }) {
                feedViewModel.refreshDataFromRepository()
            }
            val manager = LinearLayoutManager(context)
            layoutManager = manager
            adapter = this@FeedFragment.adapter
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
            setItemViewCacheSize(25)
        }
    }
}