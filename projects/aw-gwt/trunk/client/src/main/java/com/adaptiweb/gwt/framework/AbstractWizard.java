package com.adaptiweb.gwt.framework;

import com.adaptiweb.gwt.framework.logic.LogicModel;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeEvent;
import com.adaptiweb.gwt.framework.logic.LogicValueChangeHandler;
import com.adaptiweb.gwt.mvc.model.DefaultNumberModel;
import com.adaptiweb.gwt.mvc.model.NumberModel;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public abstract class AbstractWizard extends Dialog implements ValueChangeHandler<Integer>, LogicValueChangeHandler {

	NumberModel<Integer> status = new DefaultNumberModel<Integer>();

	public interface ValidatingStep {
		LogicModel getValidationModel();
	}

	protected Button prevButton;
	protected Button nextButton;
	protected Button finalButton;
	private HandlerRegistration validation;
	private int index;

	public AbstractWizard() {
		super("Send us an order");
		status.addValueChangeHandler(this);
	}

	public int getCurentStepIndex() {
		return index;
	}

	@Override
	public void onValueChange(ValueChangeEvent<Integer> event) {
		index = event.getValue().intValue();
		for (int i = 0; i < steps().length; i++) {
			steps()[i].setVisible(i == index);
		}

		if (validation != null) {
			validation.removeHandler();
			validation = null;
		}
		if (steps()[index] instanceof ValidatingStep) {
			LogicModel validationModel = ((ValidatingStep) steps()[index]).getValidationModel();
			validation = validationModel.addLogicValueChangeHandler(this, true);
		}
		else updateButtonStatus(true);
	}

	private void updateButtonStatus(boolean isValid) {
		prevButton.setEnabled(index > 0);
		nextButton.setEnabled(isValid && index + 1 < steps().length);
		finalButton.setEnabled(isValid && index + 1 == steps().length);
	}

	@Override
	public void onLogicValueChange(LogicValueChangeEvent event) {
		updateButtonStatus(event.getLogicValue());
	}

	protected abstract Widget[] steps();

	@Override
	public void show() {
		super.show();
		status.setNumber(0);
	}

	@Override
	protected void initContent(Panel panel) {
		FlowPanel content = new FlowPanel();
		for (Widget step : steps()) {
			step.setSize("500px", "332px");
			content.add(step);
		}
		panel.add(content);
	}

	@Override
	protected void initActionButton() {
		addButton("Cancel", true);
		(prevButton = addButton(" << Previous", false)).addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				status.setValue(status.getValue().intValue() - 1);
			}
		});
		(nextButton = addButton("Next >>", false)).addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				status.setValue(status.getValue().intValue() + 1);
			}
		});
		finalButton = addButton("Send", false);
	}
}
