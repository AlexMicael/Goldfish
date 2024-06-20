import java.util.*;

public class Goldfish {
    public static ArrayList<Card> jokers;
    private static ArrayList<Card> playerCards;
    private static ArrayList<Card> cpuCards;
    private static ArrayList<Integer> playerFish = new ArrayList<Integer>();
    private static ArrayList<Integer> cpuFish = new ArrayList<Integer>();
    private static Card[][] deck;
    private static String name;

    public static void main(String[] args) {
        // System.out.println("░██████╗░░█████╗░  ███████╗██╗░██████╗██╗░░██╗██╗\n██╔════╝░██╔══██╗  ██╔════╝██║██╔════╝██║░░██║██║\n██║░░██╗░██║░░██║  █████╗░░██║╚█████╗░███████║██║\n██║░░╚██╗██║░░██║  ██╔══╝░░██║░╚═══██╗██╔══██║╚═╝\n╚██████╔╝╚█████╔╝  ██║░░░░░██║██████╔╝██║░░██║██╗\n░╚═════╝░░╚════╝░  ╚═╝░░░░░╚═╝╚═════╝░╚═╝░░╚═╝╚═╝");
        // System.out.println();
        // title();
        slowPrint("Goldfish (Go Fish: Java Edition) - by Alex Hsieh");
        Scanner input = new Scanner(System.in);
        slowPrint("\n\u001B[32m[System]\u001B[0m" + " Hello, What is your name?\n\u001B[0m");
        name = input.nextLine();
        setUpDeck();
        cpuCards = setUpHand();
        playerCards = setUpHand();
        //Card temp = new Card("Temp", "Temp", -1, -1);
        //playerCards.add(temp);
        //playerCards.add(temp);
        //playerCards.add(temp);
        //playerCards.add(temp);
        //cpuCards.add(temp);
        //cpuCards.add(temp);
        //cpuCards.add(temp);
        //cpuCards.add(temp);
        trade();
        input.close();
    }

    public static void countFish(ArrayList<Card> c, boolean isPlayer) {
        deckArrange(c);
        int previous = -1;
        int count = 0;
        int fish = -1;
        for (int i = 0; i < c.size(); i++) {
            if (previous == c.get(i).getNumber()) {
                count++;
            } else {
                count = 1;
            }
            previous = c.get(i).getNumber();
            if (count == 4) {
                fish = c.get(i).getNumber();
                if (isPlayer) {
                    playerFish.add(c.get(i).getNumber());
                }
                if (!isPlayer) {
                    cpuFish.add(c.get(i).getNumber());
                }
                for (int f = 0; f < c.size(); f++) {
                    if (c.get(f).getNumber() == fish) {
                        c.remove(f);
                        f--;
                    } 
                }
                if (isPlayer) {
                    slowPrint("\n" + "\u001B[34m[" + name + "]\u001B[0m" +" Fish!");
                    slowPrint("\n\u001B[32m[System]\u001B[0m" + " You Now Have a Set of " + (fish+1));
                    slowPrint("\n\u001B[32m[System]\u001B[0m" + " You May Go Again");
                    trade();
                } else {
                    slowPrint("\n\u001B[31m[Mysterious Person]\u001B[0m" + " Now Has Set of " + (fish+1));
                    slowPrint("\n\u001B[32m[System]\u001B[0m" + " They'll Go Again");
                    cpuTrade();
                }
            }
        }
    }

