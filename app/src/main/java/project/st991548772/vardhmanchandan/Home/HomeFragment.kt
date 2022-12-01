package project.st991548772.vardhmanchandan.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.R


class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_home, container, false)
        auth = Firebase.auth


        val currentUser = auth.currentUser
        email= currentUser?.email.toString()










        return view
    }

}