package de.dhbw.td.core;

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;
import playn.core.Json;
import playn.core.Platform;
import playn.java.JavaPlatform;
import de.dhbw.td.core.level.ILevelFactory;
import de.dhbw.td.core.level.Level;
import de.dhbw.td.core.level.SimpleLevelFactory;

public class SimpleLevelFactoryTest extends TestCase {

	private Level lvl;	
	private ILevelFactory factory;
	
	@Override
	protected void setUp() throws Exception {		
		URL url = this.getClass().getResource("/levels/level1.json");
		File f = new File(url.getFile());			

		FileUtil.readFile(f);
		
		Platform platform = JavaPlatform.register();
		Json.Object jason = platform.json().parse(FileUtil.readFile(f));
		
		factory = new SimpleLevelFactory();
		lvl = factory.loadLevel(jason);
	}
	
	@Override
	protected void tearDown() throws Exception {
		lvl = null;
		factory = null;
	}

	
	public void testLevelWasLoadedAsSpecifiedInJson() {
		assertEquals(14, lvl.width);
		assertEquals(10, lvl.height);
		assertEquals(64, lvl.tilesize);
	}  

}
