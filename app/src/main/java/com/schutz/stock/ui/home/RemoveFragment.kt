package com.schutz.stock.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.schutz.stock.R

/**
 * A simple [Fragment] subclass.
 * Use the [RemoveFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RemoveFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_remove, container, false)
        return view
    }
}