    public static void printCards() {
        int i = 0;
        for (int r = 0; r < playerCards.size()/4; r++) {
            String str1 = "[" + (i+1) + "] " + playerCards.get(i).getSymbol() + " " + playerCards.get(i).getName();
            String str2 = "[" + (i+2) + "] " + playerCards.get(i+1).getSymbol() + " " + playerCards.get(i+1).getName();
            String str3 = "[" + (i+3) + "] " + playerCards.get(i+2).getSymbol() + " " + playerCards.get(i+2).getName();
            String str4 = "[" + (i+4) + "] " + playerCards.get(i+3).getSymbol() + " " + playerCards.get(i+3).getName();
            System.out.printf("%-25s %-25s %-25s %-25s\n", str1, str2, str3, str4);
            i = i+4;
        }
        if (playerCards.size() % 4 != 0) {
            if (playerCards.size() % 4 == 1) {
                System.out.println("[" + (i+1) + "] " + playerCards.get(i).getSymbol() + " " + playerCards.get(i).getName());
            }
            if (playerCards.size() % 4 == 2) {
                System.out.printf("%-25s %-25s\n", "[" + (i+1) + "] " + playerCards.get(i).getSymbol() + " " + playerCards.get(i).getName(), "[" + (i+2) + "] " + playerCards.get(i+1).getSymbol() + " " + playerCards.get(i+1).getName());
            }
            if (playerCards.size() % 4 == 3) {
                System.out.printf("%-25s %-25s %-25s\n", "[" + (i+1) + "] " + playerCards.get(i).getSymbol() + " " + playerCards.get(i).getName(), "[" + (i+2) + "] " + playerCards.get(i+1).getSymbol() + " " + playerCards.get(i+1).getName(), "[" + (i+3) + "] " + playerCards.get(i+2).getSymbol() + " " + playerCards.get(i+2).getName());
            }
        }
    }

    public static int getDeckSize() {
        int nullCards = 0;
        for (int row = 0; row < deck.length; row++) {
            for (int col = 0; col < deck[row].length; col++) {
               if (deck[row][col] == null) {
                    nullCards++;
               }
            }
        }
        return (52-nullCards);
    }

    public static double getDeckValue() {
        double sum = 0.0;
        for(int o = 0; o < deck[0].length; o++) {
            for(int i = 0; i < deck.length; i++) {
                sum += deck[i][o].getNumber();
            }
        }
        return sum;
    }

    public static double getDeckAvg() {
        double sum = 0.0;
        if (getDeckSize() == 52) {
            for (Card[] c : deck) {
                for (Card c2 : c) {
                    sum += c2.getNumber();
                }
            }
            return sum/getDeckSize();
        } else {
            return -1.0;
        }
    }

    public static void trade() {
        if (playerCards.size() == 0) {
            end();
            cpuTrade();
        }
        countFish(playerCards, true);
        System.out.println("\n_____________________________________________________________________________________________________");
        System.out.println("\nCard Deck: " + getDeckSize());
        System.out.println("Opponent's Deck: " + cpuCards.size());
        deckArrange(cpuCards);
        deckArrange(playerCards);
        if (cpuFish.size() != 0) {
            System.out.println("Opponent's Set(s) of 4: ");
            for (int i: cpuFish) {
                System.out.println( (i+1) + " ♠♥♦♣");
            }
        }
        if (playerFish.size() != 0) {
            System.out.println("Your Set(s) of 4: ");
            for (int i: playerFish) {
                System.out.println( (i+1) + " ♠♥♦♣");
            }
        }
        System.out.println( "Your Deck:");
        printCards();
        System.out.println("\n_____________________________________________________________________________________________________");
        Scanner input = new Scanner(System.in);
        slowPrint("\n\u001B[32m[System]\u001B[0m" + " Which Card Would You Like to Play:");
        int fishCard = 0;
        try {
            fishCard = input.nextInt() - 1;
        } catch(Exception e) {
            slowPrint("\n\u001B[32m[System]\u001B[0m" + " Please Input a Number Between " + 1 + " and " + playerCards.size());
            trade();
        }
        if ((fishCard >= playerCards.size()) || (fishCard < 0)) {
            slowPrint("\n\u001B[32m[System]\u001B[0m" + " Please Input a Number Between " + 1 + " and " + playerCards.size());
            trade();
        }
        slowPrint("\n" + "\u001B[34m[" + name + "]\u001B[0m" +" Do you have a(n) " + (playerCards.get(fishCard).getNumber() + 1) + "?");
        int cpuYes = 0;
        for (int i = 0; i < cpuCards.size(); i++) {
            if (cpuCards.get(i).getNumber() == playerCards.get(fishCard).getNumber()) {
                slowPrint("\u001B[31m[Mysterious Person]\u001B[0m Handed you a(n) " + cpuCards.get(i).getSymbol() + cpuCards.get(i).getName());
                playerCards.add(cpuCards.get(i));
                cpuCards.remove(i);
                i--;
                cpuYes++;
            }
        }
        countFish(playerCards, true);
        if (cpuYes == 0) {
            slowPrint("\u001B[31m[Mysterious Person]\u001B[0m Go Fish!");
            goFish(playerCards, true, playerCards.get(fishCard));
            cpuTrade();
        }
        if (cpuYes > 0) {
            countFish(playerCards, true);
            slowPrint("\n\u001B[32m[System]\u001B[0m" + " You May Go Again");
            trade();
        }
        input.close();
    }

