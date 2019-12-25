package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.UserDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/users")
public interface UsersClient extends RestService {
    @GET
    void getAllUsers(MethodCallback<List<UserDto>> items);
}
