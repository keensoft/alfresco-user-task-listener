package es.keensoft.repo.workflow.activiti;

import java.util.List;

import org.activiti.engine.parse.BpmnParseHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CustomUserTaskListenerInit implements BeanFactoryPostProcessor, ApplicationContextAware {
	
    private static ApplicationContext ctx = null;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
         ctx = applicationContext;
    }
    
	private static final Log logger = LogFactory.getLog(CustomUserTaskListenerInit.class);
	
	private String beanName;
	private String propertyName;
	private List<BpmnParseHandler> customPostBpmnParseHandlers;
	
	@Override
	@SuppressWarnings("unchecked")
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
		if (beanFactory.containsBeanDefinition(beanName)) {
			
        	BeanDefinition def = beanFactory.getBeanDefinition(beanName);
			List<Object> mapped = (List<Object>)  def.getPropertyValues().getPropertyValue(propertyName).getValue();
			for (BpmnParseHandler cpbph : customPostBpmnParseHandlers) {
        	    mapped.add(cpbph);
			}
			
		} else {
			
        	logger.warn("No " + beanName + " found on Spring. Custom parse handler has not been added");
        	
        }

	}
	
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public List<BpmnParseHandler> getCustomPostBpmnParseHandlers() {
		return customPostBpmnParseHandlers;
	}
	public void setCustomPostBpmnParseHandlers(List<BpmnParseHandler> customPostBpmnParseHandlers) {
		this.customPostBpmnParseHandlers = customPostBpmnParseHandlers;
	}
	
}
