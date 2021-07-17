package com.example.myapplication.place

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemPlaceBinding
import com.example.myapplication.entity.Place

class PlaceAdapter(private val context: Context, private val placeList: ArrayList<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    private val inflater by lazy {
        val inflater = LayoutInflater.from(context)
        inflater
    }

    fun setData(placeList: ArrayList<Place>) {
        this.placeList.clear()
        this.placeList.addAll(placeList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        val placeName = binding.placeName
        val placeAddress = binding.placeAddress
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlaceBinding.inflate(inflater, parent, false)
        val viewHolder = ViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.layoutPosition
            mOnItemClickListener?.onItemClick(position)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount() = placeList.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var mOnItemClickListener: OnItemClickListener? = null

    public fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

}