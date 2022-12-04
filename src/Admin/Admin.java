package Admin;

import RandomClass.StudentCGComparator;
import Station.Station;
import Student.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Student.Branch;
import Station.Station;

import static Student.Branch.branchDetector;

public final class Admin {
    /**
     * view/edit station list
     * view edit student details
     * start allotment
     * logout option returning to homepage
     */

    public static Admin admin;
    private static String name = "ADMIN";
    private static String ID = "admin";
    private static String password = "admin";
    public static boolean startedRound = false;


    static boolean editStation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name of station-");
        String stationname = sc.nextLine();

        System.out.println("Enter N to edit station name-");
        System.out.println("Enter C to edit CG Criteria-");
        System.out.println("Enter B to edit Branch Criteria-");
        char choice = sc.nextLine().charAt(0);

        int i = 0;
        for (i = 0; i < Station.stations.size(); i++) {
            if (Station.stations.get(i).getName().equals(stationname)) {
                break;
            }
        }
        //String name=s.getName();
//        Station s4 = Station.stations.get(i);
//    for(i=0;i<Station.stations.size();i++){
//      if(Station.stations.get(i).getName().equals(name)){
//        break;
//      }
//    }
        if (choice == 'N') {
            // Get name
            System.out.println("Enter new name of station: ");

            String newname = sc.nextLine();
            Station.stations.get(i).setName(newname);
            return true;
        } else if (choice == 'C') {

            int newcg = sc.nextInt();
            Station.stations.get(i).setCGCrtieria(newcg);
            return true;
        } else if (choice == 'B') {
            Scanner N3 = new Scanner(System.in);
            System.out.println("Enter the number of branch satisfying criteria");
            int num = sc.nextInt();
            ArrayList<Branch> newbranch = new ArrayList<Branch>();
            for (int j = 1; i <= num; i++) {
                System.out.println("Enter branch: ");
                String s1 = sc.nextLine();
                newbranch.add(branchDetector(s1));
            }
            Station.stations.get(i).setBranchesallowed(newbranch);
            return true;
        }
        System.out.println("Invalid choice");
        return true;
    }

    public static void adminLoginPage() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter L to Login");
        char s = sc.nextLine().charAt(0);
        if (s == 'L') {
            System.out.println("Enter ID");
            String id = sc.nextLine();
            System.out.println("Enter pass-");
            String pass = sc.nextLine();
            if (Admin.ID.equals(id) && Admin.password.equals(pass)) {
                System.out.println("Login Successful");
                Admin.adminCanDo();
            } else {
                System.out.println("Login Failed");
            }
        } else {
            System.out.println("Invalid Input.");
        }
    }

    private static void adminCanDo() throws IOException {
        while (true) {
            if (!startedRound) {
                System.out.println("Enter 1 to change station attributes");
                System.out.println("Enter 2 to remove station");
                System.out.println("Enter 3 to start allotment (can only start once all Students have submitted prference)");
            }
            System.out.println("Enter L to Logout ");
            Scanner sc = new Scanner(System.in);
            String choice = sc.nextLine();
            char c = choice.charAt(0);

            if ((c == '1')) {
                editStation();

            } else if (c == '2') {

                //removeStation();

            } else if (c == '3') {
                startRound();

            } else if (c == 'L') {
                break;

            } else {


                System.out.println("Please Enter a Valid Input");

            }
        }
    }

    private static Object findstation(String s, ArrayList<Station> s8) {
        int flag = 0;
        Station st;
        for (Station find : s8) {
            if (find.getName().equals(s)) {
                return find;
            } else {
                continue;
            }

        }
        return null;
    }

    public static boolean isStartedRound() {
        return startedRound;
    }

    public static void startRound() throws IOException {


        if (Student.checkForNullObjects()) {


            Admin.startedRound = true;


            Collections.sort(Student.students, new StudentCGComparator());

            for (int i = 0; i < Student.students.size(); i++) {
                Student s = Student.students.get(i);

                System.out.println(s.getName());
                System.out.println(s.getCgpa());
            }
            for (int i = 0; i < Student.students.size(); i++) {
                Student a = Student.students.get(i);
                if (!a.alloted) {

                    for (int j = 0; j < a.preferenceList.size(); j++) {

                        Station s = a.preferenceList.get(j);
                        if (s.requirmentsMatch(a)) {

                            if (s.getVacancy() > 0) {
                                a.stationAlloted =s;
                                System.out.println(a.getStationAlloted() +" "+ a.getName()  );
                                a.alloted = true;
                                s.setVacancy(s.getVacancy() - 1);
                                s.stationMeStudents.add(a);
                                break;

                            } else {

                            }


                        }
                    }
                }
            }

            Student.writeStudent();
            Station.writeStation();
        } else {
            System.out.println("These students have not entered preference order");
        }
    }
}