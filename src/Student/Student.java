package Student;

import Admin.Admin;
import Branches.DUAL;
import Branches.FD;
import Station.Station;


import javax.swing.text.DefaultStyledDocument;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Student {
    /*
     * stationslist see
     * preference list update
     * allotedstation see
     * logout option
     */
    ArrayList<String> subjectsCompleted;
    private double cgpa;
    public static ArrayList<Student> students = new ArrayList<>();
    private String BITSID;
    private String name;

    private Branches.DUAL dualbranch;
    private Branches.FD singlebranch;
    private Branch branch;
    private String password;
    public Station stationAlloted;
    public boolean alloted;
    public ArrayList<Station> preferenceList= new ArrayList<>();


    public static HashMap<String, String> StudentCred = new HashMap<>();

    public static boolean checkForNullObjects() {
        int flag =0;
        boolean b=false;
        for (int i = 0; i < students.size(); i++) {
            Student s =students.get(i);
            if(s.preferenceList==null)
            {
                flag=1;
                System.out.println("Student "+s.getName()+" has not submitted preference order");
                b=false;
            }
            else {

                continue;
            }
        }
        if(flag==0)
        {
            b= true;
        }
        return b;

    }

    public ArrayList<Station> getPreferenceList() {
        return preferenceList;
    }

    public Branch getBranch() {
        return branch;
    }

    public String getBITSID() {
        return BITSID;
    }

    public void setStationAlloted(Station stationAlloted) {
        this.stationAlloted = stationAlloted;
    }

    public double getCgpa() {
        return cgpa;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public static void createStudent() throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name -");
        String name;
        name = sc.nextLine();
        System.out.println("Enter CGPA-");
        double cgpa = sc.nextDouble();
        sc.nextLine();
        while (!checkValidCGPA(cgpa)) {
            System.out.println("Enter CGPA again");
            cgpa = sc.nextDouble();
        }
        System.out.println("Enter BITS ID-");
        String BITSID;
        BITSID = sc.nextLine();
        while (!checkUniqueBITSID(BITSID)) {
            System.out.println("Enter your BITSID (id not unique)");
            BITSID = sc.nextLine();
        }//unique check

        Branches.FD s;
        while (true) {
            System.out.println("Enter Branch-");
            System.out.println("choose from - " +
                    "CHE,\n" +
                    "    CE,\n" +
                    "    CS,\n" +
                    "    EEE,\n" +
                    "    ECE,\n" +
                    "    ENI,\n" +
                    "    ME,\n" +
                    "    MANU,\n" +
                    "    PHARMA");
            String single;
            single = sc.nextLine();
            single.trim().toUpperCase();


            if (single.equals("CHE")) {
                s = FD.CHE;
                break;

            } else if (single.equals("CE")) {
                s = FD.CE;
                break;
            } else if (single.equals("CS")) {
                s = FD.CS;
                break;
            } else if (single.equals("EEE")) {
                s = FD.EEE;
                break;
            } else if (single.equals("ECE")) {
                s = FD.ECE;
            } else if (single.equals("ENI")) {
                s = FD.ENI;
                break;
            } else if (single.equals("ME")) {
                s = FD.ME;
                break;
            } else if (single.equals("MANU")) {
                s = FD.MANU;
                break;
            } else if (single.equals("PHARMA")) {
                s = FD.PHARMA;
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
        Branches.DUAL d;
        while (true) {
            System.out.println("and -" +
                    "PICK NULL IF SINGLE DEGREE");
            System.out.println("NULL,\n" +
                    "    BIO,\n" +
                    "    CHEM,\n" +
                    "    ECO,\n" +
                    "    MATH,\n" +
                    "    PHY");
            String dual = sc.nextLine();
            dual.trim().toUpperCase();


            if (dual.equals("BIO")) {
                d = DUAL.BIO;
                break;

            } else if (dual.equals("CHEM")) {
                d = DUAL.CHEM;
                break;

            } else if (dual.equals("ECO")) {
                d = DUAL.ECO;
                break;

            } else if (dual.equals("MATH")) {
                d = DUAL.MATH;
                break;

            } else if (dual.equals("PHY")) {
                d = DUAL.PHY;
                break;
            } else if (dual.equals("NULL")) {
                d = DUAL.NULL;
                break;
            } else {
                System.out.println("Invalid Input");
            }
        }

        ArrayList<String> subjectsCompleted = new ArrayList<>();
        System.out.println("Enter number of subjects completed");

        int x = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < x; i++) {
            System.out.println("Enter subject: ");
            subjectsCompleted.add(sc.nextLine());
        }
        System.out.println("Please Enter a Password:");
        String password = sc.nextLine();
        StudentCred.put(BITSID, password);


        new Student(cgpa, BITSID, password, name, s, d, subjectsCompleted, null);

        System.out.println("Successfully registered, please login");
    }


    public Student(double cgpa, String BITSID, String password, String name, Branches.FD singlebranch, Branches.DUAL dualbranch, ArrayList<String> subjectsCompleted, ArrayList<Station> preferenceList) {
        this.cgpa = cgpa;
        this.password = password;
        this.BITSID = BITSID;
        this.name = name;
        branch = new Branch(singlebranch, dualbranch);

        branch.single = singlebranch;
        this.alloted = false;
        branch.dual = dualbranch;
        this.subjectsCompleted = subjectsCompleted;
        this.stationAlloted = null;
        this.preferenceList = preferenceList;
        StudentCred.put(BITSID, password);
        students.add(this);


    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    void submitPriorityOrder() {
        Scanner sc = new Scanner(System.in);

        // asks priority order from student and updates it in the arraylist


        while (true) {
            System.out.println("Enter priority order in 'Jio;Google;Apple;Uber' format ");
            String str = sc.nextLine();
            String[] preforder = str.split(";");
            int f = 1;

            ArrayList<Station> arr = new ArrayList<Station>();
            for (int i = 0; i < preforder.length; i++) {
                int flag = 0;
                for (int j = 0; j < Station.stations.size(); j++) {
                    Station s = Station.stations.get(j);
                    if (s.getName().equals(preforder[i])) {
                        flag = 1;
                        arr.add(s);
                        break;
                    }
                }
                if (flag == 0) {
                    f = 0;
                    System.out.println("Invalid name-" + preforder[i]);
                    break;
                }
            }
            if (f == 1) {

                if(this.preferenceList!=null){
                if(preferenceList.removeAll(preferenceList))
                for (int i = 0; i < arr.size(); i++) {

                    preferenceList.add(arr.get(i));
                }
                System.out.println("Successfully updated station list");
                break;}
                else {
                    ArrayList<Station> temp = new ArrayList<Station>();
                    for (int i = 0; i < arr.size(); i++) {
                        temp.add(arr.get(i));
                    }
                    preferenceList = new ArrayList<Station>(temp);
                    break;
                }
            }
        }
    }



public void displayStudentMenu() {
    Scanner sc = new Scanner(System.in);

    //if round hasn't begun{

    if (!Admin.isStartedRound()) {
        while (true) {
            System.out.println("Enter C to Check Station List given by admin-");
            System.out.println("Enter S to submit priority order of stations-");
            System.out.println("Enter D to see your preference list");
            System.out.println("Enter L to logout: ");
            String str = sc.nextLine();
            char c = str.toUpperCase().charAt(0);
            if (c == 'C') {
                Station.printStationList();
            } else if (c == 'S') {

                this.submitPriorityOrder();

            } else if (c == 'L') {
                break;
            } else if (c == 'D') {
                if (this.preferenceList!=null) {
                    for (int i = 0; i < preferenceList.size(); i++) {
                        System.out.println(preferenceList.get(i));
                    }
                } else {
                    System.out.println("No preference list entered as of now.");
                }
            }

        }
    }
    else {

        while (true) {
            System.out.println("Enter S to see allotted station: ");
            System.out.println("Enter L to logout");
            char c = sc.nextLine().charAt(0);
            if (c == 'S') {
                if(stationAlloted!=null)
                {
                    System.out.println("Station allotted: "+stationAlloted.getName());
                    break;
                }
                else {
                    System.out.println("Station not allotted");
                }
            } else if (c=='L'){break;
            }
        }
    }


}

public static boolean checkValidCGPA(Double cgpa){
        if(cgpa>=0&&cgpa<=10){
        return true;
        }else{
        return false;
        }
        }

public static boolean checkUniqueBITSID(String ID){
        return!StudentCred.containsKey(ID);
        }


public Station getStationAlloted(){
        return stationAlloted;
        }

public static void writeStudent()throws IOException{
        //writes student in text file
        ArrayList<Student> e=new ArrayList<Student>(Student.getStudents());
        FileWriter fr=new FileWriter("C:\\Users\\Chandrashekar\\IdeaProjects\\hack\\studentList.txt");
        BufferedWriter br=new BufferedWriter(fr);
        for(int i=0;i<e.size();i++){
        Student s=e.get(i);
        br.write(s.name+",");
        br.write(s.cgpa+",");
        br.write(s.BITSID+",");
        br.write(Branch.branchPrinter(s.branch)+",");
        ArrayList<String> sub=new ArrayList<>(s.subjectsCompleted);
        String subs="";
        for(int j=0;j<sub.size();j++){
        subs=String.join(";",sub);
        }
        br.write(subs+",");
        ArrayList<String> pref=new ArrayList<>(Station.toArrayOfString(s.preferenceList));
        String prefs="";
        for(int j=0;j<sub.size();j++){
        prefs=String.join(";",pref);
        }
        br.write(prefs+",");
        try{
        br.write(s.stationAlloted.toString()+",");

        }catch(NullPointerException err){
        err.getMessage();
        }
        br.write("\n");
        }
        br.close();
        }

public String getName(){
        return name;
        }

public static void readStudent()throws IOException{
        FileReader fr=new FileReader("C:\\Users\\Chandrashekar\\IdeaProjects\\hack\\sampleStudentList.txt");
        BufferedReader br=new BufferedReader(fr);
        String newLine="";
        while((newLine=br.readLine())!=null){
        String[]str=newLine.split(",");
        Student s;
        String name=str[0];
        String cg=str[1];
        String ID=str[2];
        Branch b=Branch.branchDetector(str[3]);
        String[]subcompleted=str[4].split(";");
        String[]stationspref=str[5].split(";");
        String pass=str[6];
        ArrayList<Station> stationlistpref=new ArrayList<Station>();
        ArrayList<String> subsarraylist=new ArrayList<>(Arrays.asList(subcompleted));
        for(int i=0;i<Station.stations.size();i++){
        Station st1=Station.stations.get(i);
        for(int j=0;j<stationspref.length;j++){
        if(st1.getName().equals(stationspref[j])){
        stationlistpref.add(st1);
        }
        }
        }


        s=new Student(Double.parseDouble(cg),ID,pass,name,b.single,b.dual,subsarraylist,stationlistpref);

        }

        br.close();
        }


//    public static void provideStudentList() throws IOException {
//        FileReader fr = new FileReader("studentlist");
//        BufferedReader br = new BufferedReader(fr);
//
//        String newLine;
//        if((newLine = br.readLine())!=null)
//        {
//            String[] arr= newLine.split(",");
//            Student s=null;
//            s.name=arr[0];
//            s.cgpa= Double.parseDouble(arr[1]);
//            s.BITSID = arr[2];
//            String[] brr = arr[3].split(";");
//            for (int i = 0; i < brr.length; i++) {
//                s.subjectsCompleted.add(brr[i]);
//            }
//
//            String[] pref = arr[4].split(";");
//
//            int flag =1;
//            for (int i = 0; i < pref.length; i++) {
//
//                flag = 0;
//                for (int j = 0; j <Station.stations.size(); j++) {
//                    if(Station.stations.get(j).getName().equals(pref[i]))
//                    {
//                        flag =1;
//                        Station st = Station.stations.get(j);
//                        s.preferenceList.add(st);
//                        students.add(s);
//                        break;
//                    }
//                }
//                if(flag==0)
//                {
//                    break;
//                }
//            }
//            if(flag==0)
//            {
//                System.out.println("Invalid Station name in preference list");
//            }
//
//        }
//    }


        }
