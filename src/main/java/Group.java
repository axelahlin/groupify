class Group {
    private Team[] teams;

    public Group(int groupSize) {
        this.teams = new Team[groupSize];
        for (int i = 0; i < groupSize; i++)
            teams[i] = new Team("", 0); // adding "null" teams

    }

    public void add(Team team) {

        teams[team.getSeeding() - 1] = team;
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
