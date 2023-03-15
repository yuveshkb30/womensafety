package com.yuvesh.womensafety

import android.Manifest
import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.location.Location
import android.location.LocationManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest

class MainActivity2 : AppCompatActivity() {


    lateinit var b1: Button
    lateinit var b2: Button
    lateinit var client: FusedLocationProviderClient
    lateinit var mydb: DatabaseHandler
    lateinit var builder: LocationSettingsRequest.Builder
    var REQUEST_CHECK_CODE: Int = 8989
    var REQUEST_LOCATION: Int = 1
    var x: String = ""
    var y: String = ""
    lateinit var locationManager: LocationManager

    lateinit var mIntent: Intent

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        b1 = findViewById(R.id.Add)
        b2 = findViewById<Button>(R.id.Emergency)


        mydb = DatabaseHandler(this)

        var mediaPlayer = MediaPlayer.create(applicationContext, R.raw.alarm)

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            onGPS()
        } else
            startTrack()

        b1.setOnClickListener {
            val i = Intent(this@MainActivity2, Register::class.java)
            startActivity(i)
        }

        b2.setOnLongClickListener {
            mediaPlayer.start()
            Toast.makeText(this@MainActivity2, "Alarm started", Toast.LENGTH_LONG).show()
            return@setOnLongClickListener true
        }

    }

        fun sendSms(number: String, msg: String, b: Boolean) {

            var smsIntent = Intent(Intent.ACTION_SENDTO)
            Uri.parse("smsto:" + number)
            smsIntent.putExtra("smsbody", msg)
            startActivity(smsIntent)


        }

        fun call() {

            var i = Intent(Intent.ACTION_CALL)
            i.setData(Uri.parse("tel:1000"))

            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            )
                startActivity(i)
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)

                    requestPermissions(arrayOf(CALL_PHONE), 1)
            }
        }


        fun startTrack() {

            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity2,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(
                    this@MainActivity2,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity2,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION
                )

            } else {
                val locationGPS: Location? =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                if (locationGPS != null) {
                    val lat: Double = locationGPS.latitude
                    val lon: Double = locationGPS.longitude
                    x = String.format(lat.toString())
                    y = String.format(lon.toString())


                } else
                    Toast.makeText(this@MainActivity2, "Unable to find", Toast.LENGTH_LONG).show()

            }
        }

        fun onGPS() {

            val builder = AlertDialog.Builder(this@MainActivity2)
            builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("yes")
            { dialog, which ->
                startActivity(Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }.setNegativeButton(
                "no"
            ) { dialog, which -> dialog.cancel() }
            val alertDialog = builder.create()
            alertDialog.show()

        }
    }
