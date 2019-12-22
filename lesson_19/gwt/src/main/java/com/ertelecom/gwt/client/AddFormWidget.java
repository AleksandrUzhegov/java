package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.TaskDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

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

    public AddFormWidget(TasksTableWidget tasksTableWidget) {
        setWidget(uiBinder.createAndBindUi(this));
        setAutoHideEnabled(false);
        this.tasksTableWidget = tasksTableWidget;
        this.form.setAction("");
        setText("Создание задачи");
    }

    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {
    }

    @UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        if (taskName.getText().length() == 0) {
            Window.alert("Необходимо заполнить название задачи");
            return;
        }
        TasksClient tasksClient = GWT.create(TasksClient.class);
        TaskDto taskDto = new TaskDto(null, taskName.getValue(), info.getValue()
                                    , null
                                    , Long.parseLong(ownerId.getSelectedValue())
                                    , Long.parseLong(executorId.getSelectedValue()) );
        tasksClient.save(tasksTableWidget.getToken(), taskDto, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Ошибка при добавлении задачи");
            }

            @Override
            public void onSuccess(Method method, Void aVoid) {
                tasksTableWidget.refresh();
                hide();
            }
        });
    }

    @UiHandler("btnClose")
    public void closeClick(ClickEvent event) {
        hide();
    }

    public void open() {
        form.reset();
        tasksTableWidget.setListUser(ownerId, null);
        tasksTableWidget.setListUser(executorId, null);
        center();
    }

}