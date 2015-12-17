import ControlPanel.*;
import BundleEdge.*;
import Term.*;

import static org.cytoscape.work.ServiceProperties.ENABLE_FOR;
import static org.cytoscape.work.ServiceProperties.MENU_GRAVITY;
import static org.cytoscape.work.ServiceProperties.PREFERRED_MENU;
import static org.cytoscape.work.ServiceProperties.TITLE;

import java.awt.Color;
import java.awt.Component;
import java.awt.List;
import java.awt.Shape;
import java.lang.Math;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.crypto.spec.IvParameterSpec;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.InvalidSearchFilterException;
import javax.swing.plaf.TabbedPaneUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI.TabbedPaneLayout;
import javax.swing.text.AttributeSet.ColorAttribute;
import javax.xml.soap.Node;
import javax.xml.stream.events.StartDocument;

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
import org.cytoscape.view.presentation.property.LineTypeVisualProperty;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.presentation.property.values.Bend;
import org.cytoscape.view.presentation.property.values.BendFactory;
import org.cytoscape.view.presentation.property.values.Handle;
import org.cytoscape.view.presentation.property.values.HandleFactory;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskIterator;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.task.EdgeViewTaskFactory;
import org.cytoscape.task.NetworkTaskFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkListener;
import org.osgi.service.condpermadmin.BundleLocationCondition;

import static org.cytoscape.work.ServiceProperties.*;

public class MenuAction extends AbstractCyAction {
    private final CyAppAdapter adapter;

    public MenuAction(CyAppAdapter adapter) {
        super("々々々々々々々々々々",
                adapter.getCyApplicationManager(),
                "network",
                adapter.getCyNetworkViewManager());
            this.adapter = adapter;
            setPreferredMenu("Apps");
            
       	 //MyControlPanel myPanel = new MyControlPanel();
       	 //adapter.getCyServiceRegistrar().registerService(myPanel,CytoPanelComponent.class,new Properties());    
    }
     @SuppressWarnings({ "unused", "null" })
	public void actionPerformed(ActionEvent e) {

    	
    	//new CyActivator();
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

        CyNode Core_node = null;
        ArrayList<CyNode> Core_neighbor = new ArrayList<CyNode>();
        ArrayList<CyEdge> Core_edge = new ArrayList<CyEdge>();
				
        //-----Term-----
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        }
        
        InputStream is = MenuAction.class.getResourceAsStream("term.txt");
        InputStreamReader isr = new InputStreamReader(is); 
        BufferedReader br = new BufferedReader(isr);	

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
        
