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

class SpecialtyAdapter(listener: OnInfoSpecialClickListener) :
    RecyclerView.Adapter<SpecialtyAdapter.SpecialViewHolder>() {

    var specialties = arrayListOf<Specialty>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val onInfoSpecialClickListener: OnInfoSpecialClickListener? = listener

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
        val diffCallback = SpecialtyDiffUtilCallback(this.specialties, items)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        this.specialties.clear()
        Filter.addUniqueItems(items, specialties)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class SpecialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val specialtyTextView = itemView.specialty_text_view

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) run {
                    val specialty = specialties[adapterPosition]
                    onInfoSpecialClickListener?.onInfoSpecialClick(specialty)
                }
            }
        }

        fun bind(specialty: Specialty) {
            specialtyTextView.text = specialty.name
        }
    }
}