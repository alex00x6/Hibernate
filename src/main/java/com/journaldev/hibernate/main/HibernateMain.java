package com.journaldev.hibernate.main;

import com.journaldev.hibernate.model.Employee;
import com.journaldev.hibernate.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

public class HibernateMain {

	public static void main(String[] args) {
		Employee emp = new Employee();
		emp.setName("Pankaj");
		emp.setRole("CEO");
		emp.setInsertTime(new Date());
		
		//Get Session
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		//start transaction
		session.beginTransaction();
		//Save the Model object
		session.save(emp);
		//Commit transaction
		session.getTransaction().commit();
		System.out.println("Employee ID="+emp.getId());
		
		//terminate session factory, otherwise program won't end
		HibernateUtil.getSessionFactory().close();
	}

	@Test
	public static void testLol(){
		System.out.println("allEmployees: ");
		allEmployees();
		System.out.println("employeeAfterDate: ");
		employeeAfterDate();
		System.out.println("employeeBetweenDates: ");
		employeeBetweenDates();
		System.out.println("employeeByDate: ");
		employeeByDate();
		System.out.println("employeeByName: ");
		employeeByName();
	}

	@Test
	public void testWithParams(){
		System.out.println("employee constructor all: ");
		requestConstructor("from Employee");

		System.out.println("employee constructor after date: ");
		requestConstructor("from Employee where insert_time >='2016-11-24'");

		System.out.println("employee constructor by name: ");
		requestConstructor("from Employee where name ='lisa'");

		System.out.println("employee constructor by date: ");
		requestConstructor("from Employee where insert_time ='2016-11-24'");

		System.out.println("employee constructor between dates: ");
		requestConstructor("from Employee where insert_time >='2016-11-23' and insert_time <='2016-11-28'");
	}

	@Test
	public static void allEmployees(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Query query = session.createQuery("from Employee");
		List<Employee> list = query.list();

		for(int i = 0; i<list.size(); i++){
			Employee emp = list.get(i);
			System.out.println(emp.getId()+" "+emp.getName()+" "+emp.getRole()+" "+emp.getInsertTime());
		}
		session.close();
	}

	@Test
	public static void employeeByName(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Query query = session.createQuery("from Employee where name ='lisa'");
		List<Employee> list = query.list();

		for(int i = 0; i<list.size(); i++){
			Employee emp = list.get(i);
			System.out.println(emp.getId()+" "+emp.getName()+" "+emp.getRole()+" "+emp.getInsertTime());
		}
		session.close();
	}

	@Test
	public static void employeeByDate(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Query query = session.createQuery("from Employee where insert_time ='2016-11-24'");
		List<Employee> list = query.list();

		for(int i = 0; i<list.size(); i++){
			Employee emp = list.get(i);
			System.out.println(emp.getId()+" "+emp.getName()+" "+emp.getRole()+" "+emp.getInsertTime());
		}
		session.close();
	}

	@Test
	public static void employeeAfterDate(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Query query = session.createQuery("from Employee where insert_time >='2016-11-24'");
		List<Employee> list = query.list();

		for(int i = 0; i<list.size(); i++){
			Employee emp = list.get(i);
			System.out.println(emp.getId()+" "+emp.getName()+" "+emp.getRole()+" "+emp.getInsertTime());
		}
		session.close();
	}

	@Test
	public static void employeeBetweenDates(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Query query = session.createQuery("from Employee where insert_time >='2016-11-23' and insert_time <='2016-11-28'");
		List<Employee> list = query.list();

		for (Employee emp : list) {
			System.out.println(emp.getId() + " " + emp.getName() + " " + emp.getRole() + " " + emp.getInsertTime());
		}
		session.close();
	}

	public void requestConstructor(String HQL){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();

		Query query = session.createQuery(HQL);
		List<Employee> list = query.list();

		for(int i = 0; i<list.size(); i++){
			Employee emp = list.get(i);
			System.out.println(emp.getId()+" "+emp.getName()+" "+emp.getRole()+" "+emp.getInsertTime());
		}

		HibernateUtil.getSessionFactory().getCurrentSession().close();
	}

}
