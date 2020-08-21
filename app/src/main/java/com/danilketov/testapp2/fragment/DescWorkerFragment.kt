package com.danilketov.testapp2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.danilketov.testapp2.R
import com.danilketov.testapp2.util.Const
import com.danilketov.testapp2.util.Filter
import com.danilketov.testapp2.util.Parameters
import com.squareup.picasso.Picasso

class DescWorkerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setSettingsToolbar()
        getSetData()

        return inflater.inflate(R.layout.fragment_desc_worker, container, false)
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

            val valueLastNameTextView: TextView = R.id.value_last_name_text_view as TextView
            val valueFirstNameTextView: TextView = R.id.value_first_name_text_view as TextView
            val valueAgeTextView: TextView = R.id.value_age_text_view as TextView
            val valueBirthdayTextView: TextView = R.id.value_birthday_text_view as TextView
            val valueSpecialTextView: TextView = R.id.value_special_text_view as TextView
            val avatarCircleImageView: ImageView = R.id.avatar_circle_image_view as ImageView

            valueLastNameTextView.text = lastName
            valueFirstNameTextView.text = firstName
            valueAgeTextView.text = age
            valueBirthdayTextView.text = birthday
            valueSpecialTextView.text = specialtyText

            Picasso.get()
                .load(avatar)
                .fit()
                .placeholder(R.drawable.no_avatar)
                .into(avatarCircleImageView)
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

    private fun setSettingsToolbar() {
        (activity as AppCompatActivity?)?.supportActionBar?.setTitle(R.string.toolbar_title_desc_worker)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}