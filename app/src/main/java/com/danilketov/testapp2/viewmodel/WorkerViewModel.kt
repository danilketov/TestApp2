package com.danilketov.testapp2.viewmodel

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danilketov.testapp2.App
import com.danilketov.testapp2.entity.Worker
import com.danilketov.testapp2.network.ApiFactory
import com.danilketov.testapp2.repository.DataRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class WorkerViewModel : ViewModel() {

    private val dataRepository: DataRepository? = App.getDataRepository()

    private val workers: MutableLiveData<ArrayList<Worker>> =
        MutableLiveData<ArrayList<Worker>>()
    private val isNetworkException =
        MutableLiveData<Boolean>()
    private val isLoading = MutableLiveData<Boolean>()
    private var disposable: Disposable? = null

    init {
        loadData()
    }

    fun getWorkers(): LiveData<ArrayList<Worker>> {
        return workers
    }

    fun isNetworkException(): LiveData<Boolean> {
        return isNetworkException
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    private fun insertWorkers(workers: ArrayList<Worker>?) {
        GetWorkersAsyncTask().execute(workers)
    }

    inner class GetWorkersAsyncTask :
        AsyncTask<ArrayList<Worker>, Void, ArrayList<Worker>>() {

        override fun onPreExecute() {
            isLoading.value = true
        }

        override fun doInBackground(vararg params: ArrayList<Worker>?): ArrayList<Worker>? {
            return try {
                workers.postValue(dataRepository?.getWorkers(params[0]))
                workers.value
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: ArrayList<Worker>?) {
            isLoading.value = false
        }
    }

    private fun loadData() {
        insertWorkers(null)
        val apiFactory = ApiFactory.getInstance()
        val apiService = apiFactory?.getApiService
        disposable = apiService?.getResponse()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ response -> insertWorkers(response.response) },
                { throwable -> isNetworkException.setValue(true) })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}