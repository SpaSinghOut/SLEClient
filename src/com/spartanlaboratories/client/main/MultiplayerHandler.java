package com.spartanlaboratories.client.main;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.spartanlaboratories.graphics.ConnectionHandler;
import com.spartanlaboratories.graphics.Input;

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
		sendValues(getContents(data));
	}
	private String[] getContents(Object[] array) {
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < array.length; i++)
			if(!Object[].class.isAssignableFrom(array[i].getClass()))
				list.add(array[i].toString());
			else
				for(String s: getContents(((Object[])array[i])))
					list.add(s);
		return list.toArray(new String[list.size()]);
	}
	private synchronized void sendValues(String... data){
		out.println("input");
		for(String s: data)out.println(s);
	}
	private void sendPhysicalInput(Object... data) {
		sendValues("physical", data);
	}
	private void sendVirtualInput(Object... data) {
		sendValues("virtual", data);
	}
	@Override
	public void notifyOfMouseWheel(Input input) {
		sendPhysicalInput("wheel", input.button);
	}
	@Override
	public void notifyOfMouseLocation(Input input) {
		sendPhysicalInput("mouse location", input.location);
	}
	@Override
	public void notifyOfKeyPress(Input input) {
		sendPhysicalInput("key", input.button);
	}
	@Override
	public void notifyOfMouseClick(Input input) {
		sendPhysicalInput("click", input.button, input.type);
	}
	@Override
	public void notifyOfButtonClick(Input input, String... buttonData) {
		sendVirtualInput("button", input.button, "none", buttonData.length, buttonData);
	}
	@Override
	public String getStat(String stat) {
		return null;
	}
}
