package com.adaptiweb.blog.example;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Required;

import com.adaptiweb.utils.spel.SpelEvaluator;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;

/**
 * Spring Freemarker configuration:
 * <pre>
 * &lt;bean class="org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean" p:templateLoaderPath="/"&gt;
 *   &lt;property name="freemarkerVariables"&gt;
 *     &lt;map&gt;
 *       &lt;entry key="spel"&gt;
 *         &lt;bean class="com.adaptiweb.blog.example.SpelFreemarkerTemplateDirective"&gt;
 *           &lt;property name="spelEvaluator"&gt;
 *             &lt;bean class="com.adaptiweb.utils.spel.SpelEvaluatorFactoryBean"
 *               p:templatePrefix="{" p:templateSuffix="}" 
 *               p:useBeanResolver="true" p:useBeanAccessor="true" p:useMapAccessor="true"/&gt;
 *           &lt;/property&gt;
 *         &lt;/bean&gt; 
 *       &lt;/entry&gt;
 *     &lt;/map&gt;
 *    &lt;/property&gt;
 *  &lt;/bean&gt;
 *  </pre>
 * 
 * Usage in *.ftl template: 
 * <pre>&lt;@spel expression="My spring bean: {@mybean}"/&gt;</pre>
 * or
 * <pre>&lt;@spel&gt;My spring bean: {@mybean}&lt;/@spel&gt;</pre> 
 */
public class SpelFreemarkerTemplateDirective implements TemplateDirectiveModel {

	SpelEvaluator spelEvaluator;
	
	@Required
	public void setSpelEvaluator(SpelEvaluator spelEvaluator) {
		this.spelEvaluator = spelEvaluator;
	}
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {

		@SuppressWarnings("unchecked")
		String spelResult = spelEvaluator
				// set all freemarker variables as root object - map
				.setRootObject(new FreemarkerVariables(env, env.getKnownVariableNames()))
				// resolve expression either from parameter or from body
				.setExpression(resolveSpelExpression(params, body))
				.evaluate(String.class);
		env.getOut().write(spelResult);
		
		spelEvaluator.dispose();
	}
	
	private String resolveSpelExpression(Map<String, Object> params, TemplateDirectiveBody body) throws TemplateException, IOException {
		if (params.containsKey("expression")) {
			TemplateModel expressionModel = (TemplateModel) params.get("expression");
			return (String) DeepUnwrap.permissiveUnwrap(expressionModel);
		} else { // expression in body
			StringWriter expressionWriter = new StringWriter();
			body.render(expressionWriter);
			return expressionWriter.toString();
		}
	}
	
	static class FreemarkerVariables implements Map<String, Object> {
		final Environment env;
		final Set<String> variableNames;

		FreemarkerVariables(Environment env, Set<String> variableNames) {
			this.env = env;
			this.variableNames = variableNames;
		}

		@Override
		public int size() {
			return variableNames.size();
		}

		@Override
		public boolean isEmpty() {
			return variableNames.isEmpty();
		}

		@Override
		public boolean containsKey(Object key) {
			return variableNames.contains(key);
		}

		@Override
		public Object get(Object key) {
			try {
				TemplateModel model = env.getVariable((String) key);
				return DeepUnwrap.permissiveUnwrap(model);
			} catch (TemplateModelException e) {
				return null;
			}
		}

		@Override
		public Set<String> keySet() {
			return variableNames;
		}

		@Override public boolean containsValue(Object value) { throw new RuntimeException("Unsupported operation"); }
		@Override public Object put(String key, Object value) { throw new RuntimeException("Unsupported operation"); }
		@Override public Object remove(Object key) { throw new RuntimeException("Unsupported operation"); }
		@Override public void putAll(Map<? extends String, ? extends Object> m) { throw new RuntimeException("Unsupported operation"); }
		@Override public void clear() { throw new RuntimeException("Unsupported operation"); }
		@Override public Collection<Object> values() { throw new RuntimeException("Unsupported operation"); }
		@Override public Set<java.util.Map.Entry<String, Object>> entrySet() { throw new RuntimeException("Unsupported operation"); }
	}
}
