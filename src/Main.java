import java.io.*;
import java.util.*;

/**
 * In this class is handled the transformation from a Dataset of games to a Dataset of decks
 * the dataset is saved in the file decks.txt
 */
public class Main {
    /**
     * Here are saved the queries from the all_absolute+.txt file
     */
    private static List<String> queries;

    /**
     * Method that reads a file and save the queries in a List<String> queries
     * @param file
     */
    public static void read(String file) {
        try {
            File myObj = new File(file);
            Scanner myReader = new Scanner(myObj);
            queries = new ArrayList<>();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                queries.add(data);
            }
            myReader.close();
        } catch ( FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        read("src/all_absolute+.txt");
        //It will contain all the different cards
        Set<String> cards = new TreeSet<>();
        for (String s : queries) {
            String[] splited = s.split(" ");
            String card = splited[1].substring(1);
            cards.add(card);
        }
        cards.remove("egin");
        //The map will help converting the strings to integers
        Map<String, Integer> cardsMap = new HashMap<>();
        int i = 1;
        for (String str : cards) {
            cardsMap.put(str, i);
            ++i;
        }
        int currentGame = 0;
        //we make a list of games where each game is represented as a list of cards played
        List<List<String>> games = new ArrayList<>();
        List<String> game = new ArrayList<>();
        for (String s : queries) {
            String[] splited = s.split(" ");
            int gameNb = Integer.parseInt(splited[0]);
            if (splited[1].equals("Begin")) continue;
            if (gameNb > currentGame) {
                currentGame = gameNb;
                games.add(game);
                game = new ArrayList<>();
            } else {
                game.add(splited[1]);
            }
        }

        //For each game we make 2 decks representing each player
        //then we add them into the list decks that represents all the decks
        List<Set<String>> decks = new ArrayList<>();
        for (List<String> l : games) {
            Set<String> deckM = new TreeSet<>();
            Set<String> deckO = new TreeSet<>();
            for (String s : l) {
                if (s.charAt(0) == 'M') {
                    String str = s.substring(1);
                    deckM.add(str);
                } else {
                    String str = s.substring(1);
                    deckO.add(str);
                }
            }
            decks.add(deckM);
            decks.add(deckO);
        }

        //here we have the decks of string that will be converted into deck of integers
        List<Set<Integer>> decksInt = new ArrayList<>();
        for (Set<String> set : decks) {
            Set<Integer> cardsInt = new TreeSet<>();
            for (String s : set) {
                int cardInt = cardsMap.get(s);
                cardsInt.add(cardInt);
            }
            decksInt.add(cardsInt);
        }

        //we show the decks of the console, the same result will be written on the decks.txt file
        for (Set<Integer> set : decksInt) {
            for (int num : set) {
                System.out.print(num+" ");
            }
            System.out.println();
        }
        //we write the decks into the decks.txt file
        try {
            FileWriter myWriter = new FileWriter("decks.txt");
            myWriter.write("@CONVERTED_FROM_TEXT\n");
            int j = 1;
            for (String str : cards) {
                myWriter.write("@ITEM="+j+"="+str+"\n");
                ++j;
            }
            for (Set<Integer> set : decksInt) {
                int index = 0;
                for (int num : set) {
                    if (index == set.size()-1)
                        myWriter.write(num+"");
                    else
                        myWriter.write(num+" ");
                    index++;
                }
                if (!decksInt.get(decksInt.size()-1).equals(set))
                    myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}