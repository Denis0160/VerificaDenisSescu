import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static tools.Utility.menu;

//per andare nella modalità "Nascosta", nel momento in qui si va a inserire il nome del contatto da cercare
//si da in input la password (1234)

public class Main {
    public static void main(String[] Args) {
        final int max = 4;
        int contN = 0;
        Scanner scanner = new Scanner(System.in);

        //vettore per salvare i contatti in modalità classica
        Contatto[] gestore = new Contatto[max];

        //vettore per salvare i contatti nascosti
        Contatto[] rubricaN = new Contatto[max];

        //Ultime chiamate
        final int maxChiamate = 5;
        String[] chiamate = new String[maxChiamate];


        double saldo = 0;


        String[] opzioni = {"VODAFONE",
                "1]Inserisci",
                "2]visualizza",
                "3]cerca1",
                "4]modifica",
                "5]cancella",
                "6]saldo",
                "7]chiama",
                "8]salva su file",
                "9]visualizza file",
                "10]rendi contatto nascosto/visibile",
                "11]esci"};

        boolean fine = true;
        int cont = 0;

        do {
            switch (menu(opzioni, scanner)) {
                case 1:
                    if (cont < max) {
                        gestore[cont] = inserimento(scanner);
                        cont++;
                    } else
                        System.out.println("Non ci sono piu contratti da vendere\n");
                    break;
                case 2:
                    visualizza(gestore, cont);
                    break;
                case 3:
                    int cercaC = cerca(gestore, cont, contN, scanner, rubricaN, opzioni, max, saldo);
                    if (cercaC != -1 && cercaC != 2) {
                        System.out.println("Il contatto è esistente\n");
                        System.out.println(gestore[cercaC].stampa());
                    } else if (cercaC == 2) {
                        System.out.println("Sei tornato in modalità classica\n");

                    } else
                        System.out.println("il contatto inserito non è presente");
                    break;
                case 4:
                    int modifica = cerca(gestore, cont, contN, scanner, rubricaN, opzioni, max, saldo);
                    modifica(gestore, modifica, scanner);

                    break;
                case 5:
                    int cancellazioneC=cerca(gestore, cont, contN, scanner, rubricaN, opzioni, max, saldo);
                    cancellazione(gestore, cancellazioneC, cont);
                    cont--;
                    break;
                case 6:
                    double ricarica = 0;
                    System.out.println("inserisci l'importo da ricaricare\n");
                    ricarica = scanner.nextDouble();
                    scanner.nextLine();
                    saldo += ricarica;

                    break;
                case 7:
                    System.out.println("Chi vuoi chiamare?\n");
                    int chiama = cerca(gestore, cont, contN, scanner, rubricaN, opzioni, max, saldo);

                    if (chiama != -1 && saldo > 0) {
                        saldo -= 0.5;
                        System.out.println("Chiamata effettuata con successo, il tuo saldo è: " + saldo);
                        ultimeChiamate(chiama, gestore, chiamate, maxChiamate);
                        visualizzaUltimeChiamate(chiamate);
                    } else {
                        if (saldo <= 0) {
                            System.out.println("Saldo insufficiente per effettuare la chiamata.");
                        } else {
                            System.out.println("Impossibile effettuare la chiamata.");
                        }
                    }
                    break;
                case 8:
                    try {
                        ScriviFile("archivio.csv", gestore, cont);
                    } catch (IOException ex) {
                        System.out.println(ex.toString());
                        break;
                    }
                    break;
                case 9:
                    try {
                        int contElementi = LeggiFile("archivio.csv", gestore);
                        visualizza(gestore, cont);
                    } catch (IOException ex) {
                        System.out.println("Errore nella lettura del file: " + ex.toString());
                    }
                    break;

                    //nascondi contatti
                case 10:
                    if(contN>max){
                        System.out.println("Non ce spazio sufficente nella rubrica nascosta, cancella un contatto\n");
                    }
                    else{
                        int nascondi = cerca(gestore, cont, contN, scanner, rubricaN, opzioni, max, saldo);
                        if (nascondi != -1 && nascondi != 2) {
                            System.out.println("Il contatto è esistente\n");
                            rubricaN[contN] = gestore[nascondi];
                            contN++;
                            System.out.println("il conttto è stato nascosto\n");
                            cancellazione(gestore,nascondi,cont);
                            cont--;

                        } else
                            System.out.println("il contatto inserito non è presente");
                    }
                    break;

                default:
                    fine = false;

            }
        } while (fine);

    }

