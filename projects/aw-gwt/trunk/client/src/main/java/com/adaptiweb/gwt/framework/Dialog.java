package com.adaptiweb.gwt.framework;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

// TODO: needed closing handler support
public class Dialog {
	final private DialogBox dialogBox = new DialogBox(false, true);
	final private PopupPanel glass = constructPopupGlass();
	final private SimplePanel contentPanel = new SimplePanel();
	final private HorizontalPanel buttonPanel = new HorizontalPanel();
	final private Timer showTimer = new Timer() {
			@Override
			public void run() {
		        dialogBox.center();
		        dialogBox.hide();
		        dialogBox.setVisible(true);
		        dialogBox.show();
			}
	    };
	private boolean inicialized = false;

	final private ClickHandler closeHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			hide();
		}
	};
	
	public void hide() {
		dialogBox.hide();
		glass.hide();
	}
	
	public void show() {
        if(inicialized == false) {
    		initContent(contentPanel);
    		initActionButton();
    		inicialized = true;
        }
        else cleanUp();
        
        glass.show();
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
			button.addClickHandler(closeHandler);
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

	public static PopupPanel constructPopupGlass() {
		final PopupPanel popupPanel = new PopupPanel();
		
		popupPanel.add(new Label(""));
		popupPanel.setPixelSize(
				Window.getScrollLeft() + Window.getClientWidth(),
                Window.getScrollTop() + Window.getClientHeight());
		
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				popupPanel.setPixelSize(event.getWidth(), event.getHeight());
			}
		});
        
        popupPanel.setPopupPosition(0, 0);
        popupPanel.setStyleName("glass");
        
        return popupPanel;
	}

	public void center() {
		dialogBox.center();
	}

	public String getTitle() {
		return dialogBox.getTitle();
	}

	public void setTitle(String title) {
		dialogBox.setTitle(title);
	}
	
}
