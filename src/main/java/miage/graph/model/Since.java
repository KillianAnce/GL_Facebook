package miage.graph.model;

public class Since extends LinkProperties {

    private int date;

    public Since (int date){
        super("Since");
        this.date = date;
    }

    /**
     * @return int return the date
     */
    public int getdate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setdate(int date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "since=" + date;
    }

}