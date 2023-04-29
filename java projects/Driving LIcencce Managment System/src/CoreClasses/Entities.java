package CoreClasses;
import java.util.*;
public class Entities {
    public driver dri = new driver();
    public class driver {
        public String Did;
        public String Fname;
        public String Lname;
        public String Sex;
        public Date DOB;
        public String Phone;
        public String Email;
        public String Address;
    }

    public class School {
        String Sid;
        String Name;
        String Phone;
        String Email;
        String Address;
    }

    public class Course {
        String Cid;
        String Name;
    }

    //public Hospital hos = new Hospital();
    public static class Hospital {
        public int Hid;
        public String Address;
        public String Email;
        public String Name;
        public String Pass;
        public String Phone;
    }
}


