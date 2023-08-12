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
        private val onProjectClickListener: OnProjectClickListener)
    : RecyclerView.Adapter<MyHomeListRecyclerViewAdapter.ViewHolder>() {

    private val homes = mutableListOf<Home>()

    fun replaceItems(myHomes: List<Home>){
        homes.clear()
        homes.addAll(myHomes)
        notifyDataSetChanged()
    }

    interface OnProjectClickListener{
        fun onProjectClick(home:Home)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = homes[position]
        holder.idView.text = (project.id).toString()
        holder.contentView.text = project.title
        holder.cardView.setOnClickListener{
            onProjectClickListener.onProjectClick(project)
//            val action =
//                HomeListRecycleViewFragmentDirections.actionProjListRecycleViewFragmentToDetailFragment(
//                    position
//                )
//            it.findNavController().navigate(action)

  //          it.findNavController().navigate(R.id.action_projListRecycleViewFragment_to_detailFragment)

        }
    }

    override fun getItemCount(): Int = homes.size

    fun getProject(pos: Int): Home {
        if (homes.size > 0)
            return homes[pos]
        else
            return Home(0,"","","", emptyArray(), emptyArray(),false)
    }

    inner class ViewHolder(binding: FragmentHomeItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.projIdView
        val contentView: TextView = binding.projTitleinCard
        val cardView: CardView = binding.projectCard

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

    }

}