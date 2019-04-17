package graphe;

public class Role extends LinkProperties {

    private String role;

    public Role(String role){
        super("Role");
        this.role = role;
    }

    /**
     * @return String return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString(){
        return "Role : " + role;
    }

}