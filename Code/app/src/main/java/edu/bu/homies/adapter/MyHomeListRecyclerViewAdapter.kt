package edu.bu.homies.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import edu.bu.homies.data.Home
import edu.bu.homies.databinding.FragmentHomeItemBinding


class MyHomeListRecyclerViewAdapter(
//        private val homes: List<Home>,
        private val onHomeClickListener: OnHomeClickListener)
    : RecyclerView.Adapter<MyHomeListRecyclerViewAdapter.ViewHolder>() {

    private val homes = mutableListOf<Home>()

    fun replaceItems(myHomes: List<Home>){
        homes.clear()
        homes.addAll(myHomes)
        notifyDataSetChanged()
    }

    interface OnHomeClickListener{
        fun onHomeClick(home:Home)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val home = homes[position]
        holder.contentView.text = home.title
        holder.cardView.setOnClickListener{
            onHomeClickListener.onHomeClick(home)
        }
    }

    override fun getItemCount(): Int = homes.size

    fun getHome(pos: Int): Home {
        if (homes.size > 0)
            return homes[pos]
        else
            return Home(0,"","", emptyArray(), emptyArray(),arrayOf<String>(),false)
    }

    inner class ViewHolder(binding: FragmentHomeItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.homeTitleinCard
        val cardView: CardView = binding.homeCard

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

    }

}