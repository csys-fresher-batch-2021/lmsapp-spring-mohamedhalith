package in.mohamedhalith.dao;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.mohamedhalith.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

	/**
	 * This method is used to find details of all employee from the Database.
	 */
	@Query("SELECT id,name,employee_id,username FROM employees WHERE active = true AND role = \'employee\' ORDER BY name ASC")
	Iterable<Employee> findAll();

	/**
	 * This method is used to find an employee with employee id as reference. If the
	 * given employee id is valid, details of the employee is returned and the
	 * employee will be null for invalid employee id
	 * 
	 * @param employeeId
	 * @return Employee
	 */
	@Query("SELECT id,name,username,employee_id,mobile_number,email FROM employees WHERE employee_id = :employeeId AND active = true")
	Employee findByEmployeeId(@Param("employeeId") int employeeId);

	/**
	 * This method is used to find an employee with employee id as reference. If the
	 * given username, password and role are valid, details of the employee is
	 * returned and the employee will be null for invalid employee id
	 * 
	 * @param username
	 * @param password
	 * @param role
	 * @return Employee
	 */
	Employee findByUsernameAndPasswordAndRole(@Param("username") String username, @Param("password") String password,
			@Param("role") String role);

	/**
	 * This method is used to find employee id for an employee with his/her
	 * username. Returns the employee id if the username is valid and returns null
	 * otherwise.
	 * 
	 * @param username
	 * @return Integer
	 */
	@Query("SELECT employee_id FROM employees WHERE username = :username")
	Integer findEmployeeId(@Param("username") String username);

	/**
	 * This method is used to find (Database) id of an employee using employee id
	 * 
	 * @param employeeId
	 * @return Integer
	 */
	@Query("SELECT id FROM employees WHERE employee_id = :employeeId")
	Integer exists(@Param("employeeId") int employeeId);

	/**
	 * This method is used to find (Database) id of an employee using email id
	 * 
	 * @param employeeId
	 * @return Integer
	 */
	@Query("SELECT id FROM employees WHERE email = :email")
	Integer exists(@Param("email") String email);

	/**
	 * This method is used to find (Database) id of an employee using mobile number
	 * 
	 * @param employeeId
	 * @return Integer
	 */
	@Query("SELECT id FROM employees WHERE mobile_number = :mobileNumber")
	Integer exists(@Param("mobileNumber") long mobileNumber);

	/**
	 * This method is used to update the status of an employee with the employee id
	 * as the input
	 * 
	 * @param employeeId
	 * @return int
	 */
	@Query("UPDATE employees SET active = false WHERE employee_id = :employeeId")
	@Modifying
	int remove(@Param("employeeId") int employeeId);
}
