package SwimTeamManager;

public class Swimmer {
    private final long id;
    private final String name;
    private final int age;
    private final String group;

    public Swimmer(long id, String name, int age, String group) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.group = group;
    }

    public String getName() { return name; }
    public long getId() { return id; }
    public int getAge() { return age; }
    public String getGroup() { return group; }
}
