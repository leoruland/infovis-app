package de.leoruland.infovisapp.controller

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.MinimapOverlay
import java.util.concurrent.Executors

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

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topicBubblesRecyclerView.layoutManager =
            GridLayoutManager(
                requireContext(),
                4,
                GridLayoutManager.VERTICAL,
                false
            )
        binding.topicBubblesRecyclerView.adapter = topicBubblesAdapter

        exhibit?.let {
            binding.exhibitTitle.text =
                String.format(getString(R.string.detail_exhibit_item_title), it.number, it.name)
            binding.exhibitDescription.text = it.description
        }
        binding.fabBack.setOnClickListener {
            if (TopicsChoiceStateHolder.isEmpty()) {
                findNavController().navigate(R.id.action_DetailExhibitFragment_to_ChoiceTopicFragment)
            } else findNavController().navigate(R.id.action_DetailExhibitFragment_to_ChoiceExhibitFragment)
        }
        binding.fabNumberinput.setOnClickListener {
            findNavController().navigate(R.id.action_DetailExhibitFragment_to_DirectNumberInputFragment)
        }

        setupImageFromURL(exhibit?.imageLink)
        setupMap()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    /**
     * Code von GeeksforGeeks: "How to Load Any Image From URL Without Using Any Dependency in Android?"
     * https://www.geeksforgeeks.org/how-to-load-any-image-from-url-without-using-any-dependency-in-android/
     * am 18.03.2022
     */
    private fun setupImageFromURL(url: String?) {
        val imageView = binding.exhibitImage
        imageView.setImageResource(R.drawable.ic_missing)
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap?
        executor.execute {
            try {
                val `in` = java.net.URL(url).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    imageView.setImageBitmap(image)
                }
            }
            catch (e: Exception) {
                Log.d("DEBUG","No image acquired")
            }
        }
    }

    private fun setupMap() {
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        map = binding.map
        map.setTileSource(TileSourceFactory.WIKIMEDIA)
        map.setBuiltInZoomControls(false)
        exhibit?.let { setMapCenter(it.location) }
        setupMarker()
        setupMinimap()
    }

    private fun setMapCenter(geoPoint: GeoPoint = GeoPoint(52.48904896922605, 13.394676226814221)) {
        val mapController = map.controller
        mapController.setZoom(7.0)
        mapController.setCenter(geoPoint);
    }

    private fun setupMinimap() {
        val minimapOverlay = MinimapOverlay(requireContext(), map.tileRequestCompleteHandler)
        minimapOverlay.width = 400
        minimapOverlay.height = 200
        minimapOverlay.setTileSource(TileSourceFactory.WIKIMEDIA)
        map.overlays.add(minimapOverlay)
    }

    private fun setupMarker() {
        val marker = Marker(map)
        exhibit?.let { marker.position = it.location }
        marker.setAnchor(Marker.ANCHOR_LEFT, Marker.ANCHOR_BOTTOM)
        marker.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_flag_location)
        map.overlays.add(marker)
        map.invalidate()
    }
}