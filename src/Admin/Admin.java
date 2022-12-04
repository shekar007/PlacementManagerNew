package Admin;

import RandomClass.StudentCGComparator;
import Station.Station;
import Student.Student;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Student.Branch;
import Station.Station;

import javax.swing.*;

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

    public static void removeStation()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter station name: ");
        String str = sc.nextLine().trim();

        for (int i = 0; i < Station.stations.size(); i++) {
            if(str.equals(Station.stations.get(i).getName()))
            {
            Station s = Station.stations.get(i);
            Station.stations.remove(s);
                System.out.println("removed");
                break;
            }
            if(i==Station.stations.size())
            {
                System.out.println("No such station exists");
            }


        }
    }

    static void  editStation() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name of station-");
        String stationname = sc.nextLine();
        int i = 0;
        for (i = 0; i < Station.stations.size(); i++) {
            if (Station.stations.get(i).getName().equals(stationname)) {
                break;
            }
        }
        if (i == Station.stations.size()) {
            System.out.println("Invalid station name "+stationname);
        } else {
            System.out.println("Enter N to edit station name-");
            System.out.println("Enter C to edit CG Criteria-");
            System.out.println("Enter B to edit Branch Criteria-");
            char choice = sc.nextLine().charAt(0);


            if (choice == 'N') {
                // Get name
                System.out.println("Enter new name of station: ");

                String newname = sc.nextLine();
                Station.stations.get(i).setName(newname);
                System.out.println("Changed");

            } else if (choice == 'C') {


                System.out.println("Enter New Minimum Criteria: ");
                try {
                    double newcg = Double.parseDouble(sc.nextLine().trim());
                    if(Student.checkValidCGPA(newcg)) {
                        Station.stations.get(i).setCGCrtieria(newcg);
                        System.out.println("Changed!");
                    }
                    else {
                        System.out.println("Not Valid cgpa");
                    }
                }
                catch (Exception err)
                {
                    System.out.println("Invalid input");
                }

            } else if (choice == 'B') {
                System.out.println("Enter the number of branch satisfying criteria");
                int num = Integer.parseInt(sc.nextLine());
                ArrayList<Branch> newbranch = new ArrayList<Branch>();
                int flag =0;
                for (int j = 0; j < num; j++) {
                    System.out.println("Enter branch: ");
                    String s1 = sc.nextLine();
                    try {
                        newbranch.add(branchDetector(s1));
                    } catch (Exception e) {
                        System.out.println("No such branch exists");
                        flag=1;
                    }
                }
                if(flag==0) {
                    Station.stations.get(i).setBranchesallowed(newbranch);

                }else {
                    System.out.println("Try again");
                }
            }
            else {
                System.out.println("Invalid choice");

            }
        }
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

               Admin.removeStation();

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
                Student a = Student.students.get(i);
                if (!a.alloted) {

                    for (int j = 0; j < a.preferenceList.size(); j++) {

                        Station s = a.preferenceList.get(j);
                        if (s.requirmentsMatch(a)) {

                            if (s.getVacancy() > 0) {
                                if(a.subjectsCompleted.containsAll(s.compSub)) {
                                    a.stationAlloted = s;
                                    a.alloted = true;
                                    s.setVacancy(s.getVacancy() - 1);
                                    s.stationMeStudents.add(a);
                                    break;
                                }
                            } else {

                            }


                        }
                    }
                }
            }
            System.out.println("Round DONE");

            Student.writeStudent();
            Station.writeStation();
        } else {
            System.out.println("These students have not entered preference order");
        }
    }
}