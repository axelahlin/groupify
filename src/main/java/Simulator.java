import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

class Simulator {
    private int groupSize;

    private LinkedList<Integer> convertToInts(LinkedList<String> pts) {
        LinkedList<Integer> data = new LinkedList<>();

        for (String s : pts) {
            if (!s.equals("")) {
                int tmp = Integer.parseInt(s);
                data.add(tmp);
            } else {
                data.add(0);
            }
        }

        return data;
    }

    public void run(String teamFile, String seedingFile) {

        LinkedList<String> teams = scanFile(teamFile);
        LinkedList<String> tmpPts = scanFile(seedingFile);

        LinkedList<Integer> seedingPts = convertToInts(tmpPts);

        LinkedList<Team> db = new LinkedList<>();

        for (int i = 0; i < Math.max(seedingPts.size(), teams.size()); i++) {
            try {
                db.add(new Team(teams.get(i), seedingPts.get(i)));
            } catch (IndexOutOfBoundsException e) {
                System.out.println("File lengths do not match, adding 0 as seeding points");
                db.add(new Team(teams.get(i), 0));
            }
        }

        for (Team t : db) {
            System.out.println(t.toString() + ": " + t.getSeeding());
        }
        System.out.println("Teams loaded successfully");

        Group[] finalGroups = placeInGroups(db);

        printGroups(finalGroups);
    }

    private void printGroups(Group[] groups) {
        for (int i = 0; i < groups.length; i++) {
            System.out.println("== Group " + (i + 1) + " ==");
            System.out.println(groups[i].toString());
        }

    }

    public Simulator(int groupSize) {
        this.groupSize = groupSize;
    }

    private Group[] placeInGroups(LinkedList<Team> data) {

        System.out.println("\n" + "Trying to draw groups...");

        LinkedList<Team> teams = data;
        Collections.sort(teams);

        Group[] groups = new Group[teams.size() / groupSize];
        for (int i = 0; i < groups.length; i++)
            groups[i] = new Group(groupSize); // init with the null group

        //Creating pots
        List<ArrayList<Team>> pots = new LinkedList<>();
        //pot splitter
        LinkedList<Team> potTeams = (LinkedList) teams.clone(); //UGLY AFFFFF
        Collections.sort(potTeams);


        final int potSize = potTeams.size() / groupSize;
        for (int i = 0; i < groupSize; i++) {
            ArrayList<Team> currentPot = new ArrayList<>();
            for (int j = 0; j < potSize; j++) { //FIX THIS AND GENERALIZE
                Team current = potTeams.remove(0);
                currentPot.add(current);
            }
            pots.add(currentPot);
        }


        for (ArrayList<Team> pot : pots) {
            int i = 0;

            List<Boolean> picks = new ArrayList<Boolean>(Arrays.asList(new Boolean[pot.size()])); //UGLY
            Collections.fill(picks, false);
            while (!pot.isEmpty()) {

                Team current = retrieveRandomTeam(pot, picks);


                pot.remove(current);

                System.out.println("Drawing " + current.toString() + " to group " + (i + 1));

                if (i == 7) {
                    groups[i].add(current);
                    i = 0;
                } else {
                    groups[i].add(current);
                    i++;
                }
            }
        }

        return groups;
    }


    private Team retrieveRandomTeam(ArrayList<Team> pot, List<Boolean> picks) {
        Random rand = new Random(System.currentTimeMillis());

        while (picks.contains(false)) {
            try {
                int i = rand.nextInt(picks.size() + 1);
                if (!pot.isEmpty() && !picks.get(i)) {
                    picks.remove(i);
                    return pot.get(i);
                }
            } catch (IndexOutOfBoundsException e) { // bad practice
                //bla bla
            }
        }

        return null;
    }

    private LinkedList<String> scanFile(String fileName) {
        String content;
        int count = 1;
        File file = new File(fileName);
        LinkedList<String> list = new LinkedList<String>();

        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            while (sc.hasNextLine()) {
                content = sc.nextLine();
                list.add(content);
            }
            sc.close();
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nProgram terminated Safely...");
        }

        return list;
    }
}
