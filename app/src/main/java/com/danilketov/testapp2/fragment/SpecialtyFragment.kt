package com.danilketov.testapp2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        setSettingsToolbar()
        initRecyclerView()

        viewModel = ViewModelProviders.of(this)[SpecialtyViewModel::class.java]
        viewModel.getSpecialties().observe(this, Observer {
            adapter?.addItems(it)
        })

        return inflater.inflate(R.layout.fragment_special, container, false)
    }

    private fun initRecyclerView() {
        special_recycler_view?.layoutManager = LinearLayoutManager(activity)
        val listener: SpecialtyAdapter.OnInfoSpecialClickListener =
            object : SpecialtyAdapter.OnInfoSpecialClickListener {
                override fun onInfoSpecialClick(specialty: Specialty?) {
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(
                            R.id.fragment_container,
                            WorkerFragment().newInstance(specialty?.name)!!
                        )
                        ?.addToBackStack(null)
                        ?.commit()
                }
            }
        val adapter = SpecialtyAdapter(listener)
        special_recycler_view?.adapter = adapter
    }

    private fun setSettingsToolbar() {
        (activity as AppCompatActivity?)?.supportActionBar?.setTitle(R.string.toolbar_title_specialty)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowHomeEnabled(false)
    }
}