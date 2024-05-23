import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static tools.Utility.menu;

public class Main {
    public static void main(String[] Args) {
        final int max = 4;
        int contN = 0;
        Scanner scanner = new Scanner(System.in);

        // Vettore per salvare i contatti in modalità classica
        Contatto[] gestore = new Contatto[max];

        // Vettore per salvare i contatti nascosti
        Contatto[] rubricaN = new Contatto[max];

        // Ultime chiamate
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
                    } else {
                        System.out.println("Non ci sono più contratti da vendere\n");
                    }
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
                    } else {
                        System.out.println("Il contatto inserito non è presente");
                    }
                    break;
                case 4:
                    int modifica = cerca(gestore, cont, contN, scanner, rubricaN, opzioni, max, saldo);
                    modifica(gestore, modifica, scanner);
                    break;
                case 5:
                    int cancellazioneC = cerca(gestore, cont, contN, scanner, rubricaN, opzioni, max, saldo);
                    cancellazione(gestore, cancellazioneC, cont);
                    cont--;
                    break;
                case 6:
                    System.out.println("Inserisci l'importo da ricaricare\n");
                    double ricarica = scanner.nextDouble();
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
                    }
                    break;
                case 9:
                    try {
                        gestore = LeggiNcontatti("archivio.csv");
                        cont = gestore.length;
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                    break;
                case 10:
                    if (contN >= max) {
                        System.out.println("Non c'è spazio sufficiente nella rubrica nascosta, cancella un contatto\n");
                    } else {
                        int nascondi = cerca(gestore, cont, contN, scanner, rubricaN, opzioni, max, saldo);
                        if (nascondi != -1 && nascondi != 2) {
                            System.out.println("Il contatto è esistente\n");
                            rubricaN[contN] = gestore[nascondi];
                            contN++;
                            System.out.println("Il contatto è stato nascosto\n");
                            cancellazione(gestore, nascondi, cont);
                            cont--;
                        } else if (nascondi == 2) {
                            System.out.println("Sei tornato in modalità classica\n");
                        } else {
                            System.out.println("Il contatto inserito non è presente\n");
                        }
                    }
                    break;
                default:
                    fine = false;
            }
        } while (fine);
    }

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

    private static void visualizza(Contatto[] gestore, int cont) {
        for (int i = 0; i < cont; i++) {
            System.out.println(gestore[i].stampa());
        }
    }

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
                        } else {
                            System.out.println("Non ci sono più contratti da vendere\n");
                        }
                        break;
                    case 2:
                        visualizza(rubricaN, contN);
                        break;
                    case 3:
                        int c = cercaN(rubricaN, contN, scanner);
                        if (c != -1) {
                            System.out.println(rubricaN[c].stampa());
                        } else {
                            System.out.println("Non ci sono contatti nascosti a questo nome");
                        }
                        break;
                    case 4:
                        int m = cercaN(rubricaN, contN, scanner);
                        modifica(rubricaN, m, scanner);
                        break;
                    case 5:
                        int v = cercaN(rubricaN, contN, scanner);
                        cancellazione(rubricaN, v, contN);
                        contN--;
                        break;
                    case 6:
                        System.out.println("Inserisci l'importo da ricaricare\n");
                        double ricarica = scanner.nextDouble();
                        scanner.nextLine();
                        saldo += ricarica;
                        break;
                    case 7:
                        int chiama = cercaN(rubricaN, contN, scanner);
                        if (chiama != -1) {
                            System.out.println("Chiamata effettuata con successo\n");
                            saldo -= 0.5;
                            ultimeChiamate(chiama, rubricaN, chiamateN, maxChiamateN);
                            visualizzaUltimeChiamate(chiamateN);
                        }
                        break;
                    case 8:
                        try {
                            ScriviFile("archivio2.csv", rubricaN, contN);
                        } catch (IOException ex) {
                            System.out.println(ex.toString());
                        }
                        break;
                    case 9:
                        try {
                            rubricaN = LeggiNcontatti("archivio2.csv");
                            contN = rubricaN.length;
                        } catch (IOException ex) {
                            System.out.println(ex);
                        }
                        break;
                    case 10:
                        int n = cercaN(rubricaN, contN, scanner);
                        if (n != -1) {
                            if (cont < max) {
                                gestore[cont] = rubricaN[n];
                                cont++;
                                cancellazione(rubricaN, n, contN);
                                contN--;
                            } else {
                                System.out.println("Non c'è spazio sufficiente nella rubrica principale, cancella un contatto\n");
                            }
                        } else {
                            System.out.println("Contatto non trovato");
                        }
                        break;

                    default:
                        fine = false;
                }
            } while (fine);
            return 2;
        }

        for (int i = 0; i < cont; i++) {
            if (nome.equalsIgnoreCase(gestore[i].nome)) {
                return i;
            }
        }
        return -1;
    }

    private static int cercaN(Contatto[] rubricaN, int contN, Scanner scanner) {
        System.out.println("Inserisci il nome del contatto che vuoi cercare\n");
        String nome = scanner.nextLine();
        for (int i = 0; i < contN; i++) {
            if (nome.equalsIgnoreCase(rubricaN[i].nome)) {
                return i;
            }
        }
        return -1;
    }

    private static void modifica(Contatto[] gestore, int modifica, Scanner scanner) {
        if (modifica == -1) {
            System.out.println("Il contatto inserito non è presente\n");
        } else {
            System.out.println("Cosa vuoi modificare?\n");
            String[] menuModifica = {"MODIFICA", "1]nome", "2]cognome", "3]numero", "4]tipo"};
            switch (menu(menuModifica, scanner)) {
                case 1 -> {
                    System.out.println("Inserisci il nome\n");
                    gestore[modifica].nome = scanner.nextLine();
                }
                case 2 -> {
                    System.out.println("Inserisci il cognome\n");
                    gestore[modifica].cognome = scanner.nextLine();
                }
                case 3 -> {
                    System.out.println("Inserisci il numero di telefono\n");
                    gestore[modifica].telefono = scanner.nextLine();
                }
                case 4 -> {
                    String[] scelta = {"TIPO", "1 abitazione", "2 aziendale", "3 cellulare"};
                    switch (menu(scelta, scanner)) {
                        case 1 -> gestore[modifica].tipo = tipoTel.abitazione;
                        case 2 -> gestore[modifica].tipo = tipoTel.aziendale;
                        case 3 -> gestore[modifica].tipo = tipoTel.cellulare;
                    }
                }
            }
        }
    }

    private static void cancellazione(Contatto[] gestore, int cancellazioneC, int cont) {
        if (cancellazioneC != -1) {
            for (int i = cancellazioneC; i < cont - 1; i++) {
                gestore[i] = gestore[i + 1];
            }
            gestore[cont - 1] = null; // Clear the last element after shifting
        }
    }

    public static void ScriviFile(String fileName, Contatto[] gestore, int cont) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        writer.write(cont + "\n");  // Salva il numero di contatti
        for (int i = 0; i < cont; i++) {
            writer.write(gestore[i].nome + "," + gestore[i].cognome + "," + gestore[i].telefono + "," + gestore[i].tipo + "\n");
        }
        writer.flush();
        writer.close();
    }

    private static Contatto[] LeggiNcontatti(String fileName) throws IOException {
        FileReader reader = new FileReader(fileName);
        Scanner input = new Scanner(reader);
        int contaElementi = 0;
        if (input.hasNextLine()) {
            contaElementi = Integer.parseInt(input.nextLine());
        }
        Contatto[] gestore = new Contatto[contaElementi];
        for (int i = 0; i < contaElementi && input.hasNextLine(); i++) {
            String lineIn = input.nextLine();
            String[] vetAttributi = lineIn.split(",");
            Contatto persona = new Contatto();
            persona.nome = vetAttributi[0];
            persona.cognome = vetAttributi[1];
            persona.telefono = vetAttributi[2];
            switch (vetAttributi[3].trim()) {
                case "abitazione" -> persona.tipo = tipoTel.abitazione;
                case "cellulare" -> persona.tipo = tipoTel.cellulare;
                case "aziendale" -> persona.tipo = tipoTel.aziendale;
            }
            gestore[i] = persona;
        }
        input.close();
        return gestore;
    }

    private static void ultimeChiamate(int chiama, Contatto[] gestore, String[] chiamate, int maxChiamate) {
        if (chiamate[maxChiamate - 1] != null) {
            for (int i = 0; i < maxChiamate - 1; i++) {
                chiamate[i]=chiamate[i+1];
            }
        }
        chiamate[maxChiamate-1]=gestore[chiama].stampa();
    }

    private static void visualizzaUltimeChiamate(String[] chiamate) {
        for (String chiamata : chiamate) {
            if (chiamata != null) {
                System.out.println(chiamata);
            }
        }
    }
}
