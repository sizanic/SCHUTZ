package com.schutz.stock.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.schutz.stock.R
import com.schutz.stock.data.entity.Allee
import com.schutz.stock.databinding.FragmentHomeBinding
import com.schutz.stock.service.DatabaseClient
import kotlin.concurrent.thread

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view: View = binding.root

/*
        val nbA = view.findViewById<TextView>(R.id.editA)
        nbA.text = "56"
*/
      return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initValues() {
        thread(start = true) {
            val alleeId = "A"
            val emplacementId = 1
            val nbRef = DatabaseClient.getInstance().getNbReference(alleeId, emplacementId)

            val nbA = view?.findViewById<TextView>(R.id.editA)
            nbA?.text = "123"
        }
    }


}