        //ALL Node
        for(CyNode node : network.getNodeList()){
        	nodeView = networkView.getNodeView(node);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.BLACK);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 18);
        }
      //ALL Edge
        temp=0;
        for(CyEdge edge : network.getEdgeList()){
        	edgeView = networkView.getEdgeView(edge);
        	edgeView.setLockedValue(BasicVisualLexicon.EDGE_TRANSPARENCY, 50);
        	edgeView.setLockedValue(BasicVisualLexicon.EDGE_LINE_TYPE, LineTypeVisualProperty.DASH_DOT);
        	temp++;
        }
      
        //Selected Node
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	x += nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
        	y += nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
        	
        	Core_neighbor = (ArrayList<CyNode>) network.getNeighborList(node, CyEdge.Type.ANY);
        	Core_edge = (ArrayList<CyEdge>) network.getAdjacentEdgeList(node,CyEdge.Type.ANY);
        	count1 += 1;
        }

        //UnSelected Node get term
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	x += nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
        	y += nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
        	
       	
        	String name = nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL);
        	//String key = String.valueOf(name.charAt(0));
        	/*----- Get Term -----*/
        	
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
        if(SiteList.size()!=0)
        	if(NanSiteList_count>0)
        		NanSiteList.set(0, SiteList.get(SiteList.size()-1)+SiteList_count.get(SiteList_count.size()-1));

        	
        

        Bend bb = null;
        
        
        /* Site Circle Selected */
        layer = (int)Math.sqrt((double)count1);
        layer /= 2;
        temp = 0;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	//neighborList = network.getNeighborList(node, CyEdge.Type.ANY);
        	
        	m = (temp*360/count1)*Math.PI/180;
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (r*(count1-1))*Math.cos((temp*360/count1)*Math.PI/180)+a);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (r*(count1-1))*Math.sin((temp*360/count1)*Math.PI/180)+b);        	
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float )0, (float) 0.4, (float) 0.7));
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_PAINT, Color.getHSBColor((float )0, (float) 0.4, (float) 0.7));
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.DIAMOND);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SIZE, 70.0);
        	
        	temp+=1;           	
        }
     
        
        
        /* Site Circle Unselected */
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
        	
        	float c2 = (float) 0.85;
        	float c3 = (float) 0.85;
        	if(key.equals("NaN"))
        	{
        		c2 = (float) 0.05;
        		c3 = (float) 0.5;	
        		nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float) 0, c2, c3));
        		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_PAINT, Color.getHSBColor((float) 0, c2, c3));
        	}	
        	else
        	{
        		c = ClassList.indexOf(key);
        		nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float) ((float)(c+count1)/(ClassList.size()+count1)), c2, c3));
        		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_PAINT, Color.getHSBColor((float) ((float)(c+count1)/(ClassList.size()+count1)), c2, c3));
        	}
        	
        	
        	ArrayList<CyEdge> node_edge = (ArrayList<CyEdge>) network.getAdjacentEdgeList(node,CyEdge.Type.ANY);	
        	for(int i=0;i<node_edge.size();i++)
        		for(int j=0;j<Core_edge.size();j++)
        			if(node_edge.get(i)==Core_edge.get(j))
        			{
        				edgeView = networkView.getEdgeView(node_edge.get(i));
        				edgeView.setLockedValue(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT, Color.getHSBColor((float) ((float)(c+count1)/(ClassList.size()+count1)), c2, c3));
        				edgeView.setLockedValue(BasicVisualLexicon.EDGE_TRANSPARENCY,250);
        				edgeView.setLockedValue(BasicVisualLexicon.EDGE_LINE_TYPE, LineTypeVisualProperty.SOLID);
        			}

        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SIZE, 60.0);
        	
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
        
        
        temp=0;
        /* Bundle Edge */ 
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	x = nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
        	y = nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
        	Core_node = node;
        }
        nodeView = networkView.getNodeView(Core_node);

        ArrayList<CyEdge> Core_node_edge = (ArrayList<CyEdge>) network.getAdjacentEdgeList(Core_node,CyEdge.Type.ANY);

        ArrayList<CyNode> groupCyNode = new ArrayList<CyNode>();
    	ArrayList<CyEdge> groupCyEdge = new ArrayList<CyEdge>();
        
        for(int i=0;i<ClassList.size();i++){

        	groupCyNode = new ArrayList<CyNode>();
        	groupCyEdge = new ArrayList<CyEdge>();
        	int gcn=0;
        	int gce=0;

        	double gx=0;
        	double gy=0;

        	int gc=0;
        	temp=0;
        	
        	for(CyEdge edge:Core_node_edge){
    			edgeView = networkView.getEdgeView(edge);
    			nodeView = networkView.getNodeView(edge.getTarget());
    			
    			int c=-1;
        		boolean isIn = false;
        		String key = nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL);
        		for(int ki=0;ki<TermList.size();ki++){
            		if(TermList.get(ki).Term_node.contains(key)){
            			key = TermList.get(ki).Term_name;
            			isIn = true;
            			break;
            		}
        		}
        		if(!isIn)
        			key = "NaN";        		
        		
        		if(key.equals("NaN"))
        			continue;
        		else
        			c = (int) ClassList.indexOf(key);
        		
        		
        		if(i==c)
        		{
         			gx+=nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
        			gy+=nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);   
        			groupCyNode.add(edge.getTarget());
        			gcn++;
        			groupCyEdge.add(edge);
        			gce++;
        		}
        		
        	}
        	
        	gx/=groupCyNode.size();
        	gy/=groupCyNode.size();
        	

        	        	
        	for(CyEdge edge : groupCyEdge){

        		edgeView = networkView.getEdgeView(edge); 
        		bb = edgeView.getVisualProperty(BasicVisualLexicon.EDGE_BEND);
            	java.util.List<Handle> handles = bb.getAllHandles();
            	for(Handle h : handles)
            		edgeView.setVisualProperty(BasicVisualLexicon.EDGE_LABEL, h.toString());
            	
        	}
        	//bend.insertHandleAt(0, h);        	
        	//edgeView.setVisualProperty(BasicVisualLexicon.EDGE_BEND, bend);
        	
        /*	BendFactory bf =null;
			HandleFactory hf = null;
			Bend bend = bf.createBend();
			java.util.List<Handle> hlist = bend.getAllHandles();

			final Handle h = hf.createHandle(gx/6.0, gy/6.0);
			hlist.add(h);
			final Map<Long, Bend> mappingValues;
			mappingValues = new HashMap<>();
			String suid = String.valueOf(edgeView.getSUID());
			mappingValues.put(edgeView.getSUID(),bend);

			final VisualMappingFunctionFactory discreteFactory = null;
			final String BEND_MAP_COLUMN = "BEND_MAP_ID";
			EdgeBendVisualProperty ee = new EdgeBendVisualProperty(bend,suid,suid);
			
			final DiscreteMapping<Long, Bend> function = (DiscreteMapping<Long, Bend>) discreteFactory
					.createVisualMappingFunction(BEND_MAP_COLUMN, Long.class, network.getDefaultEdgeTable(), ee);
			function.putAll(mappingValues);*/
        }
        
        

        
        
        	for(CyEdge edge : groupCyEdge){

        		edgeView = networkView.getEdgeView(edge); 
        		bb = edgeView.getVisualProperty(BasicVisualLexicon.EDGE_BEND);
            	java.util.List<Handle> handles = bb.getAllHandles();
            	for(Handle h : handles)
            		edgeView.setVisualProperty(BasicVisualLexicon.EDGE_LABEL, h.toString());
            	
        	}	
            	//for (Handle handle : handles)
            		//handle.defineHandle( networkView, (View<CyEdge>)  edgeView, 55.0, 55.0);
            	
            	
            	
        			/*final Map<Long, Bend> mappingValues;
        			mappingValues = new HashMap<>();
        			mappingValues.put(edgeView.getSUID(),bend);*/

        			//hlist.get(0).defineHandle( networkView, (View<CyEdge>) edgeView, 50.0,50.0);

        	
        	
        	
        
        //CyEdge edge = CyTableUtil.getEdgesInState(arg0, arg1, arg2);
        //temp=0;

        

        networkView.updateView();


    }
}

