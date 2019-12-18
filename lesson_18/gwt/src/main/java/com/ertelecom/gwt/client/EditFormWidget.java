package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.TaskDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class EditFormWidget extends DialogBox  {
    private TasksTableWidget tasksTableWidget;
    private TasksClient client;
    interface AddFormBinder extends UiBinder<Widget, EditFormWidget> {}
    private static final AddFormBinder uiBinder = GWT.create(AddFormBinder.class);

    @UiField
    FormPanel form;

    @UiField
    Hidden id;

    @UiField
    TextBox taskName;

    @UiField
    ListBox statusId;

    @UiField
    ListBox ownerId;

    @UiField
    ListBox executorId;

    @UiField
    TextArea info;

    @UiField
    Hidden token;

    public EditFormWidget(TasksTableWidget tasksTableWidget) {
        setWidget(uiBinder.createAndBindUi(this));
        setAutoHideEnabled(false);
        this.tasksTableWidget = tasksTableWidget;
        client = GWT.create(TasksClient.class);
        form.setMethod("POST");
        form.setAction(Defaults.getServiceRoot().concat("tasks"));
        setText("Редактирование задачи");
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

    @UiHandler("btnDelete")
    public void deleteClick(ClickEvent event) {
        client.deleteTask(id.getValue(), token.getValue(), new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Ошибка удаления задачи: " + throwable.getMessage());
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
            }

            @Override
            public void onSuccess(Method method, Void result) {
                tasksTableWidget.refresh();
                hide();
            }
        });
    }

    public void open(TaskDto taskDto) {
        id.setValue(taskDto.getId().toString());
        taskName.setValue(taskDto.getTaskName());
        info.setValue(taskDto.getInfo());
        tasksTableWidget.setListStatus(statusId, taskDto.getStatus() );
        tasksTableWidget.setListUser(ownerId, taskDto.getOwner());
        tasksTableWidget.setListUser(executorId, taskDto.getExecutor());
        token.setValue(tasksTableWidget.getToken());
        center();
    }

}