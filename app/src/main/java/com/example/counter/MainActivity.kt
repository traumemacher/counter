package com.example.counter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var tvSteps: TextView
    private lateinit var tvCalories: TextView
    private lateinit var sensorManager: SensorManager
    private var totalSteps = 0f
    private var initialSteps = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvSteps = findViewById(R.id.tvSteps)
        tvCalories = findViewById(R.id.tvCalories)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1)
        if (!ServiceUtils.isServiceRunning(this, StepCounterService::class.java)) {
            val startServiceIntent = Intent(this, StepCounterService::class.java)
            startForegroundService(startServiceIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        val stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (initialSteps < 1) {
            initialSteps = event.values[0]
        }
        totalSteps = event.values[0] - initialSteps
        tvSteps.text = "Шаги: ${totalSteps.toInt()}"
        calculateCalories()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun calculateCalories() {
        // Предположим, что сжигается 0.04 ккал на шаг
        val calories = totalSteps * 0.04
        tvCalories.text = "Калории: ${String.format("%.2f", calories)}"
    }
}
