public class User {
    private String email;
    private String password;

    public User(){
    }
    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    //getters
    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }
}
