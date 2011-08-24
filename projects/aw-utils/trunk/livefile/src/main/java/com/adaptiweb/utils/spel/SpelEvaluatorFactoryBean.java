package com.adaptiweb.utils.spel;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryAccessor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class SpelEvaluatorFactoryBean implements FactoryBean<SpelEvaluator>, ApplicationContextAware {

	private ApplicationContext applicationContext;

	private boolean useMapAccessor = false;
	private boolean useBeanAccessor = false;
	private boolean useBeanResolver = false;
	private String templatePrefix = null;
	private String templateSuffix = null;
	private Object defaultRootObject = null;
	
	ExpressionParser expressionParser = new SpelExpressionParser();
	ParserContext parserContext = null;
	
	@PostConstruct
	protected void initParserContext() {
		if (templatePrefix != null && templateSuffix != null) {
			parserContext = new ParserContext() {
				@Override
				public boolean isTemplate() {
					return true;
				}
				@Override
				public String getExpressionPrefix() {
					return templatePrefix;
				}
				@Override
				public String getExpressionSuffix() {
					return templateSuffix;
				}
			};
		}
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * Enable use of {@link MapAccessor}
	 * 
	 * @param useMapAccessor true or false, default is false
	 */
	public void setUseMapAccessor(boolean useMapAccessor) {
		this.useMapAccessor = useMapAccessor;
	}
	
	/**
	 * Enable use of {@link BeanFactoryAccessor} to access spring beans as property names
	 * 
	 * @param useBeanAccessor true or false, default is false
	 */
	public void setUseBeanAccessor(boolean useBeanAccessor) {
		this.useBeanAccessor = useBeanAccessor;
	}
	
	/**
	 * Enable use of {@link BeanFactoryResolver} to access spring beans via @beanName
	 * 
	 * @param useBeanResolver true or false, default is false
	 */
	public void setUseBeanResolver(boolean useBeanResolver) {
		this.useBeanResolver = useBeanResolver;
	}
	
	/**
	 * Handle expression as template, SpelExpression is determined by {@link #templatePrefix} and {@link #templateSuffix}.
	 * Both templatePrefix and templateSuffix has to be defined to enable expression template.
	 * 
	 * @param templatePrefix prefix for Spel expression, default is null
	 */
	public void setTemplatePrefix(String templatePrefix) {
		this.templatePrefix = templatePrefix;
	}
	
	/**
	 * Handle expression as template, SpelExpression is determined by {@link #templatePrefix} and {@link #templateSuffix}.
	 * Both templatePrefix and templateSuffix has to be defined to enable expression template.
	 * 
	 * @param templateSuffix suffix for Spel expression, default is null
	 */
	public void setTemplateSuffix(String templateSuffix) {
		this.templateSuffix = templateSuffix;
	}
	
	public void setDefaultRootObject(Object rootObject) {
		this.defaultRootObject = rootObject;
	}
	
	@Override
	public SpelEvaluator getObject() {
		return new SpelEvaluator() {
			ThreadLocal<StandardEvaluationContext> evaluationContext = new ThreadLocal<StandardEvaluationContext>() {
				@Override
				protected StandardEvaluationContext initialValue() {
					StandardEvaluationContext ec = new StandardEvaluationContext();
					if (useMapAccessor) {
						ec.addPropertyAccessor(new MapAccessor());
					}
					if (useBeanAccessor) {
						ec.addPropertyAccessor(new BeanFactoryAccessor());
						ec.setRootObject(applicationContext);
					}
					if (useBeanResolver) {
						ec.setBeanResolver(new BeanFactoryResolver(applicationContext));
					}
					if (defaultRootObject != null) {
						ec.setRootObject(defaultRootObject);
						if (!useMapAccessor && defaultRootObject instanceof Map)
							ec.addPropertyAccessor(new MapAccessor());
					}
					return ec;
				}
			};
			
			ThreadLocal<Expression> expression = new ThreadLocal<Expression>();

			@Override
			public SpelEvaluator setVariables(Map<String, Object> variables) {
				evaluationContext.get().setVariables(variables);
				evaluationContext.get().addPropertyAccessor(new MapAccessor());
				return this;
			}
			
			@Override
			public SpelEvaluator setVariable(String name, Object value) {
				evaluationContext.get().setVariable(name, value);
				return this;
			}
			
			@Override
			public SpelEvaluator setRootObject(Object rootObject) {
				evaluationContext.get().setRootObject(rootObject);
				if (!useMapAccessor && rootObject instanceof Map) {
					evaluationContext.get().addPropertyAccessor(new MapAccessor());
				}
				return this;
			}
			
			@Override
			public SpelEvaluator setExpression(String expressionString) {
				expression.set(parserContext != null 
					? expressionParser.parseExpression(expressionString, parserContext)
					: expressionParser.parseExpression(expressionString));
				return this;
			}
			
			@Override
			public <T> T evaluate(Class<T> desiredResultType) {
				if (expression == null) throw new RuntimeException("setExpression() must be called prior to evaluate()");
				return expression.get().getValue(evaluationContext.get(), desiredResultType);
			}
			
			@Override
			public Object evaluate() {
				if (expression == null) throw new RuntimeException("setExpression() must be called prior to evaluate()");
				return expression.get().getValue(evaluationContext.get());
			}
			
			@Override
			public void dispose() {
				evaluationContext.remove();
			}
		};
	}

	@Override
	public Class<?> getObjectType() {
		return SpelEvaluator.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
	
}
