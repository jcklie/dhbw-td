/*  Copyright (C) 2013 by Jan-Christoph Klie Inc. All rights reserved.
 *  Released under the terms of the GNU General Public License version 3 or later.
 *  
 *  Contributors:
 *  Jan-Christoph Klie, Martin Kiessling - All together
 */

package de.dhbw.td.backup;

import playn.core.util.Callback;

public class Time{

	public boolean running = false;

	private double currentTime;
	private double waitTime;
	private double endTime;
	private Callback<String> timerCallback;

	public Time(double waitTime, Callback<String> callback) {
		this.waitTime = waitTime;
		this.timerCallback = callback;
	}

	public void start() {
		currentTime = 0;
		endTime = waitTime;
		running = true;
	}

	public void update(double delta) {

		if (running) {
			currentTime += delta;
			if (currentTime >= endTime) {
				running = false;
				timerCallback.onSuccess("Time has elapsed");
			}
		}
	}

}