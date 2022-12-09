package project.st991548772.vardhmanchandan.Diet

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentDietBinding
import java.util.*


class DietFragment : Fragment() {

    private lateinit var binding: FragmentDietBinding
  /*  private lateinit var yearSpinner: Spinner
    private lateinit var monthSpinner: Spinner*/
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
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_diet)
       /* yearSpinner = binding.year
        monthSpinner = binding.month*/
        auth = Firebase.auth
        rView = binding.list

        email = auth.currentUser?.email.toString()

        edt = binding.date

        val cal = Calendar.getInstance()


        var day :Int
        var month:Int = cal.get(Calendar.MONTH)-1
        var year :Int = cal.get(Calendar.YEAR)
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


        binding.add.setOnClickListener(){
            Navigation.findNavController(this.requireView()).navigate(R.id.action_dietFragment_to_addDietFragment)
        }


        viewModel.dietList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            display(it)
        })

        return inflater.inflate(R.layout.fragment_diet, container, false)


    }
    fun display(it: ArrayList<DietRecord>) {

        rView.adapter = DietDisplayAdapter(it,email)
        rView.layoutManager = LinearLayoutManager(this.requireContext())
        rView.setHasFixedSize(true)
    }







}