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

class WorkerFragment : Fragment() {

    private var adapter: WorkerAdapter? = null
    private lateinit var viewModel: WorkerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_worker, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSettingsToolbar()
        initRecyclerView()
        getNameSpecialty()
        setViewModel()
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this)[WorkerViewModel::class.java]
        viewModel.getWorkers()
            .observe(this, Observer {
                var workers = it
                if (workers != null && getNameSpecialty() != null) {
                    workers = Filter.getFilteredWorkers(workers, getNameSpecialty())
                    adapter?.addItems(workers)
                } else {
                    Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show()
                }
            })
        viewModel.loadData()

        viewModel.isLoading().observe(this, Observer {
            progress_bar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.isNetworkException().observe(this, Observer {
            if (it != null) {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setSettingsToolbar() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        setToolbarTitle(getNameSpecialty())
    }

    private fun initRecyclerView() {
        worker_recycler_view?.layoutManager = LinearLayoutManager(activity)
        worker_recycler_view.itemAnimator = null
        val listener: WorkerAdapter.OnInfoWorkerClickListener =
            object : WorkerAdapter.OnInfoWorkerClickListener {
                override fun onInfoWorkerClick(worker: Worker?) {
                    val lastName: String = Converter.getFormattedString(worker?.lastName)
                    val firstName: String = Converter.getFormattedString(worker?.firstName)
                    val age: String = Converter.getFormattedAge(worker?.birthday)
                    val birthday: String? = Converter.getFormattedBirthday(worker?.birthday)
                    val avatar: String? = Converter.getAvatarWorker(worker?.avatarUrl, worker)
                    val specialtyJSON = Gson().toJson(worker?.specialty)

                    requireFragmentManager()
                        .beginTransaction()
                        .replace(
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
                        .addToBackStack(null)
                        .commit()
                }
            }
        adapter = WorkerAdapter(listener)
        worker_recycler_view?.adapter = adapter
    }

    private fun getNameSpecialty(): String? {
        val args = arguments
        var nameSpecialty: String? = null
        if (args != null) {
            nameSpecialty = args.getString(Const.KEY_WORKER_SPECIALTY)
            setToolbarTitle(nameSpecialty)
        } else {
            Toast.makeText(activity, R.string.frag_args_null, Toast.LENGTH_SHORT).show()
        }
        return nameSpecialty
    }

    private fun setToolbarTitle(title: String?) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    fun newInstance(specialty: String?): Fragment? {
        val args = Bundle()
        args.putString(Const.KEY_WORKER_SPECIALTY, specialty)
        val fragment = WorkerFragment()
        fragment.arguments = args
        return fragment
    }
}