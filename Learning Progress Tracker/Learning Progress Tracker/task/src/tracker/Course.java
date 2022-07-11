package tracker;

import java.util.ArrayList;

public class Course {

    private int submissions;
    private final int pointsToComplete;
    private final String name;
    int totalPoints;
    private ArrayList<IDAndPoints> students; //need to sort by value and then by key

    public Course(String name, int pointsToComplete) {
        this.pointsToComplete = pointsToComplete;
        this.name = name;
        submissions = 0;
        totalPoints = 0;
        students = new ArrayList<>();
    }

    public void updateCourse(int studentID, int grade){
        submissions++;
        totalPoints += grade;
        for(IDAndPoints i : students) {
            if(i.getID() == studentID) {
                i.setPoints(i.getPoints() + grade);
                return;
            }
        }
        students.add(new IDAndPoints(studentID, grade));
    }

    public int getSubmissions() {
        return submissions;
    }

    public int getPointsToComplete() {
        return pointsToComplete;
    }

    public String getName() {
        return name;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public ArrayList<IDAndPoints> getStudents() {
        return students;
    }
}
