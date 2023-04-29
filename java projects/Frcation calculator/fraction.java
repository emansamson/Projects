
package com.mycompany.labproject1;



public class fraction {
    private int num;
    private int denm;

    fraction(){}

    fraction(int x,int y)
    {
    num=x;
    denm=y;
    }
    public void setnum(int num){
        this.num=num;

    }
     public void setdenum(int denm){
        this.denm=denm;
       }
    public int getnum(){
        return num;
       }
    public int getdenm(){
        return denm;
    }

  int gcd()
           {

              int n=getnum();
              int d=getdenm();

           int gcd=0, greater;
          if(n<d)
            greater=n;
             else
              greater=d;


      for (int i=1;i<=greater;i++)
      {
          if(n%i==0 && d%i==0)
            gcd=i;

     }

      return gcd;

           }
  public String tostring(){
      return getnum()+ "/" +getdenm();
  }

fraction simplify() {
     int m=gcd();
  fraction obj=new fraction(num/m,denm/m);

    return obj;
}

public fraction addition(fraction num1,fraction num2){
    fraction sum=new fraction((num1.num*num2.denm) +(num1.denm*num2.num),num1.denm*num2.denm);
    return sum;
}

public fraction subtraction(fraction num1,fraction num2){
    fraction result=new fraction((num1.num*num2.denm) -(num1.denm*num2.num),num1.denm*num2.denm);
    return result;
}
public fraction multiplication(fraction num1,fraction num2){
    fraction product=new fraction(num1.num*num2.num ,num1.denm*num2.denm);
    return product;
}

fraction division(fraction num1,fraction num2){
    fraction result=new fraction(num1.num*num2.denm ,num1.denm*num2.num);
    return result;
}



}


