enum tipoTel{abitazione,aziendale,cellulare}
public class Contatto {
    public String nome;
    public String cognome;
    public String telefono;

    public tipoTel tipo;




    public String stampa()
    {
        return String.format("Nome: %s Cognome: %s Telefono: %s, tipo: %s", nome, cognome, telefono, tipo.toString());
    }

}
