package edu.bu.homies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import edu.bu.homies.R
import edu.bu.homies.databinding.FragmentDetailBinding
import edu.bu.homies.viewmodel.CurHomeViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

//        val position:Int = arguments?.getInt("homeId")?:0

        val viewModel = ViewModelProvider(requireActivity()).get(CurHomeViewModel::class.java)

        viewModel.curHome.observe(viewLifecycleOwner, Observer {
            binding.homeTitle.text = it?.title?:""
            binding.homeDesc.text = it?.description?:""
            binding.homeFavoriteSwitch.isChecked = it.isFavorite?:false

            binding.homeTypesSpinner.adapter = ArrayAdapter(requireActivity(),android.R.layout.simple_list_item_1,
                it.links?: emptyArray())

            for(keyword in it.keywords?: emptyArray()){
                var chip = Chip(this.context)
                chip.text = keyword
                chip.isClickable = true
                binding.homeRoommates.addView(chip)
            }
        })

        binding.editHome.setOnClickListener{
            view.findNavController().
            navigate(R.id.action_detailFragment_to_editFragment)
        }

        binding.homeFavoriteSwitch.setOnCheckedChangeListener{ _, isChecked ->
            viewModel.updateHomeSwitch(isChecked)
        }

        binding.homeTypesSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var link = viewModel.curHome.value?.links?.get(p2)?:""
                var links = viewModel.curHome.value?.links?.toMutableList()?:mutableListOf<String>()
                links.remove(link)
                links.add(0,link)
                var arrayLinks = links.toTypedArray()
                viewModel.updateHomeTypes(arrayLinks)

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

}