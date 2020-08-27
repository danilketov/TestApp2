package com.danilketov.testapp2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.danilketov.testapp2.R
import com.danilketov.testapp2.util.Const
import com.danilketov.testapp2.util.Filter
import com.danilketov.testapp2.util.Parameters
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_desc_worker.*

class DescWorkerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_desc_worker, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setSettingsToolbar()
        getSetData()
    }

    private fun setSettingsToolbar() {
        (activity as AppCompatActivity).supportActionBar?.apply {
            setTitle(R.string.toolbar_title_desc_worker)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun getSetData() {
        val args = arguments
        if (args != null) {
            val lastName = args.getString(Const.KEY_WORKER_LAST_NAME)
            val firstName = args.getString(Const.KEY_WORKER_FIRST_NAME)
            val age = args.getString(Const.KEY_WORKER_AGE)
            val birthday = args.getString(Const.KEY_WORKER_BIRTHDAY)
            val avatar = args.getString(Const.KEY_WORKER_AVATAR)
            val specialtyJSON = args.getString(Const.KEY_SPECIALTY_JSON)
            val specialtyText: String = Filter.getSpecialtyText(specialtyJSON)

            value_last_name_text_view.text = lastName
            value_first_name_text_view.text = firstName
            value_age_text_view.text = age
            value_birthday_text_view.text = birthday
            value_special_text_view.text = specialtyText

            Picasso.get()
                .load(avatar)
                .fit()
                .placeholder(R.drawable.no_avatar)
                .into(avatar_circle_image_view)
        } else {
            Toast.makeText(activity, R.string.frag_args_null, Toast.LENGTH_SHORT).show()
        }
    }

    fun newInstance(parameters: Parameters): Fragment? {
        val args = Bundle()
        args.putString(Const.KEY_WORKER_LAST_NAME, parameters.lastName)
        args.putString(Const.KEY_WORKER_FIRST_NAME, parameters.firstName)
        args.putString(Const.KEY_WORKER_AGE, parameters.age)
        args.putString(Const.KEY_WORKER_BIRTHDAY, parameters.birthday)
        args.putString(Const.KEY_WORKER_AVATAR, parameters.avatar)
        args.putString(Const.KEY_SPECIALTY_JSON, parameters.specialtyJSON)
        val fragment = DescWorkerFragment()
        fragment.arguments = args
        return fragment
    }
}