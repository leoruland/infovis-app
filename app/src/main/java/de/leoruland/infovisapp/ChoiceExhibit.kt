package de.leoruland.infovisapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import de.leoruland.infovisapp.databinding.FragmentChoiceExhibitBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChoiceExhibit : Fragment() {

    private var _binding: FragmentChoiceExhibitBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChoiceExhibitBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabBack.setOnClickListener {
            findNavController().navigate(R.id.action_ChoiceExhibitFragment_to_ChoiceTopicFragment)
        }

        binding.fabCheck.setOnClickListener {
            findNavController().navigate(R.id.action_ChoiceExhibitFragment_to_DetailExhibitFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}