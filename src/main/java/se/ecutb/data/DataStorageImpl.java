package se.ecutb.data;

import se.ecutb.model.Person;
import se.ecutb.util.PersonGenerator;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


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
        List<Person> result = new ArrayList<>();
        for(Person person : personList){
            if(filter.test(person)){
                result.add(person);
            }
        }
        return result;
    }

    @Override
    public Person findOne(Predicate<Person> filter) {
        for(Person person : personList){
            if(filter.test(person)){
                return person;
            }
        }
        return null;
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString){

        return personToString.apply(findOne(filter));

    }

    @Override
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString){

        List<String> result = new ArrayList<>();
        for(Person person : personList){
            if(filter.test(person)){
                result.add(personToString.apply(person));
            }
        }
        return result;
    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer){

        for(Person person : personList){
            if(filter.test(person)){
                consumer.accept(person);
            }
        }
    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator){

        List<Person> result = new ArrayList<>(personList);

        result.sort(comparator);

        return result;
    }

    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator){


        List<Person> result = new ArrayList<>();
        for(Person person : personList){
            if(filter.test(person)){
                result.add(person);
            }
        }

        result.sort(comparator);

        return result;
    }




}
