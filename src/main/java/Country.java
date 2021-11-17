import java.util.ArrayList;

public class Country {

    private final String name;
    private Confederation confederation;
    private ArrayList<Team> rivals;

    public Country(String name, ArrayList<Team> rivals) {
        this.name = name;
        this.rivals = rivals;
    }

    public String getName() {
        return name;
    }

}
