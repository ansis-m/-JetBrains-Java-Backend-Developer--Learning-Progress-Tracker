package tracker;

import java.util.ArrayList;

public class Student implements Comparable<Student>{

    private int id;
    private String email;
    private String name;
    private String[] surname;
    private int[] points;
    private boolean[] sentNotifications;
    ArrayList<String> notifications;


    Student(int id, String email){
        this.notifications = new ArrayList<>();
        this.id = id;
        this.email = email;
        points = new int[Courses.values().length];
        sentNotifications = new boolean[Courses.values().length];
        for(int i = 0; i < Courses.values().length; i++){
            points[i] = 0;
            sentNotifications[i] = false;
        }

    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public void addPoints(int[] pointsToAdd) {
        for(int i = 0; i < Courses.values().length; i++) {
            points[i] += pointsToAdd[i + 1];
            if (sentNotifications[i] == false && points[i] >= Courses.values()[i].getPointsToComplete()){
                sentNotifications[i] = true;
                sendNotification(Courses.values()[i].getName());
            }
        }
    }

    public void sendNotification(String course) {

        StringBuilder fullName = new StringBuilder(name);
        for(String s : surname){
            fullName.append(" ");
            fullName.append(s);
        }

        String result = String.format("To: %s\nRe: Your Learning Progress\nHello, %s! You have accomplished our %s course!\n", email, fullName, course);
        notifications.add(result);
    }

    public int printAndDeleteNotifications() {

        int count = 0;
        for(String s : notifications) {
            System.out.print(s);
            count = 1;
        }
        this.notifications.clear();
        return count;
    }

    @Override
    public int compareTo(Student student) {
        if(this.id > student.getId())
            return 1;
        else if(this.id < student.getId())
            return -1;
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getSurname() {
        return surname;
    }

    public void setSurname(String[] surname) {
        this.surname = surname;
    }

    public int[] getPoints() {
        return points;
    }

    public void setPoints(int[] points) {
        this.points = points;
    }

}
