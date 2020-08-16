package com.danilketov.testapp2.util

import androidx.recyclerview.widget.DiffUtil
import com.danilketov.testapp2.entity.Worker

class WorkerDiffUtilCallback : DiffUtil.Callback() {

    private var oldList: List<Worker>? = null
    private var newList: List<Worker>? = null

    fun WorkerDiffUtilCallback(oldList: List<Worker>?, newList: List<Worker>?) {
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
        val oldWorker: Worker = oldList!![oldItemPosition]
        val newWorker: Worker = newList!![newItemPosition]
        return oldWorker.id === newWorker.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldWorker: Worker = oldList!![oldItemPosition]
        val newWorker: Worker = newList!![newItemPosition]
        return oldWorker.lastName.equals(newWorker.lastName) &&
                oldWorker.firstName.equals(newWorker.firstName)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}