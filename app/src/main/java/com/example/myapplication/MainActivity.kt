package com.example.myapplication

import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView

    private val latitudes = listOf(42.877350, 42.547741, 42.463128, 42.594458, 42.339338, 42.457581,42.673351, 42.809858,43.0091774, 42.568189, 43.255952, 43.187529)

    private val longitudes= listOf(-8.542951, -6.596599, -6.118461, -5.580649, -3.701364, -2.442358, -1.812782, -1.655793, -1.31951, -0.552859, -1.071501, -0.614529)



    private val placemarkTapListener = MapObjectTapListener { _, point ->
        startActivity(Intent(this, InfoActivity::class.java).apply {
            putExtra("latitude", point.latitude)
            putExtra("longitude", point.longitude)
        })
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("f6cd547c-35ea-48f1-8a1c-580a85cba16b")

        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapview)

        val imageProvider = ImageProvider.fromResource(this, R.drawable.marker)
        for(i:Int in latitudes.indices){
            val placemark = mapView.map.mapObjects.addPlacemark().apply {
                geometry = Point(latitudes[i], longitudes[i])
                setIcon(imageProvider)
                println()
            }
            placemark.setIconStyle(
                IconStyle().apply {
                    anchor = PointF(0.5f, 1.0f)
                    scale = 0.1f
                }
            )
            placemark.addTapListener(placemarkTapListener)
        }



    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

}