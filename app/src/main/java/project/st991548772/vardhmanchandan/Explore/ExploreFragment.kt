package project.st991548772.vardhmanchandan.Explore

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import project.st991548772.vardhmanchandan.R


class ExploreFragment : Fragment() {

//This is a explore fragment which has only two textfields which redirects to the website
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_explore, container, false)

        val mTextView = view.findViewById<TextView>(R.id.exploreExercise)
        // Finding and displaying the content
        // that consists a URL as a hyperlink
        mTextView.movementMethod = LinkMovementMethod.getInstance()

        val dTextView = view.findViewById<TextView>(R.id.exploreDiet)
        // Finding and displaying the content
        // that consists a URL as a hyperlink
        dTextView.movementMethod = LinkMovementMethod.getInstance()
        return view
    }


}