

import java.util.Properties;

import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;

import Term.*;

/**
 * An implementation of CyPluginAdapter
 */
public class ControlPanelApp extends AbstractCySwingApp {
	
	public ControlPanelApp(CySwingAppAdapter swingAdapter){
		super(swingAdapter);
		CySwingApplication cytoscapeDesktopService = swingAdapter.getCySwingApplication();
		
		MyControlPanel myControlPanel = new MyControlPanel(swingAdapter);
		swingAdapter.getCyServiceRegistrar().registerService(myControlPanel,CytoPanelComponent.class, new Properties());
		
		ControlPanelAction controlPanelAction = new ControlPanelAction(cytoscapeDesktopService,myControlPanel);
		swingAdapter.getCySwingApplication().addAction(controlPanelAction);
	}
}
