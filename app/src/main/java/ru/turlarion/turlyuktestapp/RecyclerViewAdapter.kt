package ru.turlarion.turlyuktestapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class RecyclerViewAdapter(
    private var list: List<CompanyPojo>,
    public val context: Context,
    private val baseUrl: String
):
RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>(){

    fun updateDataSet(newList: List<CompanyPojo>){
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(itemView, context)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.companyName?.text = list[position].name
        Picasso.get()
            .load(baseUrl + list[position].imgAddr)
            .error(R.mipmap.img_404)
            .into(holder.companyImg)

    }

    override fun getItemCount() = list.size

    class CustomViewHolder(itemView: View, context: Context):
        RecyclerView.ViewHolder(itemView){

        var companyName: TextView? = null
        var companyImg: ImageView? = null

        init {
            companyName = itemView.findViewById(R.id.textView)
            companyImg = itemView.findViewById(R.id.imageView)
            itemView.setOnClickListener {
                val myIntent = Intent(context, CompanyActivity::class.java)
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                myIntent.putExtra("id", layoutPosition+1)
                context.startActivity(myIntent)
            }
        }

    }
}