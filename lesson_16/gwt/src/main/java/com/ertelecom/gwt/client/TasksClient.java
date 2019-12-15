package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.TaskDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;

@Path("/tasks")
public interface TasksClient extends RestService {
    @GET
    void getAllTasks(MethodCallback<List<TaskDto>> items);

    @GET
    @Path("?statusId={statusId}&ownerName={ownerName}")
    void getFilterTasks(@PathParam("statusId") String statusId
                       ,@PathParam("ownerName") String ownerName
                       , MethodCallback<List<TaskDto>> items);

    @GET
    @Path("delete/{id}")
    void deleteTask(@PathParam("id") String id, MethodCallback<Void> result);
}
