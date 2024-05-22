package com.schutz.stock.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.schutz.stock.R
import com.schutz.stock.service.DatabaseClient
import java.text.SimpleDateFormat
import java.util.Date

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val btnAdd = view?.findViewById<Button>(R.id.btnAdd)
        btnAdd?.setOnClickListener {
            // close keyboard
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            searchReference()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun searchReference() {
        val textAllee = view?.findViewById<TextView>(R.id.editTextAllee)
        val textEmpl = view?.findViewById<TextView>(R.id.editTextEmplacement)
        val textDate = view?.findViewById<TextView>(R.id.editTextDate)
        val textRef = view?.findViewById<EditText>(R.id.editTextReference)
        val searchRef = textRef?.text
        val reference = DatabaseClient.getInstance().searchReference(searchRef.toString())

        if (reference != null) {
            val date = Date(reference.date)
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            textAllee?.text = reference.alleeId
            textEmpl?.text = reference.emplacementId.toString()
            textDate?.text = sdf.format(date)

            // preparer la suppression
            val bundle = Bundle()
            bundle.putString("Allee", reference.alleeId)
            bundle.putString("Emplacement", reference.emplacementId.toString())
            parentFragment?.arguments = bundle
        }
        else {
            textAllee?.text = " / "
            textEmpl?.text = " / "
            textDate?.text = " / "
            Toast.makeText(context , "Aucun emplacement trouvé pour la référence : $searchRef", Toast.LENGTH_SHORT).show()
        }
    }
}