package com.yukthi.automation;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.yukthi.ccg.xml.BeanNode;
import com.yukthi.ccg.xml.ICustomNodeHandler;
import com.yukthi.ccg.xml.XMLAttributeMap;
import com.yukthi.utils.exceptions.InvalidStateException;

/**
 * Factory to create new steps and validations.
 * 
 * @author akiran
 */
public class ExecutableFactory implements ICustomNodeHandler
{
	private static Logger logger = LogManager.getLogger(ExecutableFactory.class);
	
	/**
	 * Mapping from step name to type.
	 */
	private Map<String, Class<? extends IStep>> nameToStepType = new HashMap<>();

	/**
	 * Mapping from name to validation type.
	 */
	private Map<String, Class<? extends IValidation>> nameToValidationType = new HashMap<>();

	/**
	 * Instantiates a new executable factory.
	 *
	 * @param appConfiguraion
	 *            the app configuraion
	 */
	public ExecutableFactory(IApplicationConfiguration appConfiguraion)
	{
		Set<String> basePackages = appConfiguraion.getBasePackages();

		if(basePackages == null)
		{
			basePackages = new HashSet<>();
		}

		basePackages.add("com.yukthi");

		loadStepTypes(basePackages);
		loadValidationTypes(basePackages);
	}

	/**
	 * Loads all step types found in specified base packages.
	 * 
	 * @param basePackages
	 *            Base packages to be scanned
	 */
	private void loadStepTypes(Set<String> basePackages)
	{
		Reflections reflections = null;
		Set<Class<? extends IStep>> stepTypes = null;
		Executable executable = null;

		for(String pack : basePackages)
		{
			reflections = new Reflections(pack, new TypeAnnotationsScanner(), new SubTypesScanner());

			stepTypes = reflections.getSubTypesOf(IStep.class);

			for(Class<? extends IStep> stepType : stepTypes)
			{
				if(stepType.isInterface() || Modifier.isAbstract(stepType.getModifiers()))
				{
					continue;
				}

				executable = stepType.getAnnotation(Executable.class);

				if(executable == null)
				{
					continue;
				}

				nameToStepType.put(executable.value(), stepType);
			}
		}
	}

	/**
	 * Loads all validation types found in specified base packages.
	 * 
	 * @param basePackages
	 *            Base packages to be scanned
	 */
	private void loadValidationTypes(Set<String> basePackages)
	{
		Reflections reflections = null;
		Set<Class<? extends IValidation>> validationTypes = null;
		Executable executable = null;

		for(String pack : basePackages)
		{
			reflections = new Reflections(pack, new TypeAnnotationsScanner(), new SubTypesScanner());

			validationTypes = reflections.getSubTypesOf(IValidation.class);

			for(Class<? extends IValidation> validationType : validationTypes)
			{
				if(validationType.isInterface() || Modifier.isAbstract(validationType.getModifiers()))
				{
					continue;
				}

				executable = validationType.getAnnotation(Executable.class);

				if(executable == null)
				{
					continue;
				}

				nameToValidationType.put(executable.value(), validationType);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yukthi.ccg.xml.ICustomNodeHandler#createCustomNodeBean(com.yukthi.ccg
	 * .xml.BeanNode, com.yukthi.ccg.xml.XMLAttributeMap)
	 */
	@Override
	public Object createCustomNodeBean(BeanNode beanNode, XMLAttributeMap attrMap)
	{
		Object parent = beanNode.getParent();

		if(parent instanceof IStepContainer)
		{
			IStep step = newStep(beanNode.getName());

			if(step != null)
			{
				return step;
			}
		}

		if(parent instanceof IValidationContainer)
		{
			Object validator = newValidation(beanNode.getName());
			return validator;
		}

		logger.warn("************************No step or validator found with name - " + beanNode.getName());
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yukthi.ccg.xml.ICustomNodeHandler#handleCustomNodeEnd(com.yukthi.ccg.
	 * xml.BeanNode, com.yukthi.ccg.xml.XMLAttributeMap)
	 */
	@Override
	public void handleCustomNodeEnd(BeanNode beanNode, XMLAttributeMap attrMap)
	{
		Object parent = beanNode.getParent();
		Object bean = beanNode.getActualBean();

		if((bean instanceof IStep) && (parent instanceof IStepContainer))
		{
			((IStepContainer) parent).addStep((IStep) bean);
		}

		if((bean instanceof IValidation) && (parent instanceof IValidationContainer))
		{
			((IValidationContainer) parent).addValidation((IValidation) bean);
		}
	}

	/**
	 * Creates a new step with specified name. A step can be named using @
	 * {@link Executable}
	 * 
	 * @param stepTypeName
	 *            Step name
	 * @return Matching step instance
	 */
	public IStep newStep(String stepTypeName)
	{
		Class<? extends IStep> stepType = nameToStepType.get(stepTypeName);

		if(stepType == null)
		{
			return null;
		}

		try
		{
			return stepType.newInstance();
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while creating step of type - {}", stepType.getName());
		}
	}

	/**
	 * Creates a new validation with specified name. A step can be named using @
	 * {@link Executable}
	 * 
	 * @param validationTypeName
	 *            Validation name
	 * @return Matching validation instance
	 */
	public IValidation newValidation(String validationTypeName)
	{
		Class<? extends IValidation> validationType = nameToValidationType.get(validationTypeName);

		if(validationType == null)
		{
			return null;
		}

		try
		{
			return validationType.newInstance();
		} catch(Exception ex)
		{
			throw new InvalidStateException(ex, "An error occurred while creating validation of type - {}", validationType.getName());
		}
	}
}
