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

import java.util.List;

public class FilterFormWidget extends Composite {
    private TasksTableWidget tasksTableWidget;
    interface FilterFormBinder extends UiBinder<Widget, FilterFormWidget> {}
    private static FilterFormWidget.FilterFormBinder uiBinder = GWT.create(FilterFormWidget.FilterFormBinder.class);

    @UiField
    FormPanel form;

    @UiField
    ListBox statusId;

    @UiField
    TextBox ownerName;

    public FilterFormWidget(TasksTableWidget tasksTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.tasksTableWidget = tasksTableWidget;
        tasksTableWidget.setListStatus(statusId, null);

        form.setAction("#");
    }

    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {
    }

    @UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        filter();
    }

    public void filter() {
        TasksClient client = GWT.create(TasksClient.class);
        client.getFilterTasks(statusId.getSelectedValue()
                            , ownerName.getValue()
                            , tasksTableWidget.getToken()
                            , new MethodCallback<List<TaskDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
                Window.alert("Невозможно получить список items: Сервер не отвечает");
            }

            @Override
            public void onSuccess(Method method, List<TaskDto> items) {
                GWT.log("Received " + items.size() + " items");
                tasksTableWidget.table.setRowData( items );
            }
        });
    }


}
