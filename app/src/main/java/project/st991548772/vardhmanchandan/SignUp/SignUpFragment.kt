package project.st991548772.vardhmanchandan.SignUp

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentSignUpBinding
import java.util.*


class SignUpFragment : Fragment() {


    val db = Firebase.firestore
    private var user:User=User("","","","",0,"")
    private lateinit var auth: FirebaseAuth


    private lateinit var binding:FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_sign_up, container, false)
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_sign_up)
        auth = Firebase.auth


        //setting array for spinner
        val genderList: ArrayList<String> = ArrayList()
        genderList.add("Male")
        genderList.add("Female")
        genderList.add("Other")

        var gender:String="Other"
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this.requireContext(), android.R.layout.simple_spinner_item, genderList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.gender.adapter=arrayAdapter
        binding.gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                gender=genderList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        binding.signUp.setOnClickListener() {
            auth.createUserWithEmailAndPassword(binding.email.text.toString(), binding.password.text.toString())
                .addOnCompleteListener(this.requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this.requireContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
            user=User(
                binding.name.text.toString(),
                binding.email.text.toString(),
                binding.weight.text.toString(),
                binding.height.text.toString(),
                Integer.parseInt(binding.age.text.toString()),
                gender
            )

            db.collection("users").document(binding.email.text.toString())
                .set(user)
                .addOnSuccessListener { Log.d(ContentValues.TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error writing document", e) }

            var bundle:Bundle= bundleOf()
            bundle.putString("email",binding.email.text.toString())
            view.findNavController()
                .navigate(R.id.action_signUpFragment_to_scheduleFragment,bundle)
        }




        return view
    }




}