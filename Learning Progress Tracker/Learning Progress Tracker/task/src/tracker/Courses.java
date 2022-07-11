package tracker;

public enum Courses {

    JAVA("Java", 600),
    DSA("DSA", 400),
    DATABASES("Databases", 480),
    SPRING("Spring", 550);

    final String name;
    final int pointsToComplete;

    Courses(String name, int pointsToComplete) {
        this.name = name;
        this.pointsToComplete = pointsToComplete;
    }

    public String getName() {
        return name;
    }

    public int getPointsToComplete() {
        return pointsToComplete;
    }
}
