package project.st991548772.vardhmanchandan.Home

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity(){
  /* *//* private lateinit var appBarConfiguration: AppBarConfiguration
    //lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    //lateinit var bottomNav: BottomNavigationView*//*

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle*/





    lateinit var binding: project.st991548772.vardhmanchandan.databinding.ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController=this.findNavController(R.id.studentNavHost)
        val bottomView=binding.bottomNavigationView
        bottomView.setupWithNavController(navController)
        //replaceFragment(HomeFragment())

        var actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.back);
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
/*binding.bottomNavigationView.setOnItemSelectedListener {
    when(it.itemId){
        R.id.home-> replaceFragment(HomeFragment())
        R.id.exercise->replaceFragment(ExerciseCRUDFragment())
        R.id.diet->replaceFragment(DietFragment())
        else->{

        }
    }
    true
}*/









       /* val navController = this.findNavController(R.id.studentNavHost)

        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.navMenu.setNavigationItemSelectedListener(this)*/





        //val head = findViewById<TextView>(R.id.pageTitle)
       /* drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)*/
       /* val listener = NavController.OnDestinationChangedListener {controller, destination, arguments ->
           // head.text = destination.label
        }*/
       /* val bottomView = findViewById<BottomNavigationView>(R.id.bottomNav)
        navController.addOnDestinationChangedListener(listener)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.exerciseCRUDFragment, R.id.dietFragment))
        //navView.setupWithNavController(navController)
        bottomView.setupWithNavController(navController)*/
        //setSupportActionBar(toolbar)

        /*val navController = this.findNavController(R.id.studentNavHost)
        val listener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->


            }
        navController.addOnDestinationChangedListener(listener)
        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setupWithNavController(navController)
        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)*/




        /*val navView: BottomNavigationView = binding.bottomNav

        val navController = findNavController(R.id.studentNavHost)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.exerciseCRUDFragment, R.id.dietFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
    }
/*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }*/

    /*override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> {
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                Log.i("MainActivity", "Sign out clicked!")
            }
            R.id.exerciseCRUDFragment -> {
                Toast.makeText(this, "Exercise", Toast.LENGTH_SHORT).show()
            }
        }

        //binding.my_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }*/


  /*  private fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmenTranscation=fragmentManager.beginTransaction()
        fragmenTranscation.replace(R.id.frame_layout,fragment)
        fragmenTranscation.commit()

    }*/

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}