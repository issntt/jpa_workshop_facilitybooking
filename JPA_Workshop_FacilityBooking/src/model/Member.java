package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
// Note that "member" is a reserved word in MySQL database. Therefore we 
// map to table Members in the database instead, to avoid unnecessary troubles.
@Table(name = "Members")
public class Member extends Person implements Comparable<Member> {

	@Id
	@GeneratedValue
	private int id;
	
	private int memberNumber;

	public Member () {}
	
    public Member (String surname, String firstName, String secondName,
						int memberNumber) {
        super (surname, firstName, secondName);
        this.memberNumber = memberNumber;
    }

    public int getId() {
       return id;
    }

    public void setId(int id) {
       this.id = id;
    }
    
    public int getMemberNumber () {
        return memberNumber;
    }
    
    public void setMemberNumber (int memberNumber) {
    	this.memberNumber = memberNumber;
    }
    
    public String toString () {
        return (memberNumber + " - " + super.toString ());
    }
    //  Added so that Members can be sorted by membership number
    public int compareTo (Member other) {
        return (getMemberNumber() - other.getMemberNumber());
    }


}
