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
				t_term.Name = line;
				line = br.readLine();
				str = line.split(",");
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
	
}
