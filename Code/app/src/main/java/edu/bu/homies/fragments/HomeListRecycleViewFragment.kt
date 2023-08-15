package edu.bu.homies.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import edu.bu.homies.R
import edu.bu.homies.activities.AddHome
import edu.bu.homies.adapter.MyHomeListRecyclerViewAdapter
import edu.bu.homies.data.Home
import edu.bu.homies.databinding.FragmentHomeListRecyclerViewBinding
import edu.bu.homies.viewmodel.CurHomeViewModel
import edu.bu.homies.viewmodel.HomeListViewModel

/**
 * A fragment representing a list of Items.
 */
class HomeListRecycleViewFragment : Fragment() {
    private var _binding: FragmentHomeListRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 1

    private lateinit var myAdapter: MyHomeListRecyclerViewAdapter
    private lateinit var viewModel: CurHomeViewModel
    private lateinit var listViewModel: HomeListViewModel
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
        sharedPref = activity?.getSharedPreferences("ListPreferences", MODE_PRIVATE)!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeListRecyclerViewBinding.inflate(inflater,
            container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favesVal = sharedPref.getBoolean("faves",false)
        viewModel = ViewModelProvider(requireActivity()).get(CurHomeViewModel::class.java)
        listViewModel = ViewModelProvider(this).get(HomeListViewModel::class.java)

        binding.homelist?.apply{
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            myAdapter = MyHomeListRecyclerViewAdapter(
//                listViewModel.homeList?.value?: emptyList(),
                object : MyHomeListRecyclerViewAdapter.OnHomeClickListener {
                    override fun onHomeClick(home: Home) {
                        viewModel.setCurHome(home)
                        view.findNavController().navigate(
                            R.id.action_homeListRecycleViewFragment_to_detailFragment
                        )
                    }
                }
            )
            this.adapter = myAdapter

            listViewModel.homeList.observe(viewLifecycleOwner, Observer {
//                myAdapter.notifyDataSetChanged()
                binding.homeFavoriteFilter.isChecked = favesVal
                displayFavoriteHomes(favesVal)

            })

            viewModel.curHome.observe(viewLifecycleOwner, Observer {
                myAdapter.notifyDataSetChanged()
            })

            ItemTouchHelper(SwipeToDeleteCallback()).attachToRecyclerView(this)
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this.context, AddHome::class.java)
            startActivity(intent)
        }

        binding.homeFavoriteFilter.setOnCheckedChangeListener{_, isFilterOn ->
            val editor = sharedPref.edit()
            editor?.putBoolean("faves", isFilterOn)
            displayFavoriteHomes(isFilterOn)
            editor?.apply()
        }

    }

    private fun displayFavoriteHomes(filter: Boolean){
        listViewModel.homeList.observe(viewLifecycleOwner){ homes ->
            if(filter == true)
                myAdapter.replaceItems(homes.filter {it.isFavorite})
            else
                myAdapter.replaceItems(homes)
            viewModel.initCurHome(myAdapter.getHome(0))
        }
    }

    inner class SwipeToDeleteCallback: ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ) = makeMovementFlags(
            ItemTouchHelper.ACTION_STATE_SWIPE,
            ItemTouchHelper.RIGHT
        )

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.bindingAdapterPosition
            // get the home to be deleted
            val home = myAdapter.getHome(position)
            // delete the home and update curHome livedata in the viewmodel
            // add your code here
            if (viewModel.isCurHome(home)) {
                if (position > 0)
                    viewModel.setCurHome(myAdapter.getHome(position - 1))
                else if (myAdapter.getItemCount() > 1 )
                    viewModel.setCurHome(myAdapter.getHome(position + 1))
                else
                    viewModel.setCurHome(Home(0,"No more homes","", emptyArray(),emptyArray(),false))

            }
            listViewModel.delHome(home)
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                HomeListRecycleViewFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}