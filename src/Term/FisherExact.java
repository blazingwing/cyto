package Term;

public class FisherExact {

 public double getP(int a, int b, int c, int d) {
     int n = a + b + c + d;

     double p;
     p = (double)(product(a+b)*product(c+d)*product(a+c)*product(b+d)) / (product(a)*product(b)*product(c)*product(d)*product(n));
     return p;
 }
 public int product(int e){
	 int sum=1;
	 for(int i=1;i<e;i++)
		 sum*=i;
	 return sum;
 }

}
