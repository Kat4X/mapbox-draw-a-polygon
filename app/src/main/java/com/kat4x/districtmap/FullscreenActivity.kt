package com.kat4x.districtmap

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.kat4x.districtmap.databinding.ActivityFullscreenBinding
import com.mapbox.geojson.GeoJson
import com.mapbox.geojson.Point
import com.mapbox.geojson.Polygon
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.FillLayer
import com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource


class FullscreenActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityFullscreenBinding
    private var _mapbox: MapboxMap? = null
    private val mapbox get() = _mapbox!!

    //MapBox Layers
    private val SOURCE_ID = "source-id"
    private val LAYER_ID = "layer-id"
    private val SETTLEMENT_LABEL = "settlement-label"

    //List of points
    private val POINTS: MutableList<MutableList<Point>> = mutableListOf()
    private val OUTER_POINTS: MutableList<Point> = mutableListOf()

    init {
        OUTER_POINTS.add(Point.fromLngLat(76.948320, 43.242796));
        OUTER_POINTS.add(Point.fromLngLat(76.946772, 43.254893));
        OUTER_POINTS.add(Point.fromLngLat(76.942651, 43.254674));
        OUTER_POINTS.add(Point.fromLngLat(76.940848, 43.259675));
        OUTER_POINTS.add(Point.fromLngLat(76.931907, 43.258956));
        OUTER_POINTS.add(Point.fromLngLat(76.932938, 43.253830));
        OUTER_POINTS.add(Point.fromLngLat(76.934827, 43.241451));
        POINTS.add(OUTER_POINTS);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token))
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        _mapbox = mapboxMap
        mapbox.setStyle(Style.MAPBOX_STREETS) { style ->
            style.addSource(GeoJsonSource(SOURCE_ID, Polygon.fromLngLats(POINTS)))
            style.addLayerBelow(FillLayer(LAYER_ID, SOURCE_ID).withProperties(
                fillColor(ContextCompat.getColor(this, R.color.custom_purple_500))
            ),  SETTLEMENT_LABEL)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

}