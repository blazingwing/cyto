
import Term.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.app.swing.CySwingAppAdapter;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.CyVersion;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.application.swing.CytoPanelName;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.group.CyGroupFactory;
import org.cytoscape.group.CyGroupManager;
import org.cytoscape.group.data.CyGroupAggregationManager;
import org.cytoscape.io.datasource.DataSourceManager;
import org.cytoscape.io.read.CyNetworkReaderManager;
import org.cytoscape.io.read.CyPropertyReaderManager;
import org.cytoscape.io.read.CySessionReaderManager;
import org.cytoscape.io.read.CyTableReaderManager;
import org.cytoscape.io.write.CyNetworkViewWriterManager;
import org.cytoscape.io.write.CyPropertyWriterManager;
import org.cytoscape.io.write.CySessionWriterManager;
import org.cytoscape.io.write.CyTableWriterManager;
import org.cytoscape.io.write.PresentationWriterManager;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.model.CyTableUtil;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.property.CyProperty;
import org.cytoscape.service.util.CyServiceRegistrar;
import org.cytoscape.session.CySessionManager;
import org.cytoscape.task.create.CloneNetworkTaskFactory;
import org.cytoscape.task.create.CreateNetworkViewTaskFactory;
import org.cytoscape.task.create.NewEmptyNetworkViewFactory;
import org.cytoscape.task.create.NewNetworkSelectedNodesAndEdgesTaskFatory;
import org.cytoscape.task.create.NewNetworkSelectedNodesOnlyTaskFactory;
import org.cytoscape.task.create.NewSessionTaskFactory;
import org.cytoscape.task.destroy.DeleteColumnTaskFactory;
import org.cytoscape.task.destroy.DeleteSelectedNodesAndEdgesTaskFactory;
import org.cytoscape.task.destroy.DeleteTableTaskFactory;
import org.cytoscape.task.destroy.DestroyNetworkTaskFactory;
import org.cytoscape.task.destroy.DestroyNetworkViewTaskFactory;
import org.cytoscape.task.edit.CollapseGroupTaskFactory;
import org.cytoscape.task.edit.ConnectSelectedNodesTaskFactory;
import org.cytoscape.task.edit.EditNetworkTitleTaskFactory;
import org.cytoscape.task.edit.ExpandGroupTaskFactory;
import org.cytoscape.task.edit.GroupNodesTaskFactory;
import org.cytoscape.task.edit.MapGlobalToLocalTableTaskFactory;
import org.cytoscape.task.edit.MapTableToNetworkTablesTaskFactory;
import org.cytoscape.task.edit.RenameColumnTaskFactory;
import org.cytoscape.task.edit.UnGroupNodesTaskFactory;
import org.cytoscape.task.edit.UnGroupTaskFactory;
import org.cytoscape.task.hide.HideSelectedEdgesTaskFactory;
import org.cytoscape.task.hide.HideSelectedNodesTaskFactory;
import org.cytoscape.task.hide.HideSelectedTaskFactory;
import org.cytoscape.task.hide.UnHideAllEdgesTaskFactory;
import org.cytoscape.task.hide.UnHideAllNodesTaskFactory;
import org.cytoscape.task.hide.UnHideAllTaskFactory;
import org.cytoscape.task.read.LoadNetworkFileTaskFactory;
import org.cytoscape.task.read.LoadNetworkURLTaskFactory;
import org.cytoscape.task.read.LoadTableFileTaskFactory;
import org.cytoscape.task.read.LoadTableURLTaskFactory;
import org.cytoscape.task.read.LoadVizmapFileTaskFactory;
import org.cytoscape.task.read.OpenSessionTaskFactory;
import org.cytoscape.task.select.DeselectAllEdgesTaskFactory;
import org.cytoscape.task.select.DeselectAllNodesTaskFactory;
import org.cytoscape.task.select.DeselectAllTaskFactory;
import org.cytoscape.task.select.InvertSelectedEdgesTaskFactory;
import org.cytoscape.task.select.InvertSelectedNodesTaskFactory;
import org.cytoscape.task.select.SelectAdjacentEdgesTaskFactory;
import org.cytoscape.task.select.SelectAllEdgesTaskFactory;
import org.cytoscape.task.select.SelectAllNodesTaskFactory;
import org.cytoscape.task.select.SelectAllTaskFactory;
import org.cytoscape.task.select.SelectConnectedNodesTaskFactory;
import org.cytoscape.task.select.SelectFirstNeighborsNodeViewTaskFactory;
import org.cytoscape.task.select.SelectFirstNeighborsTaskFactory;
import org.cytoscape.task.select.SelectFromFileListTaskFactory;
import org.cytoscape.task.visualize.ApplyPreferredLayoutTaskFactory;
import org.cytoscape.task.visualize.ApplyVisualStyleTaskFactory;
import org.cytoscape.task.write.ExportNetworkImageTaskFactory;
import org.cytoscape.task.write.ExportNetworkViewTaskFactory;
import org.cytoscape.task.write.ExportSelectedTableTaskFactory;
import org.cytoscape.task.write.ExportTableTaskFactory;
import org.cytoscape.task.write.ExportVizmapTaskFactory;
import org.cytoscape.task.write.SaveSessionAsTaskFactory;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.model.View;
import org.cytoscape.view.presentation.RenderingEngineManager;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.LineTypeVisualProperty;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.presentation.property.values.Bend;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.Task;
import org.cytoscape.work.TaskManager;
import org.cytoscape.work.undo.UndoSupport;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MyControlPanel extends JPanel implements CytoPanelComponent {
	
	private static final long serialVersionUID = 8292806967891823933L;


	public MyControlPanel(final CySwingAppAdapter adapter) {
		
		this.setVisible(true);
		this.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic operations"));
		
		jp1.setBorder(javax.swing.BorderFactory.createTitledBorder("GO Tree Interval"));
		jp2.setBorder(javax.swing.BorderFactory.createTitledBorder("q-value cut"));
		jp3.setBorder(javax.swing.BorderFactory.createTitledBorder("Kappa score"));
		jp4.setBorder(javax.swing.BorderFactory.createTitledBorder("Magic"));
		jp = new JPanel[]{jp1,jp2,jp3,jp4};
		
		jp1.add(jl1);		
		
		jcb1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19","20"}));
		jcb1.setEnabled(true);
		jcb1.setSelectedIndex(5);
		jp1.add(jcb1);
		
		jp1.add(jl2);
		
		jcb2.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19","20"}));
		jcb2.setEnabled(true);
		jcb2.setSelectedIndex(12);
		jp1.add(jcb2);
		
		jp2.add(jl3);		
		
		jtf1.setPreferredSize(new Dimension(125,25));
		jp2.add(jtf1);	
		
		jp3.add(jl4);		
		jtf2.setPreferredSize(new Dimension(125,25));;
		jp3.add(jtf2);
		
		
		jb1.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				Magic(evt,adapter);
				}
			});
		jp4.add(jb1);
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);

		//mf.kappascore_t = Double.parseDouble(jtf2.getText());
	}

	public void Magic(java.awt.event.ActionEvent evt,CySwingAppAdapter adapter){
		
    	//new CyActivator();
        final CyApplicationManager manager = adapter.getCyApplicationManager();
        final CyNetworkView networkView = manager.getCurrentNetworkView();
        final CyNetwork network = manager.getCurrentNetwork();

        String line = null;
        ArrayList<Term> TermList = new ArrayList<Term>();
        ArrayList<Term> TermList_o = new ArrayList<Term>();
        ArrayList<Term> TermList_oo = new ArrayList<Term>();
        ArrayList<Term> CmpTermList = new ArrayList<Term>();
        ArrayList<Term> CmpTermList2 = new ArrayList<Term>();
        ArrayList<Term> GroupGeneList = new ArrayList<Term>();
        ArrayList<Term> GroupTermList = new ArrayList<Term>();
        ArrayList<Term> GGList = new ArrayList<Term>();
        ArrayList<Double> AnnotationSite = new ArrayList<Double>();
        int NanAnnotationSite = 0;
        
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
        TermList_oo=ff.ReadTermTxt("homo_term.txt");
        CmpTermList=ff.ReadCmpTxt("homo_cmp.txt");
        CmpTermList2=ff.ReadCmpTxt("homo_cmp2.txt");
    	//double[][] Kappa=ff.ReadKappaTxt("homo_kappa.txt", TermList_o.size());   
    	
        double x = 0;
        double y = 0;
        double count1 = 0;
        double count2 = 0; 
        double count2_withoutNan = 0;
        double count3 = 0;
        double r = 19;
        double m = 0;
        double temp = 0;
        int layer = 0;
        int ref_count=0;
        int distance = 140;
        
        //ALL Node
        for(CyNode node : network.getNodeList()){
        	nodeView = networkView.getNodeView(node);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.BLACK);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 20);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, (double)0);

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
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.WHITE);
        	
        	Core_edge = (ArrayList<CyEdge>) network.getAdjacentEdgeList(node,CyEdge.Type.ANY);
        	count1 += 1;
        }

        //UnSelected Node get term
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	x += nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION);
        	y += nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION);
        	//String key = String.valueOf(name.charAt(0));
        	/*----- Get Term -----*/
        	String name = nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL);
        	String key = name;
        	String key2 = name;
        	String key3 = name;
        	
        	key2=mf.TransCmp(CmpTermList, key);
        	key3=mf.TransCmp(CmpTermList2, key);
        	if(!name.equals(key2))
        		Node_name.add(key2);
        	else
        		Node_name.add(key3);
        	
        	count2 += 1;
        }
        
        count3 = count1 + count2;
        double xa = x/count3;
        double xb = y/count3;
        
        for(int i=0;i<TermList_oo.size();i++){
    		int a=0;
    		for(int j=0;j<Node_name.size();j++)
        		if(TermList_oo.get(i).Node.contains(Node_name.get(j)))
        			a++;
    		if(a>0){
    			if(mf.LevelCheck(jcb1.getSelectedIndex()+1, jcb2.getSelectedIndex()+1, TermList_oo.get(i).level_min,TermList_oo.get(i).level_max)){
    					TermList_o.add(TermList_oo.get(i));
    			}
    		}
    	}
        
        

        //-----Term FisherExcat p-value---
        
        for(int i=0;i<TermList_o.size();i++)
        {
        	int a=0,b=0,c=0,d=0;
        	for(int j=0;j<Node_name.size();j++)
        		if(TermList_o.get(i).Node.contains(Node_name.get(j)))
        			a++;
        	b=(int)count2-a;
        	for(int j=0;j<TermList_o.get(i).Node.size();j++)
        		for(int k=0;k<TermList_o.size();k++)
        		{
        			if(i==k)
        				continue;
        			if(TermList_o.get(k).Node.contains(TermList_o.get(i).Node.get(j))){
        				c++;
        				break;
        			}
        		}
        	d=ref_count-c;
        	TermList_o.get(i).pvalue=mf.FisherExact_LoggetP(a, b, c, d);
        	if(TermList_o.get(i).pvalue <= Double.parseDouble(jtf1.getText()))
        		TermList.add(TermList_o.get(i));
        }
        
        //**********//
        
        for(int i=0;i<TermList.size();i++)
            ref_count+=TermList.get(i).Node.size();
        
        double[] TermpvalueTable = new double[TermList.size()];
        TermpvalueTable=mf.SortTable(TermpvalueTable);
        for(int i=0;i<TermList.size();i++)
        	TermList.get(i).qvalue=mf.FDR(TermList.get(i).pvalue, TermpvalueTable);
        
        
     
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
        		double k=mf.Kappa_getP(group, Node_name);
        		//double k=Kappa[TermList_index.get(i)][TermList_index.get(j)];
        		//double k=ff.GetKappaTxt("homo_kappa.txt", i, j);
        		
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
        		if(GroupKappaTable[i][j]>=Double.parseDouble(jtf2.getText())){
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
        	
        	String group_name = mf.Min_P(group).Function;
        	
        	Term t = new Term();
    		t.Name=group_name;
    		for(int j=0;j<group.size();j++)
    			for(int k=0;k<group.get(j).Node.size();k++)
    				if(!t.Node.contains(group.get(j).Node.get(k)))
    					t.Node.add(group.get(j).Node.get(k));
    			
    		GroupGeneList.add(t);
    		
    		Term tt = new Term();
    		tt.Name=group_name;
    		for(int j=0;j<group.size();j++)
    			tt.Node.add(group.get(j).Name);
    		
    		GroupTermList.add(tt);
    		
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
        	GroupGeneList.get(i).qvalue=mf.FDR(GroupGeneList.get(i).pvalue, GrouppvalueTable);
        	GroupTermList.get(i).qvalue=GroupGeneList.get(i).qvalue;
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
        	Term tempt = mf.Min_Q(t);
        	
        	for(int j=0;j<GroupTermList.size();j++)
        		if(GroupTermList.get(j).Node.contains(tempt.Name))
        			g.add(GroupTermList.get(j));
        	if(g.size()==0)
        		continue;        	
        	Term tempg = mf.Min_Q(g);
        	
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
        		tt.Function = tempg.Name;
        		tt.Node.add(Node_name.get(i));
        		GGList.add(tt);
        	}	
        }
        

        //****************************
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	nodeView = networkView.getNodeView(node);
        	
        	String name = nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL);
        	String key = name;
        	String key2 = mf.TransCmp(CmpTermList, key);
        	String key3 = mf.TransCmp(CmpTermList2, key);
        	boolean isIn = false;
        	
        	for(int i=0;i<GGList.size();i++)
        	{
        		if(GGList.get(i).InTerm(key) || GGList.get(i).InTerm(key2) || GGList.get(i).InTerm(key3))
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
        if(NanSiteList_count>0)
        	NanAnnotationSite = NanSiteList.get(0);
        

        
        
        
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
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float )0, (float) 0.4, (float) 0));
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_PAINT, Color.getHSBColor((float )0, (float) 0.4, (float) 0));
        	
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
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (distance+r*(count1-1)+r*(layer-(temp%layer))*layer)*Math.cos(m)+xa);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (distance+r*(count1-1)+r*(layer-(temp%layer))*layer)*Math.sin(m)+xb);
        	
        	//Site add 
        	Node_x.add(nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION));
        	Node_y.add(nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION));
        	
        	double c = 0;
        	String key = Node_class.get((int)temp);        	
        	
        	float c2 = (float) 0.85;
        	float c3 = (float) 0.9;
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
        		if(c==0 && SiteList_count.get(0)>3)
        			c+=0.5;
        		//nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float) ((float)((c/2)+(c%2==1?ClassList.size()/2:0))/ClassList.size()), c2, c3));
        		//nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_PAINT, Color.getHSBColor((float) ((float)((c/2)+(c%2==1?ClassList.size()/2:0))/ClassList.size()), c2, c3));
        		nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float) ((float)c/ClassList.size()), c2, c3));
        		nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_PAINT, Color.getHSBColor((float) ((float)c/ClassList.size()), c2, c3));
        	}
        	
        	
        	ArrayList<CyEdge> node_edge = (ArrayList<CyEdge>) network.getAdjacentEdgeList(node,CyEdge.Type.ANY);	
        	for(int i=0;i<node_edge.size();i++)
        		for(int j=0;j<Core_edge.size();j++)
        			if(node_edge.get(i)==Core_edge.get(j))
        			{
        				edgeView = networkView.getEdgeView(node_edge.get(i));
        				//edgeView.setLockedValue(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT, Color.getHSBColor((float) ((float)((c/2)+(c%2==1?ClassList.size()/2:0))/ClassList.size()), c2, c3));
        				edgeView.setLockedValue(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT, Color.getHSBColor((float) ((float)c/ClassList.size()), c2, c3));
        				edgeView.setLockedValue(BasicVisualLexicon.EDGE_TRANSPARENCY,200);
        				edgeView.setLockedValue(BasicVisualLexicon.EDGE_LINE_TYPE, LineTypeVisualProperty.SOLID);
        			}

        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.ELLIPSE);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SIZE, 60.0);
        	
        	temp+=1;
        } 
        
        
        for(int i=0;i<SiteList.size();i++){
        	AnnotationSite.add(((double)SiteList_count.get(i)/2+SiteList.get(i)));
        }
        //Change Site 
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
        
        /* Add Annotation */
        temp = 0;
        
        for(int i=0;i<GGList.size();i++){
        	CyNode centroid = network.addNode();
        	
        	network.getRow(centroid).set(CyNetwork.NAME, GGList.get(i).Function);

        	networkView.updateView();        	
        	double num = AnnotationSite.get(i);
        	m = (num*360/count2)*Math.PI/180;

        	int dis=(int) ((Math.abs(Math.cos(num*360/count2*Math.PI/180))+1.5)*distance);
        	double c=0;
        	float c2 = (float) 0.85;
        	float c3 = (float) 0.9;
    		if(i==0 && SiteList_count.get(0)>3)
    			c+=0.5;
    		else
    			c=i;
        	nodeView = networkView.getNodeView(centroid);

        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (dis+r*(count1-1)+r*layer*layer)*Math.cos(m)+xa);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (dis+r*(count1-1)+r*layer*layer)*Math.sin(m)+xb);
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL, GGList.get(i).Function);
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 24);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 0);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, 0);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.getHSBColor((float) ((float)c/ClassList.size()), c2, c3));
            
            networkView.updateView();
        	
        }
        for(int i=0;i<(NanSiteList_count>0?1:0);i++){
        	CyNode centroid = network.addNode();
        	
        	network.getRow(centroid).set(CyNetwork.NAME, GGList.get(i).Function);
        	
        	networkView.updateView();
        	double num=((double)NanSiteList_count/2+NanAnnotationSite);
        	m = (num*360/count2)*Math.PI/180;

        	int dis=(int) ((Math.abs(Math.cos(num*360/count2*Math.PI/180))+1.5)*distance);
        	double c=0;
        	float c2 = (float) 0.05;
        	float c3 = (float) 0.5;
        	
        	nodeView = networkView.getNodeView(centroid);

        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (dis+r*(count1-1)+r*layer*layer)*Math.cos(m)+xa);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (dis+r*(count1-1)+r*layer*layer)*Math.sin(m)+xb);
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL, "other functions");
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 24);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 0);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, 0);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.getHSBColor((float) 0, c2, c3));
        	
        	
        }
        
        

        networkView.updateView();
	}

	public Component getComponent() {
		return this;
	}


	public CytoPanelName getCytoPanelName() {
		return CytoPanelName.WEST;
	}


	public String getTitle() {
		return "Visualization";
	}


	public Icon getIcon() {
		return null;
	}
	//*****

	//*****
	public JPanel jp1=new JPanel();
	public JPanel jp2=new JPanel();
	public JPanel jp3=new JPanel();
	public JPanel jp4=new JPanel();

	public JPanel[] jp = new JPanel[]{jp1,jp2,jp3,jp4};
	public JLabel jl1 = new JLabel("GO Tree Interval : ");
	public JComboBox jcb1 = new JComboBox();
	public JLabel jl2 = new JLabel(" ~ ");
	public JComboBox jcb2 = new JComboBox();
	public JLabel jl3 = new JLabel("q-value cut :");
	public JTextArea jtf1 = new JTextArea("0.05");
	public JLabel jl4 = new JLabel("Kappa score :");
	public JTextArea jtf2 = new JTextArea("0.4");
	public JButton jb1 = new JButton("Magic !!!");
	public MathFuction mf=new MathFuction();

}
