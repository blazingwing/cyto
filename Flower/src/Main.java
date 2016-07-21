import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

import Term.*;

public class Main {
	
	public static void main(String args[]) throws IOException{
		
		ArrayList<Term> TermList = new ArrayList<Term>();
		ArrayList<Term> TermList_o = new ArrayList<Term>();
        ArrayList<Term> TermList_oo = new ArrayList<Term>();
		ArrayList<Integer> TermList_index = new ArrayList<Integer>();
        ArrayList<Term> CmpTermList = new ArrayList<Term>();
        ArrayList<Term> GroupGeneList = new ArrayList<Term>();
        ArrayList<Term> CmpTermList2 = new ArrayList<Term>();
        ArrayList<Term> GroupTermList = new ArrayList<Term>();
        ArrayList<Term> GGList = new ArrayList<Term>();
        ArrayList<Term> GGList_o = new ArrayList<Term>();
        
        ArrayList<String> ClassList = new ArrayList<String>();
        ArrayList<String> Node_class = new ArrayList<String>();
        ArrayList<String> Node_name2 = new ArrayList<String>();
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
        

		FileWriter fwt = new FileWriter("answer_t.txt");
		FileWriter fwg = new FileWriter("answer_g.txt");
		FileWriter fwl = new FileWriter("answer_list.txt");
        
        
        MathFuction mf = new MathFuction();

        System.out.println("fff");
        	FileFactory ff = new FileFactory();
        	TermList_oo=ff.ReadTermTxt("homo_bp.txt");
        	/*for(int i=0;i<TermList.size();i++){
        		System.out.println(TermList.get(i).Name);
        		for(int j=0;j<TermList.get(i).Node.size();j++)
        			System.out.println(TermList.get(i).Node.get(j));
        	}*/
        	System.out.println("ff term");
        	CmpTermList=ff.ReadCmpTxt("homo_cmp.txt");
        	CmpTermList2=ff.ReadCmpTxt("homo_cmp2.txt");
        	/*for(int i=0;i<CmpTermList.size();i++){
        		System.out.println(CmpTermList.get(i).Name);
        		for(int j=0;j<CmpTermList.get(i).Node.size();j++)
        			System.out.println(CmpTermList.get(i).Node.get(j));
        	}*/
        	System.out.println("ff cmp");
        	Node_name2=ff.ReadTestTxt("test.txt");
        	
        	/*for(int i=0;i<Node_name2.size();i++){
        		System.out.println(Node_name2.get(i));
        	}*/
        	System.out.println("ff test");
        	ArrayList<String> Node_name= new ArrayList<String>();
        	
        	for(int i=0;i<Node_name2.size();i++){
        		String name = Node_name2.get(i);
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
        		//System.out.println(key2);
        	}  
        	
        	System.out.println("term_oo to term_o");
            for(int i=0;i<TermList_oo.size();i++){
        		int a=0;
        		for(int j=0;j<Node_name.size();j++)
            		if(TermList_oo.get(i).Node.contains(Node_name.get(j)))
            			a++;
        		if(a>=3 && (100*a/TermList_oo.get(i).Node.size())>=4){
        			if(mf.LevelCheck(6, 13, TermList_oo.get(i).level_min,TermList_oo.get(i).level_max)){
        					TermList_o.add(TermList_oo.get(i));
        			}
        		}
        	}
            
            for(int i=0;i<TermList_o.size();i++)
                ref_count+=TermList_o.get(i).Node.size();
            
            System.out.println("term_o to term p-value");
            for(int i=0;i<TermList_o.size();i++)
            {
            	String str="";
            	String str2="";
            	int a=0,b=0,c=0,d=0;
            	for(int j=0;j<Node_name.size();j++)
            		if(TermList_o.get(i).Node.contains(Node_name.get(j)))
            		{
            			str+=(Node_name.get(j)+",");
            			a++;
            		}
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
            	str2=String.valueOf(100*a/count2);
            	if(TermList_o.get(i).pvalue <= 0.05)
            	{
            		fwt.write(TermList_o.get(i).Function + "\r\n" + "#=" + a + "  %=" + str2 + "\r\n["  + str + "]\r\np-value = " + TermList_o.get(i).pvalue + "\r\n\r\n");
            		TermList.add(TermList_o.get(i));
            	}
            }
            
            //**********//
            
            ref_count = 0;
            for(int i=0;i<TermList.size();i++)
                ref_count+=TermList.get(i).Node.size();
            
            System.out.println("q-value");
            double[] TermpvalueTable = new double[TermList.size()];
            TermpvalueTable=mf.SortTable(TermpvalueTable);
            for(int i=0;i<TermList.size();i++)
            	TermList.get(i).qvalue=mf.FDR(TermList.get(i).pvalue, TermpvalueTable);
            
            
          //-----Term grouping Kappa p-value---
            
            System.out.println("kappa");
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
 
            System.out.println("grouping connect");
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
            		if(GroupKappaTable[i][j]>=0.4){
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
            
            System.out.println("group size filter");
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
            System.out.println("group p-value");
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
            System.out.println("GGlist");
            for(int i=0;i<Node_name.size();i++)
            {
            	ArrayList<Term> t = new ArrayList<Term>();
            	ArrayList<Term> g = new ArrayList<Term>();
            	
            	fwl.write(Node_name2.get(i)+"\t");            	
            	
            	for(int j=0;j<TermList.size();j++)
            		if(TermList.get(j).Node.contains(Node_name.get(i)))
            			t.add(TermList.get(j));
            	if(t.size()==0){
            		fwl.write("un-significant term\r\n");
            		continue;
            	}
            	Term tempt = mf.Min_Q(t);
            	fwl.write(tempt.Function+"\t"+tempt.qvalue+"\t");
            	
            	
            	for(int j=0;j<GroupTermList.size();j++)
            		if(GroupTermList.get(j).Node.contains(tempt.Name))
            			g.add(GroupTermList.get(j));
            	if(g.size()==0){
            		fwl.write("un-significant group\r\n");
            		continue;            		
            	}
            	Term tempg = mf.Min_Q(g);
            	fwl.write(tempg.Name+"\t"+tempg.qvalue+"\r\n");
            	
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
            	if(!(GGList_o.get(i).Node.size()<1))
            		GGList.add(GGList_o.get(i));
        	
        	fwt.close();
        	fwg.close();
        	fwl.close();
        	
        	
		}	

}
