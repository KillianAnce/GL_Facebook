package miage.graph.model;
import java.util.HashSet;
import java.util.Set;

public class Shared extends LinkProperties {
    
    private Set<String> sharedProperty;
    
    public Shared(){
        super("Shared");
        this.sharedProperty = new HashSet<>();
    }
    
    /**
     * Constructeur utilis� dans la cr�ation d'un graphe � partir
     * du fichier d'import
     * @param shared
     */
    public Shared(Set<String> shared){
        super("Shared");
        this.sharedProperty = shared;
    }

    public Set<String> getShared() {
        return sharedProperty;
    }

    public void setShared(String shared) {
        this.sharedProperty.add(shared);
    }

    @Override
    public String toString(){
        return "shared=" + sharedProperty.toString().replace(",", "+").trim();
    }
}