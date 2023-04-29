
package com.mycompany.labproject1;

import java.util.*;


public class Labproject1 {

    public static void main(String[] args) {
         
          System.out.println("enter numrator of fraction 1 ");
        Scanner accp=new Scanner(System.in);
        int numerator=accp.nextInt();
        System.out.println("enter denominetor of fraction 1 ");
        int denomnetor=accp.nextInt();
        fraction fractionA=new fraction(numerator,denomnetor);
         System.out.println("enter numrator of fraction 2 ");
        int numeratorB=accp.nextInt();
        System.out.println("ente5r denominetor of fraction 2 ");
        int denomnetorB=accp.nextInt(); 
        fraction fractionB=new fraction(numeratorB,denomnetorB);
         fraction obj= new fraction();
         
         
       
       
        System.out.print("Sum" + obj.addition(fractionA, fractionB).simplify().tostring());
         

        
        
        
    
        
   }
}
