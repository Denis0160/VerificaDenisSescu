import java.util.Scanner;
import java.util.Random;

public class Esercizii {
    public static void main(String[] Args){
        /*realizzare un programma che dato un vettore di numeri interi random, compresi tra 1 e 20, metta
        i numeri pari in un vettore 'pari' e i numeri dispari in un vettore 'dispari',successivamente faccia la
        somma di tutti i numeri
         */
        Random casuale=new Random();
        int[] rand=new int[5];
        int[] pari=new int[rand.length];
        int[] dispari=new int[rand.length];

        for(int i=0; i< rand.length;i++){
            rand[i]= casuale.nextInt(20)+1;
            System.out.println(rand[i]);

        }
        int indicePari=0;
        for(int i=0;i< pari.length;i++){
            if(rand[i]%2==0){
                pari[indicePari]=rand[i];
                indicePari++;

            }

        }
        System.out.println("numeri pari\n");
        for(int i=0;i<pari.length;i++){
                    if(pari[i]!=0) {
                        System.out.println(pari[i]);
                    }
        }
        int indiceDispari=0;
        for(int i=0;i<dispari.length;i++){
            if(rand[i]%2!=0){
                dispari[indiceDispari]=rand[i];
                indiceDispari++;
            }
        }
        System.out.println("I numeri dispari sono:\n");
        for(int i=0; i<dispari.length;i++){
            if(dispari[i]!=0){
                System.out.println(dispari[i]);
            }
        }

        int somma=0;
        for(int i=0;i<dispari.length;i++){
            somma=somma+dispari[i]+pari[i];
        }
        System.out.println("La somma risulta:\n");
        System.out.println(somma);
    }
}
