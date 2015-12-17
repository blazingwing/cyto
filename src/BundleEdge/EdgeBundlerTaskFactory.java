package BundleEdge;

import org.cytoscape.view.presentation.property.values.BendFactory;
import org.cytoscape.view.presentation.property.values.HandleFactory;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.task.AbstractNetworkTaskFactory;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.application.CyApplicationManager;


public class EdgeBundlerTaskFactory extends AbstractNetworkTaskFactory {

	private HandleFactory hf;
	private BendFactory bf;
	private VisualMappingManager vmm;
	private VisualMappingFunctionFactory discreteFactory;
	private int selection;
	private CyApplicationManager cyApplicationManagerServiceRef;
	
	public EdgeBundlerTaskFactory(HandleFactory hf, BendFactory bf, VisualMappingManager vmm, VisualMappingFunctionFactory discreteFactory, int selection, CyApplicationManager cam) {
		super();
		this.hf = hf;
		this.bf = bf;
		this.vmm = vmm;
		this.discreteFactory = discreteFactory;
		this.selection = selection;
		this.cyApplicationManagerServiceRef = cam;
	}
	
	public TaskIterator createTaskIterator(CyNetwork network) {
		CyNetworkView view = cyApplicationManagerServiceRef.getCurrentNetworkView();
		return new TaskIterator(new EdgeBundlerTask(view, hf, bf, vmm, discreteFactory, selection));
	}
}