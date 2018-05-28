package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import gui.GUI;

public class MapButtonHandler implements ActionListener {
	GUI g;
	Model _m;
    City c;
	public MapButtonHandler(GUI g,Model m,City s){
		this.g=g;
		_m=m;
		c=s;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		this.g.setB(true);
		_m.setCity(this.c);
		g.fillEmpty();
	}

}
