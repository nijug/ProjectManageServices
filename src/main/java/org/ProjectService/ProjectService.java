package org.ProjectService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.sql.Date;


public class ProjectService {

    private static String BASE_URL = "http://20.215.201.5:8080/project/";
    private static ProjectService instance;
    private final ProjectServiceInterface projectApi;

    private ProjectService(ProjectServiceInterface projectApi) {
        this.projectApi = projectApi;
    }

    public static synchronized ProjectService getInstance(String accessToken) {
        if (instance == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new DateTypeAdapter())
                    .create();
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();

                        // Add the Authorization header
                        Request request = original.newBuilder()
                                .header("Authorization", "Bearer " + accessToken)
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    })
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            ProjectServiceInterface projectApi = retrofit.create(ProjectServiceInterface.class);
            instance = new ProjectService(projectApi);
        }
        System.out.println("BASE_URL: " + BASE_URL);
        return instance;
    }

    public static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
        System.out.println("BASE_URL: " + BASE_URL);
        instance = null;
    }
    public void addProjects(Project project) {
        Call<Void> call = projectApi.addProject(project);
        handleResponse(call);
    }

    public Project[] readProjects() {
        Call<Project[]> call = projectApi.readProjects();
        return handleResponse(call);
    }

    public void deleteProjects(Integer projectId) {
        Call<Void> call = projectApi.deleteProject(projectId);
        handleResponse(call);
    }

    public void updateProjects(Project updatedProject) {
        Call<Void> call = projectApi.updateProject(updatedProject.getId(), updatedProject);
        handleResponse(call);
    }

    public Project getProjectById(Integer projectId) {
        Call<Project> call = projectApi.getProjectById(projectId);
        return handleResponse(call);
    }

    private <T> T handleResponse(Call<T> call) {
        try {
            retrofit2.Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                System.err.println("Error: HTTP status code " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}