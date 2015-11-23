

import java.awt.Color;
import java.awt.Component;
import java.lang.Math;
import java.util.Properties;

import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.event.ActionEvent;
import java.awt.image.SinglePixelPackedSampleModel;

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


import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.service.util.AbstractCyActivator;

import org.osgi.framework.BundleContext;

public class MenuAction extends AbstractCyAction {
    private final CyAppAdapter adapter;

    public MenuAction(CyAppAdapter adapter) {
        super("��������������������",
                adapter.getCyApplicationManager(),
                "network",
                adapter.getCyNetworkViewManager());
            this.adapter = adapter;
            setPreferredMenu("Apps");
            
       	 MyControlPanel myPanel = new MyControlPanel();
       	 adapter.getCyServiceRegistrar().registerService(new MyControlPanel(),CytoPanelComponent.class,new Properties());
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
        double count3 = 0;
        double r = 5;
        double[] xTable = { .5, -.5, -.2, -.5 };
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	x += nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
        	y += nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
        	count1 += 1;
        }
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	x += nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
        	y += nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);

        	count2 += 1;  	
        }
        count3 = count1 + count2;        
                
        double a = x/count3;
        double b = y/count3;

        double temp = 0;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, r*count1*Math.cos((temp*360/count1)*Math.PI/180)+a);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, r*count1*Math.sin((temp*360/count1)*Math.PI/180)+b);

        	temp+=1;        	
        }

        
        
        
        
        int layer = (int)Math.sqrt((double)count2);
        temp = 0;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	
        	double m = (temp*360/count2)*Math.PI/180;
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, r*count2*Math.cos(m)+a+r);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, r*count2*Math.sin(m)+b+r);
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float) (temp*Math.PI/360), 1, 1));

        	temp+=1;
        }
        
        


        //View<CyNode> centroidView = networkView.getNodeView(centroid);
        
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