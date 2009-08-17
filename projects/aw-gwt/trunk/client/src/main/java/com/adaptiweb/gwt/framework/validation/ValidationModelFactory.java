package com.adaptiweb.gwt.framework.validation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.google.gwt.event.shared.HandlerRegistration;

public final class ValidationModelFactory {
	
	private static abstract class AbstractValidationModelSet extends AbstractValidationModel
	implements ValidationModelSet, ValidationHandler {
		
		private final String type;
		private final Map<ValidationModel, HandlerRegistration> models
			= new HashMap<ValidationModel, HandlerRegistration>();
		
		public AbstractValidationModelSet(String type) {
			this.type = type;
		}

		protected abstract boolean validate();
		
		@Override
		public boolean add(ValidationModel...models) {
			boolean result = false;
			for (ValidationModel model : models) {
				if (!this.models.containsKey(model)) {
					result = true;
					this.models.put(model, model.addValidationHandler(this, false));
				}
			}
			setValid(validate());
			return result;
		}

		@Override
		public boolean remove(ValidationModel...models) {
			boolean result = false;
			for (ValidationModel model : models) {
				if (this.models.containsKey(model)) {
					result = true;
					this.models.remove(model).removeHandler();
				}
			}
			setValid(validate());
			return result;
		}

		@Override
		public void onValidationChange(ValidationEvent event) {
			setValid(validate());
		}
		
		@Override
		public Iterator<ValidationModel> iterator() {
			return models.keySet().iterator();
		}
		
		@Override
		public int size() {
			return models.size();
		}

		@Override
		public String toDebugString() {
			StringBuilder sb = new StringBuilder();
			sb.append(super.toDebugString(type)).append('{');
			
			for (ValidationModel validation : this) {
				sb.append(GwtGoodies.toDebugString(validation)).append(',');
			}
			sb.setCharAt(sb.length() - 1 , '}');
			return sb.toString();
		}
	}
	
	private static abstract class
	AbstractLogicValidationModelSet extends AbstractValidationModelSet {

		protected int validCounter = 0;
		
		public AbstractLogicValidationModelSet(String setType, ValidationModel... models) {
			super(setType);
			add(models);
		}

		@Override
		public boolean add(ValidationModel...models) {
			for (ValidationModel model : models)
				if (model.isValid()) validCounter++;
			return super.add(models);
		}
		
		@Override
		public boolean remove(ValidationModel...models) {
			for (ValidationModel model : models)
				if (model.isValid()) validCounter--;
			return super.remove(models);
		}
		
		@Override
		public void onValidationChange(ValidationEvent event) {
			validCounter += event.isValid() ? +1 : -1;
			super.onValidationChange(event);
		}
	}

	public static ValidationModelSet and(ValidationModel...models) {
		return new AbstractLogicValidationModelSet("and", models) {
			@Override
			protected boolean validate() {
				return validCounter == size();
			}
		};
	}

	public static ValidationModelSet or(ValidationModel...models) {
		return new AbstractLogicValidationModelSet("or", models) {
			@Override
			protected boolean validate() {
				return validCounter > 0;
			}
		};
	}

	public static ValidationModelSet xor(ValidationModel...models) {
		return new AbstractLogicValidationModelSet("xor", models) {
			@Override
			protected boolean validate() {
				return validCounter == 0 || validCounter == size();
			}
		};
	}

	public static ValidationModel not(final ValidationModel model) {
		return new AbstractValidationModel() {
			{
				model.addValidationHandler(new ValidationHandler() {
					@Override
					public void onValidationChange(ValidationEvent event) {
						setValid(!event.getModel().isValid());
					}
				}, true);
			}
			@Override
			public String toDebugString() {
				return super.toDebugString("not") + "={" + GwtGoodies.toDebugString(model) + "}";
			}
		};
	}
	
}
