package de.leoruland.infovisapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.databinding.FragmentChoiceExhibitBinding
import de.leoruland.infovisapp.model.ExhibitsRepository
import de.leoruland.infovisapp.viewmodel.TopicsChoiceStateHolder

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChoiceExhibitFragment : Fragment() {

    private var _binding: FragmentChoiceExhibitBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val exhibitAdapter =
        ExhibitAdapter(ExhibitsRepository.getExhibits(TopicsChoiceStateHolder.getTopics()))

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
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}