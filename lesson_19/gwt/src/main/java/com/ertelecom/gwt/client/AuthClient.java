package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.JwtAuthRequestDto;
import com.ertelecom.gwt.common.JwtAuthResponseDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;

public interface AuthClient extends RestService {
    @POST
    @Path("/authenticate")
    void authenticate(@BeanParam() JwtAuthRequestDto authRequest, MethodCallback<JwtAuthResponseDto> result);
}