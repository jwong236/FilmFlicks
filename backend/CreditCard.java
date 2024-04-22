import java.util.Date;
public class CreditCard {
    private final String firstName;
    private final String lastName;
    private final String creditCardNumber;
    private final Date expirationDate;

    public CreditCard(String firstName, String lastName, String creditCardNumber, Date expirationDate) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCardNumber = creditCardNumber;
        this.expirationDate = expirationDate;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getLastName(){
        return this.lastName;
    }

    public String getCreditCardNumber(){
        return this.creditCardNumber;
    }

    public Date getExpirationDate(){
        return this.expirationDate;
    }



}
