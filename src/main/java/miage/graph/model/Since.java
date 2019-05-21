package miage.graph.model;

public class Since extends LinkProperties {

    private String date;

    public Since (String date){
        super("Since");
        this.date = date;
    }

    /**
     * @return int return the date
     */
    public String getdate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setdate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "since=" + date;
    }

}