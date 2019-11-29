package application;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import data.FacilityService;
import data.MemberService;
import model.Facility;
import model.Member;

// TIPS:
// 1. You don't need to create database tables if you change the Hibernate settings
// "hibernate.hbm2ddl.auto" in the persistence.xml to "create"
// However, you'll lose all data every time you run the application
// One way is only turn the above feature on for the first run, then turn it 
// off on the subsequent runs.

// 2. There are different ways to test this application.
// For example, below is one way:
// - In the first run, you may only create and persist entities.
// Then check the change in database
// - In the second run, you may only update entities.
// Then check the change in database
// - In the third run, you may only display the data from
// database, using JPA to retrieve and transform to Java
// objects. E.g. listMembers
public class ClubApplication {
   public static void main(String[] args) {
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPAWorkshop");
      EntityManager em = emf.createEntityManager();
      MemberService ms = new MemberService(em);
      FacilityService fs = new FacilityService(em);

//--------- Dealing with Members ----------------------
		createAndPersistMembers(em, ms);
//    updateMember(em, ms);
//		listMembers(ms);
//		listMembersByName(ms);
//		removeMember(em, ms);

//----------- Dealing with Facilities --------------------
		createAndPersistFacilities(em, fs);
//      updateFacility(em, fs);    
//      listFacilities(fs);
//      listFacilitiesByName(fs);

   }

   /**
    * 
    * @param em to begin and commit transtion
    * @param ms to create and persist data
    */
   static void createAndPersistMembers(EntityManager em, MemberService ms) {
      // All actions against database must be inside a transaction
      em.getTransaction().begin();
      ms.createMember(101, "Einstein", "Albert", null);
      ms.createMember(102, "Picasso", "Pablo", "Ruiz");

      em.getTransaction().commit();
      System.out.println("Members have been persisted");
   }

   static void updateMember(EntityManager em, MemberService ms) {
      // In real applications, we almost always retrieve data before updating.
      // Therefore, we will have the id of the record before updating.
      // Here, let's assume that we want to update the first object of
      // Member, we'll get its id and update accordingly

       Integer id = getIdOfFirstMember(ms);
       if (id != null) {
          updateMemberNumber(em, ms, id, 200);
       } else {
          System.out.println("There is no member to update");
       }
   }
   
   // Using Integer instead of int, we can check
   // against null
   static Integer getIdOfFirstMember(MemberService ms) {
      List<Member> members = ms.listAllMembers();

      if (members.size() == 0) {
         return null;
      }

      return members.get(0).getId();
   }

   // This method will update the number member given an id of a member, and
   // We need EntityManager object to begin and commit transaction
   // We need MemberService object to call its updateMember method
   static void updateMemberNumber(EntityManager em, MemberService ms, int id, int newMemberNumber) {
      // All actions against database must be inside a transaction
      em.getTransaction().begin();

      // Here, we only want to update member number, all the name fields are null
      // In other occasions, we can also update the name fields as well
      ms.updateMember(id, null, null, null, newMemberNumber);

      em.getTransaction().commit();
   }

   static void listMembers(MemberService ms) {
      List<Member> members = ms.listAllMembers();

      for (Member member : members) {
         System.out.println(member);
      }
   }

   static void listMembersByName(MemberService ms) {
      List<Member> members = ms.listMembers("Albert");

      for (Member member : members) {
         System.out.println(member);
      }
   }

   static void removeMember(EntityManager em, MemberService ms) {
      em.getTransaction().begin();
      ms.removeMember(1);
      em.getTransaction().commit();
   }

   static void createAndPersistFacilities(EntityManager em, FacilityService fs) {
      // All actions against database must be inside a transaction
      em.getTransaction().begin();
      fs.createFacility("Chair", "Office chair");
      fs.createFacility("Meeting room 3A", "Meeting room at 3rd floor");
      fs.createFacility("Meeting room 3B", "Meeting room at 3rd floor");
      fs.createFacility("Meeting room 4A", "Meeting room at 4th floor");

      em.getTransaction().commit();
      System.out.println("Facilities have been persisted");
   }

   static void updateFacility(EntityManager em, FacilityService fs) {
      // All actions against database must be inside a transaction
      em.getTransaction().begin();

      // Here, we only want to update member number, all the name fields are null
      // In other occasions, we can also update the name fields as well
      Facility facitily = fs.updateFacility(1, "Super chair", "Chair for supper");

      if (facitily == null) {
         System.out.println("Can't find the facility id");
      }
      
      em.getTransaction().commit();
   }
   
   static void listFacilities(FacilityService fs) {
      List<Facility> facilities = fs.listAllFacilities();

      for (Facility facility : facilities) {
         System.out.println(facility);
      }
   }
   
   static void listFacilitiesByName(FacilityService fs) {
      List<Facility> facilities = fs.listFacilities("room");

      for (Facility facility : facilities) {
         System.out.println(facility);
      }
   }
}
