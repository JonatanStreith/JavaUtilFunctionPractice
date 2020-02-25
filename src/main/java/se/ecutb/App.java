package se.ecutb;


import se.ecutb.data.DataStorage;
import se.ecutb.model.Gender;
import se.ecutb.model.Person;

import javax.crypto.spec.PSource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class App
{

    private static DataStorage dataStorage;

    static {
        dataStorage = DataStorage.INSTANCE;
    }

    public static void main( String[] args )
    {
        getErik();            //  1

        getFemales();         //  2

        bornAfter();          //  3

        find123();            //  4

        find456ToString();    //  5

        findEBoys();          //  6

        findAgeBelowTen();    //  7

        printAllUlfs();       //  8

        printAllDoubles();    //  9

        printPalindromes();   //  10

        sortAs();             //  11

        sort1950Reverse();    //  12

        sortMultiTypes();     //  13

    }



    public static <T> void printList(List<T> list){
        for (T t: list) {
            System.out.println(t);
        }
    }

    public static long getAge(LocalDate birthDate){

        return birthDate.until(LocalDate.now(), ChronoUnit.YEARS);
    }

    public static boolean isPalindrome(String in){

        StringBuilder sb = new StringBuilder(in);
        sb.reverse();
        return sb.toString().equalsIgnoreCase(in);

    }

    //----------------

    public static void getErik(){
        List<Person> Eriks = dataStorage.findMany(p -> p.getFirstName().equals("Erik"));


        printList(Eriks);
    }

    public static void getFemales(){

        List<Person> females = dataStorage.findMany(p -> p.getGender()== Gender.FEMALE);

        printList(females);
    }


    public static void bornAfter(){

        LocalDate newMillenium = LocalDate.of(2000, 01, 01);
        List<Person> futureBabies = dataStorage.findMany(p -> (p.getBirthDate().isAfter(newMillenium)));
        printList(futureBabies);
    }

    public static void find123(){

        Person oneTwoThree = dataStorage.findOne(p -> p.getId() == 123);

        System.out.println(oneTwoThree);
    }

    public static void find456ToString(){

        String toBeFound = dataStorage.findOneAndMapToString(p -> p.getId() == 456, p -> "Name: " + p.getFirstName() + " " + p.getLastName() + " born " + p.getBirthDate() );

        System.out.println(toBeFound);
    }

    public static void findEBoys(){

        List<String> EBoys = dataStorage.findManyAndMapEachToString(p -> p.getFirstName().substring(0, 1).equals("E") && p.getGender() == Gender.MALE, p -> "Name: " + p.getFirstName() + " " + p.getLastName() + " born " + p.getBirthDate() );

        printList(EBoys);

    }

    public static void findAgeBelowTen() {

        List<String> belowNine = dataStorage.findManyAndMapEachToString(p -> getAge(p.getBirthDate()) < 10, p -> p.getFirstName() + " " + p.getLastName() + " " + getAge(p.getBirthDate()) + " years" );

        printList(belowNine);

    }

    public static void printAllUlfs() {

        dataStorage.findAndDo(p -> p.getFirstName().equals("Ulf"), p -> System.out.println(p) );
    }

    public static void printAllDoubles() {

        dataStorage.findAndDo(p -> p.getLastName().toLowerCase().contains(p.getFirstName().toLowerCase()), p -> System.out.println(p) );
    }

    public static void printPalindromes() {

        dataStorage.findAndDo(p -> isPalindrome(p.getFirstName()), p -> System.out.println(p.getFirstName() + " " + p.getLastName()));
    }

    public static void sortAs(){

        List<Person> theAs = dataStorage.findAndSort(p -> p.getFirstName().substring(0, 1).equals("A"), Comparator.comparing(p -> p.getBirthDate()));

        printList(theAs);

    }

    public static void sort1950Reverse(){

        List<Person> reverse50s = dataStorage.findAndSort(p -> p.getBirthDate().getYear() < 1950, (p1, p2) -> p2.getBirthDate().compareTo(p1.getBirthDate()) );

        printList(reverse50s);
    }

    public static void sortMultiTypes(){
        List<Person> multi = dataStorage.findAndSort( Comparator
                .comparing(Person::getLastName)
                .thenComparing(Person::getFirstName)
                .thenComparing(Person::getBirthDate)
        );
        printList(multi);
    }

/*    public static void sortMultiTypesBuggy(){
        List<Person> multi = dataStorage.findAndSort( Comparator
                .comparing(p -> p.getLastName())
                .thenComparing(p -> p.getFirstName())
                .thenComparing(p -> p.getBirthDate())
        );
        printList(multi);
    }*/


}