    //metodo per inserire creare i contatti
    private static Contatto inserimento(Scanner scanner) {
        Contatto contatto = new Contatto();
        System.out.println("Inserisci il nome\n");
        contatto.nome = scanner.nextLine();
        System.out.println("Inserisci il cognome\n");
        contatto.cognome = scanner.nextLine();
        System.out.println("Inserisci il numero di telefono\n");
        contatto.telefono = scanner.nextLine();


        String[] scelta = {"TIPO", "1 abitazione", "2 aziendale", "3 cellulare"};

        switch (menu(scelta, scanner)) {
            case 1 -> contatto.tipo = tipoTel.abitazione;
            case 2 -> contatto.tipo = tipoTel.aziendale;
            case 3 -> contatto.tipo = tipoTel.cellulare;
        }

        return contatto;
    }


    //metodo per visualizzare i contatti
    private static void visualizza(Contatto[] gestore, int cont) {
        for (int i = 0; i < cont; i++) {
            System.out.println(gestore[i].stampa());
        }
    }

    //metodo per cercare un contatto, ma se l'utente inserisce la password, allora va in modalità nascosta
    private static int cerca(Contatto[] gestore, int cont, int contN, Scanner scanner, Contatto[] rubricaN, String[] opzioni, int max, double saldo) {
        String[] opzioniN = {"NASCOSTO",
                "1]Inserisci",
                "2]visualizza",
                "3]cerca1",
                "4]modifica",
                "5]cancella",
                "6]saldo",
                "7]chiama",
                "8]salva su file",
                "9]visualizza file",
                "10]rendi contatto nascosto/visibile",
                "11]esci"};
        final int maxChiamateN = 5;
        String[] chiamateN = new String[maxChiamateN];
        System.out.println("Inserisci il nome del contatto che vuoi cercare\n");
        String password = "1234";
        String nome = scanner.nextLine();

        if (nome.equalsIgnoreCase(password)) {
            boolean fine = true;
            do {
                switch (menu(opzioniN, scanner)) {
                    case 1:
                        if (contN < max) {
                            rubricaN[contN] = inserimento(scanner);
                            contN++;
                        } else
                            System.out.println("Non ci sono piu contratti da vendere\n");
                        break;

                    case 2:
                        visualizza(rubricaN, contN);
                        break;
                    case 3:
                        int c = cercaN(rubricaN, contN, scanner);
                        if (c != -1) {
                            System.out.println(rubricaN[c]);
                        } else
                            System.out.println("Non ci sono contatti nascosti a questo nome");
                    case 4:
                        int m = cercaN(rubricaN, contN, scanner);
                        modifica(rubricaN, m, scanner);
                        break;
                    case 5:
                        int v = cercaN(rubricaN, contN, scanner);
                        cancellazione(rubricaN, v, contN);
                        break;
                    case 6:
                        double ricarica = 0;
                        System.out.println("inserisci l'importo da ricaricare\n");
                        ricarica = scanner.nextDouble();
                        scanner.nextLine();
                        saldo += ricarica;

                        break;
                    case 7:
                        int chiama = cercaN(rubricaN, contN, scanner);
                        if (chiama != -1) {
                            System.out.println("Chiamata effettuata con successo\n");
                            saldo = saldo - 0.5;
                            ultimeChiamate(chiama, rubricaN, chiamateN, maxChiamateN);
                            visualizzaUltimeChiamate(chiamateN);

                        } else
                            System.out.println("Contatto non trovata\n");
                        break;
                    case 8:
                        try {
                            ScriviFile("archivio2.csv", rubricaN, contN);
                        } catch (IOException ex) {
                            System.out.println(ex.toString());
                            break;
                        }
                        break;
                    case 9:
                        try {
                            int contElementi = LeggiFile("archivio2.csv", rubricaN);
                            visualizza(rubricaN, contElementi);
                        } catch (IOException ex) {
                            System.out.println("Errore nella lettura del file: " + ex.toString());
                        }
                        break;
                    case 10:
                        if(cont>max){
                            System.out.println("Non ce spazio sufficente nella rubrica nascosta, cancella un contatto\n");
                        }
                        else{
                            int nascondi = cercaN(rubricaN,contN,scanner);
                            if (nascondi != -1) {
                                System.out.println("Il contatto è esistente\n");
                                gestore[cont] = rubricaN[nascondi];
                                cont++;
                                System.out.println("il conttto è stato nascosto\n");
                                cancellazione(rubricaN,nascondi,contN);
                                contN--;

                            } else
                                System.out.println("il contatto inserito non è presente");
                        }
                        break;

                    default:
                        fine = false;
                        return 2;
                }
            } while (fine);
        } else {
            for (int i = 0; i < cont; i++) {
                if (gestore[i].nome.equalsIgnoreCase(nome)) {
                    return i;
                }
            }
        }
        return -1;
    }


