

import java.awt.Color;
import java.lang.Math;

import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.event.ActionEvent;
import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;


public class MenuAction extends AbstractCyAction {
    private final CyAppAdapter adapter;

    public MenuAction(CyAppAdapter adapter) {
        super("々々々々々々々々々々",
                adapter.getCyApplicationManager(),
                "network",
                adapter.getCyNetworkViewManager());
            this.adapter = adapter;
            setPreferredMenu("Apps");
    }
     public void actionPerformed(ActionEvent e) {
    	
    	 
        final CyApplicationManager manager = adapter.getCyApplicationManager();
        final CyNetworkView networkView = manager.getCurrentNetworkView();
        final CyNetwork network = manager.getCurrentNetwork();
      
        //CyNode centroid = network.addNode();
                
        //network.getRow(centroid).set(CyNetwork.NAME, "Centroid");
        //for (CyNode node : CyTableUtil.getNodesInState(network,"selected",true)/* network.getNodeList()*/) {
        //	network.addEdge(centroid, node, true);
       // }

        //networkView.updateView();
        //View<CyNode> nodeView = networkView.getNodeView(centroid);
        View<CyNode> nodeView;
        double x = 0;
        double y = 0;
        double count1 = 0;
        double count2 = 0; 
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	x += nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
        	y += nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
        	count1 += 1;
        }
        double a = 0;
        double b = 0;
        
        if(count1 > 0){
        	a = x/count1;
        	b = y/count1;
        }
        
        double temp = 0;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_X_LOCATION, count1*Math.cos((temp*360/count1)*Math.PI/180)+a);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_Y_LOCATION, count1*Math.sin((temp*360/count1)*Math.PI/180)+b);

        	temp+=1;        	
        }
        

        x=0;
        y=0;        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
       	//for (CyNode node : CyTableUtil.getNodesInState(network,"selected",true)){
        	nodeView = networkView.getNodeView(node);
        	x += nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
        	y += nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);

        	count2 += 1;
        	//nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.BLUE);

        	nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float) (count2*Math.PI/180), 1, 1));
        }

        //View<CyNode> centroidView = networkView.getNodeView(centroid);
        
        temp = 0;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_X_LOCATION, 5*(count1+count2)*Math.cos((temp*360/count2)*Math.PI/180)+a);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_Y_LOCATION, 5*(count1+count2)*Math.sin((temp*360/count2)*Math.PI/180)+b);

        	temp+=1;
        }
        


        
        
        //centroidView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, x);
        //centroidView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, y);
        //centroidView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.RED);
        
        //setDefaultLayout("Circular");
        /*final CyLayoutAlgorithmManager alMan = adapter.getCyLayoutAlgorithmManager();
        CyLayoutAlgorithm algor = null;
        algor = alMan.getLayout("Circular");
        TaskIterator itr = algor.createTaskIterator(networkView,algor.createLayoutContext(),CyLayoutAlgorithm.ALL_NODE_VIEWS,null);
        adapter.getTaskManager().execute(itr);
        SynchronousTaskManager<?> synTaskMan = adapter.getCyServiceRegistrar().getService(SynchronousTaskManager.class);           
        synTaskMan.execute(itr);
        adapter.getVisualMappingManager().getVisualStyle(networkView).apply(networkView);*/
                       
        networkView.updateView();
    }
}