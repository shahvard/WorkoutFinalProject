package project.st991548772.vardhmanchandan.Exercise

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.annotation.Nullable
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import project.st991548772.vardhmanchandan.Login.LoginFragment
import project.st991548772.vardhmanchandan.R


class ExerciseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_exercise, container, false)
        //var tab_toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        var tab_viewpager = view.findViewById<ViewPager>(R.id.tab_viewpager)
        var tab_tablayout = view.findViewById<TabLayout>(R.id.tab_tablayout)


        //setSupportActionBar(tab_toolbar)
        setupViewPager(tab_viewpager)

        tab_tablayout.setupWithViewPager(tab_viewpager)
        return view
    }



    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(this.childFragmentManager)

        // LoginFragment is the name of Fragment and the Login
        // is a title of tab
        adapter.addFragment(ExerciseCRUDFragment(), "Exercise")
        adapter.addFragment(ExploreExerciseFragment(), "Explore Exercise")

        // setting adapter to view pager.
        viewpager.setAdapter(adapter)
    }


    class ViewPagerAdapter : FragmentPagerAdapter {

        // objects of arraylist. One is of Fragment type and
        // another one is of String type.*/
        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
        private final var fragmentTitleList1: ArrayList<String> = ArrayList()

        // this is a secondary constructor of ViewPagerAdapter class.
        public constructor(supportFragmentManager: FragmentManager)
                : super(supportFragmentManager)

        override fun getCount()=fragmentList1.size



        // returns which item is selected from arraylist of fragments.
        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }
        @Nullable
        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList1.get(position)
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }

    }


}
