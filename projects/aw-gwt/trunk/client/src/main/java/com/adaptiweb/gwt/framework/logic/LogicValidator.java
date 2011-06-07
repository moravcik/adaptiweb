package com.adaptiweb.gwt.framework.logic;

import com.adaptiweb.gwt.framework.style.Color;
import com.adaptiweb.gwt.framework.style.Display;
import com.adaptiweb.gwt.framework.style.DynamicStyle;
import com.adaptiweb.gwt.framework.style.Style;
import com.adaptiweb.gwt.framework.style.SwapStylesLogicValueChangeHandler;
import com.adaptiweb.gwt.framework.validation.WidgetEnabler;
import com.adaptiweb.gwt.widget.TabValidationWidget;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.Widget;

public class LogicValidator {

	public static final String EMAIL_PATTERN = "^\\S+@\\S+\\.\\S+$";

	private static final Style DUMMY_STYLE = new Style() {
		@Override
		public void apply(Element element) {
			// do nothing - reset styles
			element.getStyle().setBackgroundColor("");
			element.getStyle().setBorderColor("");
			element.getStyle().setColor("");
			element.getStyle().setProperty("cursor", "");
		}
	};
	
	public static final DynamicStyle VALID_STYLE = new DynamicStyle() {
		@Override
		public void apply(Element element) {
			element.getStyle().setBackgroundColor(Color.LightGreen.colorValue());
			element.getStyle().setBorderColor(Color.Green.colorValue());
		}
		@Override
		public void cancel(Element element) {
			element.getStyle().setBackgroundColor("");
			element.getStyle().setBorderColor("");
		}
	};

	public static final DynamicStyle INVALID_STYLE = new DynamicStyle() {
		@Override
		public void apply(Element element) {
			element.getStyle().setBackgroundColor(Color.custom(0xff9966).colorValue());
			element.getStyle().setBorderColor(Color.Red.colorValue());
		}
		@Override
		public void cancel(Element element) {
			element.getStyle().setBackgroundColor("");
			element.getStyle().setBorderColor("");
		}
	};
	
	private static final DynamicStyle DISABLED_STYLE = new DynamicStyle() {
		@Override
		public void apply(Element element) {
			element.getStyle().setColor(Color.custom("#ccc").colorValue());
			element.getStyle().setCursor(Cursor.DEFAULT);
		}
		@Override
		public void cancel(Element element) {
			element.getStyle().setColor("");
			element.getStyle().setProperty("cursor", "");
		}
	};
	
	private final LogicModelSet validations = LogicModelFactory.and();
	
	public LogicModelSet getValidation() {
		return validations;
	}
	
	public boolean isValid() {
		return validations.getLogicValue();
	}

	public void addValidation(LogicModel validation) {
		validations.add(validation);
	}

	public LogicModel applyInvalidVisualizer(Widget widget) {
		return applyInvalidVisualizer(validations, widget.getElement());
	}

	public LogicModel applyValidVisualizer(Widget widget) {
		return applyValidVisualizer(validations, widget.getElement());
	}

	public LogicModel applyEnablerVisualizer(Widget widget) {
		return applyEnablerVisualizer(validations, widget.getElement());
	}
	
	public static LogicModel applyValidVisualizer(LogicModel logicModel, Element element) {
		return SwapStylesLogicValueChangeHandler.create(element, VALID_STYLE, DUMMY_STYLE).register(logicModel);
	}

	public static LogicModel applyInvalidVisualizer(LogicModel logicModel, Element element) {
		return SwapStylesLogicValueChangeHandler.create(element, DUMMY_STYLE, INVALID_STYLE).register(logicModel);
	}

	public static LogicModel applyLogicShowHide(LogicModel logicModel, Element element) {
		return SwapStylesLogicValueChangeHandler.create(element, Display.DEFAULT, Display.NONE).register(logicModel);
	}

	public static LogicModel applyEnablerVisualizer(LogicModel logicModel, Element element) {
		return SwapStylesLogicValueChangeHandler.create(element, DUMMY_STYLE, DISABLED_STYLE).register(logicModel);
	}
	
	public <T extends ButtonBase> void applyEnabler(T submit) {
		new WidgetEnabler(submit).apply(validations);
	}

	public void applyEnabler(TabBar tabBar, int tabIndex) {
		new WidgetEnabler(new TabValidationWidget(tabBar, tabIndex)).apply(validations);
	}

}
