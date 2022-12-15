package project.st991548772.vardhmanchandan.Diet.AddDiet

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentAddDietBinding



class AddDietFragment : Fragment() {

    private lateinit var binding: FragmentAddDietBinding
    private lateinit var typeSpinner:Spinner
    private lateinit var typeOfMeal:String
    private lateinit var calendar:CalendarView
    private lateinit var date:String
    private lateinit var db:FirebaseFirestore
    private lateinit var auth:FirebaseAuth
    private lateinit var email:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_add_diet)
        typeSpinner=binding.typeOfMeal
        calendar=binding.calendarView
        db=Firebase.firestore
        auth=Firebase.auth
        email=auth.currentUser?.email.toString()

        //getting information regarding the diet from user
        var typeOfMealArray=resources.getStringArray(R.array.typeOfMeal)
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, typeOfMealArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter=arrayAdapter
        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                typeOfMeal=typeOfMealArray[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        //taking date from the user
        calendar
            .setOnDateChangeListener(
                CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->

                    date = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)


                })



        //When the user click on add button this is executed and this adds the record to the firebase database
        binding.add.setOnClickListener(){
            val dietRecord= hashMapOf(
                    "Date" to date,
                    "TypeOfMeal" to typeOfMeal,
                    "Calories" to binding.calories.text.toString()+ " calories",
                    "ItemName" to binding.mealName.text.toString()

            )

            //Using Alert dialog box to ask the user to confirm
            val builder = AlertDialog.Builder(this.requireContext())
            builder.setMessage("Are you sure you want to Add?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->

                    db.collection("Diet").document(email).collection(typeOfMeal).document(date)
                        .set(dietRecord)
                        .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!")
                            Toast.makeText(this.requireContext(),"Record has been successfully added",
                                Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(this.requireView())
                                .navigate(R.id.action_addDietFragment_to_dietFragment2)
                        }
                        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }



                }
                .setNegativeButton("No") { dialog, id ->

                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }




        return inflater.inflate(R.layout.fragment_add_diet, container, false)
    }


}