    public static void cpuTrade() {
        if (cpuCards.size() == 0) {
            goFish(cpuCards, false);
            end();
            trade();
            cpuTrade();
        } else {
            int rand = (int)(Math.random() * cpuCards.size()-1);
            slowPrint("\n\u001B[31m[Mysterious Person]\u001B[0m Do you have a(n) " + (cpuCards.get(rand).getNumber()+1) + "?");
            int playerYes = 0;
            for (int i = 0; i < playerCards.size(); i++) {
                if(playerCards.get(i).getNumber() == cpuCards.get(rand).getNumber()) {
                    cpuCards.add(playerCards.get(i));
                    slowPrint("\u001B[34m[" + name + "]\u001B[0m"+ " You Handed Over Your " + playerCards.get(i).getSymbol() + playerCards.get(i).getName() );
                    playerCards.remove(i);
                    playerYes++;
                    i--;
                }
            }
            countFish(cpuCards, false);
            if (playerYes > 0) {
                cpuTrade();
            } else {
                slowPrint("\u001B[34m[" + name + "]\u001B[0m" + " Go Fish!");
                goFish(cpuCards, false, cpuCards.get(rand));
                trade();
            }
        }
    }

    public static void goFish(ArrayList<Card> a, boolean isPlayer, Card askedCard) {
        if (getDeckSize() == 0) {
            slowPrint("\n\u001B[32m[System]\u001B[0m" + " The Deck is Empty!");
            if (a.size() == 0) {
                if (isPlayer) {
                    trade();
                } else {
                    cpuTrade();
                }
            }
        }
        for (int i = 0; i < 1; i++) {
            int rand = (int)(Math.random() * 4);
            int rand2 = (int)(Math.random() * 13);
            if (deck[rand2][rand] == null) {
                i--;
            } else {
                if (isPlayer) {
                    slowPrint("\nYou Got a(n) " + deck[rand2][rand].getSymbol() + deck[rand2][rand].getName());
                }
                if (!isPlayer) {
                    slowPrint("\n\u001B[31m[Mysterious Person]\u001B[0m Received a Card from the Deck");
                }
                a.add(deck[rand2][rand]);
                Card tempCard = deck[rand2][rand];
                deck[rand2][rand] = null;
                if (isPlayer) {
                    countFish(playerCards, true);
                } else {
                    countFish(cpuCards, false);
                }
                if (tempCard.getNumber() == askedCard.getNumber()) {
                    if (isPlayer) {
                        slowPrint("\n\u001B[32m[System]\u001B[0m" + " You May Go Again");
                        trade();
                    }
                    if (!isPlayer) {
                        slowPrint("\u001B[31m[Mysterious Person]\u001B[0m Got a(n) " +  tempCard.getSymbol() + tempCard.getName());
                        slowPrint("\n\u001B[32m[System]\u001B[0m" + " They'll Go Again");
                        cpuTrade();
                    }
                }
                if (playerCards.size() == 0 && cpuCards.size() == 0) {
                    end();
                }
            }
        }
    }

    public static void goFish(ArrayList<Card> a, boolean isPlayer) {
        if (getDeckSize() == 0) {
            slowPrint("\n\u001B[32m[System]\u001B[0m" + " The Deck is Empty!");
        }
        for (int i = 0; i < 1; i++) {
            int rand = (int)(Math.random() * 4);
            int rand2 = (int)(Math.random() * 13);
            if (deck[rand2][rand] == null) {
                i--;
            } else {
                if (isPlayer) {
                    slowPrint("\nYou Got a(n) " + deck[rand2][rand].getSymbol() + deck[rand2][rand].getName());
                }
                if (!isPlayer) {
                    slowPrint("\n\u001B[31m[Mysterious Person]\u001B[0m Received a Card from the Deck");
                }
                a.add(deck[rand2][rand]);
                deck[rand2][rand] = null;
                if (isPlayer) {
                    countFish(playerCards, true);
                } else {
                    countFish(cpuCards, false);
                }
                if (playerCards.size() == 0 && cpuCards.size() == 0) {
                    end();
                }
            }
        }
    }

