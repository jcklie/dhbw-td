package de.dhbw.td.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import de.dhbw.td.core.TowerDefense;

public class TowerDefenseJava {

	public static void main(String[] args) {
		JavaPlatform platform = JavaPlatform.register();
		platform.assets().setPathPrefix("de/dhbw/td/resources");
		PlayN.run(new TowerDefense());
	}
}
