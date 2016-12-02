package hu.unideb.inf.rft.nvkshop.logging;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

//@Component
public class LoggableBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		String[] beans = beanFactory.getBeanDefinitionNames();

		for (String beanName : beans) {
			Object bean = beanFactory.getBean(beanName);
			for (Field field : bean.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(Log.class)) {
					try {
						field.setAccessible(true);
						field.set(bean, LoggerFactory.getLogger(beanName.getClass()));
						logger.info("Logger set into field {} in bean: {}",field.getName(),beanName);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						logger.info("Unable to set logger instance to field {} in bean {}", field.getName(), beanName);
					}
				}
			}
		}
	}

}
