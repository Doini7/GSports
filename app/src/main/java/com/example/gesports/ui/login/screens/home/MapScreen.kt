package com.example.gesports.ui.login.screens.home

import CustomTopBar
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

private val GYM_ZEUS_LOCATION = GeoPoint(38.125642, -0.874710)
private const val GYM_ZEUS_NOMBRE = "Gimnasio Zeus"
private const val GYM_ZEUS_DIRECCION = "C/ Gral. Primo de Rivera, 14 · Callosa de Segura"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    navController: NavController,
    onLogout: () -> Unit = {}
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        Configuration.getInstance().userAgentValue = context.packageName
    }

    var permisoConcedido by remember { mutableStateOf(false) }
    var permisoDenegado by remember { mutableStateOf(false) }
    var ubicacionUsuario by remember { mutableStateOf<GeoPoint?>(null) }
    var cargando by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        val concedido = permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (concedido) {
            permisoConcedido = true
            permisoDenegado = false
            cargando = true
            obtenerUbicacion(context) { geoPoint ->
                ubicacionUsuario = geoPoint
                cargando = false
            }
        } else {
            permisoDenegado = true
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Gimnasio Zeus",
                isMainScreen = false,
                onLogoutClick = onLogout,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                cargando -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color(0xFF9C27B0))
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Obteniendo tu ubicación...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                permisoConcedido || permisoDenegado -> {
                    OsmMapView(
                        gymLocation = GYM_ZEUS_LOCATION,
                        userLocation = ubicacionUsuario,
                        permisoDenegado = permisoDenegado
                    )
                }

                else -> {
                    CircularProgressIndicator(color = Color(0xFF9C27B0))
                }
            }
        }
    }
}


@Composable
private fun OsmMapView(
    gymLocation: GeoPoint,
    userLocation: GeoPoint?,
    permisoDenegado: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                MapView(ctx).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    controller.setZoom(20.0)
                    controller.setCenter(gymLocation)

                    val markerGym = Marker(this).apply {
                        position = gymLocation
                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                        title = GYM_ZEUS_NOMBRE
                        snippet = GYM_ZEUS_DIRECCION
                    }
                    overlays.add(markerGym)

                    if (userLocation != null) {
                        val markerUser = Marker(this).apply {
                            position = userLocation
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            title = "Tu ubicación"
                            snippet = "Estás aquí"
                        }
                        overlays.add(markerUser)
                    }
                }
            },
            update = { mapView ->
                val userMarker = mapView.overlays.filterIsInstance<Marker>()
                    .find { it.title == "Tu ubicación" }

                if (userLocation != null) {
                    if (userMarker != null) {
                        userMarker.position = userLocation
                    } else {
                        val nuevoMarker = Marker(mapView).apply {
                            position = userLocation
                            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            title = "Tu ubicación"
                            snippet = "Estás aquí"
                        }
                        mapView.overlays.add(nuevoMarker)
                    }
                }
                mapView.invalidate()
            }
        )

        if (permisoDenegado) {
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = Color(0xCCFFFFFF),
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 4.dp
            ) {
                Text(
                    text = "📍 Permiso de ubicación denegado. Mostrando la ubicación del gimnasio.",
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray
                )
            }
        }
    }
}


@SuppressLint("MissingPermission")
private fun obtenerUbicacion(context: Context, onResult: (GeoPoint?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            if (location != null) {
                onResult(GeoPoint(location.latitude, location.longitude))
            } else {
                val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
                    com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY, 5000L
                ).setMaxUpdates(1).build()

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    object : com.google.android.gms.location.LocationCallback() {
                        override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                            fusedLocationClient.removeLocationUpdates(this)
                            val loc = result.lastLocation
                            onResult(if (loc != null) GeoPoint(loc.latitude, loc.longitude) else null)
                        }
                    },
                    android.os.Looper.getMainLooper()
                )
            }
        }
        .addOnFailureListener {
            onResult(null)
        }
}