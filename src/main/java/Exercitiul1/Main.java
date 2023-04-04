package Exercitiul1;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.stream.Collectors;

public class Main {
    public static List<Angajat> citire(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            File file=new File("src/main/resources/angajati.json");
            List<Angajat> angajat = mapper.readValue(file, new TypeReference<List<Angajat>>() {});
            return angajat;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        List<Angajat> angajat=citire();
        int op;
        do{
            System.out.println("0. Iesire");
            System.out.println("1. Afisare lista angajati");
            System.out.println("2. Afiasare angajati cu salariu peste 2500");
            System.out.println("3. Creare lista angajati din aprilie");
            System.out.println("4. Afisare angajati fara functii de conducere");
            System.out.println("5. Afisare nume angajati cu majuscule");
            System.out.println("6. Afisare salariu sub 3000");
            System.out.println("7. Afisare date pentru primul angajat");
            System.out.println("8. Afisare statistici salariu");
            System.out.println("9. Exista vreun Ion in firma?");
            System.out.println("10. Afisare numar de persoane angajate in vara anului precedent");
            System.out.println("\nDati optiunea: ");
            op= scan.nextInt();

            switch (op){
                case 0:
                    System.out.println("Executie terminata...");
                    break;

                case 1:
                    angajat.forEach(System.out::println); //folosind referinte
                    System.out.println();
                    break;

                case 2:
                    angajat.stream()
                            .filter(angajat1 -> angajat1.getSalariu()>2500)
                            .forEach(System.out::println);
                    System.out.println();
                    break;

                case 3:
                    LocalDate data_curenta = LocalDate.now();
                    int an_curent=data_curenta.getYear();
                    List<Angajat> angajati_aprilie=angajat
                            .stream()
                            .filter(post->post.getPostul().toLowerCase().contains("sef")||post.getPostul().toLowerCase().contains("director"))
                            .filter(luna->luna.getData_angajarii().getYear()==an_curent-1&&luna.getData_angajarii().getMonth()== Month.APRIL)
                            .collect(Collectors.toList());
                    System.out.println(angajati_aprilie);
                    break;

                case 4:
                    List<Angajat> salarii = angajat
                            .stream()
                            .filter(post->!post.getPostul().toLowerCase().contains("sef")&&!post.getPostul().toLowerCase().contains("director"))
                                    .collect(Collectors.toList());
                    Collections.sort(salarii, (s1,s2) -> {
                        return (int) (s2.getSalariu() - s1.getSalariu());
                    });
                    salarii.stream()
                            .forEach(System.out::println);
                    System.out.println();
                    break;

                case 5:
                    List<String> NUME = angajat
                            .stream()
                            .map(num -> num.getNume().toUpperCase())
                            .collect(Collectors.toList());
                    System.out.println(NUME);
                    System.out.println();
                    break;

                case 6:
                    List<Float> salariu = angajat
                            .stream()
                            .map(Angajat::getSalariu)
                            .filter(sal -> sal <3000)
                            .collect(Collectors.toList());
                        System.out.println(salariu);
                    break;

                case 7:
                    Optional<Angajat> primul = angajat
                            .stream()
                            .min(Comparator.comparing(a->a.getData_angajarii().getYear()));
                        if(primul.isPresent())
                            System.out.println("Primul angajat: " + primul.get());
                        else
                            System.out.println("Nu exista angajati");
                    break;

                case 8:
                    DoubleSummaryStatistics statistica = angajat
                        .stream()
                        .collect(Collectors.summarizingDouble(Angajat::getSalariu));
                    System.out.println("Salariul minim: " + statistica.getMin());
                    System.out.println("Salariul mediu: " + statistica.getAverage());
                    System.out.println("Salariul maxim: " + statistica.getMax());
                    break;

                case 9:
                    Optional<Angajat> Ion = angajat
                            .stream()
                            .filter(ion->ion.getNume().contains("Ion"))
                            .findAny();
                    if(Ion.isPresent())
                        System.out.println("Firma are cel putin un Ion");
                    else
                        System.out.println("Firma nu are nici un Ion angajat");
                    break;

                case 10:
                    LocalDate data = LocalDate.now();
                    int an_trecut = data.getYear()-1;
                    long vara = angajat
                            .stream()
                            .filter(v->v.getData_angajarii().getYear()==an_trecut&&(v.getData_angajarii().getMonth()==Month.JUNE||
                                    v.getData_angajarii().getMonth()==Month.JULY||v.getData_angajarii().getMonth()==Month.AUGUST))
                            .count();
                    System.out.println("Numarul persoanelor angajate in vara anului trecut: " + vara);
                    break;

                default:
                    System.out.println("Optiunea nu exista!");
                    break;
            }
        }while(op!=0);
    }
}