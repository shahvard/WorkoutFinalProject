package project.st991548772.vardhmanchandan.AddExercise

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.AddFragmentBinding
import java.util.*

class AddFragment : Fragment() {




    private lateinit var binding: AddFragmentBinding
    private lateinit var typeOfWorkoutSelected:String
    private lateinit var duration:String
    private lateinit var distance:String
    private lateinit var calendar: CalendarView
    private lateinit var date:String

    private lateinit var auth: FirebaseAuth
    private lateinit var email:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.setContentView(this.requireActivity(), R.layout.add_fragment)
        calendar=binding.calendarView
        auth=Firebase.auth
        val currentUser = auth.currentUser
        email= currentUser?.email.toString()
        //adding values to spinner
        val typeOfWorkoutList: ArrayList<String> = ArrayList()
        typeOfWorkoutList.add("Cycling")
        typeOfWorkoutList.add("Running")
        typeOfWorkoutList.add("Swimming")


        var typeOfWorkout:String="None"
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, typeOfWorkoutList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeOfWorkout.adapter=arrayAdapter
        binding.typeOfWorkout.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                typeOfWorkout=typeOfWorkoutList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        calendar
            .setOnDateChangeListener(
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->

                     date = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)


                })





        binding.add.setOnClickListener(){

            duration=binding.duration.text.toString()
            distance=binding.distance.text.toString()

            val db = Firebase.firestore

            val record = hashMapOf(
                "Date" to date,
                "TypeOfWorkout" to typeOfWorkout,
                "duration" to duration,
                "distance" to distance
            )

            db.collection("Exercises").document(email).collection(typeOfWorkout).document(date)
                .set(record)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!")
                    Navigation.findNavController(this.requireView())
                        .navigate(R.id.action_addFragment_to_exerciseFragment)

                    val builder = AlertDialog.Builder(this.requireContext())
                    builder.setMessage("Are you sure you want to Add?")
                        .setCancelable(false)
                        .setPositiveButton("Yes") { dialog, id ->
                            Toast.makeText(this.requireContext(),"Record has been successfully added",Toast.LENGTH_SHORT).show()

                        }
                        .setNegativeButton("No") { dialog, id ->
                            // Dismiss the dialog
                            dialog.dismiss()
                        }
                    val alert = builder.create()
                    alert.show()


                }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }




        }
        return inflater.inflate(R.layout.add_fragment, container, false)
    }



}