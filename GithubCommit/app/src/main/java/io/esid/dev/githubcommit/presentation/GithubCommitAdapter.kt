package io.esid.dev.githubcommit.presentation

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.esid.dev.githubcommit.R
import io.esid.dev.githubcommit.model.GithubCommitResponse
import kotlinx.android.synthetic.main.layout_commit.view.*

/**
 * Created by Aldi on 2/25/2018.
 */
class GithubCommitAdapter(val context: Context, var data: MutableList<GithubCommitResponse>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_commit, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ViewHolder) {
            holder.bindCommit(data[position])
        }
    }

    fun updateItem(newData: MutableList<GithubCommitResponse>){
        data = newData
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindCommit(item: GithubCommitResponse) {
            itemView.author.text = item.commit.author.name
            itemView.commitSha.text = String.format(itemView.context.getString(R.string.commit_text), item.sha)
            itemView.message.text = item.commit.message
        }
    }

}