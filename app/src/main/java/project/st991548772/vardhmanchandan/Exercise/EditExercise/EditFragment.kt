package project.st991548772.vardhmanchandan.Exercise.EditExercise

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
import project.st991548772.vardhmanchandan.Exercise.ViewExercise.Record
import project.st991548772.vardhmanchandan.Exercise.ViewExercise.ViewViewModel
import project.st991548772.vardhmanchandan.databinding.EditFragmentBinding
import java.util.*

class EditFragment : Fragment() {

    companion object {
        fun newInstance() = EditFragment()
    }


    private val viewModel by lazy {
        ViewModelProvider(this).get(ViewViewModel::class.java)
    }

    private lateinit var binding: EditFragmentBinding
    private lateinit var yearSpinner: Spinner
    private lateinit var monthSpinner: Spinner
    private lateinit var email: String
    private lateinit var rView: RecyclerView
    private lateinit var auth: FirebaseAuth
    var month = 0
    var year = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.edit_fragment)
        yearSpinner = binding.year
        monthSpinner = binding.month
        auth = Firebase.auth
        rView = binding.list

        email = auth.currentUser?.email.toString()



        val c = Calendar.getInstance()

        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH) + 1

        getFromViewModel()
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
                getFromViewModel()


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



        return inflater.inflate(R.layout.edit_fragment, container, false)


    }

    fun getFromViewModel(){
        viewModel.getFromDatabase(month,year,email)
    }

    fun display(it: ArrayList<Record>) {

        rView.adapter = ExerciseEditAdapter(it,email)
        rView.layoutManager = LinearLayoutManager(this.requireContext())
        rView.setHasFixedSize(true)
    }



}