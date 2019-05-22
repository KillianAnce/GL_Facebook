package miage.graph.model;

public class Role extends LinkProperties {

    private String role;

    public Role(String role){
        super("Role");
        this.role = role;
    }

    /**
     * @return String Retourne le role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role Ajouter/Modifier le role de la propriété
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString(){
        return "role=" + role;
    }

}