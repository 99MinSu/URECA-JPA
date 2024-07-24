package com.mycom.myapp;

import com.mycom.myapp.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {

   public static void main(String[] args) {
      // EntityManager <= EntityManagerFactory
      // Entity <=> Table 에 대응되는 자바 클래스, @Entity 가지는 
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("my-pu");
      EntityManager em = emf.createEntityManager();
      
      // persistence context
      try {
         em.getTransaction().begin();
         
         Product p = new Product();
         p.setId(1L);
         p.setName("Phone");
         
         // Entity 객체가 persistence context 안으로 들어온다.
         em.persist(p);
         
         em.getTransaction().commit();
      }finally {
         em.close();
      }
   }

}
