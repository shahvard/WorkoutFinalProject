package project.st991548772.vardhmanchandan.SignUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentSignUpBinding
import java.util.*


class SignUpFragment : Fragment() {


    private val signUpModel by lazy{
        ViewModelProvider(this).get(SignUpViewModel::class.java)
    }

    private lateinit var binding:FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_sign_up, container, false)
        binding = DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_sign_up)

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
                view: View,
                position: Int,
                id: Long
            ) {
                gender=genderList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.signUp.setOnClickListener() {
            signUpModel.addUser(
                binding.name.text.toString(),
                binding.email.text.toString(),
                binding.weight.text.toString(),
                binding.height.text.toString(),
                Integer.parseInt(binding.age.text.toString()),
                gender
            )

        }


        return view
    }




}