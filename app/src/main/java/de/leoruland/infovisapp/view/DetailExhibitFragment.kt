package de.leoruland.infovisapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.databinding.FragmentDetailExhibitBinding
import de.leoruland.infovisapp.viewmodel.ExhibitChoiceStateHolder
import de.leoruland.infovisapp.viewmodel.TopicsChoiceStateHolder

class DetailExhibitFragment : Fragment() {
    private var _binding: FragmentDetailExhibitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailExhibitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exhibitTitle.text = ExhibitChoiceStateHolder.getExhibit()?.name + ""
        binding.fabBack.setOnClickListener {
            if (TopicsChoiceStateHolder.isEmpty()) {
                findNavController().navigate(R.id.action_DetailExhibitFragment_to_ChoiceTopicFragment)
            } else findNavController().navigate(R.id.action_DetailExhibitFragment_to_ChoiceExhibitFragment)
        }
        binding.fabNumberinput.setOnClickListener {
            findNavController().navigate(R.id.action_DetailExhibitFragment_to_DirectNumberInputFragment)
        }
    }
}