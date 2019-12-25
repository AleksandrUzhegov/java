package com.ertelecom.gwt.client;

import com.ertelecom.gwt.common.TaskDto;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import java.util.List;

@Path("/tasks")
public interface TasksClient extends RestService {
    @GET
    void getAllTasks(@HeaderParam("Authorization") String token
                    ,MethodCallback<List<TaskDto>> items);

    @GET
    @Path("?statusId={statusId}&ownerName={ownerName}")
    void getFilterTasks(@HeaderParam("Authorization") String token
                       ,@PathParam("statusId") String statusId
                       ,@PathParam("ownerName") String ownerName
                       , MethodCallback<List<TaskDto>> items);

    @DELETE
    @Path("delete/{id}")
    void deleteTask(@HeaderParam("Authorization") String token
                   ,@PathParam("id") String id
                   ,MethodCallback<Void> result);

    @POST
    void save(@HeaderParam("Authorization") String token, @BeanParam() TaskDto taskDto, MethodCallback<Void> result);
}
