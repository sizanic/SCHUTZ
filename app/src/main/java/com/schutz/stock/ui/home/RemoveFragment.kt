package com.schutz.stock.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.schutz.stock.R
import com.schutz.stock.data.entity.Reference
import com.schutz.stock.service.DatabaseClient

/**
 */
class RemoveFragment : Fragment(), AdapterView.OnItemSelectedListener  {

    private var selectedReference: Reference? = null
    private var referenceText: TextView? = null
    private var emplacementSpinner: Spinner? = null
    private var alleeSpinner: Spinner? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_remove, container, false)

        alleeSpinner = view.findViewById(R.id.spinnerAlleeDel)
        emplacementSpinner = view.findViewById(R.id.spinnerEmplacementDel)
        referenceText = view.findViewById(R.id.editTextReferenceDel)

        alleeSpinner?.onItemSelectedListener = this
        emplacementSpinner?.onItemSelectedListener = this


        val btnAdd = view.findViewById<Button>(R.id.btnRemove)
        btnAdd?.setOnClickListener {
            removeReference()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectedReference = null
        val values = ArrayList<String>()
        val allees = DatabaseClient.getInstance().allees
        allees.forEach {
            values.add(it.id)
        }
        val spinnerAllee = view.findViewById<Spinner>(R.id.spinnerAlleeDel)
        spinnerAllee?.adapter = ArrayAdapter(requireView().context, android.R.layout.simple_list_item_1, values)
    }

    fun initEmplacement(alleeId: String) {
        val emplacements = DatabaseClient.getInstance().getEmplacementsOccupes(alleeId)
        val values = ArrayList<String>()
        values.add(" ")

        emplacements.forEach {
            values.add(it.id.toString())
        }
        emplacementSpinner?.adapter = ArrayAdapter(requireView().context, android.R.layout.simple_list_item_1, values)
        emplacementSpinner?.setSelection(0)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val selectedItem = parent!!.getItemAtPosition(position).toString()

        when (parent.id) {
            R.id.spinnerAlleeDel -> {
                selectedReference = null
                referenceText?.text = ""
                initEmplacement(selectedItem)
            }

            R.id.spinnerEmplacementDel -> {
                if (selectedItem.isBlank()) {
                    referenceText?.text = ""
                    selectedReference = null
                    return
                }

                val alleeID = alleeSpinner?.selectedItem.toString()
                val emplacementID = emplacementSpinner?.selectedItem.toString().toInt()
                selectedReference = DatabaseClient.getInstance().getReference(alleeID, emplacementID)
                val ref = selectedReference
                if (ref != null)
                    referenceText?.text = ref.id
                else
                    referenceText?.text = ""
            }
        }


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }


    private fun removeReference() {
        val ref = selectedReference ?: return
        val isDeleted = DatabaseClient.getInstance().removeReference(ref)
        if (isDeleted) {
            referenceText?.text = ""
            Toast.makeText(context , "Allée %s, l'emplacement %d est libéré".format(ref.alleeId, ref.emplacementId), Toast.LENGTH_SHORT).show()
            initEmplacement(ref.alleeId)
            selectedReference = null
        }
        else {
            Toast.makeText(context , "Allée %s, impossible de libérer l'emplacement %d".format(ref.alleeId, ref.emplacementId), Toast.LENGTH_SHORT).show()
        }
    }

}