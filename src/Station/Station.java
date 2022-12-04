package Station;

import Branches.DUAL;
import Branches.FD;
import Student.Branch;
import Student.Student;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Station {

    public static ArrayList<Station> stations = new ArrayList<Station>();
    String name;
    public double CGCrtieria;
    ArrayList<Branch> branchesallowed;
    int vacancy;
    public ArrayList<String> compSub = new ArrayList<>();
    public ArrayList<Student> stationMeStudents = new ArrayList<Student>();


    public ArrayList<Branch> getBranchesallowed() {
        return branchesallowed;
    }


    public static void printStationList() {
        for (int i = 0; i < stations.size(); i++) {
            Station s = stations.get(i);
            System.out.print("Station name: " + s.name + "  " + "CG Criteria: " + s.getCGCrtieria() + "  " + "Branches Allowed: ");
            for (int j = 0; j < s.branchesallowed.size(); j++) {
                System.out.print(Branch.branchPrinter(s.branchesallowed.get(j)) + " ");
            }
            System.out.println();
        }
    }


    @Override
    public String toString() {
        return this.name;
    }

    public static void readStation() throws IOException {
        FileReader fr = new FileReader("C:\\Users\\Chandrashekar\\IdeaProjects\\hack\\stations.txt");
        BufferedReader br = new BufferedReader(fr);
        String newLine = "";

        while ((newLine = br.readLine()) != null) {
            String[] str = newLine.split(",");
            Station s;
            String name = str[0];
            double cg = Double.parseDouble(str[1]);
            String[] branches = str[2].split(";");
            ArrayList<Branch> branchArrayList = new ArrayList<Branch>();
            for (int i = 0; i < branches.length; i++) {
                Branch b = Branch.branchDetector(branches[i]);
                branchArrayList.add(b);
            }
            int vac = Integer.parseInt(str[3]);
            String[] arr1 = str[4].split(";");
            ArrayList<String> arr2 = new ArrayList<>();
            for (int i = 0; i < arr1.length; i++) {
                arr2.add(arr1[i]);
            }
            s = new Station(name, cg, branchArrayList, vac,arr2);

        }
        br.close();


    }

    public static void writeStation() throws IOException {
        File f = new File("C:\\Users\\Chandrashekar\\IdeaProjects\\hack\\stationAllotment.txt");
        FileWriter fr = new FileWriter(f);
        BufferedWriter writer = new BufferedWriter(fr);
        for (int i = 0; i < stations.size(); i++) {
            Station s = stations.get(i);
            writer.write(s.name + ",");
            writer.write(s.CGCrtieria + ",");
            for (int j = 0; j < s.branchesallowed.size(); j++) {
                Branch b = s.branchesallowed.get(j);
                if (j != s.branchesallowed.size() - 1) {
                    writer.write(Branch.branchPrinter(b) + ";");
                } else {
                    writer.write(Branch.branchPrinter(b));
                }
            }
            for (int j = 0; j < s.stationMeStudents.size(); j++) {
                Student st = s.stationMeStudents.get(j);
                if (j != s.stationMeStudents.size() - 1) {
                    writer.write(st.getName() + ";");
                } else {
                    writer.write(st.getName());
                }
            }
            writer.write("\n");

        }
        writer.close();
    }

    public static ArrayList<String> toArrayOfString(ArrayList<Station> s) {
        ArrayList<String> stringOf = new ArrayList<>();
        for (int i = 0; i < s.size(); i++) {
            String str = s.get(i).toString();
            stringOf.add(str);
        }
        return stringOf;

    }

    public boolean requirmentsMatch(Student x) {
        Branch b = x.getBranch();
        ArrayList<Branch> arr = new ArrayList<Branch>(this.branchesallowed);

        int flag = 0;
        for (int i = 0; i < arr.size(); i++) {
            Branch ballowed = branchesallowed.get(i);
            if (Branch.compareBranch(ballowed, b)) {
                if (this.CGCrtieria <= x.getCgpa()) {

                    flag = 1;
                    break;

                }

            }
        }
        if (flag == 0) {
            return false;
        } else {
            return true;

        }
    }

    public int getVacancy() {
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

    public void setBranchesallowed(ArrayList<Branch> branchesallowed) {
        this.branchesallowed = branchesallowed;
    }


    public Station(String name, double CGCrtieria, ArrayList<Branch> b, int vacancy, ArrayList<String> compSub) {
        this.name = name;
        this.CGCrtieria = CGCrtieria;
        branchesallowed = b;
        this.vacancy = vacancy;
        this.compSub=compSub;
        stations.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCGCrtieria() {
        return CGCrtieria;
    }

    public void setCGCrtieria(double CGCrtieria) {
        this.CGCrtieria = CGCrtieria;
    }


    //    private void removeStation(Station s) {
//        boolean remove = stations.remove(s);
//        if (!remove) {
//            System.out.println("Station doesn't exist");
//        } else {
//            System.out.println("Station removed.");
//        }
//        //stations.txt remove also
//    }
    public static boolean checkForNullObjects() {
        if (stations == null) {
            return false;
        } else {
            return true;
        }
    }


}

