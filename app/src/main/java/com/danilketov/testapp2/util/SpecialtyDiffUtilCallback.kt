package com.danilketov.testapp2.util

import androidx.recyclerview.widget.DiffUtil
import com.danilketov.testapp2.entity.Specialty

class SpecialtyDiffUtilCallback : DiffUtil.Callback() {

    private var oldList: List<Specialty>? = null
    private var newList: List<Specialty>? = null

    fun SpecialtyDiffUtilCallback(oldList: List<Specialty>?, newList: List<Specialty>?) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int {
        return oldList!!.size
    }

    override fun getNewListSize(): Int {
        return newList!!.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSpecialty: Specialty = oldList!![oldItemPosition]
        val newSpecialty: Specialty = newList!![newItemPosition]
        return oldSpecialty.id === newSpecialty.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSpecialty: Specialty = oldList!![oldItemPosition]
        val newSpecialty: Specialty = newList!![newItemPosition]
        return oldSpecialty.name.equals(newSpecialty.name)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}