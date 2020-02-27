package se.ecutb.data;

import se.ecutb.model.Person;
import se.ecutb.util.PersonGenerator;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * Skapa implementationer till alla metoder. Jag har redan skrivit en metodimplementation för hjälp
 */
public class DataStorageImpl implements DataStorage {

    private static final DataStorage INSTANCE;

    static {
        INSTANCE = new DataStorageImpl();
    }

    private List<Person> personList;

    private DataStorageImpl(){
        personList = PersonGenerator.getInstance().generate(1000);
    }

    static DataStorage getInstance(){
        return INSTANCE;
    }


    @Override
    public List<Person> findMany(Predicate<Person> filter) {

        return personList.stream()
                .filter(filter)
                .collect(Collectors.toList());

    }

    @Override
    public Person findOne(Predicate<Person> filter) {

        return personList.stream()
                .filter(filter)
                .findFirst()
                .get();
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString){

        return personToString.apply(findOne(filter));

    }

    @Override
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString){

        return personList.stream()
                .filter(filter)
                .map(personToString)
                .collect(Collectors.toList());

    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer){

        personList.stream()
                .filter(filter)
                .forEach(consumer);


    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator){

        return personList.stream()
                .sorted(comparator)
               .collect(Collectors.toList());
    }

    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator){


        return personList.stream()
                .filter(filter)
                .sorted(comparator)
                .collect(Collectors.toList());
    }




}
