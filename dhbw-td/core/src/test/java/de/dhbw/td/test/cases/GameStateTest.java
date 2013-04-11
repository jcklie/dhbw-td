package de.dhbw.td.test.cases;

import java.awt.Point;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import junit.framework.TestCase;
import playn.core.Json;
import playn.core.Platform;
import playn.java.JavaPlatform;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.waves.WaveController;
import de.dhbw.td.core.waves.WaveFactory;
import de.dhbw.td.test.util.FileUtil;

public class GameStateTest extends TestCase {
	
	private WaveFactory waveLoader;
	private WaveController waveController;
	private Json.Object jason;
	private Queue<Point> waypoints;
	private GameState stateOfTheWorld;
	
	@Override
	protected void setUp() throws Exception {
		URL url = this.getClass().getResource("/waves/waves.json");
		File f = new File(url.getFile());

		FileUtil.readFile(f);

		Platform platform = JavaPlatform.register();
		jason = platform.json().parse(FileUtil.readFile(f));

		waypoints = new LinkedList<Point>();

		waypoints.add(new Point(0, 3));
		waypoints.add(new Point(4, 3));
		waypoints.add(new Point(4, 7));
		waypoints.add(new Point(7, 7));
		waypoints.add(new Point(7, 2));
		waypoints.add(new Point(13, 2));

		waveLoader = new WaveFactory();
		waveController = waveLoader.nextWaveController(jason, waypoints);
		
		stateOfTheWorld = new GameState();
	}

	@Override
	protected void tearDown() throws Exception {
		waveController = null;
	}
	
	public void testNewWave(){
		List<Enemy> enemies = waveController.nextWave().getEnemies();
		stateOfTheWorld.newWave(enemies);
	}
}
