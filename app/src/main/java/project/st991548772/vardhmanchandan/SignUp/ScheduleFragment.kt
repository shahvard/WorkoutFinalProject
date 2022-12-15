package project.st991548772.vardhmanchandan.SignUp

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentScheduleBinding
import project.st991548772.vardhmanchandan.databinding.FragmentSignUpBinding
import java.util.ArrayList


class ScheduleFragment : Fragment() {

   //In this class we take the schedule the user wants to follow throught the week
    //and setting the value to the database
    val db = Firebase.firestore

    private lateinit var binding: FragmentScheduleBinding
    private lateinit var day:String
    private lateinit var startTime:String
    private lateinit var endTime:String
    private lateinit var distance:String
    private lateinit var typeOfWorkoutSelected:String
    private lateinit var email:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_schedule, container, false)
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_schedule)

        email= arguments?.getString("email").toString()



        val typeOfWorkoutList: ArrayList<String> = ArrayList()
        typeOfWorkoutList.add("Cycling")
        typeOfWorkoutList.add("Running")
        typeOfWorkoutList.add("Swimming")

        var typeOfWorkout:String="None"
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, typeOfWorkoutList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.typeOfExercise.adapter=arrayAdapter
        binding.typeOfExercise.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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


        binding.monImg.setOnClickListener(){
            day="Monday"
            binding.tueImg.visibility=View.GONE
            binding.wedImg.visibility=View.GONE
            binding.thuImg.visibility=View.GONE
            binding.friImg.visibility=View.GONE
            binding.satImg.visibility=View.GONE
            binding.sunImg.visibility=View.GONE
        }

        binding.tueImg.setOnClickListener(){
            day="Tuesday"
            binding.monImg.visibility=View.GONE
            binding.wedImg.visibility=View.GONE
            binding.thuImg.visibility=View.GONE
            binding.friImg.visibility=View.GONE
            binding.satImg.visibility=View.GONE
            binding.sunImg.visibility=View.GONE
        }

        binding.wedImg.setOnClickListener(){
            day="Wednesday"
            binding.tueImg.visibility=View.GONE
            binding.monImg.visibility=View.GONE
            binding.thuImg.visibility=View.GONE
            binding.friImg.visibility=View.GONE
            binding.satImg.visibility=View.GONE
            binding.sunImg.visibility=View.GONE
        }

        binding.thuImg.setOnClickListener(){
            day="Thursday"
            binding.tueImg.visibility=View.GONE
            binding.wedImg.visibility=View.GONE
            binding.monImg.visibility=View.GONE
            binding.friImg.visibility=View.GONE
            binding.satImg.visibility=View.GONE
            binding.sunImg.visibility=View.GONE
        }

        binding.friImg.setOnClickListener(){
            day="Friday"
            binding.tueImg.visibility=View.GONE
            binding.wedImg.visibility=View.GONE
            binding.thuImg.visibility=View.GONE
            binding.monImg.visibility=View.GONE
            binding.satImg.visibility=View.GONE
            binding.sunImg.visibility=View.GONE
        }

        binding.satImg.setOnClickListener(){
            day="Saturday"
            binding.tueImg.visibility=View.GONE
            binding.wedImg.visibility=View.GONE
            binding.thuImg.visibility=View.GONE
            binding.friImg.visibility=View.GONE
            binding.monImg.visibility=View.GONE
            binding.sunImg.visibility=View.GONE
        }

        binding.sunImg.setOnClickListener(){
            day="Sunday"
            binding.tueImg.visibility=View.GONE
            binding.wedImg.visibility=View.GONE
            binding.thuImg.visibility=View.GONE
            binding.friImg.visibility=View.GONE
            binding.satImg.visibility=View.GONE
            binding.monImg.visibility=View.GONE
        }

        binding.addWorkout.setOnClickListener(){
            if(binding.noExerciseCheckBox.isChecked){
                startTime="NA"
                endTime="NA"
                distance="NA"
                typeOfWorkout="NA"

            }
            else{
                startTime=binding.startTime.text.toString()
                endTime=binding.endTime.text.toString()
                distance=binding.distance.text.toString()
                typeOfWorkoutSelected=typeOfWorkout


            }
            val schedule = hashMapOf(
                "startTime" to startTime,
                "endTime" to endTime,
                "distance" to distance +" kms",
                "typeOfWorkout" to typeOfWorkout
            )

            db.collection("Schedule").document("$email").collection("dayList").document("$day")
                .set(schedule)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
            binding.tueImg.visibility=View.VISIBLE
            binding.wedImg.visibility=View.VISIBLE
            binding.thuImg.visibility=View.VISIBLE
            binding.monImg.visibility=View.VISIBLE
            binding.satImg.visibility=View.VISIBLE
            binding.sunImg.visibility=View.VISIBLE
            binding.friImg.visibility=View.VISIBLE

            binding.startTime.text.clear()
            binding.endTime.text.clear()
            binding.distance.text.clear()


        }

        binding.submit.setOnClickListener(){
            var bundle:Bundle= bundleOf()
            bundle.putString("email",email)

            view.findNavController()
                .navigate(R.id.action_scheduleFragment_to_caloriesFragment,bundle)
        }
        return view
    }


}