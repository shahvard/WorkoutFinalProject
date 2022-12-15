package project.st991548772.vardhmanchandan.SignUp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentCaloriesBinding
import project.st991548772.vardhmanchandan.databinding.FragmentScheduleBinding

class CaloriesFragment : Fragment() {

    private lateinit var binding: FragmentCaloriesBinding
    val db = Firebase.firestore
    //getting the user decided number of calories intake per day
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_calories)
        val email =arguments?.getString("email").toString()

        //when the confirm button is clicked takes the value from the edittext and saves it in the database
        binding.confirm.setOnClickListener() {
            val calories = hashMapOf(
                "Calories" to binding.calories.text.toString()
            )
            db.collection("Calories").document("$email").set(calories)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
                    Toast.makeText(this.requireContext(),"Sign up has been completed. Login to continue",
                    Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(this.requireView())
                        .navigate(R.id.action_caloriesFragment_to_loginFragment)
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }
        }
        return inflater.inflate(R.layout.fragment_calories, container, false)
    }


}