package miage.graph.model;
import java.util.HashSet;
import java.util.Set;

public class Shared extends LinkProperties {
    
    private Set<String> shared;
    
    public Shared(){
        super("Shared");
        this.shared = new HashSet<>();
    }
    
    public Shared(Set<String> shared){
        super("Shared");
        this.shared = shared;
    }

    public Set<String> getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared.add(shared);
    }

    @Override
    public String toString(){
        return "shared=" + shared.toString().replace(",", "+").trim();
    }
}