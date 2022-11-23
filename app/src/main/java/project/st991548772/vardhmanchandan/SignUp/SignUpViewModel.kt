package project.st991548772.vardhmanchandan.SignUp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class SignUpViewModel: ViewModel() {
    private lateinit var database: DatabaseReference
    val db = Firebase.firestore
    private var user:User=User("","","","",0,"")

    fun addUser(name:String,email:String,weight:String,height:String,age:Int,gender:String){
        user=User(name,email,weight,height,age,gender)

        db.collection("users").document(email)
            .set(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

    }
}