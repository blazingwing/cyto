
import Term.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.naming.ldap.ManageReferralControl;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
import org.cytoscape.task.NetworkTaskFactory;
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
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;
import org.cytoscape.work.swing.DialogTaskManager;
import org.cytoscape.view.model.CyNetworkView;
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
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;
import org.cytoscape.work.undo.UndoSupport;
import org.omg.CORBA.PUBLIC_MEMBER;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

public class MyControlPanel extends JPanel implements CytoPanelComponent {
	
	private static final long serialVersionUID = 8292806967891823933L;


	public MyControlPanel(final CySwingAppAdapter adapter) {
		
		this.setVisible(true);
		this.setBorder(javax.swing.BorderFactory.createTitledBorder("Basic Operations"));
		this.setPreferredSize(new Dimension(310,305));
		
		jp1.setBorder(javax.swing.BorderFactory.createTitledBorder("Selection Option"));
		jp1.setPreferredSize(new Dimension(300,205));
		
		jp2.setBorder(javax.swing.BorderFactory.createTitledBorder("Statistical Option"));
		jp2.setPreferredSize(new Dimension(300,75));
		
		jp3.setBorder(javax.swing.BorderFactory.createTitledBorder("Grouping Option"));
		jp3.setPreferredSize(new Dimension(300,225));
		
		jp4.setBorder(javax.swing.BorderFactory.createTitledBorder("Submit"));
		jp4.setPreferredSize(new Dimension(300,105));
		
		//Selection Option
		
		//radio
		jr1.setSelected(true);
		jr1.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				UseTaskMonitorTaskFactory useTaskMonitorTaskFactory = new UseTaskMonitorTaskFactory();
				ChangeRadio(1);	
				}
			});
		jp1.add(jr1);
		jr2.setSelected(false);
		jr2.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
				UseTaskMonitorTaskFactory useTaskMonitorTaskFactory = new UseTaskMonitorTaskFactory();
				ChangeRadio(2);	
				}
			});
		jp1.add(jr2);
		
		//level
		jl1_level.setFont(new Font(jl1_level.getText(), 1, 13));
		jp1.add(jl1_level);
		jcb1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19","20"}));
		jcb1.setEnabled(true);
		jcb1.setSelectedIndex(5);
		jp1.add(jcb1);
		
		jl1_leveldash.setFont(new Font(jl1_leveldash.getText(), 1, 15));
		jp1.add(jl1_leveldash);
		
		jcb2.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19","20"}));
		jcb2.setEnabled(true);
		jcb2.setSelectedIndex(12);
		jp1.add(jcb2);
		
		//#
		jl1_hashgene.setFont(new Font(jl1_hashgene.getText(), 1, 15));
		jl1_hashgene.setForeground(Color.getHSBColor((float)0.95,(float) 0.95,(float) 0.25));
		jp1_1.add(jl1_hashgene);	
		
		jtf1_hashgene.setPreferredSize(new Dimension(100,25));;
		jtf1_hashgene.setFont(new Font(jtf1_hashgene.getText(), 1, 15));
		jp1_1.add(jtf1_hashgene);
		
		//%
		jl1_pergene.setFont(new Font(jl1_pergene.getText(), 1, 15));
		jl1_pergene.setForeground(Color.getHSBColor((float)0.95,(float) 0.95,(float) 0.25));
		jp1_2.add(jl1_pergene);
		
		jtf1_pergene.setPreferredSize(new Dimension(100,25));;
		jtf1_pergene.setFont(new Font(jtf1_pergene.getText(), 1, 15));
		jp1_2.add(jtf1_pergene);	
		
		jp1.add(jp1_1);
		jp1.add(jp1_2);	
		
		//core
		jcheck1.setFont(new Font(jcheck1.getText(), 1, 15));
		jcheck1.setForeground(Color.getHSBColor((float)0.95,(float) 0.95,(float) 0.25));
		jcheck1.setSelected(true);
		jp1_3.add(jcheck1);
		
		jtf1_core.setText("Core");
		jtf1_core.setPreferredSize(new Dimension(100,25));
		jtf1_core.setFont(new Font(jtf1_core.getText(), 1, 15));
		jp1_3.add(jtf1_core);

		jp1.add(jp1_3);
		
		//jp1.add(jcc1);
		
		
		//p-value
		jl2_pvalue.setFont(new Font(jl2_pvalue.getText(), 1, 15));	
		jl2_pvalue.setForeground(Color.getHSBColor((float)0.95,(float) 0.95,(float) 0.25));
		jp2.add(jl2_pvalue);		
		
		jtf2_pvalue.setPreferredSize(new Dimension(100,25));
		jtf2_pvalue.setFont(new Font(jtf2_pvalue.getText(), 1, 15));
		jp2.add(jtf2_pvalue);	
		
		
		//kappa
		jl3_kappa.setFont(new Font(jl3_kappa.getText(), 1, 15));	
		jl3_kappa.setForeground(Color.getHSBColor((float)0.95,(float) 0.95,(float) 0.25));
		jp3_1.add(jl3_kappa);
		
		jtf3_kappa.setPreferredSize(new Dimension(100,25));;
		jtf3_kappa.setFont(new Font(jtf3_kappa.getText(), 1, 15));
		jp3_1.add(jtf3_kappa);
		
		//bar
		jl3_slider0.setFont(new Font(jl3_slider0.getText(), 1, 15));
		jl3_slider0.setForeground(Color.getHSBColor((float)0.95,(float) 0.95,(float) 0.25));
		jp3_11.add(jl3_slider0);
		
		js1.setMinimum(0);
		js1.setMaximum(100);
		js1.setValue(40);
		js1.setPreferredSize(new Dimension(200,50));
		js1.addChangeListener(new SliderListener());
		jp3_11.add(js1);
		
		jl3_slider1.setFont(new Font(jl3_slider1.getText(), 1, 15));
		jl3_slider1.setForeground(Color.getHSBColor((float)0.95,(float) 0.95,(float) 0.25));
		jp3_11.add(jl3_slider1);
		
		//size
		jl3_inigroupterm.setFont(new Font(jl3_inigroupterm.getText(), 1, 15));
		jl3_inigroupterm.setForeground(Color.getHSBColor((float)0.95,(float) 0.95,(float) 0.25));
		jp3_2.add(jl3_inigroupterm);
		
		jtf3_inigroupterm.setPreferredSize(new Dimension(100,25));;
		jtf3_inigroupterm.setFont(new Font(jtf3_inigroupterm.getText(), 1, 15));
		jp3_2.add(jtf3_inigroupterm);	
		
		jl3_inigroupgene.setFont(new Font(jl3_inigroupgene.getText(), 1, 15));
		jl3_inigroupgene.setForeground(Color.getHSBColor((float)0.95,(float) 0.95,(float) 0.25));
		jp3_3.add(jl3_inigroupgene);
		
		jtf3_inigroupgene.setPreferredSize(new Dimension(100,25));;
		jtf3_inigroupgene.setFont(new Font(jtf3_inigroupgene.getText(), 1, 15));
		jp3_3.add(jtf3_inigroupgene);	
		
		jp3.add(jp3_1);		
		jp3.add(jp3_11);
		jp3.add(jp3_2);
		jp3.add(jp3_3);

		final DialogTaskManager dialogTaskManager = null;		
		
		jb1.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){	

				UseTaskMonitorTaskFactory useTaskMonitorTaskFactory = new UseTaskMonitorTaskFactory();
				//dialogTaskManager.execute(new TaskIterator(new UseTaskMonitorTask()));
				/*
				TaskManager taskManager= adapter.getTaskManager();
				CloneNetworkTaskFactory cntf = adapter.get_CloneNetworkTaskFactory();
				TaskIterator ti = cntf.createTaskIterator(adapter.getCyApplicationManager().getCurrentNetwork());	
				taskManager.execute(ti);				
				ti.append(ti);
				taskManager.execute(ti);
				*/
				//getNetworkList(adapter);
				//adapter.getCyApplicationManager().getCurrentNetworkView().updateView();
				
				Magic(evt,adapter);		
				}
			});
		jb1.setPreferredSize(new Dimension(224,25));
		jb2.setPreferredSize(new Dimension(130,25));
		jb3.setPreferredSize(new Dimension(90,25));
		jp4.add(jb1);
		jp4.add(jb2);
		jb2.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
					try {
						Export(adapter);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		jb3.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent evt){
					Default();
				}
			});
		jp4.add(jb3);
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		this.add(jp4);

		//mf.kappascore_t = Double.parseDouble(jtf2.getText());
	}
	
	
	public class SliderListener implements ChangeListener {
	    public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
	            int fps = (int)source.getValue();
	            jtf3_kappa.setText(String.valueOf((double)fps/100));
	        }    
	    }
	}
	
	public JPanel jp1=new JPanel();
	public JPanel jp2=new JPanel();
	public JPanel jp3=new JPanel();
	public JPanel jp4=new JPanel();
	public JPanel jp3_1=new JPanel();
	public JPanel jp3_11=new JPanel();
	public JPanel jp3_2=new JPanel();
	public JPanel jp3_3=new JPanel();
	public JPanel jp1_1=new JPanel();
	public JPanel jp1_2=new JPanel();
	public JPanel jp1_3=new JPanel();
	
	//1
	public JRadioButton jr1 = new JRadioButton("Biological Process");
	public JRadioButton jr2 = new JRadioButton("Cellular Component");
	public JLabel jl1_level = new JLabel("GO Tree Interval (Level) :");
	public JComboBox jcb1 = new JComboBox();
	public JLabel jl1_leveldash = new JLabel(" ~ ");
	public JComboBox jcb2 = new JComboBox();
	public JLabel jl1_hashgene = new JLabel("            # for genes :");
	public JTextArea jtf1_hashgene = new JTextArea("3");
	public JLabel jl1_pergene = new JLabel("           % for genes :");
	public JTextArea jtf1_pergene = new JTextArea("4");
	public JCheckBox jcheck1 = new JCheckBox("Auto Core");
	public JTextArea jtf1_core = new JTextArea("Core");
	//2
	public JLabel jl2_pvalue = new JLabel("        p-value cut off :");
	public JTextArea jtf2_pvalue = new JTextArea("0.05");
	//3
	public JLabel jl3_kappa = new JLabel("          Kappa score :");
	public JTextArea jtf3_kappa = new JTextArea("0.4");
	
	public JLabel jl3_slider0 = new JLabel(" 0 ");
	public JSlider js1 = new JSlider();
	public JLabel jl3_slider1 = new JLabel(" 1 ");
	
	public JLabel jl3_inigroupterm = new JLabel("   Initial term size :");
	public JTextArea jtf3_inigroupterm = new JTextArea("3");
	
	public JLabel jl3_inigroupgene = new JLabel("  Initial gene size :");
	public JTextArea jtf3_inigroupgene = new JTextArea("1");


	public JButton jb1 = new JButton("¡i Start ¡j");
	public JButton jb2 = new JButton("Export file");
	public JButton jb3 = new JButton("Default");	

	public JColorChooser jcc1 = new JColorChooser();
	
	public MathFuction mf=new MathFuction();
		
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
        ArrayList<Term> GGList_o = new ArrayList<Term>();
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
        if(jr1.isSelected())
        	TermList_oo=ff.ReadTermTxt("homo_bp.txt");
        else if(jr2.isSelected())
        	TermList_oo=ff.ReadTermTxt("homo_cc.txt");
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
    		if(a>=Double.parseDouble(jtf1_hashgene.getText()) && (100*a/TermList_oo.get(i).Node.size())>=Double.parseDouble(jtf1_pergene.getText())){
    			if(mf.LevelCheck(jcb1.getSelectedIndex()+1, jcb2.getSelectedIndex()+1, TermList_oo.get(i).level_min,TermList_oo.get(i).level_max)){
    					TermList_o.add(TermList_oo.get(i));
    			}
    		}
    	}
        
        

        //-----Term FisherExcat p-value---
        
        for(int i=0;i<TermList_o.size();i++)
            ref_count+=TermList_o.get(i).Node.size();
        
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
        	if(TermList_o.get(i).pvalue <= Double.parseDouble(jtf2_pvalue.getText()))
        		TermList.add(TermList_o.get(i));
        }
        
        //**********//
        
        ref_count = 0;
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
      //-----Term grouping Kappa connect---
        for(int i=0;i<TermList.size();i++)
        {
        	if(TermToGroupIndex[i]==-1){
        		TermToGroupIndex[i]=(int)temp;
        		temp++;
        	}
        	int gi=TermToGroupIndex[i];
        	for(int j=i+1;j<TermList.size();j++)
        	{
        		if(GroupKappaTable[i][j]>=Double.parseDouble(jtf3_kappa.getText())){
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
      //-----Term grouping merge---       
        /*
        for(int i=0;i<temp;i++){
        	
        	ArrayList<Term> group = new ArrayList<Term>();
        	for(int j=0;j<TermList.size();j++)
        		if(TermToGroupIndex[j]==i)
        			group.add(TermList.get(j));
        	
        	if(group.size()<Double.parseDouble(jtf3.getText()))
        		continue;   
        	
        	for(int j=0;j<group.size();j++){  
        		
            	//String group_name = mf.Min_P(group).Function;
        		ArrayList<Term> group2 = new ArrayList<Term>();
        		group2.add(group.get(j));
            	
            	Term t = new Term();
            	t.Node.add(group.get(j).Name);
            	
            	Term tt = new Term();
            	for(int k=0;k<group.get(j).Node.size();k++)
            		tt.Node.add(group.get(j).Node.get(k));  
            	
        		//t.Name=group_name;
        		
        		for(int k=0;k<group.size();k++){
        			
        			if(j==k)
        				continue;
        			
        			if(mf.Merge_percent(group.get(j), group.get(k))>=(Double.parseDouble(jtf4.getText())/100)){
        				group2.add(group.get(k));
        				t.Node.add(group.get(k).Name);
        				for(int l=0;l<group.get(k).Node.size();l++)
        					if(!tt.InTerm(group.get(k).Node.get(l)))
        						tt.Node.add(group.get(k).Node.get(l));  
        			}
        			
        			if(t.Node.size()<Double.parseDouble(jtf3.getText()))
        				continue;
        			
        			String group_name = mf.Min_P(group2).Function;
        			t.Function = group_name;
        			tt.Function = group_name;
        			
        			GroupGeneList.add(t);
        			GroupTermList.add(tt);
        		}
        	}
        	
        }
        */
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
        	
        	if(group.size()<Double.parseDouble(jtf3_inigroupterm.getText()))
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
        	for(int j=0;j<GGList_o.size();j++)
        		if(GGList_o.get(j).Name.equals(tempg.Name))
        			index = j;
        	if(index>-1)
        	{
        		GGList_o.get(index).Node.add(Node_name.get(i));
        	}
        	else
        	{
        		Term tt = new Term();
        		tt.Name = tempg.Name;
        		tt.Function = tempg.Name;
        		tt.Node.add(Node_name.get(i));
        		GGList_o.add(tt);
        	}	
        }
        
        for(int i=0;i<GGList_o.size();i++)
        	if(!(GGList_o.get(i).Node.size()<Double.parseDouble(jtf3_inigroupgene.getText())))
        		GGList.add(GGList_o.get(i));
        

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
        
        //auto-core
        if(jcheck1.isSelected() && count1==0){
        
        	CyNode centroid = network.addNode();
        	
        	network.getRow(centroid).set(CyNetwork.NAME, jtf1_core.getText());

        	networkView.updateView();        	

        	nodeView = networkView.getNodeView(centroid);

        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, xa);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, xb);        	
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL, jtf1_core.getText());
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_FILL_COLOR, Color.getHSBColor((float )0, (float) 0.4, (float) 0));
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_PAINT, Color.getHSBColor((float )0, (float) 0.4, (float) 0));
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SHAPE, NodeShapeVisualProperty.DIAMOND);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_SIZE, 70.0);
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.WHITE);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 20);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_WIDTH, (double)0);
        	networkView.updateView();

        	for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        		
        		nodeView = networkView.getNodeView(node);
        		if(nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL).compareTo(jtf1_core.getText())==0)
        			continue;

        		network.addEdge(centroid, node, true);
        		networkView.updateView();
        	}

        	count1++;
        	Core_edge = (ArrayList<CyEdge>) network.getAdjacentEdgeList(centroid,CyEdge.Type.ANY);
        	networkView.updateView();

        }
        
        /* Site Circle Unselected */
        layer = (int)Math.sqrt((double)count2);
        layer /= 2;
        if(layer<1)
        	layer=1;
        temp = 0;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", false)){
        	
        	nodeView = networkView.getNodeView(node);
        	if(nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL).compareTo(jtf1_core.getText())==0)
    			continue;
        	
        	m = (temp*360/count2)*Math.PI/180;
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (distance+r*(count1-1)+r*(layer-(temp%layer))*layer)*Math.cos(m)+xa);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (distance+r*(count1-1)+r*(layer-(temp%layer))*layer)*Math.sin(m)+xb);
        	
        	//Site add 
        	Node_x.add(nodeView.getVisualProperty(BasicVisualLexicon.NODE_X_LOCATION));
        	Node_y.add(nodeView.getVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION));

        	double c = 0;
        	String key = Node_class.get((int)temp);        	
        	
        	float c2 = (float) 0.92;
        	float c3 = (float) 0.92;
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
        				edgeView.setLockedValue(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT, Color.getHSBColor((float) ((float)c/((ClassList.size()==0)?1:ClassList.size())), c2, c3));
        				edgeView.setLockedValue(BasicVisualLexicon.EDGE_TRANSPARENCY,200);
        				edgeView.setLockedValue(BasicVisualLexicon.EDGE_LINE_TYPE, LineTypeVisualProperty.SOLID);
        			}

        	nodeView.setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 180);
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
        	if(nodeView.getVisualProperty(BasicVisualLexicon.NODE_LABEL).compareTo(jtf1_core.getText())==0)
    			continue;
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
        	float c2 = (float) 0.92;
        	float c3 = (float) 0.92;
    		if(i==0 && SiteList_count.get(0)>3)
    			c+=0.5;
    		else
    			c=i;
        	nodeView = networkView.getNodeView(centroid);

        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_X_LOCATION, (dis+r*(count1-1)+r*layer*layer)*Math.cos(m)+xa);
        	nodeView.setVisualProperty(BasicVisualLexicon.NODE_Y_LOCATION, (dis+r*(count1-1)+r*layer*layer)*Math.sin(m)+xb);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_HEIGHT, (double)30+layer*2);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_DEPTH, (double)70+layer*2);
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL, GGList.get(i).Function);
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 24+layer);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 0);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, 0);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.getHSBColor((float) ((float)c/ClassList.size()), c2, c3));
            
            networkView.updateView();
        	
        }
        for(int i=0;i<(NanSiteList_count>0?(SiteList_count.size()>0)?1:0:0);i++){
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
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_HEIGHT, (double)30+layer*2);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_DEPTH, (double)70+layer*2);
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL, "other functions");
        	
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_FONT_SIZE, 24+layer);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_TRANSPARENCY, 0);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_BORDER_TRANSPARENCY, 0);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL_COLOR, Color.getHSBColor((float) 0, c2, c3));
        	
        	networkView.updateView();
        }

        

        networkView.updateView();
        

	}

	public void Default(){
		jcb1.setSelectedIndex(5);
		jcb2.setSelectedIndex(12);
		jtf2_pvalue.setText("0.05");
		jtf3_kappa.setText("0.4");
		jl3_inigroupterm.setText("3");
		jtf3_inigroupgene.setText("1");
		jtf1_hashgene.setText("3");
		jtf1_pergene.setText("4");
		js1.setValue(40);
	}
	
	public void ChangeRadio(int a){
		if(a==1)
			jr2.setSelected(false);
		if(a==2)
			jr1.setSelected(false);
	}
	
	public void Export(CySwingAppAdapter adapter) throws IOException{

		FileFactory ff = new FileFactory();

		Vector<String> columnNames = new Vector<String>();
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		JTable t;
		
        final CyApplicationManager manager = adapter.getCyApplicationManager();
        final CyNetworkView networkView = manager.getCurrentNetworkView();
        final CyNetwork network = manager.getCurrentNetwork();
        View<CyNode> nodeView = null;
        View<CyEdge> edgeView = null;
        
        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL, "RRRRRRR");

        }
        
		//t = new JTable(data, columnNames);

		ff.Output();


        for (CyNode node : CyTableUtil.getNodesInState(network, "selected", true)){
        	nodeView = networkView.getNodeView(node);
        	nodeView.setLockedValue(BasicVisualLexicon.NODE_LABEL, "EEEEEEE");

        }
        
        
		//t.setAutoCreateRowSorter(true);

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
	

	public void getNetworkList(CyAppAdapter adapter){
		ArrayList<CyNetwork> netlist;
		ArrayList<String> netNames;
		netlist	= new ArrayList<CyNetwork>();
		netNames = new  ArrayList<String>();
		CyNetworkManager netmanager = adapter.getCyNetworkManager();	
		Set<CyNetwork> setnet = netmanager.getNetworkSet();
		//System.out.println("NETWORKS :"+setnet);
		//creating list of networks out of the set and list of networks' names


		for (CyNetwork net:setnet){
			//System.out.println(net);
			String name =net.getRow(net).get("name", String.class);
			if (!(name.equals("null"))){

				netlist.add(net);
				//System.out.println("sel net" + name);
				netNames.add(name);
			}
		}

	}
	//*****

	//*****

}
