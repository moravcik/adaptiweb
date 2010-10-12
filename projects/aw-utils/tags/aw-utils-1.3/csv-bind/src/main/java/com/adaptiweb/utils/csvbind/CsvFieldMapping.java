package com.adaptiweb.utils.csvbind;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;

import com.adaptiweb.utils.csvbind.annotation.CsvField;
import com.adaptiweb.utils.csvbind.editor.CsvFieldPatternEditor;

public class CsvFieldMapping<T> extends ColumnPositionMappingStrategy<T> {

	public static class CsvFieldDescriptor {
		private Field field;
		private CsvField fieldAnnotation;
		private PropertyEditor fieldEditor;
		
		public CsvFieldDescriptor(Field field, CsvField fieldAnnotation) {
			this.field = field;
			this.fieldAnnotation = fieldAnnotation;
		}
		public Field getField() {
			return field;
		}
		public PropertyEditor getFieldEditor() {
			return fieldEditor;
		}
		public CsvField getFieldAnnotation() {
			return fieldAnnotation;
		}
	}
	
	private Map<String, CsvFieldDescriptor> csvDescriptors = new HashMap<String, CsvFieldDescriptor>();
	private Set<CsvFieldDescriptor> csvSortedDescriptors = new TreeSet<CsvFieldDescriptor>(
			new Comparator<CsvFieldDescriptor>() {
		        public int compare(CsvFieldDescriptor d1, CsvFieldDescriptor d2) {
		            int i1 = d1.fieldAnnotation.index();
		            int i2 = d2.fieldAnnotation.index();
		            return ((Integer) i1).compareTo(i2);
		        }
			}
		);
	
	protected Integer listIndex = null; 
	protected CollectionPropertyEditor listEditor = null;

	/**
	 * Set target class of bean, include loading input fields and setting editors.
	 */
	@SuppressWarnings({"unchecked" ,"rawtypes"})
	@Override
	public void setType(Class beanClass) {
		super.setType(beanClass);
		csvDescriptors.clear();
		csvSortedDescriptors.clear();
		loadInputFields(beanClass);
	}

    /**
     * Retrieve inputFields for {@link ColumnPositionMappingStrategy}.
     * inputFields array consists of sorted bean fields' names as they appear in CSV file.
     * Properties in target bean class are annotated by {@link CsvField} annotations.
     * 
     * @see ColumnPositionMappingStrategy, CsvField
     * 
     * @param beanClass target bean class
     */
    private void loadInputFields(Class<T> beanClass) {
        try {
        	// find annotated CSV fields
        	for (Field f : getAllDeclaredFields(beanClass)) {
        		if (f.isAnnotationPresent(CsvField.class)) {
        			CsvFieldDescriptor desc = new CsvFieldDescriptor(f, f.getAnnotation(CsvField.class));
        			desc.fieldEditor = loadInputEditor(f.getName(), desc.fieldAnnotation);
        			csvDescriptors.put(f.getName(), desc);
        			csvSortedDescriptors.add(desc);
        		}
        	}
        } catch (Exception e) {
			throw new IllegalArgumentException(e);
        }
        if (csvDescriptors.isEmpty())
            throw new IllegalArgumentException("No CSV fields defined for class: " + beanClass.getName());

        List<String> fieldNames = new ArrayList<String>();
        for (CsvFieldDescriptor desc : csvSortedDescriptors) 
        	fieldNames.add(desc.field.getName());
        
        setColumnMapping(fieldNames.toArray(new String[fieldNames.size()]));
    }

    /**
     * Get and store PropertyEditor for field with fieldName by input annotation or default.
     * @param fieldName
     * @param annotation
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws IntrospectionException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private PropertyEditor loadInputEditor(String fieldName, CsvField fieldAnnotation) throws InstantiationException, IllegalAccessException, IntrospectionException {
		PropertyEditor editor = null;
		if (fieldAnnotation.enumType() != DummyEnum.class) { // explicitly defined enum class
			editor = new EnumEditor(fieldAnnotation.enumType());
			
		} else if (fieldAnnotation.editor() != PropertyEditor.class) { // explicitly defined editor
			editor = (PropertyEditor) fieldAnnotation.editor().newInstance();
			if (editor instanceof CsvFieldPatternEditor) 
				((CsvFieldPatternEditor) editor).setPatterns(fieldAnnotation.patterns());

		} else { // load default editor
			PropertyDescriptor descriptor = findDescriptor(fieldName);
			editor = new CsvToBean<T>() {
				@Override
				public PropertyEditor getPropertyEditor(PropertyDescriptor propertydescriptor) throws InstantiationException, IllegalAccessException {
					return super.getPropertyEditor(propertydescriptor);
				}
			}.getPropertyEditor(descriptor);
		}
		if (editor != null) {
			// wrap list editor
			if (fieldAnnotation.list()) {
				editor = listEditor = new CollectionPropertyEditor(editor);
				listIndex = fieldAnnotation.index();
			}
			return editor;
		} else throw new IllegalArgumentException("No editor for: " + fieldName);
	}

    /**
     * Get declared fields of beanClass and all superclasses.
     * @param beanClass
     * @return
     */
    protected List<Field> getAllDeclaredFields(Class<?> beanClass) {
    	ArrayList<Field> fields = new ArrayList<Field>();
    	if (beanClass != null) {
    		fields.addAll(Arrays.asList(beanClass.getDeclaredFields()));
    		Object superClass = beanClass.getSuperclass();
    		if (superClass != null && superClass instanceof Class<?>)
    			fields.addAll(getAllDeclaredFields((Class<?>) superClass));
    	}
    	return fields;
    }

    public CsvFieldDescriptor getDescriptor(String field) {
    	return csvDescriptors.get(field);
    }

    protected void initMapping() {
        if (listEditor != null) listEditor.list.clear();
    }
    
    @Override
    protected String getColumnName(int i) {
        return super.getColumnName(listIndex == null || i < listIndex ? i : listIndex.intValue());
    }
    
    /**
     * List CSV editor
     */
    public static class CollectionPropertyEditor extends PropertyEditorSupport {
        private PropertyEditor itemEditor;
        private List<Object> list = new ArrayList<Object>();
        
        public CollectionPropertyEditor(PropertyEditor itemEditor) {
            this.itemEditor = itemEditor;
        }
        public void setAsText(String s) throws IllegalArgumentException {
            itemEditor.setAsText(s);
            if (itemEditor.getValue() != null) list.add(itemEditor.getValue()); // skip null values
        }
        @Override
        public void setValue(Object value) {
        	list.clear();
        	if (value != null && value instanceof Collection<?>) 
        		list.addAll((Collection<?>) value);
        }
        public Object getValue() {
            return new ArrayList<Object>(list);
        }
		public PropertyEditor getItemEditor() {
			return itemEditor;
		}
    }

	public Collection<CsvFieldDescriptor> getFieldDescriptors() {
		return csvSortedDescriptors;
	}

	public enum DummyEnum {}

	private static class EnumEditor<T extends Enum<T>> extends PropertyEditorSupport {
		
		private Class<T> enumType;
		
		protected EnumEditor(Class<T> enumType) {
			this.enumType = enumType;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public String getAsText() {
			return getValue() != null ? ((T) getValue()).name() : null;
		}
		@Override
		public void setAsText(String arg) throws IllegalArgumentException {
			try {
				setValue(Enum.valueOf(enumType, arg));
			} catch (IllegalArgumentException e) {
				setValue(null);
			} catch (NullPointerException e) {
				setValue(null);
			}
		}
		
	}
}
