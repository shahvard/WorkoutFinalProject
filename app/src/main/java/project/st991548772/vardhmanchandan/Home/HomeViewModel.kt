package project.st991548772.vardhmanchandan.Home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.Diet.DietViewModel
import project.st991548772.vardhmanchandan.Exercise.ViewExercise.Record
import project.st991548772.vardhmanchandan.SignUp.User
import java.util.*
import kotlin.collections.ArrayList

class HomeViewModel: ViewModel() {

    val user: MutableLiveData<User> = MutableLiveData()
    val schedule: MutableLiveData<Schedule> = MutableLiveData()
    val exercisesThisMonth: MutableLiveData<Int> = MutableLiveData(0)
    val totalCalories: MutableLiveData<Int> = MutableLiveData(0)
    val todaysCalories: MutableLiveData<Int> = MutableLiveData(0)

        //function to get the personal info from the database
    fun getUserPersonalInfo(email:String){
        val db=Firebase.firestore
        val docRef = db.collection("users").document(email).get()
            .addOnSuccessListener { result ->
                user.value = User(result.data?.get("name").toString(), email,

                result.data?.get("weight").toString(),
                    result.data?.get("height").toString(),
                Integer.parseInt(result.data?.get("age").toString()),
                result.data?.get("gender").toString())

                Log.d("user",user.value!!.name)
            }
    }

    //getting todays schedule from the database
    fun getSchedule(day:String,email: String){
        val db=Firebase.firestore
        val docRef = db.collection("Schedule").document(email).collection("dayList").document(day).get()
            .addOnSuccessListener { result ->
                schedule.value= Schedule(result.data?.get("startTime").toString()
                ,result.data?.get("endTime").toString()
               ,result.data?.get("distance").toString()
               ,result.data?.get("typeOfWorkout").toString())
            }
        }

    //getting number of exercises done this month from database
    fun getExercisesThisMonth(month: String, email: String) {

        val db = Firebase.firestore
        val type=arrayOf("Swimming","Cycling","Running")
        var ar :ArrayList<Record> = arrayListOf()
        exercisesThisMonth.value=0
        for (a in type){


            val docRef = db.collection("Exercises").document(email).collection(a).get()
                .addOnSuccessListener { result ->
                    for (document in result) {

                        if(document.id.split("-").get(1)==month){
                            exercisesThisMonth.value = exercisesThisMonth.value!! + 1
                            Log.d("Exercises",exercisesThisMonth.value.toString())

                        }

                    }



                }
        }

    }

    //getting calories consuumed today and total calories for the day from the database
    fun getCalories(email:String){
        val db = Firebase.firestore

        val type = arrayOf("Breakfast", "Lunch", "Dinner")


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = (c.get(Calendar.MONTH)) + 1
        val day = c.get(Calendar.DAY_OF_MONTH)
        val todaydate = "$day-$month-$year"

        todaysCalories.value=0
        for (a in type) {


            db.collection("Diet").document(email).collection(a).get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.id == todaydate) {
                            Log.d(ContentValues.TAG, "${document.id} => ${document.data}")


                            todaysCalories.value = todaysCalories.value!! +

                                        Integer.parseInt(document.data.get("Calories").toString().split(" ").get(0))




                        }


                    }
                }
        }

        db.collection("Calories").document(email).get()
            .addOnSuccessListener { result ->
                totalCalories.value = Integer.parseInt(result.data?.get("Calories").toString())
                Log.d("totalCalories",totalCalories.value.toString())
            }



    }

}


//data class for schedule 
data class Schedule(var startTime:String,var endTime:String,var distance:String,var typeOfWorkout:String) {

}



