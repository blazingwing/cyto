package Term;

import java.util.ArrayList;

public class Term {
	
	public Term(){

	}
	
	public String Name=null;
	public String Function=null;
	public ArrayList<String> Node = new ArrayList<String>();
	public double pvalue=0;
	
	public boolean InTerm(String key){
		if(Node.contains(key))
			return true;
		return false;
	}
	
	public int InTermIndex(String key){
		if(Node.contains(key))
			return Node.indexOf(key);
		return -1;
	}
	
	public String Comparison(String key){
		if(Node.contains(key))
			return Name;
		return key;
	}

}
