package com.danilketov.testapp2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danilketov.testapp2.R
import com.danilketov.testapp2.entity.Specialty
import com.danilketov.testapp2.util.Filter
import com.danilketov.testapp2.util.SpecialtyDiffUtilCallback
import kotlinx.android.synthetic.main.item_view_special.view.*

class SpecialtyAdapter(listener: OnInfoSpecialClickListener) : RecyclerView.Adapter<SpecialtyAdapter.SpecialViewHolder>() {

    var specialties = arrayListOf<Specialty>()
        set(value) {
            field = value
            notifyDataSetChanged() // попробуй применить checkUpdateItems
        }

    private val onInfoSpecialClickListener: OnInfoSpecialClickListener? = null

    interface OnInfoSpecialClickListener {
        fun onInfoSpecialClick(specialty: Specialty?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_special, parent, false)
        return SpecialViewHolder(itemView)
    }

    override fun getItemCount() = specialties.size

    override fun onBindViewHolder(holder: SpecialViewHolder, position: Int) {
        val specialty = specialties[position]
        holder.bind(specialty)
    }

    fun addItems(items: List<Specialty>) {
        checkUpdateItems(items)
    }

    private fun checkUpdateItems(items: List<Specialty>) {
        val diffCallback = SpecialtyDiffUtilCallback(this.specialties, specialties)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        this.specialties.clear()
        Filter.addUniqueItems(items, specialties)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class SpecialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val specialtyTextView = itemView.specialty_text_view

        init {
            itemView.setOnClickListener(View.OnClickListener {
                val adapterPos = adapterPosition
                if (adapterPos!= RecyclerView.NO_POSITION) run {
                    val specialty = specialties[adapterPos]
                    onInfoSpecialClickListener?.onInfoSpecialClick(specialty)
                }
            })
        }

        fun bind(specialty: Specialty) {
            specialtyTextView.text = specialty.name
        }
    }
}