package project.st991548772.vardhmanchandan.Diet

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DietViewModel : ViewModel() {

    val dietList: MutableLiveData<ArrayList<DietRecord>> = MutableLiveData()


    fun getFromDatabase(date:String,email:String){

        val db = Firebase.firestore
        val type=arrayOf("Breakfast","Lunch","Dinner")
        var ar :ArrayList<DietRecord> = arrayListOf()
        for (a in type){


            val docRef = db.collection("Diet").document(email).collection(a).get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if(document.id==date){
                            Log.d(ContentValues.TAG, "${document.id} => ${document.data}")

                            ar.add(DietRecord(document.data.get("Date").toString(),document.data.get("TypeOfMeal").toString(),document.data.get("ItemName").toString(),document.data.get("Calories").toString()))

                        }

                    }

                    dietList.postValue(ar)

                }
        }


    }


}

data class DietRecord(var Date:String, var TypeOfDiet:String, var ItemName:String, var Calories:String) {

}
