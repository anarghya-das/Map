package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GUI;

public class WestButtonHandler implements ActionListener {
	Model m;
	GUI g;
	public WestButtonHandler(Model m, GUI gui) {
		g=gui;
		this.m=m;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		g.setB(true);
		this.m.west();
	}

}
