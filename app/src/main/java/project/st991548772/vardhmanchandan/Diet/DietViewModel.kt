package project.st991548772.vardhmanchandan.Diet

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class DietViewModel : ViewModel() {

    val dietList: MutableLiveData<ArrayList<DietRecord>> = MutableLiveData()

    var todaysCalories: MutableLiveData<Int> =MutableLiveData(0)
    var totalCalories: MutableLiveData<Int> =MutableLiveData()



    fun getFromDatabase(date: String, email: String) {

        val db = Firebase.firestore
        val type = arrayOf("Breakfast", "Lunch", "Dinner")
        var ar: ArrayList<DietRecord> = arrayListOf()

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
                        if (document.id == date) {
                            Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                            ar.add(
                                DietRecord(
                                    document.data.get("Date").toString(),
                                    document.data.get("TypeOfMeal").toString(),
                                    document.data.get("ItemName").toString(),
                                    document.data.get("Calories").toString()
                                )

                                )
                            todaysCalories.value = todaysCalories.value!! +
                                    Integer.parseInt(document.data.get("Calories").toString().split(" ").get(0)
                                        .toString()
                                    )



                        }


                        dietList.postValue(ar)

                    }

                    db.collection("Calories").document(email).get()
                        .addOnSuccessListener { result ->
                            totalCalories.value = Integer.parseInt(result.data?.get("Calories").toString())
                        }
                }




        }
    }






    data class DietRecord(
        var Date: String,
        var TypeOfDiet: String,
        var ItemName: String,
        var Calories: String
    ) {}
}