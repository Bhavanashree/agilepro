package com.agilepro.services.admin;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agilepro.commons.UserRole;
import com.agilepro.commons.models.admin.EmployeeModel;
import com.agilepro.controller.CbillerUserDetails;
import com.agilepro.persistence.entity.admin.CustomerEntity;
import com.agilepro.persistence.entity.admin.DesignationEntity;
import com.agilepro.persistence.entity.admin.EmployeeEntity;
import com.agilepro.persistence.repository.admin.IEmployeeRepository;
import com.yukthi.persistence.ITransaction;
import com.yukthi.utils.exceptions.InvalidStateException;
import com.yukthi.utils.exceptions.NullValueException;
import com.yukthi.webutils.repository.UserEntity;
import com.yukthi.webutils.repository.UserRoleEntity;
import com.yukthi.webutils.services.BaseCrudService;
import com.yukthi.webutils.services.CurrentUserService;
import com.yukthi.webutils.services.UserRoleService;
import com.yukthi.webutils.services.UserService;

/**
 * employee.
 *
 * @author Bhavana
 */
@Service
public class EmployeeService extends BaseCrudService<EmployeeEntity, IEmployeeRepository>
{
	private static Logger logger = LogManager.getLogger(EmployeeService.class);

	/**
	 * UserService for UserEntity table.
	 */
	@Autowired
	private UserService userService;

	/**
	 * UserRoleService for saving the role.
	 */
	@Autowired
	private UserRoleService userRoleService;

	/**
	 * Used to fetch current user info.
	 */
	@Autowired
	private CurrentUserService currentUserService;

	/**
	 * used to fetch the designations.
	 */
	@Autowired
	private DesignationService designationService;

	public EmployeeService()
	{
		super(EmployeeEntity.class, IEmployeeRepository.class);
	}

	/**
	 * Save.
	 *
	 * @param model
	 *            the model
	 * @return the employee entity
	 */
	public EmployeeEntity save(EmployeeModel model)
	{
		CbillerUserDetails cbiller = (CbillerUserDetails) currentUserService.getCurrentUserDetails();

		Long customerId = cbiller.getCustomerId();

		// saving employee
		EmployeeEntity employeeEntity = super.save(model);

		// saving user
		saveUser(model, customerId, employeeEntity);

		return employeeEntity;
	}

	/**
	 * Update.
	 *
	 * @param model
	 *            the model
	 * @return the employee entity
	 */
	public EmployeeEntity update(EmployeeModel model)
	{
		if(model == null)
		{
			throw new NullValueException("EmployeeModel Object is null");
		}

		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			// updating employee
			EmployeeEntity customerEmployee = super.update(model);

			// updating user
			updateUser(model, customerEmployee);

			transaction.commit();
			return customerEmployee;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while updating model - {}", model);
		}
	}

	/**
	 * Save user.
	 *
	 * @param model
	 *            the model
	 * @param customerUser
	 *            the employee user
	 */
	private void saveUser(EmployeeModel model, Long customerId, EmployeeEntity employeeEntity)
	{
		UserEntity userEntity = new UserEntity();

		// model is having the password
		userEntity.setPassword(model.getPassword());

		userEntity.setUserName(employeeEntity.getMailId());
		userEntity.setDisplayName(employeeEntity.getName());
		userEntity.setBaseEntityId(employeeEntity.getId());
		userEntity.setBaseEntityType(EmployeeEntity.class.getName());

		UserRoleEntity roleEntity = new UserRoleEntity();
		roleEntity.setOwnerType(CustomerEntity.class.getName());
		roleEntity.setOwnerId(customerId);
		roleEntity.setRole(UserRole.EMPLOYEE_VIEW);
		roleEntity.setUser(userEntity);

		userService.save(userEntity, null);
		userRoleService.save(roleEntity, null);
		logger.debug("Added new employee with user-name - " + userEntity.getUserName());
	}

	/**
	 * Update user.
	 *
	 * @param model
	 *            the model
	 * @param employee
	 *            the employee user
	 */
	private void updateUser(EmployeeModel model, EmployeeEntity employee)
	{
		UserEntity userEntity = userService.fetchUserByBaseEntity(employee.getClass().getName(), employee.getId());

		if(userEntity == null)
		{
			throw new InvalidStateException("No user record found with base type details [Type: {}, Id: {}]", employee.getClass().getName(), employee.getId());
		}

		userEntity.setPassword(model.getPassword());
		userEntity.setUserName(model.getMailId());
		userEntity.setDisplayName(model.getName());
		userEntity.setVersion(userEntity.getVersion());

		userService.update(userEntity, null);
		logger.debug("Updated employee user with user-name - " + userEntity.getUserName());
	}

	/**
	 * Fetches designation based roles of the specified employee.
	 * 
	 * @param empId
	 *            Employee id whose designation roles needs to be fetched
	 * @return Designation roles of the employee
	 */
	public List<UserRole> getEmployeeDesignationRoles(long empId)
	{
		Long designationId = super.repository.fetchDesignationId(empId);
		DesignationEntity designation = designationService.fetch(designationId);
		return designation.getRoles();
	}

	/**
	 * deleteById.
	 * 
	 * @return employee
	 */
	@Override
	public boolean deleteById(long id)
	{
		try(ITransaction transaction = repository.newOrExistingTransaction())
		{
			userService.deleteByBaseEntity(EmployeeEntity.class.getName(), id);

			boolean res = super.deleteById(id);

			transaction.commit();
			return res;
		} catch(RuntimeException ex)
		{
			throw ex;
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while deleting employee with id - {}", id);
		}
	}

	/**
	 * Deletes all entities.
	 */
	public void deleteAll()
	{
		repository.deleteAll();
	}
}
