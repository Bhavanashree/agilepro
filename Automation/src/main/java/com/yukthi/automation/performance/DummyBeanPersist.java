package com.yukthi.automation.performance;

/**
 * The Class BeanPersist.
 */
public class DummyBeanPersist implements IBeanPersister
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yukthi.automation.performance.IBeanPersister#beanPersiter(com.yukthi.
	 * automation.performance.BeanDetails)
	 */
	@Override
	public void persist(Object bean)
	{
		System.out.println(bean + "from dummyPerisister");
	}
}
