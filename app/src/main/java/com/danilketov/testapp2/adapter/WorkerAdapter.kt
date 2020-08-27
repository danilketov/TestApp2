package com.danilketov.testapp2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danilketov.testapp2.R
import com.danilketov.testapp2.entity.Worker
import com.danilketov.testapp2.util.Converter
import com.danilketov.testapp2.util.WorkerDiffUtilCallback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_view_worker.view.*

class WorkerAdapter(listener: OnInfoWorkerClickListener) :
    RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder>() {

    var workers = arrayListOf<Worker>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val onInfoWorkerClickListener: OnInfoWorkerClickListener? = listener

    interface OnInfoWorkerClickListener {
        fun onInfoWorkerClick(worker: Worker?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_worker, parent, false)
        return WorkerViewHolder(itemView)
    }

    override fun getItemCount() = workers.size

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        val worker = workers[position]
        holder.bind(worker)
    }

    fun addItems(items: List<Worker>) {
        checkUpdateItems(items)
    }

    private fun checkUpdateItems(items: List<Worker>) {
        val diffCallback = WorkerDiffUtilCallback(this.workers, items)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        this.workers.clear()
        workers.addAll(items)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class WorkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val firstNameTextView = itemView.first_name_text_view
        private val lastNameTextView = itemView.last_name_text_view
        private val ageTextView = itemView.age_text_view
        private val avatarImageView = itemView.avatar_circle_image_view

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val worker = workers[adapterPosition]
                    onInfoWorkerClickListener?.onInfoWorkerClick(worker)
                }
            }
        }

        fun bind(worker: Worker) {
            with(Converter) {
                firstNameTextView.text = getFormattedString(worker.firstName)
                lastNameTextView.text = getFormattedString(worker.lastName)
                ageTextView.text = getFormattedAge(worker.birthday)

                val avatarUrl = worker.avatarUrl
                Picasso.get()
                    .load(getAvatarWorker(avatarUrl, worker))
                    .fit()
                    .placeholder(R.drawable.no_avatar)
                    .into(avatarImageView)
            }
        }
    }
}