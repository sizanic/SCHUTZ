package com.schutz.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.stockschutz.R

// TODO: Rename parameter arguments, choose names that match

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentNavigation.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentNavigation : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_navigation, container, false)

        val navHome = view.findViewById<Button>(R.id.button_home)
        navHome.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_home)
        }

        val navAdd = view.findViewById<Button>(R.id.button_add)
        navAdd.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_add)
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navAdd = view.findViewById<Button>(R.id.button_add)
        navAdd.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.navigation_add)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_navigation.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentNavigation().apply {
/*
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
*/
            }
    }
}