    public static void end() {
        boolean extraAddon= false;
        if (cpuCards.size() == 0 && playerCards.size() == 0) {
            slowPrint("\n\u001B[32m[System]\u001B[0m" + " The Game is Officially Over!");
            if (cpuFish.size() != 0) {
                System.out.println("\nOpponent's Set(s) of 4: ");
                for (int i: cpuFish) {
                    System.out.println( (i+1) + " ♠♥♦♣");
                }
            }
            if (playerFish.size() != 0) {
                System.out.println("\nYour Set(s) of 4: ");
                for (int i: playerFish) {
                    System.out.println( (i+1) + " ♠♥♦♣");
                }
            }
            if (cpuFish.size() > playerFish.size()) {
                slowPrint("\nWith " + cpuFish.size() + " Sets, Compared to " + playerFish.size() + ", Mysterious Person has Won this Game of Go Fish!");
            }
            if (cpuFish.size() < playerFish.size()) {
                slowPrint("\nWith " + playerFish.size() + " Sets, Compared to " + cpuFish.size() + ", " + name + " has Won this Game of Go Fish!");
            }
            if (cpuFish.size() == playerFish.size()) {
                slowPrint("\nWith Both Sides Having" + playerFish.size() + " Sets, this Game is a Tie");
            }
            if (extraAddon) {
                extraEnd();
            }
            System.exit(0);
        }
    }

    public static ArrayList<Card> setUpHand() {
        ArrayList<Card> tempCards = new ArrayList<Card>();
        for (int i = 0; i < 4; i++) {
            int rand = (int)(Math.random() * 4);
            int rand2 = (int)(Math.random() * 13);
            if (deck[rand2][rand] == null) {
                i--;
            } else {
                tempCards.add(deck[rand2][rand]);
                deck[rand2][rand] = null;
            }
        }
        return tempCards;
    }

