package project.st991548772.vardhmanchandan.Exercise

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
        val imageView: ImageView =itemView.findViewById(R.id.imageView)
        val dateEditText: EditText =itemView.findViewById(R.id.dateEditText)
        val typeOfWorkoutEditText: EditText =itemView.findViewById(R.id.typeOfWorkoutEditText)
        val distanceEditText: EditText =itemView.findViewById(R.id.distanceEditText)
        val durationEditText: EditText =itemView.findViewById(R.id.durationEditText)
        val editTxt:TextView=itemView.findViewById(R.id.confirmEdit)
        val deleteTxt:TextView=itemView.findViewById(R.id.confirmDelete)


        val dateTextView: TextView =itemView.findViewById(R.id.dateTxtView)
        val typeOfWorkoutTextView: TextView =itemView.findViewById(R.id.typeOfWorkoutTxtView)
        val distanceTextView: TextView =itemView.findViewById(R.id.distanceTxtView)
        val durationTextView: TextView =itemView.findViewById(R.id.durationTxtView)
        val confirmEdit:TextView=itemView.findViewById(R.id.confirmEditOk)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.exerciseeditlist_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.dateEditText.visibility=View.INVISIBLE
        holder.typeOfWorkoutEditText.visibility=View.INVISIBLE
        holder.distanceEditText.visibility=View.INVISIBLE
        holder.durationEditText.visibility=View.INVISIBLE
        holder.confirmEdit.visibility=View.INVISIBLE


        var currentItem = list.get(position)
        when (currentItem?.TypeOfWorkout) {
            "Running" -> holder.imageView.setImageResource(R.drawable.ic_baseline_directions_run_24)
            "Cycling" -> holder.imageView.setImageResource(R.drawable.ic_baseline_directions_bike_24)
            "Swimming" -> holder.imageView.setImageResource(R.drawable.swimming)
        }
        holder.dateTextView.setText(currentItem.Date)
        holder.typeOfWorkoutTextView.setText(currentItem.TypeOfWorkout)
        holder.distanceTextView.setText(currentItem.distance )
        holder.durationTextView.setText(currentItem.duration)



        holder.editTxt.setOnClickListener() {
            holder.dateEditText.visibility=View.VISIBLE
            holder.typeOfWorkoutEditText.visibility=View.VISIBLE
            holder.distanceEditText.visibility=View.VISIBLE
            holder.durationEditText.visibility=View.VISIBLE
            holder.confirmEdit.visibility=View.VISIBLE


            holder.dateEditText.setText(currentItem.Date)
            holder.typeOfWorkoutEditText.setText(currentItem.TypeOfWorkout)
            holder.distanceEditText.setText(currentItem.distance )
            holder.durationEditText.setText(currentItem.duration)


            holder.dateTextView.visibility=View.INVISIBLE
            holder.typeOfWorkoutTextView.visibility=View.INVISIBLE
            holder.distanceTextView.visibility=View.INVISIBLE
            holder.durationTextView.visibility=View.INVISIBLE
            holder.editTxt.visibility=View.INVISIBLE
            holder.deleteTxt.visibility=View.INVISIBLE





        }

        holder.confirmEdit.setOnClickListener(){

            var date=holder.dateEditText.text.toString()
            var typeOfWorkout=holder.typeOfWorkoutEditText.text.toString()
            var distance=holder.distanceEditText.text.toString()
            var duration=holder.durationEditText.text.toString()
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Are you sure you want to Edit?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val db= Firebase.firestore
                    val record = hashMapOf(
                        "Date" to holder.dateEditText.text.toString(),
                        "TypeOfWorkout" to holder.typeOfWorkoutEditText.text.toString(),
                        "duration" to holder.durationEditText.text.toString(),
                        "distance" to holder.distanceEditText.text.toString()
                    )

                    db.collection("Exercises").document(email).collection(holder.typeOfWorkoutEditText.text.toString()).document(holder.dateEditText.text.toString())
                        .set(record, SetOptions.merge())


                    Toast.makeText(
                        holder.itemView.context, "Record has been successfully Edited",
                        Toast.LENGTH_SHORT
                    ).show()


                    currentItem.Date=date
                    currentItem.TypeOfWorkout=typeOfWorkout
                    currentItem.distance=distance
                    currentItem.duration=duration


                    holder.dateTextView.setText(currentItem.Date)
                    holder.typeOfWorkoutTextView.setText(currentItem.TypeOfWorkout)
                    holder.distanceTextView.setText(currentItem.distance )
                    holder.durationTextView.setText(currentItem.duration)


                    holder.dateTextView.visibility=View.VISIBLE
                    holder.typeOfWorkoutTextView.visibility=View.VISIBLE
                    holder.distanceTextView.visibility=View.VISIBLE
                    holder.durationTextView.visibility=View.VISIBLE
                    holder.editTxt.visibility=View.VISIBLE
                    holder.deleteTxt.visibility=View.VISIBLE

                    holder.dateEditText.visibility=View.INVISIBLE
                    holder.typeOfWorkoutEditText.visibility=View.INVISIBLE
                    holder.distanceEditText.visibility=View.INVISIBLE
                    holder.durationEditText.visibility=View.INVISIBLE
                    holder.confirmEdit.visibility=View.INVISIBLE


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
                    list.remove(currentItem)
                    db.collection("Exercises").document(email).collection(holder.typeOfWorkoutTextView.text.toString()).document(holder.dateTextView.text.toString())
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