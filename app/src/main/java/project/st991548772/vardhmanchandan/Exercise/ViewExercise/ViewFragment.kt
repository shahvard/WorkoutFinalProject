package project.st991548772.vardhmanchandan.Exercise.ViewExercise

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.ViewFragmentBinding
import java.util.*

class ViewFragment : Fragment() {

    companion object {
        fun newInstance() = ViewFragment()
    }


    private lateinit var binding: ViewFragmentBinding
    private lateinit var yearSpinner: Spinner
    private lateinit var monthSpinner: Spinner
    private lateinit var email: String
    private lateinit var rView: RecyclerView
    private lateinit var auth: FirebaseAuth

    private val viewModel by lazy {
        ViewModelProvider(this).get(ViewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.view_fragment)
        yearSpinner = binding.year
        monthSpinner = binding.month
        auth = Firebase.auth
        rView = binding.list

        email = auth.currentUser?.email.toString()

        var month = 0
        var year = 0

        val c = Calendar.getInstance()

        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH) + 1

        viewModel.getFromDatabase(month, year, email)
        var months:Array<String> =resources.getStringArray(R.array.months_of_year)

        val monthAdapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            months
        )
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        monthSpinner.adapter = monthAdapter
        monthSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (months[position]) {
                    "January" -> month = 1
                    "February" -> month = 2
                    "March" -> month = 3
                    "April" -> month = 4
                    "May" -> month = 5
                    "June" -> month = 6
                    "July" -> month = 7
                    "August" -> month = 8
                    "September" -> month = 9
                    "October" -> month = 10
                    "November" -> month = 11
                    "December" -> month = 12
                }

                viewModel.getFromDatabase(month, year, email)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val yearList= resources.getStringArray(R.array.year)

        val yearAdapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            yearList
        )
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter


        yearSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                year = Integer.parseInt(yearList[position])

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        viewModel.list.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            display(it)
        })


        return inflater.inflate(R.layout.view_fragment, container, false)
    }

    fun display(it: ArrayList<Record>) {

        rView.adapter = ExerciseDisplayAdapter(it)
        rView.layoutManager = LinearLayoutManager(this.requireContext())
        rView.setHasFixedSize(true)
    }


   /* override fun onViewStateRestored(savedInstanceState: Bundle?) {

        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.view_fragment)

        monthSpinner = binding.month
        val monthList: ArrayList<String> = ArrayList()
        monthList.add("January")
        monthList.add("February")
        monthList.add("March")
        monthList.add("April")
        monthList.add("May")
        monthList.add("June")
        monthList.add("July")
        monthList.add("August")
        monthList.add("September")
        monthList.add("October")
        monthList.add("November")
        monthList.add("December")
        val monthAdapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            monthList
        )
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        monthSpinner.adapter = monthAdapter
        var month = 0
        var year = 0

        val c = Calendar.getInstance()

        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH) + 1

        viewModel.getFromDatabase(month, year, email)



        monthSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (monthList[position]) {
                    "January" -> month = 1
                    "February" -> month = 2
                    "March" -> month = 3
                    "April" -> month = 4
                    "May" -> month = 5
                    "June" -> month = 6
                    "July" -> month = 7
                    "August" -> month = 8
                    "September" -> month = 9
                    "October" -> month = 10
                    "November" -> month = 11
                    "December" -> month = 12
                }

                viewModel.getFromDatabase(month, year, email)

                //Log.d("list",viewModel.list.value!!.get(0).toString())

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        yearSpinner = binding.year
        rView = binding.list
        val yearList: ArrayList<String> = ArrayList()
        yearList.add("2022")
        yearList.add("2021")
        yearList.add("2020")
        yearList.add("2019")
        yearList.add("2018")
        yearList.add("2017")

        val yearAdapter = ArrayAdapter<String>(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            yearList
        )
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner.adapter = yearAdapter

            viewModel.list.observe(viewLifecycleOwner, androidx.lifecycle.Observer
            {
                display(it)
            })


            super.onViewStateRestored(savedInstanceState)
        }*/


    }
