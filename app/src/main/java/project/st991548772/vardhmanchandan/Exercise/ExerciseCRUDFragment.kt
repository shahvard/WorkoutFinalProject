package project.st991548772.vardhmanchandan.Exercise

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.Exercise.ViewExercise.Record
import project.st991548772.vardhmanchandan.Exercise.ViewExercise.ViewViewModel
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentExerciseCRUDBinding
import java.util.*


class ExerciseCRUDFragment : Fragment() {

private lateinit var binding:FragmentExerciseCRUDBinding

    private lateinit var email: String
    private lateinit var rView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private val viewModel by lazy {
        ViewModelProvider(this).get(ViewViewModel::class.java)
    }

    lateinit var edt : EditText
    var myDate = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_exercise_c_r_u_d, container, false)
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_exercise_c_r_u_d)
        auth = Firebase.auth
        rView = binding.list


        email = auth.currentUser?.email.toString()

        edt = binding.date
        binding.add.setOnClickListener(){
            Navigation.findNavController(this.requireView())
                .navigate(R.id.action_exerciseCRUDFragment_to_addFragment)
        }

        val c = Calendar.getInstance()

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)

        val day = c.get(Calendar.DAY_OF_MONTH)
        val dat = (day.toString() + "-" + (month + 1) + "-" + year)

        viewModel.getFromDatabase(dat,email)


        val appCompat = requireActivity() as AppCompatActivity
        val navHostFragment = appCompat.supportFragmentManager.findFragmentById(R.id.studentNavHost) as NavHostFragment
        val navController = navHostFragment.findNavController()

        binding.bottomNav.setupWithNavController(navController)

        edt.setOnClickListener {
            val c = Calendar.getInstance()

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

        viewModel.list.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            display(it)
        })






        return view
    }


    fun display(it: ArrayList<Record>) {

        rView.adapter = ExerciseEditAdapter(it,email)
        rView.layoutManager = LinearLayoutManager(this.requireContext())
        rView.setHasFixedSize(true)
    }

}