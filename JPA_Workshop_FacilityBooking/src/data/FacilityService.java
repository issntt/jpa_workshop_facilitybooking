package data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Facility;


public class FacilityService {
protected EntityManager em;
   
   public FacilityService(EntityManager em) {
      this.em = em;
   }
   
   public Facility createFacility(String name, String description) {
      Facility facility = new Facility();
      facility.setName(name);
      facility.setDescription(description);
      
      em.persist(facility);
      
      return facility;
   }
   
   public Facility updateFacility(int id, String name, String description) {
      Facility facility = findFacility(id);
      
      if (facility == null) 
         return null;
     
      if (name != null) {
         facility.setName(name);
      }
      
      if (description != null) {
         facility.setDescription(description);
      }
      
      return facility;
   }
   
   public void removeFacility(int id) {
      Facility facility = findFacility(id);
      
      // At this point, the object facility is managed
      if (facility != null) {
         em.remove(facility);
      }
      
      // At this point, the object is removed.
      // However, the change is not yet reflected into
      // database until being commit();
   }
   
   public Facility findFacility(int id) {
      return em.find(Facility.class, id);
   }
   
   public List<Facility> listAllFacilities() {
      String q = "SELECT f FROM Facility f";
      
      TypedQuery<Facility> query = em.createQuery(q, Facility.class);
      return query.getResultList();
   }
   
   public List<Facility> listFacilities(String name) {
      String q = "SELECT f " + 
            " FROM Facility f " +
            " WHERE f.name LIKE :name ";
            
      TypedQuery<Facility> query = em.createQuery(q, Facility.class)
            .setParameter("name", "%" + name + "%");
      return query.getResultList();
   }
}
