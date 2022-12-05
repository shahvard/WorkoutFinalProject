package project.st991548772.vardhmanchandan.Exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.FragmentExerciseCRUDBinding


class ExerciseCRUDFragment : Fragment() {

//cprivate lateinit var binding:FragmentExerciseCRUDBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_exercise_c_r_u_d, container, false)

        //binding= DataBindingUtil.setContentView(this.requireActivity(), R.layout.fragment_exercise_c_r_u_d)
        view.findViewById<Button>(R.id.add).setOnClickListener(){
            Navigation.findNavController(this.requireActivity(),R.id.add)
                .navigate(R.id.action_exerciseFragment_to_addFragment)
        }

    view.findViewById<Button>(R.id.view).setOnClickListener(){
        Navigation.findNavController(this.requireActivity(),R.id.add)
            .navigate(R.id.action_exerciseFragment_to_viewFragment)
    }

        return view
    }


}