package com.schutz.stock.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.internal.ViewUtils.hideKeyboard
import com.schutz.stock.R
import com.schutz.stock.service.DatabaseClient
import java.util.Calendar
import java.util.GregorianCalendar


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
            // close keyboard
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

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
        val date = view?.findViewById<DatePicker>(R.id.editTextDate)
//        val dateId = date?.get

        val calendar: Calendar = GregorianCalendar(date?.year!!, date.month, date.dayOfMonth)
        val selectedTimestamp = calendar.getTimeInMillis()

//        val currentTimestamp = System.currentTimeMillis()

        val newReference = DatabaseClient.getInstance().addReference(alleeID, emplacementID, referenceId, selectedTimestamp)

        if (newReference != null)
            Toast.makeText(context , "Référence $referenceId ajouté dans l'allée $alleeID, emplacement $emplacementID", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context , "Impossible d'ajouter la référence $referenceId dans l'allée $alleeID, emplacement $emplacementID", Toast.LENGTH_SHORT).show()

    }


}