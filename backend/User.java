public class User {
    private String email;
    private String password;

    private String recaptchaValue;


    public User(){
    }
    public User(String email, String password, String recaptchaValue){
        this.email = email;
        this.password = password;
        this.recaptchaValue = recaptchaValue;
    }

    //getters
    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public String getRecaptchaValue(){
        return this.recaptchaValue;
    }
}
