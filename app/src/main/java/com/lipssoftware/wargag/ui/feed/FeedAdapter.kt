package com.lipssoftware.wargag.ui.feed

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.lipssoftware.wargag.R
import com.lipssoftware.wargag.data.entities.Post
import com.lipssoftware.wargag.databinding.ItemPostBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FeedAdapter @Inject constructor(
    val onCardClick: (Post?, FragmentNavigator.Extras) -> Unit,
    val onFavoriteClick: (Post) -> Unit,
    val onLoadNextPage: () -> Unit) : RecyclerView.Adapter<FeedAdapter.ContentHolder>(){

    private var items: List<Post> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContentHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentHolder, position: Int) {
        val response = items[position]
        holder.bind(response)
        if(position + 1 == items.size) {
            onLoadNextPage()
            Log.i("download", "new load activated")
        }
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = items.size

    fun addItems(posts: List<Post>){
        items = posts
        notifyDataSetChanged()
    }

    inner class ContentHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post?) {
            ViewCompat.setTransitionName(binding.textNickname, "nickname_${post?.content_id}")
            ViewCompat.setTransitionName(binding.imageProfileStatus, "profileImage_${post?.content_id}")
            ViewCompat.setTransitionName(binding.imagePreview, "image_${post?.content_id}")
            ViewCompat.setTransitionName(binding.textDescription, "description_${post?.content_id}")
            ViewCompat.setTransitionName(binding.textDate, "date_${post?.content_id}")
            ViewCompat.setTransitionName(binding.textRating, "rating_${post?.content_id}")
            ViewCompat.setTransitionName(binding.buttonFavorite, "favoriteButton_${post?.content_id}")
            binding.textNickname.text = post?.nickname
            binding.textDate.text = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format((post?.created_at as Long) * 1000)
            if (!post.description.isNullOrEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    binding.textDescription.text = Html.fromHtml(post.description, Html.FROM_HTML_MODE_COMPACT)
                else
                    binding.textDescription.text = Html.fromHtml(post.description)
            }
            else
                binding.textDescription.visibility = View.GONE
            post.rating.let {
                binding.textRating.text = it.toString()
                if(it < 0)
                    binding.textRating.setTextColor(Color.RED)
                else if (it > 0)
                    binding.textRating.setTextColor(Color.GREEN)
            }
            binding.textRating.text = post.rating.toString()
            post.media_url?.let { Picasso.get().load(it).placeholder(R.drawable.placeholder).into(binding.imagePreview) }
            Picasso.get().load(post.author?.status_image).placeholder(R.drawable.ava_new).into(binding.imageProfileStatus)
            val extras = FragmentNavigatorExtras(
                binding.textNickname to "nickname_${post.content_id}",
                binding.imageProfileStatus to "profileImage_${post.content_id}",
                binding.imagePreview to "image_${post.content_id}",
                binding.textDescription to "description_${post.content_id}",
                binding.textDate to "date_${post.content_id}",
                binding.textRating to "rating_${post.content_id}",
                binding.buttonFavorite to "favoriteButton_${post.content_id}"
            )
            binding.buttonFavorite.isSelected = post.isFavorite
            binding.buttonFavorite.setOnClickListener {
                it.isSelected = !it.isSelected
                post.isFavorite = !post.isFavorite
                onFavoriteClick(post)
            }
            binding.executePendingBindings()
            binding.root.setOnClickListener { onCardClick(post, extras) }
        }
    }
}