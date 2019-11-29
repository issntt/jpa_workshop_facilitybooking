package model;

import javax.persistence.MappedSuperclass;

// In this situation, we only need to store ONE type of entity,
// Member, to database, not Person.
// 1. Person does NOT need to be persisted, therefore, use Mapped Superclass
// 2. If there is other classes other than Member that also 1. Extends from 
// Person and 2. Needs to be persisted, we also need to declare Person as
// @Inheritance
// In this case, Person does NOT create such whole class tree to be stored, 
// so, no need to use @Inheritance.
@MappedSuperclass
public class Person {

    private String surname;
    private String firstName;
    private String secondName;
    
    public Person() {}

    public Person (String surname, String firstName, String secondName) {
        this.surname = surname;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
    	this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }
    
    public void setSecondName(String secondName) {
    	this.secondName = secondName;
    }

    public String toString () {
        String fullName = firstName;
        if (secondName != null) {
            fullName += " " + secondName;
        }
        fullName += " " + surname;
        return (fullName);
    }
}
