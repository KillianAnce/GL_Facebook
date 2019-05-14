package graphe;

public class Since extends LinkProperties {

    private int value;

    public Since (int value){
        super("Since");
        this.value = value;
    }

    /**
     * @return int return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return "since=" + value;
    }

}