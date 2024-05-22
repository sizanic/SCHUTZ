package com.schutz.stock.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.schutz.stock.MainActivity
import com.schutz.stock.R
import com.schutz.stock.databinding.FragmentHomeBinding
import com.schutz.stock.service.DatabaseClient


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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.windowInsetsController?.hide(WindowInsetsCompat.Type.systemBars())
        initValues()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initValues() {
        var totalOccuped = 0
        var totalEmpl = 0

        val navController = view?.findNavController()
        val alleesTxt = mapOf(R.id.textA to 0, R.id.textB to 1, R.id.textC to 2
            , R.id.textD to 3, R.id.textE to 4, R.id.textF to 5
            , R.id.textG to 6, R.id.textH to 7, R.id.textI to 8)
        alleesTxt.forEach{
            val alleeText = view?.findViewById<TextView>(it.key)
            val value = it.value
            alleeText?.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("AlleeId", value)
                parentFragment?.arguments = bundle
                navController?.navigate(R.id.navigation_add)
            }
        }


        //thread(start = true)
            val allees = mapOf("A" to R.id.editA, "B" to R.id.editB , "C" to R.id.editC
                , "D" to R.id.editD , "E" to R.id.editE , "F" to R.id.editF
                , "G" to R.id.editG , "H" to R.id.editH , "I" to R.id.editI )
            allees.forEach{
                val nbEmpl = DatabaseClient.getInstance().getNbEmplacement(it.key)
                val nbRef = DatabaseClient.getInstance().getNbReference(it.key)
                val editText = view?.findViewById<TextView>(it.value)
                editText?.text = "%d / %d".format(nbEmpl-nbRef, nbEmpl)

                totalEmpl += nbEmpl
                totalOccuped += nbRef
            }
        val pieChart = view?.findViewById<PieChart>(R.id.pieChart)

        pieChart?.legend?.isEnabled = false

        val percent = 100f*totalOccuped / totalEmpl
        val noOfEmp = ArrayList<PieEntry>()
        noOfEmp.add(PieEntry(100f - percent, "%2.0f".format(100f - percent)))
        noOfEmp.add(PieEntry(percent, "%2.0f".format(percent)))
        val dataSet = PieDataSet(noOfEmp, "STOCKAGE")

        val colors = ArrayList<Int>()
        colors.add(4278235216.toInt()) // FF00BO50
        colors.add(Color.RED)
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setDrawValues(false)
        data.setValueFormatter(PercentFormatter(pieChart))
//        data.setValueTextSize(48f)
        data.setValueTextColor(Color.BLACK)
        pieChart?.centerText = "STOCKAGE RESTANT\r\n%2.0f %%".format(100f - percent)
        pieChart?.description = null
        pieChart?.setData(data)
        pieChart?.invalidate()
        pieChart?.animateY(800, Easing.EaseInOutQuad)

    }


}