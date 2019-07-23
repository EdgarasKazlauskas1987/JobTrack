package Model;


public class Workplace {

    private int workplaceId;
    private int userId;
    private String name;
    private int salary;

    public int getUserId() {
        return userId;
    }

    public int getWorkplaceId() {
        return workplaceId;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public Workplace(int workplaceId, int userId, String name, int salary) {
        this.workplaceId = workplaceId;
        this.userId = userId;
        this.name = name;
        this.salary = salary;
    }

    public Workplace(String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return  name;
    }
}



