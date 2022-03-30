package de.leoruland.infovisapp.numberinput

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.databinding.FragmentDirectNumberInputBinding
import de.leoruland.infovisapp.repository.MockExhibitsRepository
import de.leoruland.infovisapp.states.ExhibitChoiceStateHolder

class DirectNumberInputFragment : Fragment() {
    private var _binding: FragmentDirectNumberInputBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDirectNumberInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.errorText.visibility = View.INVISIBLE
        binding.fabClose.setOnClickListener { closePanel() }
        binding.searchButton.setOnClickListener {
            val exhibitId = binding.numberInput.text.toString()
            val exhibit = MockExhibitsRepository.getExhibit(exhibitId)
            when {
                exhibit != null -> {
                    ExhibitChoiceStateHolder.setExhibit(exhibit)
                    openExhibit()
                }
                exhibitId.isNotBlank() -> {
                    binding.errorText.visibility = View.VISIBLE
                    binding.errorText.text = String.format(getString(R.string.error_object_not_found), exhibitId)
                }
                else -> binding.errorText.visibility = View.INVISIBLE
            }
        }
    }

    private fun closePanel() {
        navController = findNavController()
        navController.navigateUp()
    }

    private fun openExhibit() {
        navController = findNavController()
        navController.navigate(R.id.action_DirectNumberInputFragment_to_DetailExhibitFragment)
    }
}