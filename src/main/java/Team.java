class Team implements Comparable<Team> {
    private String name;
    private int seedPos;
    private Country country;

    public Team(String name, int seedPos) {
        this.name = name;
        this.seedPos = seedPos;
    }
    public Team(String name, int seedPos, Country country) {
        this.name = name;
        this.seedPos = seedPos;
        this.country = country;
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
