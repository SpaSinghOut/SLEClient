package com.spartanlaboratories.client.main;

import java.util.ArrayList;

import com.spartanlaboratories.graphics.HUDElement;
import com.spartanlaboratories.graphics.Window;
import com.spartanlaboratories.measurements.Location;

@SuppressWarnings("serial")
public class Gui extends Window{
	
	protected Gui(MultiplayerHandler connectionHandler, Location size){
		super(connectionHandler, size);
		//loadTextures("res/test.png");
	}
	
	@Override
	public void setDisplayLevel(String string) {
		
	}
	public void loadTexture(String textureName){
		loadTextures(textureName);
	}
	public ArrayList<HUDElement> elements(){
		return getUI();
	}
}
