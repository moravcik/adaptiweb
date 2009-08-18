package com.adaptiweb.gwt.framework.modify;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.HasDebugInfo;
import com.adaptiweb.gwt.mvc.model.NumberModel;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

public final class ModifiedModelFactory {
	
	private static abstract class AbstractModifiedModelSet extends BaseModifiedModel
	implements ModifiedModelSet, ModifiedHandler, HasDebugInfo {
		
		private final String type;
		private final Map<ModifiedModel, HandlerRegistration> models
			= new HashMap<ModifiedModel, HandlerRegistration>();
		
		public AbstractModifiedModelSet(String type) {
			this.type = type;
		}

		protected abstract boolean eval();
		
		@Override
		public boolean add(ModifiedModel...models) {
			boolean result = false;
			for (ModifiedModel model : models) {
				if (!this.models.containsKey(model)) {
					result = true;
					this.models.put(model, model.addModifiedHandler(this, false));
				}
			}
			setModified(eval());
			return result;
		}

		@Override
		public boolean remove(ModifiedModel...models) {
			boolean result = false;
			for (ModifiedModel model : models) {
				if (this.models.containsKey(model)) {
					result = true;
					this.models.remove(model).removeHandler();
				}
			}
			setModified(eval());
			return result;
		}

		@Override
		public void onModify(ModifiedEvent event) {
			setModified(eval());
		}
		
		@Override
		public Iterator<ModifiedModel> iterator() {
			return models.keySet().iterator();
		}
		
		@Override
		public int size() {
			return models.size();
		}
		
		@Override
		public void burn() {
			for (ModifiedModel model : this) model.burn();
		}

		@Override
		public String toDebugString() {
			boolean first= true;
			StringBuilder sb = new StringBuilder();
			sb.append(type);
			if(isModified()) sb.append('*');
			sb.append('{');
			
			for (ModifiedModel model : this) {
				if (first) first = false;
				else sb.append(',');
				sb.append(GwtGoodies.toDebugString(model));
			}
			return sb.append('}').toString();
		}
	}
	
	static abstract class
	AbstractLogicModifiedModelSet extends AbstractModifiedModelSet {

		protected int modifiedCounter = 0;
		
		public AbstractLogicModifiedModelSet(String setType, ModifiedModel... models) {
			super(setType);
			add(models);
		}

		@Override
		public boolean add(ModifiedModel...models) {
			for (ModifiedModel model : models)
				if (model.isModified()) modifiedCounter++;
			return super.add(models);
		}
		
		@Override
		public boolean remove(ModifiedModel...models) {
			for (ModifiedModel model : models)
				if (model.isModified()) modifiedCounter--;
			return super.remove(models);
		}
		
		@Override
		public void onModify(ModifiedEvent event) {
			modifiedCounter += event.isModified() ? +1 : -1;
			super.onModify(event);
		}
	}

	public static ModifiedModelSet and(ModifiedModel...models) {
		return new AbstractLogicModifiedModelSet("and", models) {
			@Override
			protected boolean eval() {
				return modifiedCounter == size();
			}
		};
	}

	public static ModifiedModelSet or(ModifiedModel...models) {
		return new AbstractLogicModifiedModelSet("or", models) {
			@Override
			protected boolean eval() {
				return modifiedCounter > 0;
			}
		};
	}

	public static ModifiedModelSet xor(ModifiedModel...models) {
		return new AbstractLogicModifiedModelSet("xor", models) {
			@Override
			protected boolean eval() {
				return modifiedCounter == 0 || modifiedCounter == size();
			}
		};
	}

	public static ModifiedModel not(final ModifiedModel model) {
		return new BaseModifiedModel() {
			{
				model.addModifiedHandler(new ModifiedHandler() {
					@Override
					public void onModify(ModifiedEvent event) {
						setModified(!event.getModel().isModified());
					}
				}, true);
			}
			@Override
			public void burn() {
				model.burn();
			}
		};
	}

	public static <T> ConfigureableModifiedModel<T> create(final HasValue<T> hasValue) {
		return new AbstractHasValueCahangeHandlersModifiedModel<T>(hasValue) {
			@Override
			protected T getCurrentValue() {
				return hasValue.getValue();
			}
		};
	}

	public static <T extends Number> ConfigureableModifiedModel<T> create(final NumberModel<T> numberModel) {
		return new AbstractHasValueCahangeHandlersModifiedModel<T>(numberModel) {
			@Override
			protected T getCurrentValue() {
				return numberModel.getNumber();
			}
			
		};
	}
}
