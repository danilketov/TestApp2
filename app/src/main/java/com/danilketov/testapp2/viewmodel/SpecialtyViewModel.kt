package com.danilketov.testapp2.viewmodel

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danilketov.testapp2.App
import com.danilketov.testapp2.entity.Specialty
import com.danilketov.testapp2.network.ApiFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class SpecialtyViewModel : ViewModel() {

    private val dataRepository = App.getDataRepository()
    private val specialties: MutableLiveData<ArrayList<Specialty>> =
        MutableLiveData()
    private val isNetworkException = MutableLiveData<Boolean>()
    private val isLoading = MutableLiveData<Boolean>()
    private var disposable: Disposable? = null

    fun getSpecialties(): LiveData<ArrayList<Specialty>> {
        return specialties
    }

    fun isNetworkException(): LiveData<Boolean> {
        return isNetworkException
    }

    fun isLoading(): LiveData<Boolean> {
        return isLoading
    }

    private fun insertSpecialties(specialties: ArrayList<Specialty>?) {
        GetSpecialtiesAsyncTask().execute(specialties)
    }

    @SuppressLint("StaticFieldLeak")
    inner class GetSpecialtiesAsyncTask :
        AsyncTask<ArrayList<Specialty>, Void, ArrayList<Specialty>>() {

        override fun onPreExecute() {
            isLoading.value = true
        }

        override fun doInBackground(vararg params: ArrayList<Specialty>): ArrayList<Specialty>? {
            return try {
                specialties.postValue(dataRepository?.getSpecialties(params[0]))
                specialties.value
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: ArrayList<Specialty>?) {
            isLoading.value = false
        }
    }

    fun loadData() {
        insertSpecialties(null)
        val apiFactory = ApiFactory.getInstance()
        val apiService = apiFactory?.getApiService
        disposable = apiService?.getResponse()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                val specialties = ArrayList<Specialty>()
                for (workerInfo in it.response!!) {
                    specialties.addAll(workerInfo.specialty!!)
                }
                insertSpecialties(specialties)
            }, {
                isNetworkException.setValue(true)
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}