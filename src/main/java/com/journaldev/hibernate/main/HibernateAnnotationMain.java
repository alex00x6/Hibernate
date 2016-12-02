package com.journaldev.hibernate.main;

import com.journaldev.hibernate.model.Employee1;
import com.journaldev.hibernate.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

public class HibernateAnnotationMain {

	public static void main(String[] args) {
		Employee1 emp = new Employee1();
		emp.setName("David");
		emp.setRole("Developer");
		emp.setInsertTime(new Date());
		
		//Get Session
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		//start transaction
		session.beginTransaction();
		//Save the Model object
		session.save(emp);
		//Commit transaction
		session.getTransaction().commit();
		System.out.println("Employee ID="+emp.getId());
		
		//terminate session factory, otherwise program won't end
		sessionFactory.close();
	}

	@Test
	public void test(){
		System.out.println("===========все employee==========");
		testAnnotation("from Employee1");
		System.out.println("==========employee по имени===========");
		testAnnotation("from Employee1 where name = 'David'");
		System.out.println("==========employee по точной дате добавления===========");
		testAnnotation("from Employee1 where insert_time = '2016-11-24'");
		System.out.println("==========employee добавленные после определенной даты===========");
		testAnnotation("from Employee1 where insert_time >='2016-11-24'");
		System.out.println("==========employee добавленные между определенными датами===========");
		testAnnotation("from Employee1 where insert_time >='2016-11-23' and insert_time <='2016-11-24'");
		System.out.println("=====================");
	}

	public void testAnnotation(String HQL){
		SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery(HQL);
		List<Employee1> list = query.list();
		for(int i = 0; i<list.size(); i++){
			Employee1 emp = list.get(i);
			System.out.println(emp.getId()+" "+emp.getName()+" "+emp.getRole()+" "+emp.getInsertTime());
		}
		sessionFactory.getCurrentSession().close();
	}

}
