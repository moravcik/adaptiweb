package com.adaptiweb.gwt.framework;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Dialog {
	final private DialogBox dialogBox = new DialogBox(false, true);
	final private static Glass glass = new Glass();
	final private SimplePanel contentPanel = new SimplePanel();
	final private HorizontalPanel buttonPanel = new HorizontalPanel();
	final private Timer showTimer = new Timer() {
			@Override
			public void run() {
		        dialogBox.center();
		        dialogBox.setVisible(true);
			}
	    };
	private boolean inicialized = false;

	final private ClickHandler hideByClickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			hide();
		}
	};
	
	final private CloseHandler<PopupPanel> closeHandler = new CloseHandler<PopupPanel>() {
		@Override
		public void onClose(CloseEvent<PopupPanel> event) {
			afterHide(event.isAutoClosed());
		}
	};
	
	public boolean isAutoHide() {
		return dialogBox.isAutoHideEnabled();
	}
	
	public void setAutoHide(boolean autoHide) {
		dialogBox.setAutoHideEnabled(autoHide);
	}
	
	public boolean isModal() {
		return dialogBox.isModal();
	}

	public void setModal(boolean modal) {
		if (modal != dialogBox.isModal()) {
			if (isShowing()) {
				if (modal) glass.show();
				else glass.hide();
			}
			dialogBox.setModal(modal);
		}
	}
	
	public void addAutoHidePartner(Element partner) {
		dialogBox.addAutoHidePartner(partner);
	}
	
	public void removeAutoHidePartner(Element partner) {
		dialogBox.removeAutoHidePartner(partner);
	}

	public HandlerRegistration addCloseHandler(CloseHandler<PopupPanel> handler) {
		return dialogBox.addCloseHandler(handler);
	}

	/**
	 * If you intended to overwrite this method,
	 * overwrite {@link #afterHide(boolean)} or register {@link CloseHandler} by {@link #addCloseHandler(CloseHandler)}.
	 */
	public final void hide() {
		dialogBox.hide();
	}
	
	public void show() {
		if (isShowing()) return;
        if (inicialized == false) {
    		initContent(contentPanel);
    		initActionButton();
    		inicialized = true;
        }
        else cleanUp();
        
        if (isModal()) glass.show();
        dialogBox.setVisible(false);
        dialogBox.show();
        showTimer.schedule(1);
	}
	
	protected void cleanUp() {
	}

	public Dialog(String title) {
		dialogBox.setText(title);
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(contentPanel);
		verticalPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		verticalPanel.add(buttonPanel);
		dialogBox.add(verticalPanel);
		dialogBox.addCloseHandler(closeHandler);
		buttonPanel.setSpacing(2);
	}
	
	protected void initContent(Panel panel) {
	}

	protected void initActionButton() {
		addButton("Close", true);
	}
	
	public Button addButton(String label, boolean closingButton) {
		return addButton(new Button(label), closingButton);
	}
	
	public <T extends ButtonBase> T addButton(T button, boolean closingButton) {
		buttonPanel.add(new Label(" "));
		buttonPanel.add(button);
		buttonPanel.add(new Label(" "));
		if(closingButton)
			button.addClickHandler(hideByClickHandler);
		return button;
	}
	
	public void wrapOpenDialogButton(ElementIdentifier elementId) {
		Button.wrap(GwtGoodies.getElementById(elementId))
		.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				show();
			}
		});
	}

	protected <T extends Panel> T replaceSimplePannel(T replacement) {
		contentPanel.add(replacement);
		return replacement;
	}

	public void center() {
		dialogBox.center();
	}

	public String getTitle() {
		return dialogBox.getText();
	}

	public void setTitle(String title) {
		dialogBox.setText(title);
	}

	public boolean isShowing() {
		return dialogBox.isShowing();
	}
	
	protected void afterHide(boolean autoHiden) {
		if (isModal()) glass.hide();
	}
}
