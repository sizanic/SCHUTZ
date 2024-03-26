package com.schutz.stock.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.schutz.stock.R
import com.schutz.stock.service.DatabaseClient


/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var spinnerEmplacement: Spinner? = null

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

        spinnerEmplacement = view?.findViewById<Spinner>(R.id.spinnerEmplacement)

        val btnAdd = view?.findViewById<Button>(R.id.btnAdd)
        btnAdd?.setOnClickListener(View.OnClickListener {
            addReference()
        })

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

        val selectedItem = parent!!.getItemAtPosition(position).toString()

        when (parent.id) {
            R.id.spinnerAllee -> {
                val emplacements = DatabaseClient.getInstance().getEmplacementsLibres(selectedItem)
                val values = ArrayList<Int>()
                emplacements.forEach() {
                    values.add(it.id)
                }
                spinnerEmplacement?.adapter = ArrayAdapter<Int>(requireView().context, android.R.layout.simple_list_item_1, values)
            }

            R.id.spinnerEmplacement -> {
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun addReference() {
        val allee = view?.findViewById<Spinner>(R.id.spinnerAllee)
        val alleeID = allee?.selectedItem.toString()
        val emplacement = view?.findViewById<Spinner>(R.id.spinnerEmplacement)
        val emplacementID = emplacement?.selectedItem.toString().toInt()
        val reference = view?.findViewById<EditText>(R.id.editTextReference)
        val referenceId = reference?.text.toString()
        val date = view?.findViewById<EditText>(R.id.editTextDate)
        val dateId = date?.text

        val currentTimestamp = System.currentTimeMillis()

        val newReference = DatabaseClient.getInstance().addReference(alleeID, emplacementID, referenceId, currentTimestamp)

        if (newReference != null)
            Toast.makeText(context , "Référence $referenceId ajouté dans l'allée $alleeID, emplacement $emplacementID", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context , "Impossible d'ajouter la référence $referenceId dans l'allée $alleeID, emplacement $emplacementID", Toast.LENGTH_SHORT).show()

    }


}