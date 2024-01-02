package com.example.spacemate

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spacemate.R
import com.example.spacemate.booking

class CustomAdapter2(private val mList: List<ItemsViewModel2>) :
    RecyclerView.Adapter<CustomAdapter2.ViewHolder>() {

    // create new views
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: ContactActivity) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design2, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel2 = mList[position]

        // sets the image to the imageview from our itemHolder class

        //var imageBytes = ItemsViewModel.image2
        //val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        //holder.imageView.setImageBitmap(bitmap)
        //holder.imageView.setImageBitmap(ItemsViewModel.image2)
        //holder.imageView.setImageResource(ItemsViewModel.image)
        // sets the text to the textview from our itemHolder class
        holder.textView.text = "${ItemsViewModel2.Bname} with Email: ${ItemsViewModel2.Bmail} is intrested to rent ${ItemsViewModel2.hName}(House Name)" +
                " located at ${ItemsViewModel2.location}"

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView2)
    }
}
