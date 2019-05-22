package miage.graph.model;

public class Role extends LinkProperties {

    private String roleProperty;

    public Role(String role){
        super("Role");
        this.roleProperty = role;
    }

    /**
     * @return String Retourne le role
     */
    public String getRole() {
        return roleProperty;
    }

    /**
     * @param role Ajouter/Modifier le role de la propriété
     */
    public void setRole(String role) {
        this.roleProperty = role;
    }

    @Override
    public String toString(){
        return "role=" + roleProperty;
    }

}