package de.leoruland.infovisapp.exhibitdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import de.leoruland.infovisapp.R
import de.leoruland.infovisapp.databinding.FragmentDetailExhibitBinding
import de.leoruland.infovisapp.states.ExhibitChoiceStateHolder
import de.leoruland.infovisapp.states.TopicsChoiceStateHolder
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class DetailExhibitFragment : Fragment() {
    private var _binding: FragmentDetailExhibitBinding? = null
    private val binding get() = _binding!!
    private val topicBubblesAdapter: TopicBubblesAdapter

    private lateinit var map: MapView
    private val exhibit = ExhibitChoiceStateHolder.getExhibit()

    init {
        topicBubblesAdapter = TopicBubblesAdapter(exhibit!!.topics)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailExhibitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLabels()
        setupBubbles()
        setupMap()
        setupActionListeners()
    }

    private fun setupLabels() {
        exhibit?.let {
            binding.exhibitTitle.text =
                String.format(getString(R.string.detail_exhibit_item_title), it.number, it.name)
            binding.exhibitRepositoryLabel.text =
                String.format(getString(R.string.detail_exhibit_repository), it.repository)
            binding.exhibitFindSpotLabel.text = getString(R.string.detail_exhibit_find_spot)
            binding.exhibitDescription.text = it.description
            binding.fabOpen.setOnClickListener { openLink(exhibit.id) }
        }
    }

    private fun setupBubbles() {
        binding.topicBubblesRecyclerView.layoutManager =
            GridLayoutManager(
                requireContext(),
                4,
                GridLayoutManager.VERTICAL,
                false
            )
        binding.topicBubblesRecyclerView.adapter = topicBubblesAdapter
    }

    private fun setupMap() {
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        map = binding.map
        map.apply {
            exhibit?.let {
                setTileSource(TileSourceFactory.WIKIMEDIA)
                setMultiTouchControls(true)
                setScrollableAreaLimitDouble(
                    BoundingBox(
                        exhibit.location.latitude,
                        exhibit.location.longitude,
                        exhibit.location.latitude,
                        exhibit.location.longitude
                    )
                )
                zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            }
            exhibit?.let { setMapCenter(it.location) }
        }
        setupMarker()
    }

    private fun setMapCenter(geoPoint: GeoPoint = GeoPoint(52.48904896922605, 13.394676226814221)) {
        val mapController = map.controller
        mapController.setZoom(5.0)
        mapController.setCenter(geoPoint);
    }

    private fun setupMarker() {
        val marker = Marker(map)
        exhibit?.let { marker.position = it.location }
        marker.setAnchor(Marker.ANCHOR_LEFT, Marker.ANCHOR_BOTTOM)
        marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_flag_location)
        map.overlays.add(marker)
        map.invalidate()
    }

    private fun setupActionListeners() {
        binding.fabBack.setOnClickListener {
            if (TopicsChoiceStateHolder.isEmpty()) {
                findNavController().navigate(R.id.action_DetailExhibitFragment_to_ChoiceTopicFragment)
            } else findNavController().navigate(R.id.action_DetailExhibitFragment_to_ChoiceExhibitFragment)
        }
        binding.fabNumberinput.setOnClickListener {
            findNavController().navigate(R.id.action_DetailExhibitFragment_to_DirectNumberInputFragment)
        }
    }

    private fun openLink(url: String) {
        if (url.isNotBlank()) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}