package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.GUI;

public class SearchButtonHandler implements ActionListener {
	GUI g;
	Model m;
	public SearchButtonHandler(GUI d,Model m) {
		g=d;
		this.m=m;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		g.setB(false);
		this.m.performSearch(g.createCons());
	}
}
