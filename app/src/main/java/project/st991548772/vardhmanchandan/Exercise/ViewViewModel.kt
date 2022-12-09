package project.st991548772.vardhmanchandan.Exercise.ViewExercise

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewViewModel : ViewModel() {



    val list:MutableLiveData<ArrayList<Record>> = MutableLiveData()


    fun getFromDatabase(month:Int,year:Int,email:String){

        val db = Firebase.firestore
        val type=arrayOf("Swimming","Cycling","Running")
        var ar :ArrayList<Record> = arrayListOf()
        for (a in type){


        val docRef = db.collection("Exercises").document(email).collection(a).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var monthFromDatabase=Integer.parseInt(document.id.split("-").get(1))
                    if(monthFromDatabase==month){
                        Log.d(TAG, "${document.id} => ${document.data}")

                        ar.add(Record(document.data.get("Date").toString(),document.data.get("TypeOfWorkout").toString(),document.data.get("distance").toString(),document.data.get("duration").toString()))

                    }

                }

                list.postValue(ar)

            }
        }


    }


}

data class Record(val Date:String,val TypeOfWorkout:String, val distance:String, val duration:String) {

}
