package graphe;
import java.util.Date;

public class Hired extends LinkProperties {

    private Date d;

    public Hired(Date d){
        super("Hired");
        this.d = d;
    }

    /**
     * @return Date return the d
     */
    public Date getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(Date d) {
        this.d = d;
    }

}