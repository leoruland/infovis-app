package de.leoruland.infovisapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.databinding.FragmentChoiceTopicBinding
import de.leoruland.infovisapp.model.ExhibitsRepository
import de.leoruland.infovisapp.viewmodel.TopicsChoiceStateHolder

class ChoiceTopicFragment : Fragment() {

    private var _binding: FragmentChoiceTopicBinding? = null
    private val binding get() = _binding!!
    private val topicAdapter = TopicAdapter(ExhibitsRepository.getTopics())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TopicsChoiceStateHolder.clearStore()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoiceTopicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topicRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.topicRecyclerView.adapter = topicAdapter

        binding.fabCheck.setOnClickListener {
            if (!TopicsChoiceStateHolder.isEmpty()) {
                findNavController().navigate(R.id.action_ChoiceTopicFragment_to_ChoiceExhibitFragment)
            }
        }
        binding.fabNumberinput.setOnClickListener {
            findNavController().navigate(R.id.action_ChoiceTopicFragment_to_DirectNumberInputFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        TopicsChoiceStateHolder.clearStore()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}