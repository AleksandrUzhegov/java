package com.ertelecom.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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

        LoginForm loginForm = new LoginForm(tasksTableWidget);
        tabPanel.add(loginForm, "Login");

        tabPanel.add(verticalPanel, "Main Page");
        tabPanel.setHeight("800px");

        tabPanel.selectTab(0);
        tabPanel.ensureDebugId("cwTabPanel");

        RootPanel.get().add(tabPanel);
    }
}
