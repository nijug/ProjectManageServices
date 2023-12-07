package org.ProjectService;

import retrofit2.Call;
import retrofit2.http.*;

public interface ProjectServiceInterface {
    @POST("create")
    Call<Void> addProject(@Body Project project);

    @GET("all")
    Call<Project[]> readProjects();

    @DELETE("{projectId}")
    Call<Void> deleteProject(@Path("projectId") Integer projectId);

    @PUT("{projectId}")
    Call<Void> updateProject(@Path("projectId") Integer projectId, @Body Project updatedProject);

    @GET("{projectId}")
    Call<Project> getProjectById(@Path("projectId") Integer projectId);
}
