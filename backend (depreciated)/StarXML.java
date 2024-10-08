import java.util.UUID;

public class StarXML {

    private final String name;
    private final int birthYear;
    private String id;

    public StarXML(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;

        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();;

    }

    public String getName(){
        return this.name;
    }

    public int getBirthYear(){
        return this.birthYear;
    }

    public String getId(){
        return this.id;
    }



    public String toString() {

        return "Name:" + getName() + ", " +
                "ID:" + getId() + ", " +
                "Year:" + String.valueOf(getBirthYear());
    }
}