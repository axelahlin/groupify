import java.util.*;
import java.io.*;

class GroupDraw {

    public static void main(String[] args) {
        new Simulator(4).run("teams.txt", "seedingPts.txt");

    }
}

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
            System.out.println("== Group " + i + " ==");
            System.out.println(groups[i].toString());
        }

    }

    public Simulator(int groupSize) {
        this.groupSize = groupSize;
    }

    private Group[] placeInGroups(LinkedList<Team> data) {

        System.out.println("\n" + "Trying to draw groups...");

        List<Team> teams = data;
        Collections.sort(teams);

        Group[] groups = new Group[teams.size() / groupSize];
        for (int i = 0; i < groups.length; i++)
            groups[i] = new Group(); // the null group

        

        List<HashSet<Team>> pots = new LinkedList<>();
        //pot splitter
        List<Team> potTeams = new LinkedList<>(teams); //UGLY AFFFFF
        for (int i = 0; i < potTeams.size()/groupSize; i++) {
            HashSet<Team> pot = new HashSet<>();
            for (int j = 0; j < potTeams.size()/groupSize; j++) {
                pot.add(potTeams.remove(j));
            }
            pots.add(pot);
        }

        for (HashSet<Team> pot : pots) {
            int i = 0;
            while (!pot.isEmpty()) {
                Team current = pot.iterator().next();
                pot.remove(current);

                System.out.println("Drawing " + current.toString() + " to group " + (i+1));

                if (i == 7) {
                
                    i = 0;
                } else {
                    i++;
        
                }
                groups[i].add(current);

            }
        }


        /* int i = 0;
        while (teams.size() != 0) {
            
            Team head = teams.remove(0);

            System.out.println("Drawing " + head.toString() + ", seeded at position " + i);
            

            if (i == 7) {
                
                i = 0;
            } else {
                i++;
    
            }
            groups[i].add(head);
        }
 */
        return groups;
    }

    private class Group {
        private Team[] teams;

        public Group() {
            this.teams = new Team[groupSize];
            for (int i = 0; i < groupSize; i++)
                teams[i] = new Team("", 0); // adding "null" teams

        }

        public void add(Team team) {
            
                teams[team.getSeeding()-1] = team;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            for (int i = 0; i < teams.length; i++) {
                sb.append(teams[i].toString() + "\n");
            }
            return sb.toString();
        }
    }

    private class Team implements Comparable<Team> {
        private String name;
        private int seedPos;

        public Team(String name, int seedPos) {
            this.name = name;
            this.seedPos = seedPos;
        }

        public String toString() {
            return name;
        }

        public int getSeeding() {
            return seedPos;
        }

        @Override
        public int compareTo(Team t2) {
            return Integer.compare(getSeeding(), t2.getSeeding());
        }

    }

    private LinkedList<String> scanFile(String fileName) {
        String content = new String();
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