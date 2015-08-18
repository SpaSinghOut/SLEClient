package com.spartanlaboratories.client.main;

import java.io.BufferedReader;
import java.io.PrintWriter;

import com.spartanlaboratories.graphics.ConnectionHandler;
import com.spartanlaboratories.graphics.Input;
import com.spartanlaboratories.measurements.Location;

public class MultiplayerHandler implements ConnectionHandler{
	private PrintWriter out;
	private BufferedReader in;
	public MultiplayerHandler(PrintWriter out, BufferedReader in){
		this.out = out;
		this.in = in;
	}
	enum InputType{
		PRESS, RELEASE, FULL,;
	}
	private void sendValues(Object... data){
		String[] strings = new String[data.length];
		for(int i = 0; i < data.length;i++)
			strings[i] = data[i].toString();
		sendValues(strings);
	}
	private synchronized void sendValues(String... data){
		out.println("input");
		for(String s: data)out.println(s);
	}
	@Override
	public void notifyOfMouseWheel(Input input) {
		sendValues("wheel", input.button);
	}
	@Override
	public void notifyOfMouseLocation(Input input) {
		sendValues("mouse location", input.location);
	}
	@Override
	public void notifyOfKeyPress(Input input) {
		sendValues("key", input.button);
	}
	@Override
	public void notifyOfMouseClick(Input input) {
		sendValues("click", input.button, input.type);
	}
}
