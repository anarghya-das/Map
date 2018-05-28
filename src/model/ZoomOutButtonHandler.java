package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GUI;
import model.Model;

public class ZoomOutButtonHandler implements ActionListener {
	Model m;
	GUI g;
	public ZoomOutButtonHandler(Model m,GUI g) {
		this.m=m;
		this.g=g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.g.setB(true);
		this.m.zoomOut();
	}

}
