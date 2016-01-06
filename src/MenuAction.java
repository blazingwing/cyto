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
import java.lang.reflect.Array;
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
        ArrayList<Term> CmpTermList = new ArrayList<Term>();
        ArrayList<Term> GroupGeneList = new ArrayList<Term>();
        ArrayList<Term> GroupTermList = new ArrayList<Term>();
        ArrayList<Term> GGList = new ArrayList<Term>();
        
        ArrayList<String> ClassList = new ArrayList<String>();
        ArrayList<String> Node_class = new ArrayList<String>();
        ArrayList<String> Node_name = new ArrayList<String>();
        
        ArrayList<Integer> SiteList= new ArrayList<Integer>();
        ArrayList<Integer> SiteList_count = new ArrayList<Integer>();
        ArrayList<Integer> NanSiteList= new ArrayList<Integer>();
        int NanSiteList_count = 0;
        ArrayList<Double> Node_x = new ArrayList<Double>();
        ArrayList<Double> Node_y = new ArrayList<Double>();

        View<CyNode> nodeView = null;
        View<CyEdge> edgeView = null;

        CyNode Core_node = null;
        ArrayList<CyEdge> Core_edge = new ArrayList<CyEdge>();
        
        MathFuction mf = new MathFuction();
				
        //-----Term-----
        FileFactory ff = new FileFactory();
        TermList=ff.ReadTermTxt("term.txt");
        CmpTermList=ff.ReadCmpTxt("cmp.txt");

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
        int ref_count=0;
        
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
        	
        	Core_edge = (ArrayList<CyEdge>) network.getAdjacentEdgeList(node,CyEdge.Type.ANY);
        	count1 += 1;
        }

        //UnSelected Node get term
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	x += nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);

        	//String key = String.valueOf(name.charAt(0));
        	/*----- Get Term -----*/
        	String name = nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL);
        	String key = name;
        	String key2 = name;
        	
        	key2=mf.TransCmp(CmpTermList, key);
        	Node_name.add(key2);        	
        	
        	count2 += 1;
        }
        
        count3 = count1 + count2;
        
        for(int i=0;i<TermList.size();){
    		int a=0;
    		for(int j=0;j<Node_name.size();j++)
        		if(TermList.get(i).Node.contains(Node_name.get(j)))
        			a++;
    		if(a<1)
    			TermList.remove(i);
    		else
    			i++;
    	}        
        
        for(int i=0;i<TermList.size();i++)
                ref_count+=TermList.get(i).Node.size();
        
        double xa = x/count3;
        double xb = y/count3;
        

        //-----Term FisherExcat p-value---
        double[] TermpvalueTable = new double[TermList.size()];
        for(int i=0;i<TermList.size();i++)
        {
        	int a=0,b=0,c=0,d=0;
        	for(int j=0;j<Node_name.size();j++)
        		if(TermList.get(i).Node.contains(Node_name.get(j)))
        			a++;
        	b=(int)count2-a;
        	for(int j=0;j<TermList.get(i).Node.size();j++)
        		for(int k=0;k<TermList.size();k++)
        		{
        			if(i==k)
        				continue;
        			if(TermList.get(k).Node.contains(TermList.get(i).Node.get(j))){
        				c++;
        				break;
        			}
        		}
        	d=ref_count-c;
        	TermList.get(i).pvalue=mf.FisherExact_LoggetP(a, b, c, d);
        }
        TermpvalueTable=mf.SortTable(TermpvalueTable);
        for(int i=0;i<TermList.size();i++)
        	TermList.get(i).pvalue=mf.FDR(TermList.get(i).pvalue, TermpvalueTable);
        
        //-----Term grouping Kappa p-value---
        temp=0;
        double[][] GroupKappaTable = new double[TermList.size()][TermList.size()];
        int[] TermToGroupIndex = new int[TermList.size()];
        for(int i=0;i<TermList.size();i++)
        {
        	TermToGroupIndex[i]=-1;
        	for(int j=i+1;j<TermList.size();j++)
        	{
        		ArrayList<Term> group = new ArrayList<Term>();
        		group.add(TermList.get(i));
        		group.add(TermList.get(j));
        		double k=mf.Kappa_getP(group);
        		GroupKappaTable[i][j]=k;
        	}
        }
        for(int i=0;i<TermList.size();i++)
        {
        	if(TermToGroupIndex[i]==-1){
        		TermToGroupIndex[i]=(int)temp;
        		temp++;
        	}
        	int gi=TermToGroupIndex[i];
        	for(int j=i+1;j<TermList.size();j++)
        	{
        		if(GroupKappaTable[i][j]>0.4){
        			if(TermToGroupIndex[j]==-1){
        				TermToGroupIndex[j]=gi;
        			}
        			else{
        				for(int k=j;k>=i;k--)
        					if(TermToGroupIndex[k]==gi)
        						TermToGroupIndex[k]=TermToGroupIndex[j];
        				gi=TermToGroupIndex[j];
        			}
        		}            		
        	} 
        }
        int maxGroup=0;
        temp=0;
        for(int i=0;i<TermList.size();i++)
        	if(maxGroup<TermToGroupIndex[i])
        		maxGroup=TermToGroupIndex[i];
        for(int i=0;i<maxGroup;i++){
        	ArrayList<Term> group = new ArrayList<Term>();
        	for(int j=0;j<TermList.size();j++)
        		if(TermToGroupIndex[j]==i)
        			group.add(TermList.get(j));
        	
        	if(group.size()<1)
        		continue;
        	
        	Term t = new Term();
    		t.Name="Group"+String.valueOf(temp);
    		for(int j=0;j<group.size();j++)
    			for(int k=0;k<group.get(j).Node.size();k++)
    				if(!t.Node.contains(group.get(j).Node.get(k)))
    					t.Node.add(group.get(j).Node.get(k));
    			
    		GroupGeneList.add(t);
    		
    		t = new Term();
    		t.Name="Group"+String.valueOf(temp);
    		for(int j=0;j<group.size();j++)
    			t.Node.add(group.get(j).Name);
    		
    		GroupTermList.add(t);
    		
        	temp++;
        }
        
        
        //-----Group FisherExcat p-value---
        double[] GrouppvalueTable = new double[GroupGeneList.size()];
        for(int i=0;i<GroupGeneList.size();i++)
        {
        	int a=0,b=0,c=0,d=0;
        	for(int j=0;j<Node_name.size();j++)
        		if(GroupGeneList.get(i).Node.contains(Node_name.get(j)))
        			a++;
        	b=(int)count2-a;
        	for(int j=0;j<GroupGeneList.get(i).Node.size();j++)
        		for(int k=0;k<GroupGeneList.size();k++)
        		{
        			if(i==k)
        				continue;
        			if(GroupGeneList.get(k).Node.contains(GroupGeneList.get(i).Node.get(j))){
        				c++;
        				break;
        			}
        		}
        	d=ref_count-c;
        	GroupGeneList.get(i).pvalue=mf.FisherExact_LoggetP(a, b, c, d);
        	GroupTermList.get(i).pvalue=GroupGeneList.get(i).pvalue;
        }
        GrouppvalueTable=mf.SortTable(GrouppvalueTable);
        for(int i=0;i<GroupGeneList.size();i++){
        	GroupGeneList.get(i).pvalue=mf.FDR(GroupGeneList.get(i).pvalue, GrouppvalueTable);
        	GroupTermList.get(i).pvalue=GroupGeneList.get(i).pvalue;
        }

        //-----Gene to find Term to find Group---
        for(int i=0;i<Node_name.size();i++)
        {
        	ArrayList<Term> t = new ArrayList<Term>();
        	ArrayList<Term> g = new ArrayList<Term>();
        	
        	for(int j=0;j<TermList.size();j++)
        		if(TermList.get(j).Node.contains(Node_name.get(i)))
        			t.add(TermList.get(j));
        	if(t.size()==0)
        		continue;
        	Term tempt = mf.Min_P(t);
        	
        	for(int j=0;j<GroupTermList.size();j++)
        		if(GroupTermList.get(j).Node.contains(tempt.Name))
        			g.add(GroupTermList.get(j));
        	if(g.size()==0)
        		continue;        	
        	Term tempg = mf.Min_P(g);
        	
        	int index = -1;
        	for(int j=0;j<GGList.size();j++)
        		if(GGList.get(j).Name.equals(tempg.Name))
        			index = j;
        	if(index>-1)
        	{
        		GGList.get(index).Node.add(Node_name.get(i));
        	}
        	else
        	{
        		Term tt = new Term();
        		tt.Name = tempg.Name;
        		tt.Node.add(Node_name.get(i));
        		GGList.add(tt);
        	}	
        }
        
        
        //****************************//
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	
        	String name = nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL);
        	String key = name;
        	String key2 = mf.TransCmp(CmpTermList, key);
        	boolean isIn = false;
        	
        	for(int i=0;i<GGList.size();i++)
        	{
        		if(GGList.get(i).InTerm(key) || GGList.get(i).InTerm(key2))
        		{
        			isIn = true;
        			key = GGList.get(i).Name;
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
        }        
        
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
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (r*(count1-1))*Math.cos((temp*360/count1)*Math.PI/180)+xa);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (r*(count1-1))*Math.sin((temp*360/count1)*Math.PI/180)+xb);        	
        	
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
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (140+r*(count1-1)+r*(layer-(temp%layer))*layer)*Math.cos(m)+xa);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (140+r*(count1-1)+r*(layer-(temp%layer))*layer)*Math.sin(m)+xb);
        	
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
        	
        	temp += 1;

        }
        temp = 0;
        
        
        
        

        networkView.updateView();


    }
}