package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import model.City;
import model.ClearButtonHandler;
import model.Constraints;
import model.EastButtonHandler;
import model.MapButtonHandler;
import model.Model;
import model.NorthButtonHandler;
import model.Observer;
import model.SearchButtonHandler;
import model.SouthButtonHandler;
import model.Tile;
import model.WestButtonHandler;
import model.ZoomInButtonHandler;
import model.ZoomOutButtonHandler;

public class GUI implements Observer {

	private Model _model;
	private Driver _windowHolder;
	JPanel map;
	JPanel control;
	JPanel search;
	JLabel z;
	JTextField f;
	JTextField f2;
	JTextField f3;
	JTextField f4;
	JTextField f5;
	boolean b;
	/** 
	 * A constructor for the GUI.  Run the provided DEMO.jar to see the basic
	 * arrangement of the GUI.  Remember to use a JScrollPane for the search
	 * results.  See sample code in lecture code repository from 2017_11_27. 
	 * 
	 * @param m - the Model object
	 * @param mapPanel - the JPanel in which the map is to be shown
	 * @param controlPanel - the JPanel in which the controls are to be shown
	 * @param searchPanel - the JPanel in which search results are to be shown
	 * @param driver - the object that holds the JFrames
	 */
	public GUI(Model m, JPanel mapPanel, JPanel controlPanel, JPanel searchPanel, Driver driver) {
		_windowHolder = driver;
		_model = m;
		_model.addObserver(this);
		map=mapPanel;
		control=controlPanel;
		search=searchPanel;
		control.setLayout(new BoxLayout(control,BoxLayout.Y_AXIS));
		createControl();
		defaultSearch();
		defaultMap();
	}
	private void defaultSearch() {
		JLabel l=new JLabel("No Search Results to Display");
		setLabelProperties(l);
		search.add(l);
	}
	private void defaultMap() {
		JLabel l=new JLabel("No Map to Display");
		setLabelProperties(l);
		map.add(l);
	}
	private void createControl() {
		JPanel c=new JPanel();
		JLabel con=new JLabel("Country");
		setLabelProperties(con);
		c.add(con);
		control.add(c);
		JPanel tf= new JPanel();
		f= new JTextField(12);
		tf.add(f);
		control.add(tf);
		JPanel r=new JPanel();
		JLabel reg=new JLabel("Region");
		setLabelProperties(reg);
		r.add(reg);
		control.add(r);
		JPanel tf2=new JPanel();
		f2=new JTextField(12);
		tf2.add(f2);
		control.add(tf2);
		JPanel ci=new JPanel();
		JLabel cit=new JLabel("City");
		setLabelProperties(cit);
		ci.add(cit);
		control.add(ci);
		JPanel tf5= new JPanel();
		f5= new JTextField(12);
		tf5.add(f5);
		control.add(tf5);
		JPanel m=new JPanel();
		JLabel cin=new JLabel("Minimum Popultion");
		setLabelProperties(cin);
		m.add(cin);
		control.add(m);
		JPanel tf3= new JPanel();
		f3= new JTextField(12);
		tf3.add(f3);
		control.add(tf3);
		JPanel ma=new JPanel();
		JLabel max=new JLabel("Maximum Population");
		setLabelProperties(max);
		ma.add(max);
		control.add(ma);
		JPanel tf4= new JPanel();
		f4= new JTextField(12);
		tf4.add(f4);
		control.add(tf4);
		JPanel b=new JPanel();
		JButton cl=new JButton("Clear");
		setButtonProperties(cl);
		cl.addActionListener(new ClearButtonHandler(this));
		JButton se=new JButton("Search");
		setButtonProperties(se);
		se.addActionListener(new SearchButtonHandler(this,_model));
		z=new JLabel("Zoom: "+_model.currentZoom());
		setLabelProperties(z);
		JButton pl=new JButton("+");
		setButtonProperties(pl);
		pl.addActionListener(new ZoomInButtonHandler(_model,this));
		JButton mi=new JButton("-");
		setButtonProperties(mi);
		mi.addActionListener(new ZoomOutButtonHandler(_model,this));
		JButton no=new JButton("N");
		setButtonProperties(no);
		no.addActionListener(new NorthButtonHandler(_model,this));
		JButton su=new JButton("S");
		setButtonProperties(su);
		su.addActionListener(new SouthButtonHandler(_model,this));
		JButton we=new JButton("E");
		setButtonProperties(we);
		we.addActionListener(new WestButtonHandler(_model,this));
		JButton ea=new JButton("W");
		setButtonProperties(ea);
		ea.addActionListener(new EastButtonHandler(_model,this));
		b.add(cl);
		b.add(se);
		b.add(z);
		b.add(pl);
		b.add(mi);
		b.add(no);
		b.add(su);
		b.add(we);
		b.add(ea);
		control.add(b);
		}
	private void createSearch() {
		search.removeAll();
		if(_model.getSearchResults().size()==0) {
			JLabel l=new JLabel("No Results");
			setLabelProperties(l);
			search.add(l);
		}
		else {
		for(City c: _model.getSearchResults()) {
			JButton j=new JButton(c.toString());
			j.addActionListener(new MapButtonHandler(this,_model,c));
			setButtonProperties(j);
			search.add(j);
		}
		}
	}
	public void createMap() {
		map.removeAll();
//		Tile t = Tile.getTile(_model.getCurrentCity(),_model.currentZoom());
		for(Tile t: _model.tiles()) {
			JLabel l=new JLabel();
			l.setIcon(new ImageIcon(t.getURL()));
			map.add(l);
		}
	}
	public void clearButton() {
		f.setText("");
		f2.setText("");
		f3.setText("");
		f4.setText("");
		f5.setText("");
	}
	public void fillEmpty() {
		clearButton();
		if(f.getText().equals("")) {
			f.setText(_model.currentCountry());
		}
		if(f2.getText().equals("")) {
			f2.setText(_model.currentRegion());
		}
		if(f5.getText().equals("")) {
			f5.setText(_model.currentCity());
		}
		if(f3.getText().equals("")) {
			f3.setText(Integer.toString(_model.currentPopulation()));
		}
	    if(f4.getText().equals("")) {
	    	f4.setText(Integer.toString(_model.currentPopulation()));
		}
	}
	public void setB(boolean b) {
		this.b=b;
	}
	public Constraints createCons() {
		String c=f.getText();
		String c2=f2.getText();
		String c3=f5.getText();
		String c4=f3.getText();
		String c5=f4.getText();
		Constraints con = new Constraints(c,c2,c3,c4,c5);
//		System.out.println(con);
		return con;
	}
	
	private void updateZoomValue() {
		z.setText("Zoom: "+_model.currentZoom());
	}
	/**
	 * All GUI update code must be in this method.  Called by the model when 
	 * any GUI update needs to occur.
	 * 
	 * Ensure that call to updateJFrameIfNotHeadless() is last statement in
	 * this method.
	 */
	@Override
	public void update() {
		updateZoomValue();
		createSearch();
//		System.out.println(b);
		if(b) {
		createMap();
		}
		updateJFrameIfNotHeadless();
	}

	/**
	 * Do not modify this method.
	 */
	public void updateJFrameIfNotHeadless() {
		if (_windowHolder != null) {
			_windowHolder.updateJFrame();
		}
	}
	private void setButtonProperties(JButton button) {
		button.setFont(new Font("Courier", Font.BOLD, 15));
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}

	private void setLabelProperties(JLabel label) {
		label.setFont(new Font("Courier", Font.BOLD, 15));
		label.setBackground(Color.WHITE);
		label.setForeground(Color.BLACK);
		label.setOpaque(true);
		label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}
}
