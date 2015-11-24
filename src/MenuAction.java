import java.awt.Color;
import java.awt.Component;
import java.awt.Shape;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.directory.BasicAttribute;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.awt.event.ActionEvent;
import java.awt.image.SinglePixelPackedSampleModel;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
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
        super("★★★★★★★★★★",
                adapter.getCyApplicationManager(),
                "network",
                adapter.getCyNetworkViewManager());
            this.adapter = adapter;
            setPreferredMenu("Apps");
            
       	 MyControlPanel myPanel = new MyControlPanel();
       	 adapter.getCyServiceRegistrar().registerService(myPanel,CytoPanelComponent.class,new Properties());       	 
    }
     public void actionPerformed(ActionEvent e) {
    	 
        final CyApplicationManager manager = adapter.getCyApplicationManager();
        final CyNetworkView networkView = manager.getCurrentNetworkView();
        final CyNetwork network = manager.getCurrentNetwork();

        String line;
        ArrayList<String> TermList = new ArrayList<String>();
        ArrayList<String> ClassList = new ArrayList<String>();
        
        //-----Term-----
        
        FileReader fr;
        BufferedReader br;
		try {
			fr = new FileReader("term.txt");
			br = new BufferedReader(fr);		

			try {
				while ((line = br.readLine())!=null) {        	
					TermList.add(line);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	


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
        double r = 15;
        double m = 0;
        double temp = 0;
        int layer = 0;
        
        
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
        	
        	String name = nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL);
        	boolean isIn = ClassList.contains(name.charAt(0));
        	if(isIn==false)
        	{
        		ClassList.add(String.valueOf(name.charAt(0)));
        	}
        	
        }
        count3 = count1 + count2;        
                
        double a = x/count3;
        double b = y/count3;

        
        
        
        
        layer = (int)Math.sqrt((double)count1);
        layer /= 2;
        temp = 0;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	//neighborList = network.getNeighborList(node, CyEdge.Type.ANY);
        	
        	m = (temp*360/count1)*Math.PI/180;
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (r*(count1-1))*Math.cos((temp*360/count1)*Math.PI/180)+a);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (r*(count1-1))*Math.sin((temp*360/count1)*Math.PI/180)+b);        	
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor(1, (float) 0.75, (float) 0.75));
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.OCTAGON);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SIZE, 70.0);
        	
        	temp+=1;        	
        }
        
        //CyEdge edge = CyTableUtil.getEdgesInState(arg0, arg1, arg2);
        
        
        //network.getConnectingEdgeList(arg0, arg1, arg2)        		

        
        
        
        
        layer = (int)Math.sqrt((double)count2);
        layer /= 2;
        temp = 0;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	
        	m = (temp*360/count2)*Math.PI/180;
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (140+r*(count1-1)+r*(layer-(temp%layer))*layer)*Math.cos(m)+a);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (140+r*(count1-1)+r*(layer-(temp%layer))*layer)*Math.sin(m)+b);
        	
        	String name = nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL);
        	
        	float c = ClassList.indexOf(name.charAt(0))/ClassList.size();
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor(c, 1, 1));
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SIZE, 50.0);
        	
        	temp+=1;
        }

        
                       
        networkView.updateView();

    }
}