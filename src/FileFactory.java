import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Term.Term;

public class FileFactory {
	
	public ArrayList<Term> ReadTermTxt(String fstr){
	
		String line = null;
	
		InputStream is = MenuAction.class.getResourceAsStream(fstr);
		InputStreamReader isr = new InputStreamReader(is); 
		BufferedReader br = new BufferedReader(isr);	

		ArrayList<Term> TermList = new ArrayList<Term>();
		Term t_term = new Term();
		String [] str=null;
		try {		
			while ((line = br.readLine())!=null) {
				t_term = new Term();
				str = line.split(";");
				t_term.Name = str[0];
				t_term.Function = str[1];
				t_term.level_min = Integer.parseInt(str[2]);
				t_term.level_max = Integer.parseInt(str[3]);
				
				line = br.readLine();
				str = line.split(";");
				for(int i=0;i<str.length;i++)
					t_term.Node.add(str[i]);
				TermList.add(t_term);
			}
		} catch (IOException e1) {
		//TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return TermList;
	}
	public ArrayList<Term> ReadCmpTxt(String fstr){
		
		String line = null;
	
		InputStream is = MenuAction.class.getResourceAsStream(fstr);
		InputStreamReader isr = new InputStreamReader(is); 
		BufferedReader br = new BufferedReader(isr);	

		ArrayList<Term> CmpList = new ArrayList<Term>();
		Term t_term = new Term();
		String [] str=null;
		try {
		
			while ((line = br.readLine())!=null) {
				t_term = new Term();
				t_term.Name = line;
				line = br.readLine();
				str = line.split(",");
				for(int i=0;i<str.length;i++)
					t_term.Node.add(str[i]);
				CmpList.add(t_term);
			}
		} catch (IOException e1) {
		//TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return CmpList;
	}
	public ArrayList<String> ReadTestTxt(String fstr){
		
		String line = null;
	
		InputStream is = MenuAction.class.getResourceAsStream(fstr);
		InputStreamReader isr = new InputStreamReader(is); 
		BufferedReader br = new BufferedReader(isr);	

		ArrayList<String> NodeList = new ArrayList<String>();
		String [] str=null;
		try {
		
			while ((line = br.readLine())!=null) {
				str = line.split("\t");

				if(!str[0].equals("CORE")&&!str[0].equals("PPI"))
					if(!NodeList.contains(str[0]))
						NodeList.add(str[0]);
				if(!NodeList.contains(str[1]))
					NodeList.add(str[1]);
			}
		} catch (IOException e1) {
		//TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return NodeList;
	}
	public double[][] ReadKappaTxt(String fstr, int size){
		
		String line = null;
		
		InputStream is = MenuAction.class.getResourceAsStream(fstr);
		InputStreamReader isr = new InputStreamReader(is); 
		BufferedReader br = new BufferedReader(isr);	
		
		double[][] g = new double[size][size];
		System.out.println(size);
		String [] str=null;
		int i=0;
		try {			
			while ((line = br.readLine())!=null) {
				System.out.println("kappa " + i);				
				str = line.split(",");
				System.out.println(str.length);
				g[i][i]=1;
				for(int j=0;j<str.length;j++){
					g[i][i+j+1]=Double.parseDouble(str[j]);
					g[i+j+1][i]=g[i][i+j+1];
				}				
				i++;
				if(i>=size)
					break;
			}
		} catch (IOException e1) {
		//TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		return g;
	}
	
	public double GetKappaTxt(String fstr,int r, int c){
		double k=0;
		int i = 0;
		
		if(r==c)
			return 1;
		if(r>c){
			int temp=r;
			r=c;
			c=temp;
		}	
		
		String line = null;
		
		InputStream is = MenuAction.class.getResourceAsStream(fstr);
		InputStreamReader isr = new InputStreamReader(is); 
		BufferedReader br = new BufferedReader(isr);
		
		try {
			line = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		i++;
		
		for(;line!=null;i++) {
			
			if(i!=r)
				continue;
			String [] str = line.split(",");
			
			return Double.parseDouble(str[c-r-1]);
		}		
		
		return k;
	}
}
