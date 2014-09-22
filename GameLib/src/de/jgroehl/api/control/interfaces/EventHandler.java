package de.jgroehl.api.control.interfaces;

import android.content.Context;
import android.view.MotionEvent;

public interface EventHandler {

	void handleEvent(MotionEvent event, Context context, int screenWidth,
			int screenHeight);

}
