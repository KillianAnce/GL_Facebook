package miage.graph.model;


public class Hired extends LinkProperties {

    private String hiredPropery;

    public Hired(String date){
        super("Hired");
        this.hiredPropery = date;
    }

    /**
     * @return Date Retourne la date
     */
    public String getDate() {
        return hiredPropery;
    }

    /**
     * @param d Ajouter/Modifier la date de la propriété
     */
    public void setDate(String date) {
        this.hiredPropery = date;
    }

    @Override
    public String toString(){
        return "hired=" + hiredPropery;
    }

}