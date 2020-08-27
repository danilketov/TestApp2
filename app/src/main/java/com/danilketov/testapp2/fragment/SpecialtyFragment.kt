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
import com.danilketov.testapp2.adapter.SpecialtyAdapter
import com.danilketov.testapp2.entity.Specialty
import com.danilketov.testapp2.viewmodel.SpecialtyViewModel
import kotlinx.android.synthetic.main.fragment_special.*

class SpecialtyFragment : Fragment() {

    private var adapter: SpecialtyAdapter? = null
    private lateinit var viewModel: SpecialtyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_special, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSettingsToolbar()
        initRecyclerView()
        setViewModel()
    }

    private fun setViewModel() {
        viewModel = ViewModelProviders.of(this)[SpecialtyViewModel::class.java]
        viewModel.getSpecialties().observe(this, Observer {
            adapter?.addItems(it)
        })
        viewModel.loadData()

        viewModel.isLoading().observe(this, Observer {
            progress_bar.visibility = if (it) View.VISIBLE else View.GONE
        })
        viewModel.isNetworkException().observe(this, Observer {
            if (it != null && it) {
                Toast.makeText(activity, R.string.error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setSettingsToolbar() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setTitle(R.string.toolbar_title_specialty)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }

    private fun initRecyclerView() {
        specialty_recycler_view?.layoutManager = LinearLayoutManager(activity)
        specialty_recycler_view.itemAnimator = null
        val listener: SpecialtyAdapter.OnInfoSpecialClickListener =
            object : SpecialtyAdapter.OnInfoSpecialClickListener {
                override fun onInfoSpecialClick(specialty: Specialty?) {
                    requireFragmentManager()
                        .beginTransaction()
                        .replace(
                            R.id.fragment_container,
                            WorkerFragment().newInstance(specialty?.name)!!
                        )
                        .addToBackStack(null)
                        .commit()
                }
            }
        adapter = SpecialtyAdapter(listener)
        specialty_recycler_view?.adapter = adapter
    }
}