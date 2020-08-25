package com.lipssoftware.wargag.ui.detail

import android.accounts.NetworkErrorException
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.lipssoftware.wargag.R
import com.lipssoftware.wargag.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

/** Class for
*/
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel: DetailViewModel by viewModels()
    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var binding: FragmentDetailBinding
    private val commentsAdapter = CommentsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.supportPostponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)
        prepareRecycler()
        viewModel.setContent(args.postDetail)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(binding.item.textNickname, "nickname_${args.postDetail.content_id}")
        ViewCompat.setTransitionName(binding.item.imageProfileStatus, "profileImage_${args.postDetail.content_id}")
        ViewCompat.setTransitionName(binding.item.imagePreview, "image_${args.postDetail.content_id}")
        ViewCompat.setTransitionName(binding.item.textDescription, "description_${args.postDetail.content_id}")
        ViewCompat.setTransitionName(binding.item.textDate, "date_${args.postDetail.content_id}")
        ViewCompat.setTransitionName(binding.item.textRating, "rating_${args.postDetail.content_id}")
        ViewCompat.setTransitionName(binding.item.buttonFavorite, "favoriteButton_${args.postDetail.content_id}")

        viewModel.content.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                binding.item.textNickname.text = it.nickname
                binding.item.textDate.text = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format((it.created_at) * 1000)
                if(!data.description.isNullOrEmpty()) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        binding.item.textDescription.text =
                            Html.fromHtml(data.description, Html.FROM_HTML_MODE_COMPACT)
                    else
                        binding.item.textDescription.text = Html.fromHtml(data.description)
                }
                it.rating.let {rating ->
                    binding.item.textRating.text = rating.toString()
                    if(rating < 0)
                        binding.item.textRating.setTextColor(Color.RED)
                    else if (rating > 0)
                        binding.item.textRating.setTextColor(Color.GREEN)
                }
                binding.item.buttonFavorite.isSelected = it.isFavorite
                binding.item.buttonFavorite.setOnClickListener { view ->
                    view.isSelected = !view.isSelected
                    it.isFavorite = !it.isFavorite
                    viewModel.updatePost(it)
                }
                try {
                    Picasso.get().load(data.author?.status_image).placeholder(R.drawable.ava_new)
                        .into(binding.item.imageProfileStatus)
                    data.media_url?.let { url ->
                        Picasso.get().load(url).placeholder(R.drawable.placeholder)
                            .into(binding.item.imagePreview)
                    }
                }
                catch (ex: NetworkErrorException){
                    Toast.makeText(context, "Нет сети!", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.comments.observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                commentsAdapter.setComments(it)
            }
        })

       binding.executePendingBindings()
    }

    private fun prepareRecycler() {
        binding.detailComments.layoutManager = LinearLayoutManager(context)
        binding.detailComments.adapter = commentsAdapter
    }
}