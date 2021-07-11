package in.rohan.sirius.ui;

public class Student implements Comparable<Student>{
    public int getStarCount() {
        return starCount;
    }

    public void setStarCount(int starCount) {
        this.starCount = starCount;
    }

    private int starCount;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Student o) {
        return Integer.valueOf(this.id).compareTo(Integer.valueOf(o.id));

    }
}
