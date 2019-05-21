package miage.graph.model;


public class Hired extends LinkProperties {

    private String date;

    public Hired(String date){
        super("Hired");
        this.date = date;
    }

    /**
     * @return Date return the d
     */
    public String getDate() {
        return date;
    }

    /**
     * @param d the d to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "hired=" + date;
    }

}