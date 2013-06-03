package com.adaptiweb.utils.spel;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
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
	private String defaultExpressionString = null;
	private Object defaultRootObject = null;
	private List<Method> functions = new ArrayList<Method>();
	
	ExpressionParser expressionParser = new SpelExpressionParser();
	Expression defaultExpression = null;
	ParserContext parserContext = null;
	
	@PostConstruct
	public void initParserContextAndDefaultExpression() {
		parserContext = new ParserContext() {
			@Override
			public boolean isTemplate() {
				return templatePrefix != null && templateSuffix != null;
			}
			@Override
			public String getExpressionSuffix() {
				return templateSuffix;
			}
			@Override
			public String getExpressionPrefix() {
				return templatePrefix;
			}
		};
		if (defaultExpressionString != null) {
			defaultExpression = parseExpression(defaultExpressionString);
		}
	}
	
	private Expression parseExpression(String expressionString) {
		return parserContext != null 
				? expressionParser.parseExpression(expressionString, parserContext)
				: expressionParser.parseExpression(expressionString);
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
	
	/**
	 * Set default expression for this SpEL evaluator, 
	 * which is used when {{@link SpelEvaluator#setExpression(String)} is not called before the evaluate
	 * 
	 * @param defaultExpression
	 */
	public void setDefaultExpression(String defaultExpression) {
		this.defaultExpressionString = defaultExpression;
	}
	
	public void setDefaultRootObject(Object rootObject) {
		this.defaultRootObject = rootObject;
	}
	
	/**
	 * Register all static methods in declared funcType as custom SpEL functions.
	 * Custom function is used in SpEL as follows:
	 * <code>#functionName(parameters)</code>
	 * 
	 * @param funcType type of utility class with static functions for SpEL
	 */
	public void setFunctions(Class<?> funcType) {
		for (Method fun : funcType.getDeclaredMethods()) {
			if (Modifier.isStatic(fun.getModifiers())) functions.add(fun);
		}
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
					for (Method fun : functions) {
						ec.registerFunction(fun.getName(), fun);
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
			public SpelEvaluator registerFunction(String name, Method method) {
				evaluationContext.get().registerFunction(name, method);
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
			
			private boolean isExpression(String expressionString) {
				if (expressionString == null) return false;
				else if (!parserContext.isTemplate()) return true;
				else return expressionString.contains(templatePrefix) 
					&& expressionString.contains(templateSuffix);
			}
			
			@Override
			public SpelEvaluator setExpression(String expressionString) {
				expression.set(parseExpression(expressionString));
				return this;
			}
			
			@Override
			public <T> T evaluate(Class<T> desiredResultType) {
				T result = getExpression().getValue(evaluationContext.get(), desiredResultType);
				return (T) doNestedEvaluation(result, desiredResultType);
			}
			
			@Override
			public Object evaluate() {
				Object result = getExpression().getValue(evaluationContext.get());
				return doNestedEvaluation(result, Object.class);
			}
			
			private Expression getExpression() {
				Expression threadLocalExpression = expression.get();
				if (defaultExpression == null && threadLocalExpression == null) {
					throw new RuntimeException(
							"setDefaultExpression() or setExpression() must be called prior to evaluate()");
				}
				return threadLocalExpression != null ? threadLocalExpression : defaultExpression;
			}
			
			@SuppressWarnings("unchecked")
			private <T> T doNestedEvaluation(T result, Class<T> desiredResultType) {
				// if result is still an expression
				if (result instanceof String && isExpression((String) result)) { 
					String resultExpression = (String) result;
					// if result is different from previous expression
					if (!expression.get().getExpressionString().equals(resultExpression)) { 
						setExpression(resultExpression);
						return (T) (desiredResultType.equals(Object.class) 
							? evaluate() 
							: evaluate(desiredResultType));
					}
				}
				return result;
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
