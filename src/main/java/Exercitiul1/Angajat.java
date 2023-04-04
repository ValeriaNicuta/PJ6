package Exercitiul1;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

interface Filtru<T>{
    public boolean test(T p);
}
public class Angajat {
    private String nume;
    private String postul;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate data_angajarii;
    private float salariu;
    public String getNume(){
        return nume;
    }
    public String getPostul() {
        return postul;
    }
    public LocalDate getData_angajarii() {
        return data_angajarii;
    }
    public float getSalariu() {
        return salariu;
    }
    public void setNume(String nume) {
        this.nume = nume;
    }
    public void setPostul(String postul) {
        this.postul = postul;
    }
    public void setData_angajarii(LocalDate data_angajarii) {
        this.data_angajarii = data_angajarii;
    }
    public void setSalariu(float salariu) {
        this.salariu = salariu;
    }
    public Angajat(){}
    public Angajat(String nume, String postul, LocalDate data_angajarii, float salariu){
        super();
        this.nume=nume;
        this.postul=postul;
        this.data_angajarii=data_angajarii;
        this.salariu=salariu;
    }
    public String toString(){
        return nume + ", " + postul + ", " + data_angajarii + ", " + salariu + " RON";
    }
}
