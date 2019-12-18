package com.ertelecom.gwt.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;

public class AddFormWidget extends DialogBox  {
    private TasksTableWidget tasksTableWidget;
    interface AddFormBinder extends UiBinder<Widget, AddFormWidget> {}
    private static final AddFormBinder uiBinder = GWT.create(AddFormBinder.class);

    @UiField
    FormPanel form;

    @UiField
    ListBox ownerId;

    @UiField
    ListBox executorId;

    @UiField
    TextBox taskName;

    @UiField
    TextArea info;

    @UiField
    Hidden token;

    public AddFormWidget(TasksTableWidget tasksTableWidget) {
        setWidget(uiBinder.createAndBindUi(this));
        setAutoHideEnabled(false);
        this.tasksTableWidget = tasksTableWidget;

        this.form.setMethod("POST");
        this.form.setAction(Defaults.getServiceRoot().concat("tasks"));
        setText("Создание задачи");
    }

    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {
        if (taskName.getText().length() == 0) {
            Window.alert("Необходимо заполнить название задачи");
            event.cancel();
        }
    }

    @UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        tasksTableWidget.refresh();
        hide();
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        form.submit();
    }

    @UiHandler("btnClose")
    public void closeClick(ClickEvent event) {
        hide();
    }

    public void open() {
        form.reset();
        tasksTableWidget.setListUser(ownerId, null);
        tasksTableWidget.setListUser(executorId, null);
        token.setValue(tasksTableWidget.getToken());
        center();
    }

}
