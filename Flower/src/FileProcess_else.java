import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Term.Term;
public class FileProcess_else {
	public static void main(String args[]) throws IOException{
		String line = null;
		
		InputStream is = FileProcess_term.class.getResourceAsStream("RNA_expression_1_tur.txt");
		InputStreamReader isr = new InputStreamReader(is); 
		BufferedReader br = new BufferedReader(isr);	
		FileWriter fw = new FileWriter("RNA_expression_1_tur_cmp.txt");

		ArrayList<String> data = new ArrayList<>();
		ArrayList<String> data2 = new ArrayList<>();
		String [] str=null;
		try {
			while ((line = br.readLine())!=null){
				str = line.split("\t|\\|");
				String str2="";
				for(int i=0;i<str.length;i++)
				{						
					if(i==1)
						data.add(str[i]);
					if(i==2)
						data2.add(str[i]);
					//str2+=str[i];
					//if(i<str.length-1)
						//str2+="\t";
				}
				//fw.write(str2+"\r\n");
			}

		} catch (IOException e1) {
		//TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		is = FileProcess_term.class.getResourceAsStream("positive.txt");
		isr = new InputStreamReader(is); 
		BufferedReader br2 = new BufferedReader(isr);
		line = br2.readLine();
		try {
			while ((line = br2.readLine())!=null){
				str = line.split("\t|\\|");
				String str2="";
				for(int i=0;i<data2.size();i++){
					if(str[0].equals(data2.get(i))){
						str2=data.get(i)+"\t"+data2.get(i);
						fw.write(str2+"\r\n");	
						break;
					}
				}							
			}

		} catch (IOException e1) {
		//TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		
		fw.close();
	}

}
