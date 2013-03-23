package de.dhbw.td.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import de.dhbw.td.core.TowerDefense;

public class TowerDefenseActivity extends GameActivity {

  @Override
  public void main(){
    platform().assets().setPathPrefix("de/dhbw/td/resources");
    PlayN.run(new TowerDefense());
  }
}
