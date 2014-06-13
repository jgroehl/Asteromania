package de.jgroehl.asteromania.sensoryInfo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorHandler implements android.hardware.SensorEventListener {

	private static final String TAG = SensorHandler.class.getSimpleName();

	private SensorManager sensorManager;

	private float headingAngle;
	private float rollAngle;
	private float pitchAngle;

	public SensorHandler(Context context, String sensorService) {
		sensorManager = (SensorManager) context.getSystemService(sensorService);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);
	}

	public float getHeadingAngle() {
		return headingAngle;
	}

	public float getRollAngle() {
		return rollAngle;
	}

	public float getPitchAngle() {
		return pitchAngle;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		Log.w(TAG, "Sensor accuracy changed, but no action was done");
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		headingAngle = sensorEvent.values[0];
		pitchAngle = sensorEvent.values[1];
		rollAngle = sensorEvent.values[2];
	}

}
