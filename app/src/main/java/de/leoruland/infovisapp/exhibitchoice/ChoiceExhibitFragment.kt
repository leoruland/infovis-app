package de.leoruland.infovisapp.exhibitchoice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.databinding.FragmentChoiceExhibitBinding
import de.leoruland.infovisapp.repository.MockExhibitsRepository
import de.leoruland.infovisapp.states.TopicsChoiceStateHolder

class ChoiceExhibitFragment : Fragment() {

    private var _binding: FragmentChoiceExhibitBinding? = null
    private val binding get() = _binding!!
    private val exhibitAdapter =
        ExhibitAdapter(MockExhibitsRepository.getExhibits(TopicsChoiceStateHolder.getTopics()))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoiceExhibitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.exhibitRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.exhibitRecyclerView.adapter = exhibitAdapter

        binding.fabBack.setOnClickListener {
            findNavController().navigate(R.id.action_ChoiceExhibitFragment_to_ChoiceTopicFragment)
        }
        binding.fabNumberinput.setOnClickListener {
            findNavController().navigate(R.id.action_ChoiceExhibitFragment_to_DirectNumberInputFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}