package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GUI;
import model.Model;

public class ZoomInButtonHandler implements ActionListener {
	Model m;
	GUI g;
	public ZoomInButtonHandler(Model m,GUI g) {
		this.m=m;
		this.g=g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.g.setB(true);
		this.m.zoomIn();
	}

}
