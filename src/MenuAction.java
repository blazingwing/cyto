
import java.awt.Color;
import java.awt.Component;
import java.awt.List;
import java.awt.Shape;
import java.lang.Math;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.crypto.spec.IvParameterSpec;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.InvalidSearchFilterException;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
import javax.swing.text.AttributeSet.ColorAttribute;
import javax.xml.soap.Node;

import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.awt.image.SinglePixelPackedSampleModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.EdgeBendVisualProperty;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.presentation.property.values.Bend;
import org.cytoscape.view.presentation.property.values.BendFactory;
import org.cytoscape.view.presentation.property.values.Handle;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkListener;

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
     @SuppressWarnings({ "unused", "null" })
	public void actionPerformed(ActionEvent e) {
    	 
        final CyApplicationManager manager = adapter.getCyApplicationManager();
        final CyNetworkView networkView = manager.getCurrentNetworkView();
        final CyNetwork network = manager.getCurrentNetwork();

        String line = null;
        ArrayList<Term> TermList = new ArrayList<Term>();
        
        ArrayList<String> ClassList = new ArrayList<String>();
        ArrayList<String> Node_class = new ArrayList<String>();
        
        ArrayList<Integer> SiteList= new ArrayList<Integer>();
        ArrayList<Integer> SiteList_count = new ArrayList<Integer>();
        ArrayList<Integer> NanSiteList= new ArrayList<Integer>();
        int NanSiteList_count = 0;
        ArrayList<Double> Node_x = new ArrayList<Double>();
        ArrayList<Double> Node_y = new ArrayList<Double>();

        View<CyNode> nodeView = null;
        View<CyEdge> edgeView = null;
        
        //-----Term-----
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        }

        
        InputStream is = MenuAction.class.getResourceAsStream("term.txt");
        InputStreamReader isr = new InputStreamReader(is); 
        BufferedReader br = new BufferedReader(isr);	
        
        //nodeView.setVisualProperty(BasicVisualLexicon.NODE_LABEL, url.toString());
		//nodeView.setVisualProperty(BasicVisualLexicon.NODE_LABEL_COLOR, Color.black);
        Term t_term = new Term();
        String [] str=null;
		try {
			
			while ((line = br.readLine())!=null) {
				t_term = new Term();
				t_term.Term_name = line;
				line = br.readLine();
				str = line.split(",");
				for(int i=0;i<str.length;i++)
					t_term.Term_node.add(str[i]);
				TermList.add(t_term);
			}
		} catch (IOException e1) {
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

        double x = 0;
        double y = 0;
        double count1 = 0;
        double count2 = 0; 
        double count2_withoutNan = 0;
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
        	
       	
        	String name = nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL);
        	//String key = String.valueOf(name.charAt(0));
        	String key = name;
        	boolean isIn = false;
        	for(int i=0;i<TermList.size();i++)
        	{
        		if(TermList.get(i).Term_node.contains(key))
        		{
        			isIn = true;
        			key = TermList.get(i).Term_name;
        			break;
        		}
        	}
        	if(isIn==false)
        	{
        		key="NaN";
        		if(NanSiteList_count>0)
        		{
        			NanSiteList_count+=1;
        		}
        		else
        		{
        			NanSiteList.add(0);
        			NanSiteList_count+=1;
        		} 
        	}
        	else
        	{
        		isIn = ClassList.contains(key);
        		if(isIn==false)
        		{
        			ClassList.add(key);
        			SiteList.add(0); 
        			SiteList_count.add(1); 
        		}
        		else
        		{
        			SiteList_count.set(ClassList.indexOf(key), SiteList_count.get(ClassList.indexOf(key))+1);
        		}
        		count2_withoutNan += 1;
        	}
        	Node_class.add(key);
        	count2 += 1;
        }
        
        
        count3 = count1 + count2;        
                
        double a = x/count3;
        double b = y/count3;
        
        for(int i=1;i<SiteList.size();i++)
        	SiteList.set(i, SiteList.get(i-1)+SiteList_count.get(i-1));
        if(NanSiteList_count>0)
        	NanSiteList.set(0, SiteList.get(SiteList.size()-1)+SiteList_count.get(SiteList_count.size()-1));
        

        Bend bb = null;
        
        
        
        layer = (int)Math.sqrt((double)count1);
        layer /= 2;
        temp = 0;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	//neighborList = network.getNeighborList(node, CyEdge.Type.ANY);
        	
        	m = (temp*360/count1)*Math.PI/180;
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (r*(count1-1))*Math.cos((temp*360/count1)*Math.PI/180)+a);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (r*(count1-1))*Math.sin((temp*360/count1)*Math.PI/180)+b);        	
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float )0.5, (float) 0, (float) 0.5));
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.OCTAGON);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SIZE, 70.0);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.BLACK);
        	
        	temp+=1;   
        	
        }
        
        //CyEdge edge = CyTableUtil.getEdgesInState(arg0, arg1, arg2);
        temp=0;
        /*for (CyEdge edge : CyTableUtil.getEdgesInState(network, "selected", false)){
        	edgeView = networkView.getEdgeView(edge);
        	

        	bb = edgeView.getVisualProperty(BasicVisualLexicon.EDGE_BEND);
        	java.util.List<Handle> handles = bb.getAllHandles();
        	//for (Handle handle : handles)
        		//handle.defineHandle( networkView, (View<CyEdge>)  edgeView, -13.0, 37.0);
        	double temp2=0;
        	for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
            	nodeView = networkView.getNodeView(node);
            	if(temp==temp2)
            	{
            		nodeView.setVisualProperty(BasicVisualLexicon.NODE_LABEL, String.valueOf(handles.get(0)));
                	nodeView.setVisualProperty(BasicVisualLexicon.NODE_LABEL_COLOR, Color.black);
                	break;
            	}
            	temp2+=1;
        	}
        	temp+=1; 
        }*/
        
        //network.getConnectingEdgeList(arg0, arg1, arg2)        		

        
        
        
        
        layer = (int)Math.sqrt((double)count2);
        layer /= 2;
        temp = 0;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	
        	m = (temp*360/count2)*Math.PI/180;
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (140+r*(count1-1)+r*(layer-(temp%layer))*layer)*Math.cos(m)+a);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (140+r*(count1-1)+r*(layer-(temp%layer))*layer)*Math.sin(m)+b);
        	
        	/* Site add */
        	Node_x.add(nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION));
        	Node_y.add(nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION));
        	
        	int c = 0;
        	String key = Node_class.get((int)temp);        	
        	
        	float c2 = 1;
        	float c3 = 1;
        	if(key.equals("NaN"))
        	{
        		c2 = (float) 0;
        		c3 = (float) 0.8;	
        	}	
        	else
        	{
        		c = ClassList.indexOf(key);
        	}
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float) ((float)c/ClassList.size()), c2, c3));
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SIZE, 50.0);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.BLACK);
        	temp+=1;
        	
        	
        	
        	/*String EDGE_BEND_DEFINITION = "3,0.003,0.3,500";
        	BendFactory bendFactory = null;        	
        	Bend defBend = bendFactory.parseSerializableString(EDGE_BEND_DEFINITION);*/
        	

        	
        }
        
        /* Change Site */
        temp = 0;
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	
        	int c = 0;
        	String key = Node_class.get((int)temp);
        	
        	
        	if(key.equals("NaN"))
        	{	
        		
        		nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, Node_x.get(NanSiteList.get(0)));
        		nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, Node_y.get(NanSiteList.get(0)));
        		
        		NanSiteList.set(0, NanSiteList.get(0)+1);

        	}
        	else
        	{
        		
        		c = (int) ClassList.indexOf(key);
     	
        		nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, Node_x.get(SiteList.get(c)));
        		nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, Node_y.get(SiteList.get(c)));

        		SiteList.set(c, SiteList.get(c)+1);
        	}
        	
        	temp+=1;
        }
        
        
        networkView.updateView();
        
        

    }
}