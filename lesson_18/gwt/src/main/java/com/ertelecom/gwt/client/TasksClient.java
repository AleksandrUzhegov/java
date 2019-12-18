package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.TaskDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;

@Path("/tasks")
public interface TasksClient extends RestService {
    @GET
    @Path("?token={token}")
    void getAllTasks(@PathParam("token") String token
                    ,MethodCallback<List<TaskDto>> items);

    @GET
    @Path("?statusId={statusId}&ownerName={ownerName}&token={token}")
    void getFilterTasks(@PathParam("statusId") String statusId
                       ,@PathParam("ownerName") String ownerName
                       ,@PathParam("token") String token
                       , MethodCallback<List<TaskDto>> items);

    @GET
    @Path("delete/{id}?token={token}")
    void deleteTask(@PathParam("id") String id
                   ,@PathParam("token") String token
                   ,MethodCallback<Void> result);
}
