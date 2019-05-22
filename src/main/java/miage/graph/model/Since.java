package miage.graph.model;

public class Since extends LinkProperties {

    private String sincedProperty;

    public Since (String date){
        super("Since");
        this.sincedProperty = date;
    }

    /**
     * @return int Retourne la date
     */
    public String getdate() {
        return sincedProperty;
    }

    /**
     * @param date Ajouter/Modifier la date de la propriété
     */
    public void setdate(String date) {
        this.sincedProperty = date;
    }

    @Override
    public String toString(){
        return "since=" + sincedProperty;
    }

}