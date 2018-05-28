package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GUI;

public class SouthButtonHandler implements ActionListener {
	Model m;
	GUI g;
	public SouthButtonHandler(Model m, GUI gui) {
		this.m=m;
		g=gui;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		g.setB(true);
		this.m.south();
	}

}
