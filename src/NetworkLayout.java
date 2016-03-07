import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;

import static org.cytoscape.work.ServiceProperties.ENABLE_FOR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.JPanel;

import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.task.NetworkTaskFactory;
import org.cytoscape.util.swing.internal.CyActivator;
import org.cytoscape.view.presentation.property.values.BendFactory;
import org.cytoscape.view.presentation.property.values.HandleFactory;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.osgi.framework.BundleContext;

import javax.swing.JLabel;

import static org.cytoscape.work.ServiceProperties.*;

import BundleEdge.*;
import Term.*;

public class NetworkLayout extends AbstractCySwingApp 
{
	public NetworkLayout(CySwingAppAdapter adapter)
	{
		super(adapter);
		adapter.getCySwingApplication().addAction(new MenuAction(adapter));	
		
		CySwingApplication cytoscapeDesktopService = swingAdapter.getCySwingApplication();
		
      	MyControlPanel myControlPanel = new MyControlPanel(adapter);
      	adapter.getCyServiceRegistrar().registerService(myControlPanel,CytoPanelComponent.class,new Properties());      	 
      	myControlPanel.setPreferredSize(new Dimension(315, 500));
      	myControlPanel.setFocusable(true);
      	myControlPanel.setMaximumSize(new Dimension(315,500));
 		//ControlPanelAction controlPanelAction = new ControlPanelAction(cytoscapeDesktopService,myControlPanel);
 		//swingAdapter.getCySwingApplication().addAction(controlPanelAction);
	}

}


