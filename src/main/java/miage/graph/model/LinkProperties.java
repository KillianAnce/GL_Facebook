package miage.graph.model;

public class LinkProperties {
    String value;

    public LinkProperties(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
