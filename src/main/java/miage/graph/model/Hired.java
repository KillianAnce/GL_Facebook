package miage.graph.model;


public class Hired extends LinkProperties {

    private String date;

    public Hired(String date){
        super("Hired");
        this.date = date;
    }

    /**
     * @return Date Retourne la date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param d Ajouter/Modifier la date de la propriété
     */
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "hired=" + date;
    }

}