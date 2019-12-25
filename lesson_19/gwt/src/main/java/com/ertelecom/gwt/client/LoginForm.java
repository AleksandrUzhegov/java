package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.JwtAuthRequestDto;
import com.ertelecom.gwt.common.JwtAuthResponseDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class LoginForm extends Composite {
    private TasksTableWidget tasksTableWidget;
    private TabLayoutPanel tabPanel;

    public void setToken(String token) {
        Storage.getLocalStorageIfSupported().setItem("jwt", "Bearer " +  token);
    }

    @UiField
    FormPanel form;

    @UiField
    TextBox textUsername;

    @UiField
    TextBox textPassword;

    @UiTemplate("LoginForm.ui.xml")
    interface LoginFormBinder extends UiBinder<Widget, LoginForm> {
    }

    private static LoginForm.LoginFormBinder uiBinder = GWT.create(LoginForm.LoginFormBinder.class);

    public LoginForm(TabLayoutPanel tabPanel, TasksTableWidget tasksTableWidget) {
        this.tasksTableWidget = tasksTableWidget;
        this.tabPanel = tabPanel;
        this.initWidget(uiBinder.createAndBindUi(this));
        this.form.setAction("");
        setToken("");
    }

    @UiHandler("form")
    public void onSubmit(FormPanel.SubmitEvent event) {
    }

    @UiHandler("form")
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        Window.alert(event.getResults());
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        JwtAuthRequestDto jwtAuthRequestDto = new JwtAuthRequestDto(textUsername.getValue(), textPassword.getValue());
        AuthClient authClient = GWT.create(AuthClient.class);
        authClient.authenticate(jwtAuthRequestDto, new MethodCallback<JwtAuthResponseDto>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(method.getResponse().getText());
                setToken("");
                Window.alert("Имя пользователя или пароль не найдены");
            }

            @Override
            public void onSuccess(Method method, JwtAuthResponseDto jwtAuthResponseDto) {
                GWT.log(jwtAuthResponseDto.getToken());
                setToken(jwtAuthResponseDto.getToken());
                tabPanel.selectTab(1);
                tabPanel.getTabWidget(0).setVisible(false);
                tabPanel.getTabWidget(0).getParent().setVisible(false);
                tasksTableWidget.open();
            }
        });
    }

    @UiHandler("btnClose")
    public void closeClick(ClickEvent event) {
        setToken("");
        form.reset();
    }


}