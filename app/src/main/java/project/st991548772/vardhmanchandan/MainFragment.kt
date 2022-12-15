package project.st991548772.vardhmanchandan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import project.st991548772.vardhmanchandan.databinding.FragmentMainBinding



//this is the main fragment which is the landing page for the app
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_main, container, false)
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_main)

        view.findViewById<Button>(R.id.signup).setOnClickListener(){
            view.findNavController()
                .navigate(R.id.action_mainFragment_to_signUpFragment)


        }

        view.findViewById<Button>(R.id.login).setOnClickListener() {
            view.findNavController()
                .navigate(R.id.action_mainFragment_to_loginFragment)
        }

        return view
    }

}