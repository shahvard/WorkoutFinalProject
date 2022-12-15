package project.st991548772.vardhmanchandan.Login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.Home.HomeActivity
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentLoginBinding
import project.st991548772.vardhmanchandan.databinding.FragmentSignUpBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
private lateinit var email: String
private lateinit var password:String
private var success=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = Firebase.auth
        val view=inflater.inflate(R.layout.fragment_login, container, false)
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_login)


        //when the login button is clicked it will get email and passsword from the textfields
        binding.login.setOnClickListener(){
            email=binding.email.text.toString()
            password=binding.password.text.toString()

            //signing in using the firebase authentication
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this.requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val intent=Intent(this.context,HomeActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this.requireContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }

        }


        return view
    }




}