package project.st991548772.vardhmanchandan.Diet

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import project.st991548772.vardhmanchandan.R
import java.util.ArrayList

class DietDisplayAdapter(private val list: ArrayList<DietViewModel.DietRecord>, private val email:String):
    RecyclerView.Adapter<DietDisplayAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView =itemView.findViewById(R.id.imageView)
        val dateTxtView: TextView =itemView.findViewById(R.id.dateTxtView)
        val typeTxtView: TextView =itemView.findViewById(R.id.typeTxtView)
        val itemTxtView: TextView =itemView.findViewById(R.id.itemTxtView)
        val caloriesTxtView: TextView =itemView.findViewById(R.id.caloriesTxtView)
        val editTxtView:TextView=itemView.findViewById(R.id.confirmEdit)
        val deleteTxtView:TextView=itemView.findViewById(R.id.confirmDelete)

        val dateEditText: TextView =itemView.findViewById(R.id.dateEditText)
        val typeEditText: TextView =itemView.findViewById(R.id.typeEditText)
        val itemEditText: TextView =itemView.findViewById(R.id.itemEditText)
        val caloriesEditText: TextView =itemView.findViewById(R.id.caloriesEditText)
        val confirmEdit:TextView=itemView.findViewById(R.id.confirmEditOk)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DietDisplayAdapter.MyViewHolder {
        val itemView=
            LayoutInflater.from(parent.context).inflate(R.layout.dietlist_item,parent,false)
        return DietDisplayAdapter.MyViewHolder(itemView)
    }


    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.dateEditText.visibility=View.INVISIBLE
        holder.typeEditText.visibility=View.INVISIBLE
        holder.itemEditText.visibility=View.INVISIBLE
        holder.caloriesEditText.visibility=View.INVISIBLE
        holder.confirmEdit.visibility=View.INVISIBLE
        var currentItem = list.get(position)
        when (currentItem?.TypeOfDiet) {
            "Breakfast" -> holder.imageView.setImageResource(R.drawable.breakfast)
            "Lunch" -> holder.imageView.setImageResource(R.drawable.lunch)
            "Dinner" -> holder.imageView.setImageResource(R.drawable.dinner)
        }


        holder.dateTxtView.text = currentItem.Date
        holder.typeTxtView.text = currentItem.TypeOfDiet
        holder.itemTxtView.text = currentItem.ItemName
        holder.caloriesTxtView.text = currentItem.Calories


        holder.editTxtView.setOnClickListener(){
            holder.dateEditText.visibility=View.VISIBLE
            holder.typeEditText.visibility=View.VISIBLE
            holder.itemEditText.visibility=View.VISIBLE
            holder.caloriesEditText.visibility=View.VISIBLE
            holder.confirmEdit.visibility=View.VISIBLE


            holder.dateEditText.setText(currentItem.Date)
            //Log.d("Date",currentItem.Date)
            holder.typeEditText.setText(currentItem.TypeOfDiet)
            holder.itemEditText.setText(currentItem.ItemName)
            holder.caloriesEditText.setText(currentItem.Calories )


            holder.dateTxtView.visibility=View.INVISIBLE
            holder.typeTxtView.visibility=View.INVISIBLE
            holder.itemTxtView.visibility=View.INVISIBLE
            holder.caloriesTxtView.visibility=View.INVISIBLE
            holder.editTxtView.visibility=View.INVISIBLE
            holder.deleteTxtView.visibility=View.INVISIBLE


        }

        holder.confirmEdit.setOnClickListener(){
            var date=holder.dateEditText.text.toString()
            var typeOfMeal=holder.typeEditText.text.toString()
            var calories=holder.caloriesEditText.text.toString()
            var itemName=holder.itemEditText.text.toString()
            val dietRecord= hashMapOf(
                "Date" to date,
                "TypeOfMeal" to typeOfMeal,
                "Calories" to calories,
                "ItemName" to itemName

            )

            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Are you sure you want to Edit?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    val db= Firebase.firestore


                    db.collection("Diet").document(email).collection(holder.typeEditText.text.toString()).document(holder.dateEditText.text.toString())
                        .set(dietRecord, SetOptions.merge())


                    Toast.makeText(
                        holder.itemView.context, "Record has been successfully Edited",
                        Toast.LENGTH_SHORT
                    ).show()

                    currentItem.Date=date
                    currentItem.TypeOfDiet=typeOfMeal
                    currentItem.ItemName=itemName
                    currentItem.Calories=calories

                    holder.dateTxtView.text = currentItem.Date
                    holder.typeTxtView.text = currentItem.TypeOfDiet
                    holder.itemTxtView.text = currentItem.ItemName
                    holder.caloriesTxtView.text = currentItem.Calories

                    holder.dateTxtView.visibility=View.VISIBLE
                    holder.typeTxtView.visibility=View.VISIBLE
                    holder.itemTxtView.visibility=View.VISIBLE
                    holder.caloriesTxtView.visibility=View.VISIBLE
                    holder.editTxtView.visibility=View.VISIBLE
                    holder.deleteTxtView.visibility=View.VISIBLE

                    holder.dateEditText.visibility=View.INVISIBLE
                    holder.typeEditText.visibility=View.INVISIBLE
                    holder.itemEditText.visibility=View.INVISIBLE
                    holder.caloriesEditText.visibility=View.INVISIBLE
                    holder.confirmEdit.visibility=View.INVISIBLE





                }
                .setNegativeButton("No") { dialog, id ->

                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        holder.deleteTxtView.setOnClickListener(){






            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setMessage("Are you sure you want to Delete?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->


                    list.remove(currentItem)
                    val db= Firebase.firestore
                    db.collection("Diet").document(email).collection(holder.typeTxtView.text.toString()).document(holder.dateTxtView.text.toString())
                        .delete()


                    Toast.makeText(
                        holder.itemView.context, "Record has been successfully Edited",
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
}