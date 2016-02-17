package ControlPanel;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.work.Task;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MyControlPanel extends JPanel implements CytoPanelComponent {
	
	private static final long serialVersionUID = 8292806967891823933L;


	public MyControlPanel() {
		
		JLabel j1 = new JLabel("LLLLLLLLLL");
		JButton j2 = new JButton("BBBBBBBBBB");
		this.add(j1);
		this.setVisible(true);
		this.add(j2);        
	}


	public Component getComponent() {
		return this;
	}


	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.WEST;
	}


	public String getTitle() {
		return "getTitle";
	}


	public Icon getIcon() {
		return null;
	}

}
