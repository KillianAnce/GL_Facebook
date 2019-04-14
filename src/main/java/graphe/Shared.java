import java.util.HashSet;
import java.util.Set;

public class Shared extends LinkProperties {
    
    private Set<String> shared;
    
    public Shared(Set<String> shared){
        super("Shared");
        this.shared = shared;
    }

    /**
     * @return Set<String> return the shared
     */
    public Set<String> getShared() {
        return shared;
    }

    /**
     * @param shared the shared to set
     */
    public void setShared(String shared) {
        this.shared.add(shared);
    }

    @Override
    public String toString(){
        return "Shared : " + shared;
    }
}