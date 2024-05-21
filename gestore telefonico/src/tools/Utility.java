package tools;

import java.util.Scanner;

public class Utility {
    public static void ClrScr() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void Wait(int x)
    {
        try{
            Thread.sleep(1000*x);
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    public static int menu(String[] opzioni, Scanner keyboard)
    {
        int scelta;

        do {
            ClrScr();
            System.out.println("=== "+opzioni[0]+" ===");
            for(int i=1;i<opzioni.length;i++)
            {
                System.out.println(opzioni[i]);
            }
            scelta = Integer.parseInt(keyboard.nextLine());
            if(scelta<1 || scelta>opzioni.length-1)
            {
                System.out.println("Valore errato. Riprova");
                Wait(2);
            }
        }while(scelta<1 || scelta>opzioni.length-1);

        return scelta;
    }
    public static void quickSortInt(int[] array) {
        // Chiamata al metodo di supporto per eseguire il Quick Sort
        quickSortInt(array, 0, array.length - 1);
    }

    // Metodo di supporto per eseguire il Quick Sort ricorsivamente
    private static void quickSortInt(int[] array, int inizio, int fine) {
        // Se l'indice di inizio è maggiore o uguale all'indice di fine, non c'è nulla da ordinare
        if (inizio >= fine) {
            return;
        }

        // Partiziona l'array e ottiene l'indice del pivot
        int indicePivot = partizionaInt(array, inizio, fine);

        // Richiama ricorsivamente quickSort sulle due metà dell'array
        quickSortInt(array, inizio, indicePivot - 1); // Parte sinistra del pivot
        quickSortInt(array, indicePivot + 1, fine);   // Parte destra del pivot
    }

    // Metodo per partizionare l'array e restituire l'indice del pivot
    private static int partizionaInt(int[] array, int inizio, int fine) {
        // Scegli un elemento pivot, ad esempio l'ultimo elemento dell'array
        int pivot=array[fine];

        // Inizializza l'indice del pivot al punto di inizio
        int indicePivot=inizio;

        // Itera attraverso l'array (escludendo l'ultimo elemento che è il pivot)
        for (int i = inizio; i < fine; i++) {
            // Se l'elemento corrente è minore o uguale al pivot, lo scambia con l'elemento a indicePivot
            if (array[i]<=pivot) {
                scambiaInt(array, i, indicePivot);
                indicePivot++; // Incrementa l'indice del pivot
            }
        }

        // Alla fine, scambia il pivot con l'elemento a indicePivot
        scambiaInt(array, indicePivot, fine);

        // Restituisce l'indice del pivot
        return indicePivot;
    }

    // Metodo per scambiare due elementi in un array
    private static void scambiaInt(int[] array, int i, int j) {
        int temp=array[i];
        array[i]=array[j];
        array[j]=temp;
    }
    public static void quickSortString(String[] array) {
        quickSortString(array, 0, array.length - 1);
    }

    private static void quickSortString(String[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int pivotIndex = partitionString(array, start, end);

        quickSortString(array, start, pivotIndex - 1);
        quickSortString(array, pivotIndex + 1, end);
    }

    private static int partitionString(String[] array, int start, int end) {
        String pivot = array[end];
        int pivotIndex = start;

        for (int i = start; i < end; i++) {
            // Confronta le stringhe utilizzando compareTo
            if (array[i].compareTo(pivot) <= 0) {
                swapString(array, i, pivotIndex);
                pivotIndex++;
            }
        }

        swapString(array, pivotIndex, end);

        return pivotIndex;
    }

    private static void swapString(String[] array, int i, int j) {
        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    public static void quickSort(Comparable[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(Comparable[] array, int start, int end) {
        if (start >= end) {
            return;
        }

        int pivotIndex = partition(array, start, end);

        quickSort(array, start, pivotIndex - 1);
        quickSort(array, pivotIndex + 1, end);
    }

    private static int partition(Comparable[] array, int start, int end) {
        Comparable pivot = array[end];
        int pivotIndex = start;

        for (int i = start; i < end; i++) {
            if (array[i].compareTo(pivot) <= 0) {
                swap(array, i, pivotIndex);
                pivotIndex++;
            }
        }

        swap(array, pivotIndex, end);
        return pivotIndex;
    }

    private static void swap(Comparable[] array, int i, int j) {
        Comparable temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
