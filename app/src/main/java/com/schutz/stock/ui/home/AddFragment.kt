package com.schutz.stock.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.schutz.stock.R
import com.schutz.stock.service.DatabaseClient
import kotlin.concurrent.thread

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        val spinnerAllee = view?.findViewById<Spinner>(R.id.spinnerAllee)
        spinnerAllee?.onItemSelectedListener = this

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
/*
        thread(start = true) {
        }
*/
    }

    private fun initValues() {
        val values = ArrayList<String>()
        val allees = DatabaseClient.getInstance().allees
        allees.forEach() {
            values.add(it.id)
        }
        val spinnerAllee = view?.findViewById<Spinner>(R.id.spinnerAllee)
        spinnerAllee?.adapter = ArrayAdapter<String>(requireView().context, android.R.layout.simple_list_item_1, values)

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // An item is selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos).
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }


}