package com.danilketov.testapp2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.danilketov.testapp2.R
import com.danilketov.testapp2.adapter.WorkerAdapter
import com.danilketov.testapp2.entity.Worker
import com.danilketov.testapp2.util.Const
import com.danilketov.testapp2.util.Converter
import com.danilketov.testapp2.util.Filter
import com.danilketov.testapp2.util.Parameters
import com.danilketov.testapp2.viewmodel.WorkerViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_worker.*
import java.util.*

class WorkerFragment : Fragment() {

    private var adapter: WorkerAdapter? = null
    private lateinit var viewModel: WorkerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setSettingsToolbar()
        initRecyclerView()
        getNameSpecialty()

        viewModel = ViewModelProviders.of(this)[WorkerViewModel::class.java]
        viewModel.getWorkers()
            .observe(this,
                Observer<ArrayList<Worker>> { workers ->
                    var workers = workers
                    if (workers != null && getNameSpecialty() != null) {
                        workers = Filter.getFilteredWorkers(workers, getNameSpecialty())
                        adapter!!.addItems(workers)
                    } else {
                        Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show()
                    }
                })

        return inflater.inflate(R.layout.fragment_worker, container, false)
    }

    private fun setSettingsToolbar() {
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initRecyclerView() {
        worker_recycler_view?.layoutManager = LinearLayoutManager(activity)
        val listener: WorkerAdapter.OnInfoWorkerClickListener =
            object : WorkerAdapter.OnInfoWorkerClickListener {
                override fun onInfoWorkerClick(worker: Worker?) {
                    val lastName: String = Converter.getFormattedString(worker?.lastName)
                    val firstName: String = Converter.getFormattedString(worker?.firstName)
                    val age: String = Converter.getFormattedAge(worker?.birthday)
                    val birthday: String? = Converter.getFormattedBirthday(worker?.birthday)
                    val avatar: String? = Converter.getAvatarWorker(worker?.avatarUrl, worker)
                    val specialtyJSON = Gson().toJson(worker?.specialty)
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(
                            R.id.fragment_container, DescWorkerFragment()
                                .newInstance(
                                    Parameters(
                                        lastName,
                                        firstName,
                                        age,
                                        birthday,
                                        avatar,
                                        specialtyJSON
                                    )
                                )!!
                        )
                        ?.addToBackStack(null)
                        ?.commit()

                }
            }
        val adapter = WorkerAdapter(listener)
        worker_recycler_view?.adapter = adapter
    }

    private fun getNameSpecialty(): String? {
        val args = arguments
        var nameSpecialty: String? = null
        if (args != null) {
            nameSpecialty = args.getString(Const.KEY_WORKER_SPECIALTY)
            (activity as AppCompatActivity?)!!.supportActionBar?.title = nameSpecialty
        } else {
            Toast.makeText(activity, R.string.frag_args_null, Toast.LENGTH_SHORT).show()
        }
        return nameSpecialty
    }

    fun newInstance(specialty: String?): Fragment? {
        val args = Bundle()
        args.putString(Const.KEY_WORKER_SPECIALTY, specialty)
        val fragment = WorkerFragment()
        fragment.arguments = args
        return fragment
    }
}