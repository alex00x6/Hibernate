package com.journaldev.hibernate.main;

import com.journaldev.hibernate.model.Employee;
import com.journaldev.hibernate.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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
	public void batchTest(){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();

		for ( int i=0; i<10000000; i++ ) {
			Employee emp = new Employee();
			emp.setName("Alexander"+ ThreadLocalRandom.current().nextInt(1, 500 + 1));
			emp.setRole("CEO");
			//emp.setInsertTime(new Date());
			session.save(emp);
		}
		tx.commit();
		HibernateUtil.getSessionFactory().close();

	}

	@Test
	public void testCriteria(){
		criteriaGreaterEquals("id", 1);

		criteriaEmployeeConstructor(Restrictions.le("id", 1));
		criteriaEmployeeConstructor(Restrictions.le("insertTime", dateFromString("2016-11-24")));
		criteriaEmployeeConstructor(Restrictions.eq("name", "lisa"));
		criteriaEmployeeConstructor(Restrictions.eq("insertTime", dateFromString("2016-11-24")));
		criteriaEmployeeConstructor(Restrictions
				.between("insertTime", dateFromString("2016-11-24"), dateFromString("2016-11-29")));

	}

	@Test
	public static void test1(){
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
		requestConstructor("from Employee where insertTime >='2016-11-24'");

		System.out.println("employee constructor by name: ");
		requestConstructor("from Employee where name ='lisa'");

		System.out.println("employee constructor by date: ");
		requestConstructor("from Employee where insertTime ='2016-11-24'");

		System.out.println("employee constructor between dates: ");
		requestConstructor("from Employee where insertTime >='2016-11-23' and insertTime <='2016-11-28'");
	}

	public void criteriaEmployeeConstructor(Criterion criterion){

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria cr = session.createCriteria(Employee.class);
		cr.add(criterion);
		List results = cr.list();

		for(int i = 0; i<results.size(); i++){
			Employee emp = (Employee) results.get(i);
			System.out.println(emp.getId()+" "+emp.getName()+" "+emp.getRole()+" "+emp.getInsertTime());
		}

		HibernateUtil.getSessionFactory().getCurrentSession().close();
	}

	@Test
	public void criteriaGreaterEquals(String key, int value){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Criteria cr = session.createCriteria(Employee.class);
		cr.add(Restrictions.ge(key, value));
		List results = cr.list();

		for(int i = 0; i<results.size(); i++){
			Employee emp = (Employee) results.get(i);
			System.out.println(emp.getId()+" "+emp.getName()+" "+emp.getRole()+" "+emp.getInsertTime());
		}

		HibernateUtil.getSessionFactory().getCurrentSession().close();
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

	private Date dateFromString(String date){
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-YYYY");
		Date resultDate = null;
		try {
			resultDate = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}

}
