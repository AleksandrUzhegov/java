package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.StatusDto;
import com.ertelecom.gwt.common.TaskDto;
import com.ertelecom.gwt.common.UserDto;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.Collections;
import java.util.List;

public class TasksTableWidget extends Composite {
    private TasksClient client;
    interface TasksTableBinder extends UiBinder<Widget, TasksTableWidget> {}
    private static TasksTableBinder uiBinder = GWT.create(TasksTableBinder.class);

    private List<StatusDto> listStatuses;
    private List<UserDto> listUsers;
    private FilterFormWidget filterFormWidget;

    @UiField
    CellTable<TaskDto> table;

    public String getToken() {
        String token = Storage.getLocalStorageIfSupported().getItem("jwt");
        if (token != null) return token;
        return "0";
    }

    public void setFilterFormWidget(FilterFormWidget filterFormWidget) {
        this.filterFormWidget = filterFormWidget;
    }

    public TasksTableWidget() {
        initWidget(uiBinder.createAndBindUi(this));
        listStatuses = Collections.emptyList();
        listUsers = Collections.emptyList();

        TextColumn<TaskDto> idColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return Long.toString(task.getId() );
            }
        };
        table.addColumn(idColumn, "Идентификатор");

        TextColumn<TaskDto> titleColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getTaskName();
            }
        };
        table.addColumn(titleColumn, "Название задачи");

        TextColumn<TaskDto> ownerColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getOwner().getUserName();
            }
        };
        table.addColumn(ownerColumn, "Владелец");

        TextColumn<TaskDto> executorColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getExecutor().getUserName();
            }
        };
        table.addColumn(executorColumn, "Исполнитель");

        TextColumn<TaskDto> infoColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getInfo();
            }
        };
        table.addColumn(infoColumn, "Информация");

        TextColumn<TaskDto> statusColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto task) {
                return task.getStatus().getStatusName();
            }
        };
        table.addColumn(statusColumn, "Статус");

        client = GWT.create(TasksClient.class);
        EditFormWidget editForm = new EditFormWidget(this);
        // кнопка "редактировать"
        Column<TaskDto, TaskDto> actionColumn = new Column<TaskDto, TaskDto>(
                new ActionCell<TaskDto>("Редактировать", new ActionCell.Delegate<TaskDto>() {
                    @Override
                    public void execute(TaskDto item) {
                        editForm.open(item);
                    }
                })) {
            @Override
            public TaskDto getValue(TaskDto item) {
                return item;
            }
        };

        table.addColumn(actionColumn, "Действие");
    }

    public void open() {
        filterFormWidget.open();
        refresh();
    }

    public void refresh() {
        filterFormWidget.refresh();
    }

    // установить список статусов из кэша
    private void setListStatusCache(ListBox statusBox, StatusDto statusDto) {
        statusBox.clear();
        if (statusDto == null) statusBox.addItem("","");
        for (StatusDto t: listStatuses) {
            statusBox.addItem( t.getStatusName(), t.getStatusId().toString());
            if (t.equals(statusDto)) {
                statusBox.setSelectedIndex(statusBox.getItemCount()-1);
            }
        }
    }

    // установка списка статусов
    public void setListStatus(ListBox statusBox, StatusDto statusDto) {
        if (listStatuses.isEmpty()) {
            StatusesClient client = GWT.create(StatusesClient.class);
            client.getAllStatuses(getToken(), new MethodCallback<List<StatusDto>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert("Невозможно получить список статусов: Сервер не отвечает");
                }

                @Override
                public void onSuccess(Method method, List<StatusDto> statusDtos) {
                    // сохраним результат в кеше
                    listStatuses = statusDtos;
                    setListStatusCache(statusBox, statusDto);
                }

            });
        } else setListStatusCache(statusBox, statusDto);
    }

    // установить список пользователей из кэша
    private void setListUserCache(ListBox userBox, UserDto userDto) {
        userBox.clear();
        for (UserDto t: listUsers) {
            userBox.addItem( t.getUserName(), t.getUserId().toString());
            if (t.equals(userDto)) {
                userBox.setSelectedIndex(userBox.getItemCount()-1);
            }
        }
    }

    // установка списка пользователей
    public void setListUser(ListBox userBox, UserDto userDto) {
        if (listUsers.isEmpty() || ((userDto!=null) && (listUsers.indexOf(userDto) < 0))) {
            UsersClient client = GWT.create(UsersClient.class);

            client.getAllUsers(getToken(), new MethodCallback<List<UserDto>>() {
                @Override
                public void onFailure(Method method, Throwable throwable) {
                    Window.alert("Невозможно получить список пользователей: Сервер не отвечает");
                }

                @Override
                public void onSuccess(Method method, List<UserDto> usersDtos) {
                    // сохраним результат в кеше
                    listUsers = usersDtos;
                    setListUserCache(userBox, userDto);
                }
            });
        } else setListUserCache(userBox, userDto);
    }
}


