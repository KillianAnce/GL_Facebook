package miage.graph.model;

public class Since extends LinkProperties {

    private String date;

    public Since (String date){
        super("Since");
        this.date = date;
    }

    /**
     * @return int Retourne la date
     */
    public String getdate() {
        return date;
    }

    /**
     * @param date Ajouter/Modifier la date de la propriété
     */
    public void setdate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "since=" + date;
    }

}