package de.dhbw.td.test.cases;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import junit.framework.TestCase;
import playn.core.Json;
import playn.core.Platform;
import playn.java.JavaPlatform;
import pythagoras.i.Point;
import de.dhbw.td.core.enemies.Enemy;
import de.dhbw.td.core.game.GameState;
import de.dhbw.td.core.waves.WaveController;
import de.dhbw.td.core.waves.WaveControllerFactory;
import de.dhbw.td.test.util.FileUtil;

public class GameStateTest extends TestCase {
	
	private WaveControllerFactory waveLoader;
	private WaveController waveController;
	private Json.Object jason;
	private Point[] waypoints;
	private GameState stateOfTheWorld;
	
	@Override
	protected void setUp() throws Exception {
		URL url = this.getClass().getResource("/waves/wave1.json");
		File f = new File(url.getFile());

		FileUtil.readFile(f);

		Platform platform = JavaPlatform.register();
		jason = platform.json().parse(FileUtil.readFile(f));

		waypoints = createWaypoints();


		waveLoader = new WaveControllerFactory();
		waveController = waveLoader.constructWaveController(jason, waypoints);
		
		stateOfTheWorld = new GameState();
	}
	
	private Point[] createWaypoints() {
		Point[] wp = {new Point(0, 3), new Point(4, 3), new Point(4, 7), new Point(7, 7), new Point(7, 2), new Point(13, 2) };
		return wp;
	}

	@Override
	protected void tearDown() throws Exception {
		waveController = null;
	}
	
	public void testNewWave(){
		List<Enemy> enemies = waveController.nextWave().enemies();
		//stateOfTheWorld.newWave(enemies);
	}
}
