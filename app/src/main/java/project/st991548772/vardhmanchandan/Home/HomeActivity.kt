package project.st991548772.vardhmanchandan.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import project.st991548772.vardhmanchandan.Diet.DietFragment
import project.st991548772.vardhmanchandan.Exercise.ExerciseFragment
import project.st991548772.vardhmanchandan.R

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNav : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loadFragment(HomeFragment())
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.exercise -> {
                    loadFragment(ExerciseFragment())
                    return@setOnNavigationItemReselectedListener
                }
                R.id.diet -> {
                    loadFragment(DietFragment())
                    return@setOnNavigationItemReselectedListener
                }
            }
        }

    }

    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}