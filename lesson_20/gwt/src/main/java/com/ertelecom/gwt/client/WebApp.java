package com.ertelecom.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.*;

import static com.google.gwt.user.client.ui.HasVerticalAlignment.ALIGN_MIDDLE;

public class WebApp implements EntryPoint {
    public void onModuleLoad() {
        Defaults.setServiceRoot("http://localhost:8189/gwt-rest");
        TasksTableWidget tasksTableWidget = new TasksTableWidget();
        VerticalPanel verticalPanel = new VerticalPanel();

        // строка с кнопкой и фильтром
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        horizontalPanel.setVerticalAlignment(ALIGN_MIDDLE);
        AddFormWidget addForm = new AddFormWidget(tasksTableWidget);

        Button addTaskButton = new Button("Создать задачу");
        addTaskButton.addClickHandler(new ClickHandler() {
              public void onClick(ClickEvent event) {
                  addForm.open();
              }
         });
        horizontalPanel.add(addTaskButton);

        FilterFormWidget filterForm = new FilterFormWidget(tasksTableWidget);
        horizontalPanel.add(filterForm);

        verticalPanel.add(horizontalPanel);
        verticalPanel.add(tasksTableWidget);

        // Create a tab panel
        TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Style.Unit.EM);
        tabPanel.setAnimationDuration(100);
        tabPanel.getElement().getStyle().setMarginBottom(10.0, Style.Unit.PX);
        tabPanel.addSelectionHandler(event -> History.newItem("page" + event.getSelectedItem()));

        History.addValueChangeHandler(event -> {
            String historyToken = event.getValue();
            try {
                if (historyToken.substring(0, 4).equals("page")) {
                    String tabIndexToken = historyToken.substring(4, 5);
                    int tabIndex = Integer.parseInt(tabIndexToken);
                    tabPanel.selectTab(tabIndex);
                } else {
                    tabPanel.selectTab(0);
                }
            } catch (IndexOutOfBoundsException e) {
                tabPanel.selectTab(0);
            }
        });

        LoginForm loginForm = new LoginForm(tabPanel, tasksTableWidget);
        tabPanel.add(loginForm, "Login");

        tabPanel.add(verticalPanel, "Main Page");
        tabPanel.setHeight("800px");

        tabPanel.selectTab(0);
        tabPanel.ensureDebugId("cwTabPanel");
        tabPanel.getTabWidget(1).setVisible(false);
        tabPanel.getTabWidget(1).getParent().setVisible(false);

        Label header = new Label("Задачи");
        header.setStyleName("headerLabel");
        RootPanel.get().add(header);

        RootPanel.get().add(tabPanel);
    }
}
