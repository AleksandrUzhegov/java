package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.AuthRequestDto;
import com.ertelecom.gwt.common.AuthResponseDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;

public interface AuthClient extends RestService {
    @POST
    @Path("/authenticate")
    void authenticate(@BeanParam() AuthRequestDto authRequest, MethodCallback<AuthResponseDto> result);
}