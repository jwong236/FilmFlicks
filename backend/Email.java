public class Email {
    private final String email;
    private final String customerId;

    public Email(String customerId, String email) {
        this.customerId = customerId;
        this.email = email;
    }

    public String customerIdGetter(){
        return this.customerId;
    }
    public String emailGetter(){
        return this.email;
    }
}
