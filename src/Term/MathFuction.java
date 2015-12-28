package Term;

import java.util.ArrayList;

public class MathFuction {

 public double FisherExact_getP(int a, int b, int c, int d) {
     int n = a + b + c + d;

     double p=0;
     p = (double)(FisherExact_product(a+b)*FisherExact_product(c+d)*FisherExact_product(a+c)*FisherExact_product(b+d)) / (FisherExact_product(a)*FisherExact_product(b)*FisherExact_product(c)*FisherExact_product(d)*FisherExact_product(n));
     return p;
 }
 public int FisherExact_product(int e){
	 int sum=1;
	 for(int i=1;i<e;i++)
		 sum*=i;
	 return sum;
 }
 public double Kappa_getP(ArrayList<Term> t){
	 int m=0;
	 int s=t.size();
	 int[] kf = new int[s];
	 int[] kg = new int[s];
	 for(int i=0;i<s;i++){
		 for(int j=0;j<s;j++){
			 
			 if(i==j){
				 kf[i]+=t.get(i).Node.size();
				 kg[j]+=t.get(i).Node.size();
				 m+=t.get(i).Node.size();
				 continue;
			 }
			 
			 for(int k=0;k<t.get(i).Node.size();k++){
				 String str=t.get(i).Node.get(k);
				 if(t.get(j).Node.contains(str)){
					 kf[j]++;
					 kg[i]++;
				 }					 
			 }
		 }
	 }
	 int n = Array_Sum(kf);
	 double p0 = (double)m/n;
	 double pc = 0;
	 for(int i=0;i<kf.length;i++)
		 pc+=(double)kf[i]*kg[i]/n;
	 pc/=n;
	 double k = (double)(p0-pc)/(1-pc);
	 
	 return k;
 }
 public int Array_Sum(int[] a){
	 int s=0;
	 for(int i=0;i<a.length;i++)
		 s++;
	 return s;	
 }
 public int[] Leaf_Plus(int []a){
	 int s=Array_Sum(a);
	 if(s>=a.length)
		 return a;
	 a[a.length-1]++;
	 for(int i=a.length-1;i>=0;i--)
		 if(a[i]>1)
			 a[i-1]++;
	 return a;
 }


}
