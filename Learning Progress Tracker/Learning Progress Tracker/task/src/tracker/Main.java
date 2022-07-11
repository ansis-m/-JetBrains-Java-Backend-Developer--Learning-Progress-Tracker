package tracker;

import java.util.*;

public class Main {

    static List<Student> students;
    static Map<String, Student> emails;
    static Statistics statistics = new Statistics();
    static int id;
    static int courseCount;

    static {
        courseCount = Courses.values().length;
        students = new ArrayList<>();
        emails = new HashMap<>();
        id = 10000;
    }

    public static void main(String[] args) {

        System.out.println("Learning Progress Tracker");
        Scanner scanner = new Scanner(System.in);

        while(true) {
            String s = scanner.nextLine().trim();
            switch (s) {
                case "exit":
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                case "back":
                    System.out.println("Enter 'exit' to exit the program.");
                    break;
                case "list":
                    if(id == 10000)
                        System.out.println("No students found.");
                    else {
                        System.out.println("Students:");
                        for(int i = 10000; i < id; i++)
                            System.out.println(i);
                    }
                    break;
                case "add points":
                    System.out.println("Enter an id and points or 'back' to return:");
                    addPoints(scanner);
                    break;
                case "add students":
                    addStudents(scanner);
                    break;
                case "find":
                    System.out.println("Enter an id or 'back' to return:");
                    findStudents(scanner);
                    break;
                case "statistics":
                    System.out.println("Type the name of a course to see details or 'back' to quit");
                    statistics(scanner);
                    break;
                case "":
                    System.out.println("No input");
                    break;
                case "notify":
                    notifyStudents();
                    break;
                default:
                    System.out.println("Unknown command!");
                    break;
            }
        }

    }

    public static void addStudents(Scanner scanner) {

        int count = 0;

        System.out.println("Enter student credentials or 'back' to return");
        while(true) {
            String s = scanner.nextLine().trim();
            if(s.equals("back")) {
                System.out.println("Total " + count + " students have been added.");
                return;
            }
            String[] array = s.split(" ");
            String email = array[array.length - 1];
            if (array.length < 3) {
                System.out.println("Incorrect credentials" + " array len " + array.length);
            }
            else if (isInvalidFirstName(array[0])) {
                System.out.println("Incorrect first name");
            }
            else if (!isValidLastName(array)) {
                System.out.println("Incorrect last name");
            }
            else if (!isValidEmail(email)) {
                System.out.println("Incorrect email");
            }
            else {
                if (emails.containsKey(email)){
                    System.out.println("This email is already taken.");
                }
                else {
                    count++;
                    Student student = new Student(id, email);
                    id++;
                    student.setName(array[0]);
                    String[] surname = new String[array.length - 2];
                    System.arraycopy(array, 1, surname, 0, array.length - 2);
                    student.setSurname(surname);
                    students.add(student);
                    emails.put(email, student);
                    System.out.println("The student has been added");
                }
            }
        }

    }

    public static void addPoints(Scanner scanner) {

        String input;
        String[] digits;
        int[] grades = new int[courseCount + 1];

        while(true) {
            input = scanner.nextLine().trim();
            if(input.equals("back"))
                return;
            digits = input.split(" ");
            if(digits.length != courseCount + 1) {
                System.out.println("Incorrect points format.");
                continue;
            }
            try{
                grades[0] = Integer.parseInt(digits[0]);
            }
            catch(Exception e) {
                System.out.printf("No student is found for id=%s", digits[0]);
            }
            try {
                for(int i = 1; i < courseCount + 1; i++) {
                    grades[i] = Integer.parseInt(digits[i]);
                    if (grades[i] < 0) {
                        throw new RuntimeException("Incorrect points format2.");
                    }

                }
                if(grades[0] < 10000 || grades[0] > id - 1) {
                    System.out.printf("No student is found for id=%d.\n", grades[0]);
                    continue;
                }
                students.get(grades[0] - 10000).addPoints(grades);
                Statistics.update(grades);
                System.out.println("Points updated.");
            }
            catch (Exception e) {
                System.out.println("Incorrect points format.");
            }
        }
    }

    public static void findStudents(Scanner scanner) {

        String input;
        int ident;

        while(true) {
            input = scanner.nextLine().trim();
            if(input.equals("back"))
                return;
            try{
               ident = Integer.parseInt(input);
               if(ident < 10000 || ident > id - 1)
                   System.out.printf("No student is found for id=%d.\n", ident);
               else {
                   int[] array = students.get(ident - 10000).getPoints();
                   System.out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n", ident, array[0], array[1], array[2], array[3]);
               }
            }
            catch (Exception e) {
                System.out.println("Incorrect id format.");
            }
        }
    }

    public static void statistics(Scanner scanner) {

        Statistics.overview(students.size());


        String input;

        infinite:
        while(true) {
            input = scanner.nextLine().trim();
            if(input.equalsIgnoreCase("back"))
                return;
            int i = 0;
            for(Courses c : Courses.values()){
                if(input.equalsIgnoreCase(c.getName())) {
                    Statistics.printCourseStatistics(input, i);
                    continue infinite;
                }
                i++;
            }
            System.out.println("Unknown course.");
        }
    }

    public static boolean isInvalidFirstName(String firstName) {

        char[] name = firstName.toCharArray();
        if(name.length < 2)
            return true;

        for(int i = 0; i < name.length; i++) {
            if(!isNormalLetter(name[i]) && name[i] != '\'' && name[i] != '-') {
                return true;
            }
            if(name[i] == '\'' || name[i] == '-') {
                if(i == 0 || i == name.length - 1)
                    return true;
                if(name[i+1] == '\'' || name[i+1] == '-')
                    return true;
            }
        }

        return false;
    }

    public static void notifyStudents() {

        int count = 0;
        for(Student s : students)
            count += s.printAndDeleteNotifications();
        System.out.printf("Total %d students have been notified.\n", count);
    }

    public static boolean isValidLastName(String[] array) {

        for(int i = 1; i < array.length - 1; i++) {
            if(isInvalidFirstName(array[i])) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidEmail(String email) {

        String[] array = email.split("@");

        if(array.length != 2)
            return false;

        var domain = array[1].split("\\.");

        return domain.length == 2;
    }

    public static boolean isNormalLetter(char a) {

        return (a >= 'A' && a <= 'Z') || (a >= 'a' && a <= 'z');
    }
}