    public static void setUpDeck() {
        Card SpadeAce = new Card("Ace Of Spades", "🂡", 0, 0);
        Card SpadeTwo = new Card("Two Of Spades", "🂢", 0, 1);
        Card SpadeThree = new Card("Three Of Spades", "🂣", 0, 2);
        Card SpadeFour = new Card("Four Of Spades", "🂤", 0, 3);
        Card SpadeFive = new Card("Five Of Spades", "🂥", 0, 4);
        Card SpadeSix = new Card("Six Of Spades", "🂦", 0, 5);
        Card SpadeSeven = new Card("Seven Of Spades", "🂧", 0, 6);
        Card SpadeEight = new Card("Eight Of Spades", "🂨", 0, 7);
        Card SpadeNine = new Card("Nine Of Spades", "🂩", 0, 8);
        Card SpadeTen = new Card("Ten Of Spades", "🂪", 0, 9);
        Card SpadeJack = new Card("Jack Of Spades", "🂫", 0, 10);
        Card SpadeQueen = new Card("Queen Of Spades", "🂭", 0, 11);
        Card SpadeKing = new Card("King Of Spades", "🂮", 0, 12);

        Card HeartAce = new Card("Ace Of Hearts", "🂱", 1, 0);
        Card HeartTwo = new Card("Two Of Hearts", "🂲", 1, 1);
        Card HeartThree = new Card("Three Of Hearts", "🂳", 1, 2);
        Card HeartFour = new Card("Four Of Hearts", "🂴", 1, 3);
        Card HeartFive = new Card("Five Of Hearts", "🂵", 1, 4);
        Card HeartSix = new Card("Six Of Hearts", "🂶", 1, 5);
        Card HeartSeven = new Card("Seven Of Hearts", "🂷", 1, 6);
        Card HeartEight = new Card("Eight Of Hearts", "🂸", 1, 7);
        Card HeartNine = new Card("Nine Of Hearts", "🂹", 1, 8);
        Card HeartTen = new Card("Ten Of Hearts", "🂺", 1, 9);
        Card HeartJack = new Card("Jack Of Hearts", "🂻", 1, 10);
        Card HeartQueen = new Card("Queen Of Hearts", "🂽", 1, 11);
        Card HeartKing = new Card("King Of Hearts", "🂾", 1, 12);

        Card DiamondAce = new Card("Ace Of Diamonds", "🃁", 2, 0);
        Card DiamondTwo = new Card("Two Of Diamonds", "🃂", 2, 1);
        Card DiamondThree = new Card("Three Of Diamonds", "🃃", 2, 2);
        Card DiamondFour = new Card("Four Of Diamonds", "🃄", 2, 3);
        Card DiamondFive = new Card("Five Of Diamonds", "🃅", 2, 4);
        Card DiamondSix = new Card("Six Of Diamonds", "🃆", 2, 5);
        Card DiamondSeven = new Card("Seven Of Diamonds", "🃇", 2, 6);
        Card DiamondEight = new Card("Eight Of Diamonds", "🃈", 2, 7);
        Card DiamondNine = new Card("Nine Of Diamonds", "🃉", 2, 8);
        Card DiamondTen = new Card("Ten Of Diamonds", "🃊", 2, 9);
        Card DiamondJack = new Card("Jack Of Diamonds", "🃋", 2, 10);
        Card DiamondQueen = new Card("Queen Of Diamonds", "🃍", 2, 11);
        Card DiamondKing = new Card("King Of Diamonds", "🃎", 2, 12);

        Card ClubAce = new Card("Ace Of Clubs", "🃑", 3, 0);
        Card ClubTwo = new Card("Two Of Clubs", "🃒", 3, 1);
        Card ClubThree = new Card("Three Of Clubs", "🃓", 3, 2);
        Card ClubFour = new Card("Four Of Clubs", "🃔", 3, 3);
        Card ClubFive = new Card("Five Of Clubs", "🃕", 3, 4);
        Card ClubSix = new Card("Six Of Clubs", "🃖", 3, 5);
        Card ClubSeven = new Card("Seven Of Clubs", "🃗", 3, 6);
        Card ClubEight = new Card("Eight Of Clubs", "🃘", 3, 7);
        Card ClubNine = new Card("Nine Of Clubs", "🃙", 3, 8);
        Card ClubTen = new Card("Ten Of Clubs", "🃚", 3, 9);
        Card ClubJack = new Card("Jack Of Clubs", "🃛", 3, 10);
        Card ClubQueen = new Card("Queen Of Clubs", "🃝", 3, 11);
        Card ClubKing = new Card("King Of Clubs", "🃞", 3, 12);

        Card[][] newDeck = {  {SpadeAce, HeartAce, DiamondAce, ClubAce},
        {SpadeTwo, HeartTwo, DiamondTwo, ClubTwo},
        {SpadeThree, HeartThree, DiamondThree, ClubThree},
        {SpadeFour, HeartFour, DiamondFour, ClubFour},
        {SpadeFive, HeartFive, DiamondFive, ClubFive},
        {SpadeSix, HeartSix, DiamondSix, ClubSix},
        {SpadeSeven, HeartSeven, DiamondSeven, ClubSeven},
        {SpadeEight, HeartEight, DiamondEight, ClubEight},
        {SpadeNine, HeartNine, DiamondNine, ClubNine},
        {SpadeTen, HeartTen, DiamondTen, ClubTen},
        {SpadeJack, HeartJack, DiamondJack, ClubJack},
        {SpadeQueen, HeartQueen, DiamondQueen, ClubQueen},
        {SpadeKing, HeartKing, DiamondKing, ClubKing}
        };

        deck = newDeck;
    }

    public static void sleep(int a) {
        try { 
            Thread.sleep(a); 
        } catch(Exception e){} 
    }

    public static void slowPrint(String a) {
    for(int i = 0; i<a.length(); i++) {
        System.out.print(a.charAt(i));
        sleep(10);
    }
    System.out.println();
    }

    public static void deckArrange(ArrayList<Card> c) {
        Collections.sort(c, Card.cardNoComparator);
    }

    public static int sortGivenArrayS(ArrayList<Integer> a) {
        int statement = 0;
        int small = 0;
        int j=0;
        int smallIndex = 0;      
        for(int i=1;i<a.size();i++){
            small = a.get(i-1);
            smallIndex = i-1;
            for(j=i;j<a.size();j++){
                if(a.get(j)<small){
                    small = a.get(j);
                    smallIndex = j;
                    statement++;
                }
            }
            int temp = a.get(smallIndex);
            a.set(smallIndex, a.get(i-1));
            a.set(i-1, temp);
        }
        return statement;   
    }
    
