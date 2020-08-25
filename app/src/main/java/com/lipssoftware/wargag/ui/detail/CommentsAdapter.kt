package com.lipssoftware.wargag.ui.detail

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lipssoftware.wargag.R
import com.lipssoftware.wargag.data.entities.Comment
import com.lipssoftware.wargag.databinding.ItemCommentBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    private var commentsList = mutableListOf<Comment>()

    fun setComments(comments: List<Comment>){
        commentsList.clear()
        commentsList.addAll(comments)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comment = commentsList[position]
        holder.bind(comment)
    }

    inner class CommentsViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(comment: Comment){
            binding.textNickname.text = comment.nickname
            binding.textDate.text = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format((comment.created_at) * 1000)
            if (!comment.comment.isNullOrEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    binding.textComment.text = Html.fromHtml(comment.comment, Html.FROM_HTML_MODE_COMPACT)
                else
                    binding.textComment.text = Html.fromHtml(comment.comment)
            }
            Picasso.get().load(comment.author?.status_image).placeholder(R.drawable.ava_new).into(binding.imageProfileStatus)
        }
    }
}
