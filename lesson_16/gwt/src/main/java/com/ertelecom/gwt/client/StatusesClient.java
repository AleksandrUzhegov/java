package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.StatusDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Path("/statuses")
public interface StatusesClient extends RestService {
    @GET
    void getAllStatuses(MethodCallback<List<StatusDto>> items);
}


