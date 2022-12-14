package project.st991548772.vardhmanchandan.Home

import android.icu.number.IntegerWidth
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.Diet.DietViewModel
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentDietBinding
import project.st991548772.vardhmanchandan.databinding.FragmentHomeBinding
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email:String
    private lateinit var binding: FragmentHomeBinding

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_home, container, false)
        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_home)


        val currentUser = auth.currentUser
        email= currentUser?.email.toString()
        viewModel.getUserPersonalInfo(email)
        viewModel.user.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

        view.findViewById<TextView>(R.id.welcomeText).text="Welcome ${it.name}"

        })

        val day = Calendar.DAY_OF_MONTH
        Log.d("day",day.toString())
        var todaysDay="Saturday"
        when (day){
            1->todaysDay="Saturday"
            2->todaysDay="Sunday"
            3->todaysDay="Monday"
            4->todaysDay="Tuesday"
            5->todaysDay="Wednesday"
            6->todaysDay="Thursday"
            7->todaysDay="Friday"

        }
        viewModel.getSchedule(todaysDay,email)
        viewModel.schedule.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            view.findViewById<TextView>(R.id.dayTextView).text=todaysDay
            view.findViewById<TextView>(R.id.timeTextView).text=it.startTime +" - "+it.endTime
            view.findViewById<TextView>(R.id.distanceTextView).text=it.distance
            view.findViewById<TextView>(R.id.typeTextView).text=it.typeOfWorkout
            when(it.typeOfWorkout){
                "Running"->view.findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.ic_baseline_directions_run_24)
                "Cycling"->view.findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.ic_baseline_directions_bike_24)
                "Swimming"-> view.findViewById<ImageView>(R.id.imageView2).setImageResource(R.drawable.swimming)

            }

        })



        val c=Calendar.getInstance()
        val month = (c.get(Calendar.MONTH)) + 1
        viewModel.getExercisesThisMonth(month.toString(),email)

        viewModel.exercisesThisMonth.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
        view.findViewById<TextView>(R.id.numberOfExTextView).text=it.toString()
        })

        viewModel.getCalories(email)
        viewModel.todaysCalories.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            view.findViewById<TextView>(R.id.todayCaloriesTextView).text=it.toString() +" /"
        })

        viewModel.totalCalories.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            view.findViewById<TextView>(R.id.totalCaloriesTextView).text=it.toString()
        })










        return view
    }



}