    public static int sortGivenArrayI(ArrayList<Integer> array) {  
        int statement = 0;
        int n = array.size();  
        for (int j = 1; j < n; j++) {  
            int key = array.get(j);  
            int i = j-1;  
            while ( (i > -1) && ( array.get(i) > key ) ) {  
                array.set(i+1, array.get(i));
                statement++;
                i--;  
            }  
            array.set(i+1, key);  
        }
        return statement;  
    }  

    public static Object setCard(Card c, int i, ArrayList<Card> array) {
        return array.set(i, c);
    }
    
    public static boolean addCard(Card c, ArrayList<Card> array) {
        return array.add(c);
    }

    public static Object getAndRemoveCard(int i, ArrayList<Card> array) {
        return array.remove(i);
    }

    public static void addCard(Card c, int i, ArrayList<Card> array) {
        array.add(i, c);
    }

    public static void extraEnd() {
        System.out.println("Ignore This:");
        if (sortGivenArrayI(playerFish) > sortGivenArrayS(cpuFish)) {
            System.out.println("Insertion");
        } else {
            System.out.println("Selection");           
        }

        Card joker = new Card("Joker", "JK", 0, 0);
        System.out.println(addCard(joker, jokers));
        addCard(joker, 0, jokers);
        System.out.println(setCard(joker, 0, jokers));
        System.out.println(getAndRemoveCard(0, jokers));
    }
    public static void title() {
    System.out.println("                                                                        ▄▄▄▄▄    ███████▀▀▀█▌      ");
    System.out.println("                                              ▄▄▄▄▄▄▄▄▄███  ▄██▀▀▀▀▀████▌  ▐██▄ ██   ███   ███     ");
    System.out.println("                       ▄▄▄▄▄▄▄          ██▀▀▀▀▀▀▀  ▐██   ███▀   ▄▄▄  ███▌  ▐███ ██   ███   ███     ");
    System.out.println("      ▄▄███▀▀███▄   ▄█▀▀      ▀██       █    ▄▄▄▄█████   ██▌  ▐█████████▌  ▐▀▀▀▀▀▀   ███  ▐███     ");
    System.out.println("    ▄█▀         ████▀  ▄▄███▄   ▀██▄    █   ▐█████████   ███       ▀▀███▌    ▄▄▄▄▄   ███▄▄████    ");
    System.out.println("   ██   ███████████   ████████   ███    █         ▐███   ████████▄▄   ██▌  ▐██████   ███▀▀▀███    ");
    System.out.println("  ██   ████▀███▀███   ███▀   █   ████   █   ▄█████████   ███▀█████▀   ██▌  ▐███ ██   ███  ▄███    ");
    System.out.println("  ██   ███  ██  ▐██   ▀██▄▄▄█▀  ▄███▌   █   ▐█████▀▀██   ██▄       ▄█████▄▄████ ▀█████████████    ");
    System.out.println("  ▐█▄   ▀████▀  ▐███▄         ▄█████    █▄▄▄████    ▀█████████████████████████▀   ▀▀▀▀▀   ▀▀      ");
    System.out.println("    ██▄       ▄▄████▀██▄▄▄▄███████▀     ▀▀█████▌      ▀████████████████▀▀██▀█▄                    ");
    System.out.println("      ▀███████████▀   ▀▀███████▀▄  ▄▄    ▄▀▀▀▀█▀▀▀▀██ ▐▌   ▄▐  █▀ ▄▄ ▐█  ▀▌ ██                    ");
    System.out.println("         ▀▀▀▀▀▀    ▄▀▀▄▄▀▀██ ██▌ ██  █▌ ▐  ▄▄█▌ ██  █ ▐██  ██  █ ▀██  █ ▐▄  ██                    ");
    System.out.println("                   █  █▌  ▐█▌ █ ██ ▐  █▄▐  ▄▄█▌ ▀▀ ▄█ ▐██  ██  ██▄▄▄███▄██▀███                    ");
    System.out.println("                  ▄█  █ ▄▄ ▀█  ▐█  ██ ██▐▄▄▄▄▄█▄█████████████▀▀▀  ▀▀▀                             ");
    System.out.println("                 ▀▄▄▄███████████████▀▀▀▀  ▀▀▀▀                                                    ");
    System.out.println("                   ▀▀                                                                             ");
    }
}
