import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import Term.*;
public class FileProcess_cmp {
	public static void main(String args[]) throws IOException{
		String line = null;
		Scanner scanner = new Scanner(System.in);
		System.out.print("input file's name : ");
		line=scanner.nextLine();
		InputStream is = FileProcess_cmp.class.getResourceAsStream(line);
		InputStreamReader isr = new InputStreamReader(is); 
		BufferedReader br = new BufferedReader(isr);	
		FileWriter fw = new FileWriter("cmp.txt");
		line = br.readLine();
		line = "";
		String [] str=null;
		int count=0;
		try {
			
			while ((line = br.readLine())!=null) {
				if(count>0)
					fw.write("\r\n");
				str = line.split("\t|\\|");
				String str2="";
				for(int i=1;i<str.length;i++)
				{
					if(str[i].equals(""))
						continue;
					str2+=str[i].toUpperCase();
					if(i<str.length-1)
						str2+=",";
				}
				fw.write(str[0]+"\r\n");
				fw.write(str2);				
				count++;
			}
		} catch (IOException e1) {
		//TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fw.close();
	}

}