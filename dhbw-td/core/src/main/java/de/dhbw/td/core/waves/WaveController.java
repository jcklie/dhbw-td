package de.dhbw.td.core.waves;

import java.util.Queue;

public class WaveController {

	public final Queue<Wave> waves;
	public static final int NUMBEROFWAVES = 36;
	public final double timeSinceLastWave = 0;
	public final Wave currentWave = null;

	public WaveController(Queue<Wave> waves) {
		this.waves = waves;
	}

	public Wave nextWave() {
		return waves.poll();
	}

	public void draw() {

	}
}
