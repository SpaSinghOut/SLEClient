package com.spartanlaboratories.client.main;

import com.spartanlaboratories.graphics.Window;
import com.spartanlaboratories.measurements.Location;

public class Gui extends Window{
	Gui(MultiplayerHandler connectionHandler, Location size){
		super(connectionHandler, size);
		loadTextures("res/test.png");
	}
	@Override
	public void setDisplayLevel(String string) {
		// TODO Auto-generated method stub
		
	}
}
