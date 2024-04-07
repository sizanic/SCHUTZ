package com.schutz.stock.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.schutz.stock.R
import com.schutz.stock.service.DatabaseClient

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val btnAdd = view?.findViewById<Button>(R.id.btnAdd)
        btnAdd?.setOnClickListener(View.OnClickListener {
            searchReference()
        })

        return view
    }

    private fun searchReference() {
        val textAllee = view?.findViewById<TextView>(R.id.editTextAllee)
        val textEmpl = view?.findViewById<TextView>(R.id.editTextEmplacement)
        val textDate = view?.findViewById<TextView>(R.id.editTextDate)
        val textRef = view?.findViewById<EditText>(R.id.editTextReference)
        val reference = DatabaseClient.getInstance().searchReference(textRef?.text.toString())

        if (reference != null) {
            textAllee?.text = reference.alleeId
            textEmpl?.text = reference.emplacementId.toString()
            textDate?.text = reference.date.toString()
        }
        else {
            textAllee?.text = " / "
            textEmpl?.text = " / "
            textDate?.text = " / "
        }
    }
}