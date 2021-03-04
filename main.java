import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Person{
    private String cnp;

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNr_tel() {
        return nr_tel;
    }

    public void setNr_tel(String nr_tel) {
        this.nr_tel = nr_tel;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    String nr_tel;
    int varsta;

    public Person(String cnp, String nr_tel, int varsta) {
        this.cnp = cnp;
        this.nr_tel = nr_tel;
        this.varsta = varsta;
    }
    public Person(){

    }
}


class Profesor extends Person{
    public Profesor(String cnp, String nr_tel, int varsta) {
        super(cnp, nr_tel, varsta);
    }

    private int salariu;
    public Profesor(int salariu){
        this.salariu=salariu;
    }

    public int getSalariu() {
        return salariu;
    }

    public void setSalariu(int salariu) {
        this.salariu = salariu;
    }
}

class Course{
    private String nume;
    private int nr_credite;

    public Course(String nume, int nr_credite) {
        this.nume = nume;
        this.nr_credite = nr_credite;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getNr_credite() {
        return nr_credite;
    }

    public void setNr_credite(int nr_credite) {
        this.nr_credite = nr_credite;
    }
}

class Student extends Person implements I1{
    private ArrayList<Course> courses=new ArrayList<>();
    private int nr_credite_max=30;
    public ArrayList<Course> getCourse() {
        return courses;
    }

    public Student(String cnp, String nr_tel, int varsta) {
        super(cnp, nr_tel, varsta);
    }

    public void setCourse(ArrayList<Course> course) {
        this.courses = course;
    }
    HashMap<Course,Integer> carnet=new HashMap<>();

    public void add_mark(Course course,Integer mark){
        if(nr_credite_max-course.getNr_credite()>=0){
            carnet.put(course,mark);
            nr_credite_max-=course.getNr_credite();
            courses.add(course);
        }
        else{
            System.out.println("Numar credite depasit");
        }
    }
    public int nr_credite_actuale(HashMap<Course,Integer> carnet){
        Iterator it = carnet.entrySet().iterator();
        int sum=0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Course c=(Course)pair.getKey();
            if((Integer)pair.getValue()>4){
                sum+=c.getNr_credite();
            }
            //sum+=(Integer)pair.getValue();
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Nr credite: " + String.valueOf(this.nr_credite_actuale(carnet));
    }

    @Override
    public double calculMedie() {
        Iterator it = carnet.entrySet().iterator();
        int sum=0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Course c=(Course)pair.getKey();
            if((Integer)pair.getValue()>4){
                sum+=(Integer)pair.getValue()*c.getNr_credite();
            }
            //sum+=(Integer)pair.getValue();
        }
        //System.out.println(sum);
        DecimalFormat format = new DecimalFormat("#.##");
        String value=format.format((double)sum/30);
        return Double.valueOf(value);
    }
}



class Grupa implements I1{
    private int nr_grupa;
    private String sectie;
    private ArrayList<Student> studenti=new ArrayList<>();

    public Grupa(int nr_grupa, String sectie, ArrayList<Student> studenti) {
        this.nr_grupa = nr_grupa;
        this.sectie = sectie;
        this.studenti = studenti;
    }

    public int getNr_grua() {
        return nr_grupa;
    }

    public void setNr_grua(int nr_grua) {
        this.nr_grupa = nr_grua;
    }

    public String getSectie() {
        return sectie;
    }

    public void setSectie(String sectie) {
        this.sectie = sectie;
    }

    public ArrayList<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(ArrayList<Student> studenti) {
        this.studenti = studenti;
    }

    @Override
    public double calculMedie() {
        double medie=0;
        for (Student s:
             studenti) {
            medie+=s.calculMedie();
        }
        DecimalFormat format = new DecimalFormat("#.##");

        return Double.valueOf(format.format((double)medie/studenti.size()));
    }

    @Override
    public String toString() {
        return "Grupa " +
                nr_grupa +
                ", sectie= " + sectie + " ," +
                "Medie grupa= " + String.valueOf(this.calculMedie());
    }
}


public class main {
    public static void main(String[] args) {
        Student p=new Student("5123456111111","0744444444",19);

        p.add_mark(new Course("Mate",4),7);
        p.add_mark(new Course("Info",6),8);

        Student p1=new Student("5456123000000","0755555555",20);
        p1.add_mark(new Course("Mate",4),7);
        p1.add_mark(new Course("Fizica",5),8);
        p1.add_mark(new Course("Ceva Materie",4),4);

        ArrayList<Student> s=new ArrayList<>();
        s.add(p);
        s.add(p1);

        Grupa g1=new Grupa(1,"Romana",s);
        System.out.println("Student 1,medie:");
        System.out.println(p.calculMedie());
        System.out.println(p.toString());
        System.out.println("Student 2,medie:");
        System.out.println(p1.calculMedie());
        System.out.println(p1.toString());

        System.out.println(g1.toString());

        //System.out.println(g1.calculMedie());

    }
}


interface I1{
    public double calculMedie();

}
