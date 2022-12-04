import Admin.Admin;
import Branches.FD;
import Station.Station;
import Student.Student;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.rmi.StubNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

import static Admin.Admin.admin;
import static Student.Student.StudentCred;

public class Main {
//homepage pe quit

    public static void main(String[] args) throws IOException {
        Station.readStation();
        Student.readStudent();
        Scanner sc = new Scanner(System.in);
        char choice;
        char c = 0;
        while(c != 'Q') {
            System.out.println("HOMEPAGE");
            System.out.println("Welcome to Placement Manager Program :) ");


            System.out.println("Enter S to Login/Register as Student");
            System.out.println("Enter A to access as Admin");
            System.out.println("Enter Q to quit");
            c = sc.nextLine().toUpperCase().charAt(0);
            if (c == 'S') {
                System.out.println("Enter L to Login or R to Register");
                char c2 = sc.nextLine().toUpperCase().charAt(0);
                while(true) {
                    if (c2 == 'L') {


                            System.out.println("Enter ID:");
                            String s1 = sc.nextLine();
                            System.out.println("Enter password");
                            String s2 = sc.nextLine();
                            if (StudentCred.containsKey(s1) && StudentCred.get(s1).equals(s2)) {
                                System.out.println("Logged In!");
                                Student st=Student.getStudents().get(0);
                                for (int i = 0; i < Student.getStudents().size(); i++) {
                                    Student s= Student.getStudents().get(i);
                                    if(s.getBITSID().equals(s1))
                                    {
                                        st = s;
                                    }
                                }
                                st.displayStudentMenu();
                            } else {
                                System.out.println("Wrong Credentials.");
                            }

                        break;

                    } else if (c2 == 'R') {
                        Student.createStudent();
                        break;
                    } else
                    {
                        System.out.println("Wrong input");
                    }
                }
            } else if (c == 'A') {
                Admin.adminLoginPage();
            }
            else if(c == 'Q') {
                c='Q';

            }

            else {
                System.out.println("Invalid input");
            }

        }

    }

}

