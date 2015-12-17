package BundleEdge;

import org.cytoscape.task.NetworkTaskFactory;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.presentation.property.values.HandleFactory;
import org.cytoscape.view.presentation.property.values.BendFactory;
import org.cytoscape.view.presentation.property.values.Handle;
import org.cytoscape.service.util.AbstractCyActivator;
import java.util.Properties;
import org.osgi.framework.BundleContext;
import org.cytoscape.application.CyApplicationManager;

import static org.cytoscape.work.ServiceProperties.*;


public class CyActivator extends AbstractCyActivator {
	public CyActivator() {

	}

	public void start(BundleContext bc) {

		HandleFactory hf = getService(bc, HandleFactory.class);
		BendFactory bf = getService(bc, BendFactory.class);
		VisualMappingManager vmm = getService(bc, VisualMappingManager.class);
		VisualMappingFunctionFactory discreteFactory = getService(bc,VisualMappingFunctionFactory.class,"(mapping.type=discrete)");
		CyApplicationManager cam = getService(bc, CyApplicationManager.class);
		
		EdgeBundlerTaskFactory edgeBundlerTaskFactory = new EdgeBundlerTaskFactory(hf, bf, vmm, discreteFactory, 0, cam);
		Properties edgeBundlerTaskFactoryProps = new Properties();
		edgeBundlerTaskFactoryProps.setProperty(ENABLE_FOR, "networkAndView");
		edgeBundlerTaskFactoryProps.setProperty(PREFERRED_MENU,"Layout.Bundle Edges");
		edgeBundlerTaskFactoryProps.setProperty(MENU_GRAVITY,"11.0");
		edgeBundlerTaskFactoryProps.setProperty(TITLE,"All Nodes and Edges");
		registerService(bc,edgeBundlerTaskFactory,NetworkTaskFactory.class, edgeBundlerTaskFactoryProps);
		
		
		edgeBundlerTaskFactory = new EdgeBundlerTaskFactory(hf, bf, vmm, discreteFactory, 1, cam);
		edgeBundlerTaskFactoryProps = new Properties();
		edgeBundlerTaskFactoryProps.setProperty(ENABLE_FOR, "networkAndView");
		edgeBundlerTaskFactoryProps.setProperty(PREFERRED_MENU,"Layout.Bundle Edges");
		edgeBundlerTaskFactoryProps.setProperty(MENU_GRAVITY,"12.0");
		edgeBundlerTaskFactoryProps.setProperty(TITLE,"Selected Nodes Only");
		registerService(bc,edgeBundlerTaskFactory,NetworkTaskFactory.class, edgeBundlerTaskFactoryProps);
		
		
		edgeBundlerTaskFactory = new EdgeBundlerTaskFactory(hf, bf, vmm, discreteFactory, 2, cam);
		edgeBundlerTaskFactoryProps = new Properties();
		edgeBundlerTaskFactoryProps.setProperty(ENABLE_FOR, "networkAndView");
		edgeBundlerTaskFactoryProps.setProperty(PREFERRED_MENU,"Layout.Bundle Edges");
		edgeBundlerTaskFactoryProps.setProperty(MENU_GRAVITY,"13.0");
		edgeBundlerTaskFactoryProps.setProperty(TITLE,"Selected Edges Only");
		registerService(bc,edgeBundlerTaskFactory,NetworkTaskFactory.class, edgeBundlerTaskFactoryProps);
	}
}