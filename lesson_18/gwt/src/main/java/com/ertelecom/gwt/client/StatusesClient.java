package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.StatusDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/statuses")
public interface StatusesClient extends RestService {
    @GET
    @Path("?token={token}")
    void getAllStatuses(@PathParam("token") String token, MethodCallback<List<StatusDto>> items);
}


