package com.adaptiweb.gwt.framework.modify;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.HasDebugInfo;
import com.google.gwt.event.shared.HandlerRegistration;

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
		public String toDebugString() {
			StringBuilder sb = new StringBuilder();
			sb.append(type).append('{');
			
			for (ModifiedModel model : this) {
				sb.append(GwtGoodies.toDebugString(model)).append(',');
			}
			sb.setCharAt(sb.length() - 1 , '}');
			return sb.toString();
		}
	}
	
	private static abstract class
	AbstractLogicModifiedModelSet extends AbstractModifiedModelSet {

		protected int validCounter = 0;
		
		public AbstractLogicModifiedModelSet(String setType, ModifiedModel... models) {
			super(setType);
			add(models);
		}

		@Override
		public boolean add(ModifiedModel...models) {
			for (ModifiedModel model : models)
				if (!model.isModified()) validCounter++;
			return super.add(models);
		}
		
		@Override
		public boolean remove(ModifiedModel...models) {
			for (ModifiedModel model : models)
				if (model.isModified()) validCounter--;
			return super.remove(models);
		}
		
		@Override
		public void onModify(ModifiedEvent event) {
			validCounter += event.isModified() ? +1 : -1;
			super.onModify(event);
		}
	}

	public static ModifiedModelSet and(ModifiedModel...models) {
		return new AbstractLogicModifiedModelSet("and", models) {
			@Override
			protected boolean eval() {
				return validCounter == size();
			}
		};
	}

	public static ModifiedModelSet or(ModifiedModel...models) {
		return new AbstractLogicModifiedModelSet("or", models) {
			@Override
			protected boolean eval() {
				return validCounter > 0;
			}
		};
	}

	public static ModifiedModelSet xor(ModifiedModel...models) {
		return new AbstractLogicModifiedModelSet("xor", models) {
			@Override
			protected boolean eval() {
				return validCounter == 0 || validCounter == size();
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
		};
	}
	
}
