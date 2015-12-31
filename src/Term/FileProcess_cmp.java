package Term;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.l2fprod.common.demo.Main;

import Term.Term;
public class FileProcess_cmp {
	public static void main(String args[]) throws IOException{
		String line = null;
		
		InputStream is = FileProcess_cmp.class.getResourceAsStream("xc.txt");
		InputStreamReader isr = new InputStreamReader(is); 
		BufferedReader br = new BufferedReader(isr);	
		FileWriter fw = new FileWriter("cmp.txt");

		String [] str=null;
		try {
			
			while ((line = br.readLine())!=null) {
				str = line.split("\t|\\|");
				String str2="";
				for(int i=1;i<str.length;i++)
				{
					str2+=str[i];
					if(i<str.length-1)
						str2+=",";
				}
				fw.write(str[0]+"\r\n");
				fw.write(str2+"\r\n");
				
				
			}
		} catch (IOException e1) {
		//TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fw.close();
	}

}
