package project.st991548772.vardhmanchandan.Diet

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentDiet2Binding

import java.util.*


class DietFragment : Fragment() {

    private lateinit var binding: FragmentDiet2Binding
    private lateinit var email: String
    private lateinit var rView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private val viewModel by lazy {
        ViewModelProvider(this).get(DietViewModel::class.java)
    }

    lateinit var edt : EditText
    var myDate = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //setting the lateinit variables
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_diet2)
        auth = Firebase.auth
        rView = binding.list
        email = auth.currentUser?.email.toString()

        val c = Calendar.getInstance()
        // on below line we are getting
        // our day, month and year.
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dat = (day.toString() + "-" + (month + 1) + "-" + year)

        //calling the viewmodel to display today's date records
        viewModel.getFromDatabase(dat,email)

        edt = binding.date

        //when the edittext is clicked an popup calendar is displayed
        edt.setOnClickListener {
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)


            val datePickerDialog = DatePickerDialog(

                this.requireContext(),
                { view, year, monthOfYear, dayOfMonth ->

                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    edt.setText(dat)


                    myDate=dat
                    viewModel.getFromDatabase(myDate,email)
                },

                year,
                month,
                day
            )

            datePickerDialog.show()
        }


        //navigating to the add diet fragment
        binding.add.setOnClickListener(){
            Navigation.findNavController(this.requireView()).navigate(R.id.action_dietFragment2_to_addDietFragment)
        }

        //observing the dietList in viewmodel if there is change display method will be called
        viewModel.dietList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            display(it)

        })

        //observing the totalCalories in viewmodel if there is change display method will be called
        viewModel.totalCalories.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            displayCalories()

        })

        //observing the todaysCalories in viewmodel if there is change display method will be called
        viewModel.todaysCalories.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            displayCalories()

        })

        val appCompat = requireActivity() as AppCompatActivity
        val navHostFragment = appCompat.supportFragmentManager.findFragmentById(R.id.studentNavHost) as NavHostFragment
        val navController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navController)

        return inflater.inflate(R.layout.fragment_diet2, container, false)


    }

    //displaying the calories on the textview
    private fun displayCalories() {
        var totalCalories=viewModel.totalCalories.value
        binding.caloriesTextView.text="Today's Calories: ${viewModel.todaysCalories.value}/$totalCalories"
    }

    //displaying the recyclerview and calling the recycler view adapter
    fun display(it: ArrayList<DietViewModel.DietRecord>) {
        rView.adapter = DietDisplayAdapter(it,email)
        rView.layoutManager = LinearLayoutManager(this.requireContext())
        rView.setHasFixedSize(true)
    }

}
