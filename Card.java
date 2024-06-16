import java.util.Comparator;

public class Card {
    private String name;
    private String symbol;
    private int type;
    private int number;

    public Card(String name, String symbol, int type, int number) {
        this.name = name;
        this.symbol = symbol;
        this.type = type;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public static Comparator<Card> cardNoComparator = new Comparator<Card>() {
        public int compare(Card s1, Card s2) {
           int rollno1 = s1.getNumber();
           int rollno2 = s2.getNumber();
           return rollno1-rollno2;
        }
    };
    
}