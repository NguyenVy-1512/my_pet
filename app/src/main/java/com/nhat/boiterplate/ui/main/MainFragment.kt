package com.nhat.boiterplate.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nhat.boiterplate.R
import com.nhat.boiterplate.adapters.DistanceSpinnerAdapter
import com.nhat.boiterplate.common.BaseFragment
import com.nhat.boiterplate.misc.buildLatLng
import com.nhat.boiterplate.misc.checkPermission
import com.nhat.boiterplate.replaceFragment
import com.nhat.boiterplate.ui.shop.InfoShopFragment
import com.nhat.huaweikit.demo.gms_services.GoogleLocationServices
import com.nhat.presentation.common.Common
import com.nhat.presentation.main.MainViewModel
import com.nhat.presentation.model.DistanceView
import com.nhat.presentation.model.PetShopView
import com.nhat.presentation.service.APIService
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : BaseFragment<PetShopView>(), OnMapReadyCallback {

    companion object {
        private val RUNTIME_PERMISSIONS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        } else {
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET
            )
        }

        private const val REQUEST_MAP_LOCATION_PERMISSION = 1999
        private const val TAG = "MainFragment"
    }

    private lateinit var distanceSpinnerAdapter: DistanceSpinnerAdapter

    override val layoutId: Int
        get() = R.layout.main_fragment

    @Inject
    lateinit var viewModel: MainViewModel

    //FIXME refactor using DI
    private lateinit var mService: APIService

    private var googleMap: GoogleMap? = null

    private val googleLocationServices: GoogleLocationServices by lazy {
        GoogleLocationServices().apply { init(requireActivity()) }
    }

    private val mInterstitialAd: InterstitialAd by lazy { InterstitialAd(this.context) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainViewModel::class.java)
        viewModel.setApiKey(getString(R.string.google_maps_key))

        getSupportToolbar()?.setTitle(R.string.app_name)

        setUpAds()

        findButton.setOnClickListener {
            safeStartSearchNearBy()
        }

        distanceSpinnerAdapter = DistanceSpinnerAdapter(
            requireContext(), // Context
            R.layout.view_distance_item // Layout
        ).apply {
            addAll(resources.getIntArray(R.array.distances).map { DistanceView(it) }
                .toMutableList())
        }

        distanceSpinner.adapter = distanceSpinnerAdapter

        distanceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                distanceSpinnerAdapter.setSelection(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Do nothing
            }
        }

        map.onCreate(savedInstanceState)

        map.getMapAsync(this)
        val Context = activity as AppCompatActivity

        mService = Common.googleApiServices

        viewModel.get().observe(
            viewLifecycleOwner,
            Observer<MutableList<PetShopView>> { listPetShop ->
                listPetShop
                    .map {
                        MarkerOptions()
                            .position(it.buildLatLng())
                            .title(it.placeName)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    }
                    .forEach { markerOptions ->
                        googleMap?.apply {
                            addMarker(markerOptions)
                            setOnInfoWindowClickListener {  Context.replaceFragment(InfoShopFragment()) }
                        }
                    }
            }
        )
        activity?.findViewById<Button>(R.id.findButton)?.setOnClickListener {
            //FIXME this function need to be complete when we implement the radius.
            //      viewModel.nearByPlace(keyword,mService);
            Context.replaceFragment(InfoShopFragment())
        }
    }

    private fun setUpAds() {
        // Create an ad request.
        val adRequest = AdRequest.Builder().build()
        // Start loading the ad in the background.
        adView.loadAd(adRequest)

        // Create the InterstitialAd with adUnitId
        mInterstitialAd.adUnitId = resources.getString(R.string.InterstitialAds_adUnitId)
        // Loading the InterstitialAd
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        // Load InterstitialAd again
        mInterstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                mInterstitialAd.show()
            }
        }
    }

    override fun onMapReady(_googleMap: GoogleMap?) {
        googleMap = _googleMap
        if (RUNTIME_PERMISSIONS.checkPermission(requireContext()).not()) {
            requestPermissions(RUNTIME_PERMISSIONS, REQUEST_MAP_LOCATION_PERMISSION)
        } else {
            googleMap?.apply {
                isMyLocationEnabled = true
                safeStartSearchNearBy()
            }
        }
    }

    private fun safeStartSearchNearBy() {
        if (RUNTIME_PERMISSIONS.checkPermission(requireContext()).not()) {
            requestPermissions(RUNTIME_PERMISSIONS, REQUEST_MAP_LOCATION_PERMISSION)
        } else {
            googleLocationServices.requestLocationUpdatesWithCallback {
                //FIXME Only get once
                googleLocationServices.removeLocationUpdatesWithCallback()
                googleMap?.moveCamera(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition(
                            LatLng(
                                it.latitude,
                                it.longitude
                            ), 16f, 0f, 0f
                        )
                    )
                )
                viewModel.nearByPlace(
                    getString(R.string.pet_store_keyword),
                    mService,
                    it.latitude,
                    it.longitude,
                    distanceSpinnerAdapter.getCurrentItem().distanceInMeters
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_MAP_LOCATION_PERMISSION) {
            if (grantResults.all { i -> i == PackageManager.PERMISSION_GRANTED }) {
                googleMap?.apply {
                    @SuppressLint("MissingPermission")
                    isMyLocationEnabled = true
                    safeStartSearchNearBy()
                }
            }
            //TODO fix UI problem for permission not allow
        }
    }

    override fun setupScreenForLoadingState() {
        TODO("Not yet implemented")
    }

    override fun setupScreenForSuccess(t: PetShopView?) {
        TODO("Not yet implemented")
    }

    override fun setupScreenForError(message: String?) {
        TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        map?.onStart()
    }

    override fun onStop() {
        super.onStop()
        map?.onStop()
    }

    override fun onResume() {
        super.onResume()
        map?.onResume()
    }

    override fun onPause() {
        super.onPause()
        map?.onPause()
    }

    override fun onDestroy() {
        map?.onDestroy()
        super.onDestroy()
    }
}