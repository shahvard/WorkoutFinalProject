package project.st991548772.vardhmanchandan.Exercise.ViewExercise

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewViewModel : ViewModel() {



    val list:MutableLiveData<ArrayList<Record>> = MutableLiveData()


    fun getFromDatabase(date:String,email:String){

        val db = Firebase.firestore
        val type=arrayOf("Swimming","Cycling","Running")
        var ar :ArrayList<Record> = arrayListOf()
        for (a in type){


        val docRef = db.collection("Exercises").document(email).collection(a).get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    if(document.id==date){
                        Log.d(TAG, "${document.id} => ${document.data}")

                        ar.add(Record(document.data.get("Date").toString(),document.data.get("TypeOfWorkout").toString(),document.data.get("distance").toString(),document.data.get("duration").toString()))

                    }

                }

                list.postValue(ar)

            }
        }


    }


}

data class Record(var Date:String,var TypeOfWorkout:String, var distance:String, var duration:String) {

}
