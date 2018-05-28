package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GUI;

public class ClearButtonHandler implements ActionListener {
	GUI g;
	public ClearButtonHandler(GUI g) {
		this.g=g;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		this.g.clearButton();
	}

}
