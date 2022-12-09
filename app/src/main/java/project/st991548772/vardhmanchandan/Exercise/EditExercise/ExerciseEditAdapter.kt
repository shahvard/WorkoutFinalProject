package project.st991548772.vardhmanchandan.Exercise.EditExercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import project.st991548772.vardhmanchandan.R
import project.st991548772.vardhmanchandan.Exercise.ViewExercise.Record

class ExerciseEditAdapter(private val list: ArrayList<Record>, private val email:String):
    RecyclerView.Adapter<ExerciseEditAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val container=itemView.findViewById<LinearLayout>(R.id.container)
        val imageView: ImageView =itemView.findViewById(R.id.imageView)
        val dateTxt: EditText =itemView.findViewById(R.id.date)
        val typeTxt: EditText =itemView.findViewById(R.id.type)
        val distanceTxt: EditText =itemView.findViewById(R.id.distance)
        val durationTxt: EditText =itemView.findViewById(R.id.duration)
        val editTxt:TextView=itemView.findViewById(R.id.confirmEdit)
        val deleteTxt:TextView=itemView.findViewById(R.id.confirmDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseEditAdapter.MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.exerciseeditlist_item,parent,false)
        return ExerciseEditAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseEditAdapter.MyViewHolder, position: Int) {
        val currentItem = list.get(position)
        when (currentItem?.TypeOfWorkout) {
            "Running" -> holder.imageView.setImageResource(R.drawable.ic_baseline_directions_run_24)
            "Cycling" -> holder.imageView.setImageResource(R.drawable.ic_baseline_directions_bike_24)
            "Swimming" -> holder.imageView.setImageResource(R.drawable.swimming)
        }
        holder.dateTxt.setText(currentItem.Date)
        holder.typeTxt.setText(currentItem.TypeOfWorkout)
        holder.distanceTxt.setText(currentItem.distance )
        holder.durationTxt.setText(currentItem.duration)

        holder.editTxt.setOnClickListener() {

            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Are you sure you want to Edit?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val db= Firebase.firestore
                    val record = hashMapOf(
                        "Date" to holder.dateTxt.text.toString(),
                        "TypeOfWorkout" to holder.typeTxt.text.toString(),
                        "duration" to holder.durationTxt.text.toString(),
                        "distance" to holder.distanceTxt.text.toString()
                    )

                    db.collection("Exercises").document(email).collection(holder.typeTxt.text.toString()).document(holder.dateTxt.text.toString())
                        .set(record, SetOptions.merge())


                    Toast.makeText(
                        holder.itemView.context, "Record has been successfully Edited",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }


        holder.deleteTxt.setOnClickListener(){
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val db= Firebase.firestore

                    db.collection("Exercises").document(email).collection(holder.typeTxt.text.toString()).document(holder.dateTxt.text.toString())
                        .delete()


                    Toast.makeText(
                        holder.itemView.context, "Record has been successfully Deleted",
                        Toast.LENGTH_SHORT
                    ).show()


                    notifyDataSetChanged()

                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    override fun getItemCount(): Int =list.size



}