package com.spartanlaboratories.client.main;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.spartanlaboratories.graphics.Quad;
import com.spartanlaboratories.measurements.Location;
import com.spartanlaboratories.util.Tracker;

public class ClientMain {
	private static final int quadInfoLines = 18;
	protected static Gui gui;
	Quad quad = initializeQuad();
	int quadInfoTracer = 0;
	int xDisplay, yDisplay;
	long tickRate = 60;
	Tracker tracker = new Tracker();
	boolean running;
	public static void main(String[] args){
		new ClientMain().start();
	}
	protected ClientMain start() {
		try (
				// The socket itself
			    Socket socket = new Socket("localhost", 7000);
				// The writer that is going to write to the socket
			    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				// The reader that is going to read from the socket
			    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			){
				sendScreenInfo(out);
				gui = new Gui(new MultiplayerHandler(out,in), new Location(xDisplay, yDisplay));
				gui.setDisplayLevel("all");
				trackerSetup();
				clientSetup();
				run(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return this;
	}
	protected void clientSetup() {
		
	}
	private void trackerSetup(){
		tracker.addEntity("tick");
		tracker.addEntity("render");
		tracker.addEntity("update");
		tracker.addEntity("polling");
		tracker.setNotifyPeriod(15);
		tracker.runInBackGround();
	}
	private void run(BufferedReader in) {
		running = true;
		while(running){
			if(readInfo(in)){
				tracker.giveStartTime("tick");
				gui.tick();
				tracker.giveEndTime("tick");
				tracker.giveStartTime("render");
				gui.render();
				tracker.giveEndTime("render");
				tracker.giveStartTime("update");
				gui.update();
				tracker.giveEndTime("update");
			}
		}
	}
	private void sendScreenInfo(PrintWriter out){
		out.println("screen info");
		xDisplay = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
		yDisplay = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
		out.println(new Location(xDisplay, yDisplay));
	}
	private boolean readInfo(BufferedReader in){
		tracker.giveStartTime("polling");
		try{
			while(in.ready())switch(in.readLine().toLowerCase()){
			case "quad":
				quad = initializeQuad();
				for(int i = 0; i < quadInfoLines; i++){
					processQuadInfo(in.readLine());
				}
				sendQuadInfo(quad);
				break;
			case "end":
				tracker.giveEndTime("polling");
				return true;
			case "load texture": case "loadtexture":
				String textureName = in.readLine();
				System.out.println("Loading texture: " + textureName);
				gui.loadTexture(textureName);
				System.out.println();
				break;
			}
		}
		catch(IOException e){
			
		}
		tracker.giveEndTime("polling");
		return false;
	}
	private void processQuadInfo(String quadInfo){
		switch(++quadInfoTracer){
		case 1:
			quad.quadValues[0].x = Double.parseDouble(quadInfo);
			break;
		case 2:
			quad.quadValues[0].y = Double.parseDouble(quadInfo);
			break;
		case 3:
			quad.quadValues[1].x = Double.parseDouble(quadInfo);
			break;
		case 4:
			quad.quadValues[1].y = Double.parseDouble(quadInfo);
			break;
		case 5:
			quad.quadValues[2].x = Double.parseDouble(quadInfo);
			break;
		case 6:
			quad.quadValues[2].y = Double.parseDouble(quadInfo);
			break;
		case 7:
			quad.quadValues[3].x = Double.parseDouble(quadInfo);
			break;
		case 8:
			quad.quadValues[3].y = Double.parseDouble(quadInfo);
			break;
		case 9:
			quad.textureValues[0].x = Double.parseDouble(quadInfo);
			break;
		case 10:
			quad.textureValues[0].y = Double.parseDouble(quadInfo);
			break;
		case 11:
			quad.textureValues[1].x = Double.parseDouble(quadInfo);
			break;
		case 12:
			quad.textureValues[1].y = Double.parseDouble(quadInfo);
			break;
		case 13:
			quad.textureValues[2].x = Double.parseDouble(quadInfo);
			break;
		case 14:
			quad.textureValues[2].y = Double.parseDouble(quadInfo);
			break;
		case 15:
			quad.textureValues[3].x = Double.parseDouble(quadInfo);
			break;
		case 16:
			quad.textureValues[3].y = Double.parseDouble(quadInfo);
			break;
		case 17:
			quad.color = quadInfo;
			break;
		case 18:
			quadInfoTracer = 0;
			quad.texture = quadInfo;
			break;
			default:
				System.out.println("incorrect quad info slot");
		}
	}
	private void sendQuadInfo(Quad quad){
		gui.addQuad(quad);
	}
	private Quad initializeQuad(){
		Quad quad = new Quad(new Location[4], new Location[4]);
		for(int i = 0; i < 4; i++){
			quad.quadValues[i] = new Location();
			quad.textureValues[i] = new Location();
		}
		return quad;
	}
	
}
