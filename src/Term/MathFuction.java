package Term;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MathFuction {

 public double FisherExact_getP(int a, int b, int c, int d) {
     int n = a + b + c + d;

     BigDecimal x=FisherExact_product(a+b).multiply(FisherExact_product(c+d)).multiply(FisherExact_product(a+c)).multiply(FisherExact_product(b+d));
     BigDecimal y=FisherExact_product(a).multiply(FisherExact_product(b)).multiply(FisherExact_product(c)).multiply(FisherExact_product(d)).multiply(FisherExact_product(n));
     BigDecimal p = x.divide(y,16,BigDecimal.ROUND_HALF_UP);

     return p.doubleValue();
 }
 public BigDecimal FisherExact_product(int e){
	 BigDecimal sum=BigDecimal.valueOf(1);
	 for(int i=1;i<=e;i++)
		 sum=sum.multiply(BigDecimal.valueOf(i));	
	 //System.out.println(sum);
	 return sum;
 }
 public double FisherExact_LoggetP(int a, int b, int c, int d) {
     int n = a + b + c + d;
     
     double x = Logsum(a+b)+Logsum(c+d)+Logsum(a+c)+Logsum(b+d);
     //System.out.println(x);
     double y = Logsum(a)+Logsum(b)+Logsum(c)+Logsum(d)+Logsum(n);
     //System.out.println(y);
     double p = Math.pow(10, x-y);
     
     return p;
 }
 public double Logsum(int a){
	 double s=Math.log10(1);
	 for(int i=2;i<=a;i++)
		 s+=Math.log10(i);
	 return s;
 }
 public double Kappa_getP(ArrayList<Term> t){
	 int m=0;
	 int s=t.size();
	 int[] kf = new int[s];
	 int[] kg = new int[s];

	 for(int i=0;i<s;i++){
		 for(int j=i+1;j<s;j++){			 
			 for(int k=0;k<t.get(i).Node.size();k++){
				 String str=t.get(i).Node.get(k);
				 if(t.get(j).Node.contains(str)){
					 kf[i]++;
					 kg[s-j-1]++;
					 m++;
				 }					 
			 }
		 }
	 }
	 for(int i=0;i<s;i++){
		 kf[i]+=t.get(i).Node.size()-m;
		 kg[s-i-1]+=t.get(i).Node.size()-m;
	 }
	 int n = Array_Sum(kf);
	 double p0 = (double)m/n;	 
	 double pc = 0;
	 for(int i=0;i<kf.length;i++)
		 pc+=(double)kf[i]*kg[i]/n;
	 pc/=n;
	 if(pc==1)
		 return p0;
	 double k = (double)(p0-pc)/(1-pc);
	 return k;
 }
 public int Array_Sum(int[] a){
	 int s=0;
	 for(int i=0;i<a.length;i++)
		 if(a[i]>0)
			 s+=a[i];
	 return s;	
 }
 public int[] Leaf_Plus(int []a){
	 int s=Array_Sum(a);
	 if(s>=a.length)
		 return a;
	 a[a.length-1]++;
	 for(int i=a.length-1;i>=0;i--)
		 if(a[i]>1){
			 a[i]=0;
			 a[i-1]++;
		 }
	 return a;
 }
 public Term Min_P(ArrayList<Term> a){
	 Term t = new Term();
	 if(a.size()==0)
		 return t;
	 
	 double p = a.get(0).pvalue;
	 int index = 0;
	 for(int i=1;i<a.size();i++)
		 if(p>a.get(i).pvalue){
			 index=i;
			 p=a.get(i).pvalue;
		 }
	 
	 t = a.get(index);	 
	 return t;
 }
 public String TransCmp(ArrayList<Term> t, String key){
	 String s=key;
 	 for(int i=0;i<t.size();i++)
 	 {
 		 s=t.get(i).Comparison(key);
 		 if(!key.equals(s))
 			 return s;
 	 }
 	 return s;
 }
 public double[] SortTable(double[] d){
	 for(int i=0;i<d.length;i++)
     	for(int j=i+1;j<d.length;j++)
     		if(d[i]>d[j]){
     			double tempd=d[i];
     			d[i]=d[j];
     			d[j]=tempd;
     		} 
	 return d;
 }
 public double FDR(double p, double[] ptable){
	 int r=-1;
	 for(int i=0;i<ptable.length;i++)
		 if(p==ptable[i]){
			 r=i;
			 break;
		 }
	 if(r==-1)
		 return p;
	 p=p*ptable.length/r;
	 return p;
 }
 

}
