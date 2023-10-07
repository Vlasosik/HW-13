package org.example.part;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;


@Data
public class UserUtil {
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/users";


    public static final HttpClient CLIENT = HttpClient.newHttpClient();
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static User createUser(User user) {
        String requestBody = OBJECT_MAPPER.writeValueAsString(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return OBJECT_MAPPER.readValue(response.body(), User.class);
    }

    @SneakyThrows
    public static User updateUser(User user) {
        String updateUser = OBJECT_MAPPER.writeValueAsString(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + user.getId()))
                .header("Content-type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(updateUser))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return OBJECT_MAPPER.readValue(response.body(), User.class);
    }

    @SneakyThrows
    public static User deleteUser(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + user.getId()))
                .header("Content-type", "application/json")
                .DELETE()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return OBJECT_MAPPER.readValue(response.body(), User.class);
    }

    @SneakyThrows
    public static List<User> infoUser(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return OBJECT_MAPPER.readValue(response.body(), new TypeReference<List<User>>() {
        });

    }

    @SneakyThrows
    public static User getInfoById(Long userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + userId))
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return OBJECT_MAPPER.readValue(response.body(), User.class);
    }

    @SneakyThrows
    public static List<User> getInfoByUserName(String getUserName) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "?username=" + getUserName))
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return OBJECT_MAPPER.readValue(response.body(), new TypeReference<List<User>>() {
        });
    }

    @SneakyThrows
    public static void getAllCommentsAndLastPost(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + user.getId() + "/posts"))
                .GET()
                .header("Content-type", "application/json")
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Posts> posts = OBJECT_MAPPER.readValue(response.body(), new TypeReference<List<Posts>>() {
        });
        Posts lastPost = posts.stream()
                .max(Comparator.comparing(Posts::getId))
                .orElse(null);
        assert lastPost != null;
        HttpRequest requestComments = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/posts" + lastPost.getId() + "/comments"))
                .GET()
                .build();
        HttpResponse<String> responseComment = CLIENT.send(requestComments, HttpResponse.BodyHandlers.ofString());
        List<Comments> commentsForLastPosts = OBJECT_MAPPER.readValue(responseComment.body(), new TypeReference<List<Comments>>() {
        });
        String fileName = "user-" + user.getId() + "-post-" + lastPost.getId() + "-comments.json";
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
            OBJECT_MAPPER.writeValue(fileWriter, commentsForLastPosts);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @SneakyThrows
    public static List<UserTask> getAllOpenTasks(User user) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/" + user.getId() + "/todos"))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<UserTask> userList = OBJECT_MAPPER.readValue(response.body(), new TypeReference<List<UserTask>>() {
        });
        return userList
                .stream()
                .filter(task -> !task.isCompleted())
                .toList();
    }
}
