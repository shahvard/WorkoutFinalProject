package project.st991548772.vardhmanchandan.ViewExercise

import android.content.ContentValues.TAG
import android.icu.text.AlphabeticIndex
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ViewViewModel : ViewModel() {

        /*private val month:MutableLiveData<String> = MutableLiveData()
        private val year:MutableLiveData<String> = MutableLiveData()*/

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
                        //Log.d("Date",document.data.get("Date").toString())

                    }

                }

                list.postValue(ar)


               // Log.d("list",list.value!!.get(0).toString())



            }
        }


    }


}

data class Record(val Date:String,val typeOfWorkout:String, val distance:String, val duration:String) {

}
