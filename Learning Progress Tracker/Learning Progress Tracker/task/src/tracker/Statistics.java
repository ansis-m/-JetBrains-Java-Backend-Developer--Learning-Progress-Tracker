package tracker;
import java.util.Collections;
import java.util.Optional;

public class Statistics {

    static Course[] courses;

    Statistics(){
        courses = new Course[Courses.values().length];
        int i = 0;
        for(Courses c : Courses.values()) {
            courses[i++] = new Course(c.getName(), c.getPointsToComplete());
        }
    }

    public static void update(int[] points) {
        for(int i = 0; i < Courses.values().length; i++){
            if(points[i + 1] != 0)
                courses[i].updateCourse(points[0], points[i + 1]);
        }
    }

    public static void printCourseStatistics(String input, int courseId){
        System.out.println(input);
        Course course = courses[courseId];
        int points = course.getPointsToComplete();
        System.out.println("id\tpoints\tcompleted");
        Collections.sort(course.getStudents());
        for(IDAndPoints i : course.getStudents()){
            System.out.printf("%d\t%d\t%.1f%%\n", i.getID(), i.getPoints(), 100.0f* i.getPoints()/points);
        }
    }

    public static void overview(int count){
        if (count == 0) {
            System.out.printf("Most popular: %s\n", "n/a");
            System.out.printf("Least popular: %s\n", "n/a");
            System.out.printf("Highest activity: %s\n", "n/a");
            System.out.printf("Lowest activity: %s\n", "n/a");
            System.out.printf("Easiest course: %s\n", "n/a");
            System.out.printf("Hardest course: %s\n", "n/a");
        }
        else {
            popularity();
            activity();
            difficulty();
        }
    }

    public static void popularity(){

        int max = 0;
        int min = 99999;
        StringBuilder maxCourses = new StringBuilder();
        StringBuilder minCourses = new StringBuilder();

        for(Course c : courses) {
            if(c.getStudents().size() > max)
                max = c.getStudents().size();
            if(c.getStudents().size() < min)
                min = c.getStudents().size();
        }

        for(Course c : courses) {
            if(c.getStudents().size() == max) {
                if(!maxCourses.toString().equals(""))
                    maxCourses.append(", ").append(c.getName());
                else
                    maxCourses = new StringBuilder(c.getName());
            }
            if(c.getStudents().size() == min) {
                if(!minCourses.toString().equals(""))
                    minCourses.append(", ").append(c.getName());
                else
                    minCourses = new StringBuilder(c.getName());
            }
        }

        if(min == max) {
            minCourses = new StringBuilder("n/a");
        }

        System.out.printf("Most popular: %s\n", maxCourses);
        System.out.printf("Least popular: %s\n", minCourses);
    }

    public static void activity(){

        int max = 0;
        int min = 99999;
        StringBuilder maxCourses = new StringBuilder();
        StringBuilder minCourses = new StringBuilder();

        for(Course c : courses) {
            if(c.getSubmissions() > max)
                max = c.getSubmissions();
            if(c.getSubmissions() < min)
                min = c.getSubmissions();
        }

        for(Course c : courses) {
            if(c.getSubmissions() == max) {
                if(!maxCourses.toString().equals(""))
                    maxCourses.append(", ").append(c.getName());
                else
                    maxCourses = new StringBuilder(c.getName());
            }
            if(c.getSubmissions() == min) {
                if(!minCourses.toString().equals(""))
                    minCourses.append(", ").append(c.getName());
                else
                    minCourses = new StringBuilder(c.getName());
            }
        }

        if(min == max) {
            minCourses = new StringBuilder("n/a");
        }

        System.out.printf("Highest activity: %s\n", maxCourses);
        System.out.printf("Lowest activity: %s\n", minCourses);
    }

    public static void difficulty(){

        float max = 0;
        float min = 99999;
        StringBuilder maxCourses = new StringBuilder();
        StringBuilder minCourses = new StringBuilder();

        for(Course c : courses) {
            if(c.getSubmissions() > 0 && (float) c.getTotalPoints()/c.getSubmissions() > max)
                max = (float) c.getTotalPoints()/c.getSubmissions();
            if(c.getSubmissions() > 0 && (float) c.getTotalPoints()/c.getSubmissions() < min)
                min = (float) c.getTotalPoints()/c.getSubmissions();
        }

        for(Course c : courses) {
            if(c.getSubmissions() > 0 && (float) c.getTotalPoints()/c.getSubmissions() == max) {
                if(!maxCourses.toString().equals(""))
                    maxCourses.append(", ").append(c.getName());
                else
                    maxCourses = new StringBuilder(c.getName());
            }
            if(c.getSubmissions() > 0 && (float) c.getTotalPoints()/c.getSubmissions() == min) {
                if(minCourses != null & minCourses.length() != 0)
                    minCourses.append(", ").append(c.getName());
                else
                    minCourses = Optional.ofNullable(c.getName()).map(StringBuilder::new).orElse(null);
            }
        }

        if(min == max) {
            minCourses = new StringBuilder("n/a");
        }

        System.out.printf("Easiest course: %s\n", maxCourses);
        System.out.printf("Hardest course: %s\n", minCourses == null ? null : minCourses.toString());
    }
}