    //metodo per cancellare un contatto
    private static void cancellazione(Contatto[] gestore, int cancellzaioneC, int cont) {
        for (int i = cancellzaioneC; i < cont - 1; i++) {
            gestore[i] = gestore[i + 1];
        }
        gestore[cont - 1] = null;
    }

    //metodo per modificare i contatti
    private static void modifica(Contatto[] gestore, int modifica, Scanner scanner) {
        String[] scelta2 = {"Cosa vuoi modificare??", "1]nome", "2]cognome", "3]telefono", "4]tipo contratto"};
        switch (menu(scelta2, scanner)) {
            case 1:
                System.out.println("Inserisci il nome\n");
                String nome = scanner.nextLine();
                gestore[modifica].nome = nome;
                break;
            case 2:
                System.out.println("Inserisci il cognome\n");
                String cognome = scanner.nextLine();
                gestore[modifica].cognome = cognome;
                break;
            case 3:
                System.out.println("Inserisci il numero di telefono\n");
                String tel = scanner.nextLine();
                gestore[modifica].telefono = tel;
                break;
            default:
                String[] tipo = {"TIPO CONTRATTO", "1]abitazione", "2]cellulare", "3]aziendale"};

                switch (menu(tipo, scanner)) {
                    case 1 -> gestore[modifica].tipo = tipoTel.abitazione;
                    case 2 -> gestore[modifica].tipo = tipoTel.cellulare;
                    case 3 -> gestore[modifica].tipo = tipoTel.aziendale;
                }
        }
    }

    //metodo per cercare nei contatti nascosti in base al nome
    private static int cercaN(Contatto[] rubricaN, int contN, Scanner scanner) {
        String nomeN;
        System.out.println("Cerca");
        nomeN = scanner.nextLine();
        for (int i = 0; i < contN; i++) {
            if (rubricaN[i].nome.equalsIgnoreCase(nomeN)) {
                return i;
            }
        }
        return -1;
    }


    public static void ScriviFile(String fileName, Contatto[] gestore, int cont) throws IOException {
        File file = new File(fileName);
        FileWriter writer = new FileWriter(file, true); // true per abilitare la modalità di append
        for (int i = 0; i < cont; i++) {
            writer.write(gestore[i].nome + ", " + gestore[i].cognome + ", " + gestore[i].telefono + ", " + gestore[i].tipo + "\n");
        }
        writer.flush();
        writer.close();
    }

    private static int LeggiFile(String fileName, Contatto[] gestore) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            return 0; // Se il file non esiste, non fare nulla e ritorna 0
        }

        FileReader reader = new FileReader(file);
        Scanner input = new Scanner(reader);
        int contaElementi = 0;

        while (input.hasNextLine() && contaElementi < gestore.length) {
            String lineIn = input.nextLine();
            String[] vetAttributi = lineIn.split(",");

            if (vetAttributi.length >= 4) {  // Assicurarsi che ci siano abbastanza dati
                Contatto persona = new Contatto();
                persona.nome = vetAttributi[0].trim();
                persona.cognome = vetAttributi[1].trim();
                persona.telefono = vetAttributi[2].trim();
                switch (vetAttributi[3].trim()) {
                    case "abitazione":
                        persona.tipo = tipoTel.abitazione;
                        break;
                    case "cellulare":
                        persona.tipo = tipoTel.cellulare;
                        break;
                    case "aziendale":
                        persona.tipo = tipoTel.aziendale;
                        break;
                    default:
                        System.out.println("Tipo contratto sconosciuto: " + vetAttributi[3]);
                        persona.tipo = tipoTel.cellulare;
                        break;
                }
                gestore[contaElementi++] = persona;
            }
        }
        input.close();
        reader.close();
        return contaElementi;
    }

    private static void ultimeChiamate(int indice, Contatto[] gestore, String[] chiamate, int maxChiamate) {
        // Sposta gli elementi per fare spazio per la nuova chiamata
        for (int i = maxChiamate - 1; i > 0; i--) {
            chiamate[i] = chiamate[i - 1];
        }
        // Aggiungi la nuova chiamata nella prima posizione
        chiamate[0] = gestore[indice].stampa();
    }

    // Metodo per visualizzare le ultime chiamate
    private static void visualizzaUltimeChiamate(String[] chiamate) {
        System.out.println("Ultime chiamate:");
        for (int i = 0; i < chiamate.length; i++) {
            if (chiamate[i] != null) {
                System.out.println((i + 1) + "] " + chiamate[i]);
            }
        }
    }
    //sistemare salva file e carica file





}