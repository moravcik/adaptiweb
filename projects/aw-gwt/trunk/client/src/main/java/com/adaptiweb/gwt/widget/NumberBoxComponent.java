package com.adaptiweb.gwt.widget;

import com.adaptiweb.gwt.framework.GwtGoodies;
import com.adaptiweb.gwt.framework.logic.LogicModelSet;
import com.adaptiweb.gwt.framework.validation.DummyValidation;
import com.adaptiweb.gwt.framework.validation.MinMaxValidation;
import com.adaptiweb.gwt.framework.validation.PrecisionValidation;
import com.adaptiweb.gwt.framework.validation.ValidationModel;
import com.adaptiweb.gwt.mvc.model.DefaultNumberModel;
import com.adaptiweb.gwt.mvc.model.NumberModel;
import com.adaptiweb.gwt.util.NumberFomatter;
import com.adaptiweb.gwt.util.NumberType;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

public class NumberBoxComponent<T extends Number> extends FormComponent implements NumberModel<T> {
	
	protected final TextBoxBase widget = new TextBox();
	protected final NumberModel<T> model;
	private final NumberFomatter<T> formatter;
	private final DummyValidation isNumber = new DummyValidation(true);

	public NumberBoxComponent(NumberType<T> type) {
		this(type.defaultFormatter(), new DefaultNumberModel<T>());
	}
	
	public NumberBoxComponent(NumberType<T> type, String format) {
		this(NumberFomatter.create(type, format), new DefaultNumberModel<T>());
	}

	public NumberBoxComponent(NumberType<T> type, NumberModel<T> model) {
		this(type.defaultFormatter(), model);
	}
	
	public NumberBoxComponent(NumberType<T> type, String format, NumberModel<T> model) {
		this(NumberFomatter.create(type, format), model);
	}

	private NumberBoxComponent(NumberFomatter<T> formatter, NumberModel<T> numberModel) {
		this.formatter = formatter;
		this.model = numberModel;
		initWidget(widget);
		registrations.add(widget.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAnyModifierKeyDown()) return;
				Double value = null;
				switch(event.getNativeEvent().getKeyCode()) {
				case KeyCodes.KEY_UP:
					value = doubleValue() + 1.0;
					break;
				case KeyCodes.KEY_DOWN:
					value = doubleValue() - 1.0;
					break;
				}
				if (value != null)
					model.setNumber(NumberBoxComponent.this.formatter.convert(value));
			}
		}));
		registrations.add(widget.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				try {
					model.setNumber(NumberBoxComponent.this.formatter.parse(widget.getValue()));
					isNumber.setValid(true);
				} catch (IllegalArgumentException e) {
					isNumber.setValid(false);
				}
			}
		}));
		registrations.add(model.addValueChangeHandler(new ValueChangeHandler<T>() {
			@Override
			public void onValueChange(ValueChangeEvent<T> event) {
				String text = NumberBoxComponent.this.formatter.format(model.getNumber());
				if (!GwtGoodies.areEquals(widget.getValue(), text)) {
					widget.setValue(text);
					model.setNumber(NumberBoxComponent.this.formatter.parse(text));
				}
			}
		}));
	}
	
	private double doubleValue() {
		return model.getNumber() == null ? 0.0 : model.getNumber().doubleValue();
	}
	
	@Override
	protected LogicModelSet validations(ValidationModel... initModels) {
		boolean calledFirstTime = getValidation() == null;
		LogicModelSet validations = super.validations(initModels);
		if (calledFirstTime) validations.add(isNumber);
		return validations;
	}

	@Override
	public T getNumber() {
		return model.getNumber();
	}

	@Override
	public void setNumber(T value) {
		model.setNumber(value);
	}

	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
		return model.addValueChangeHandler(handler);
	}
	
	public ValidationModel setMinMaxValidation(Long min, Long max) {
		return addValidation(new MinMaxValidation(this, min, max));
	}

	public ValidationModel setMinMaxValidation(Double min, Double max) {
		return addValidation(new MinMaxValidation(this, min, max));
	}
	
	public ValidationModel setPrecisionValidation(int decimalPlaces) {
		return addValidation(new PrecisionValidation(this, decimalPlaces));
	}

	// generalized
	@Override
	public T getValue() {
		return model.getNumber();
	}

	@Override
	public void setValue(T value) {
		model.setValue(value);
	}

	@Override
	public void setValue(T value, boolean fireEvents) {
		model.setValue(value, fireEvents);
	}
}
