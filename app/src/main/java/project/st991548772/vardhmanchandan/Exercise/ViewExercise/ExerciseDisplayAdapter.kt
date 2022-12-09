package project.st991548772.vardhmanchandan.Exercise.ViewExercise

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.st991548772.vardhmanchandan.R

class ExerciseDisplayAdapter(private val list:ArrayList<Record>):RecyclerView.Adapter<ExerciseDisplayAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val imageView:ImageView=itemView.findViewById(R.id.imageView)
        val dateTxt:TextView=itemView.findViewById(R.id.date)
        val typeTxt:TextView=itemView.findViewById(R.id.type)
        val distanceTxt:TextView=itemView.findViewById(R.id.distance)
        val durationTxt:TextView=itemView.findViewById(R.id.duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.exerciselist_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list.get(position)
        when (currentItem?.TypeOfWorkout) {
            "Running" -> holder.imageView.setImageResource(R.drawable.ic_baseline_directions_run_24)
            "Cycling" -> holder.imageView.setImageResource(R.drawable.ic_baseline_directions_bike_24)
            "Swimming" -> holder.imageView.setImageResource(R.drawable.swimming)
        }
        holder.dateTxt.text = currentItem.Date
        holder.typeTxt.text = currentItem.TypeOfWorkout
        holder.distanceTxt.text = currentItem.distance + " kms"
        holder.durationTxt.text = currentItem.duration + " minutes"

    }


    override fun getItemCount(): Int = list.size

}
