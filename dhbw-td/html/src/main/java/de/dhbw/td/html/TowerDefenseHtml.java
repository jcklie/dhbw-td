package de.dhbw.td.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import de.dhbw.td.core.TowerDefense;

public class TowerDefenseHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assets().setPathPrefix("dhbw-td/");
    PlayN.run(new TowerDefense());
  }
}
