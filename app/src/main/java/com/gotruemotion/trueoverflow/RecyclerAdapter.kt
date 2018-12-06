package com.gotruemotion.trueoverflow

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gotruemotion.trueoverflow.model.ItemsItem
import kotlinx.android.synthetic.main.line_item.view.*
import java.util.Date

class RecyclerAdapter(var items: ArrayList<ItemsItem>) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(val lineItem: ConstraintLayout) : RecyclerView.ViewHolder(lineItem)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.MyViewHolder =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.line_item, parent, false) as ConstraintLayout)

    private fun updateHolder(viewHolder: MyViewHolder, result: ItemsItem) {
        fun updateTextView(textView: TextView, text: String) {
            textView.text = text
            textView.contentDescription = text
        }

        with(viewHolder.lineItem) {
            updateTextView(Creator, result.owner.displayName)
            updateTextView(view_count, "${result.viewCount} views")
            updateTextView(answer_count, "${result.answerCount} answers")
            updateTextView(score, "${result.score} pts")
            updateTextView(date, "${DateFormat.format("MMM dd, yyyy", Date(result.creationDate * 1000))}")
            updateTextView(question, "${result.title}")

            checkBox.isChecked = result.isAnswered
            checkBox.contentDescription = if (result.isAnswered) "Question is answered" else "Question is not answered"
            Glide.with(context).load(result.owner.profileImage).into(profile_image)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = updateHolder(holder, items[position])

    override fun getItemCount() = items.size
}
