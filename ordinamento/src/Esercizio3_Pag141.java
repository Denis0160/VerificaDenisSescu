import java.util.Scanner;
public class Esercizio3_Pag141 {
    private static void main(String[] Args){
        /*
        Scrivi un programma che richiesti in input tre numeri interi
        visualizzi a seconda dei casi una delle seguenti risposte:
        -tutti uguali
        -due uguali e uno diverso
        -tutti e tre diversi
         */
        Scanner input=new Scanner(System.in);
        int num1, num2, num3;
        System.out.println("Inserisci i tre numeri\n");
        num1= input.nextInt();
        num2= input.nextInt();
        num3= input.nextInt();

        if(num1==num2 && num1==num3){
            System.out.println("Tutti uguali\n");
        }else if(num1==num2 || num1!=num3){
            System.out.println("Due uguali e uno diverso\n");

        }else{
            System.out.println("Tutti diversi");
        }

    }
}
