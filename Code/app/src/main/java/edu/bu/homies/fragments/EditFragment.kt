package edu.bu.homies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import edu.bu.homies.R
import edu.bu.homies.databinding.FragmentEditBinding
import edu.bu.homies.viewmodel.CurHomeViewModel
import java.lang.StringBuilder

class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = EditFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(CurHomeViewModel::class.java)

        viewModel.curHome.observe(viewLifecycleOwner, Observer {
            binding.homeTitleEdit.setText(it.title)

            val reminders: StringBuilder = StringBuilder()
            for(reminder in it.reminders){
                reminders.append(reminder)
                reminders.append(",")
            }
            reminders.deleteRange(reminders.lastIndex, reminders.lastIndex + 1)
            binding.homeRemindersEdit.setText(reminders)

            var keywords: StringBuilder = StringBuilder()
            for (keyword in it.keywords) {
                keywords.append(keyword)
                keywords.append(",")
            }
            keywords.deleteRange(keywords.lastIndex, keywords.lastIndex + 1)
            binding.homeRoommatesEdit.setText(keywords)
        })


        binding.submit.setOnClickListener {
            viewModel.updateCurHome(
                binding.homeTitleEdit.text.toString(),
                binding.homeRemindersEdit.text.toString().split(",").toTypedArray(),
                binding.homeRoommatesEdit.text.toString().split(",").toTypedArray()
            )

            view.findNavController().navigate(R.id.action_editFragment_pop)

        }

        binding.cancel.setOnClickListener {
            view.findNavController().navigate(R.id.action_editFragment_pop)
        }


    }


}