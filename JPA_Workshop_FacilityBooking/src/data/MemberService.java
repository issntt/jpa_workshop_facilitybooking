package data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Member;

public class MemberService {
	protected EntityManager em;
	
	public MemberService(EntityManager em) {
		this.em = em;
	}
	
	public Member createMember(int memberNumber, String surname, String firstName, String secondName) {
		Member member = new Member();
		member.setMemberNumber(memberNumber);
		member.setSurname(surname);
		member.setFirstName(firstName);
		member.setSecondName(secondName);
		
		// At this point, the object member is just a normal Java object
		// We need to ask the entity manager to take care of it. In this case,
		// ask the entity manager to persist it (i.e. store it the to database)
		em.persist(member);
		
		// At this point, the object is supposed to be persisted to the database. 
      // However, the change is not yet reflected into
      // database until being commit()
		return member;
	}
	
	public Member updateMember(int id, String surname, String firstName, String secondName, Integer memberNumber) {
      Member member = findMember(id);
      
      if (member == null) {
         return null;
      }
     
      // At this point, the object member is managed
      // in the Persistent Context because it is retrieved
      // using the entity manager in the previous line
      if (surname != null) {
         member.setSurname(surname);
      }
      
      if (firstName != null) {
         member.setFirstName(firstName);
      }
      
      if (secondName != null) {
         member.setSecondName(secondName);
      }
      
      // By using the wrapper class Integer for the
      // input memberNumber, we can check against null
      if (memberNumber != null) {
         member.setMemberNumber(memberNumber.intValue());
      }
      
      // At this point, the object is updated. 
      // However, the change is not yet reflected into
      // database until being commit()
      return member;
   }
	
	public void removeMember(int id) {
	   Member member = findMember(id);
	   
	   // At this point, the object member is managed
	   if (member != null) {
         em.remove(member);
      }
	   
	   // At this point, the object is removed.
	   // However, the change is not yet reflected into
	   // database until being commit();
	}
	
	public Member findMember(int id) {
	   return em.find(Member.class, id);
	}
	
	public List<Member> listAllMembers() {
	   String q = "SELECT m FROM Member m";
	   
	   TypedQuery<Member> query = em.createQuery(q, Member.class);
      return query.getResultList();
	}
	
	public List<Member> listMembers(String name) {
	   String q = "SELECT m " + 
	         " FROM Member m " +
	         " WHERE m.surname = :name " +
	         "    OR m.firstName = :name " +
	         "    OR m.secondName = :name"; 
      
      TypedQuery<Member> query = em.createQuery(q, Member.class)
            .setParameter("name", name);
      return query.getResultList();
	}
}
