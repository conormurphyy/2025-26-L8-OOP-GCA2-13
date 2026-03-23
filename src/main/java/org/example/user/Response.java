package org.example.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.User;

import java.util.List;


public class Response <T> {

    private static final ObjectMapper MAPPER = new ObjectMapper();


    private String fStatus;
    private String fMessage;
    private T fData;



    public Response() throws JsonProcessingException {
        fStatus = "";
        fMessage = "";
        fData = null;
    }

    public Response(String status,String message, T data) throws JsonProcessingException {
        fStatus = status;
        fMessage = message;
        fData = data;
    }

    public String getStatus()
    {
        return fStatus;
    }
    public void setStatus(String status)
    {
        fStatus = status;
    }
    public String getMessage()
    {
        return fMessage;
    }
    public void setMessage(String message)
    {
        fMessage = message;
    }
    public T getData()
    {
        return fData;
    }
    public void setData(T data)
    {
        fData = data;
    }


    public static <T> Response <T> success (String message, T data) throws JsonProcessingException {
        return new Response<>("SUCCESS",message,data);
    }

    public static <T> Response<T> failure(String message) throws JsonProcessingException {
        return new Response<>("Failure", message,null);
    }

    private Response<?> handleGetById(Request request) throws JsonProcessingException {
        JsonNode data = request.getData();

        if(data == null || !data.has("id"))
            return Response.failure("Missing Required Field:id");

        int id = data.get("id").asInt();
        return Response.success("Success", new User(id,"David","user",5));



    }
    private Response<?> handleGetByUsername(Request request) throws JsonProcessingException {
        JsonNode data = request.getData();
        if(data == null || !data.has("username"))
            return Response.failure("Missing Required Field:username");

        String username = data.get("username").asText();
        return Response.success("Success", new User(1,"David","user",5));
    }




    public static void main(String[] args) throws JsonProcessingException {
        User u = new User(19,"David","user",5);
        String json = MAPPER.writeValueAsString(u);
        System.out.println(json);

        System.out.println("Deserializing:");
        deserialize();

        System.out.println("Serializing list:");
        serializeList();

        System.out.println("Serializing response:");
        serializeResponse();



    }
    public static void deserialize() throws JsonProcessingException {
        String json = "{\"id\":123,\"username\":\"Alex\",\"userRating\":9.2,\"userType\":\"user\"}";
        User u = MAPPER.readValue(json, User.class);
        System.out.println(u.getUsername());
    }

    public static void serializeList() throws JsonProcessingException {
        List<User> users = List.of(new User(1,"David","user",5),new User(2,"Alex","user",4));
        String json = MAPPER.writeValueAsString(users);
        System.out.println(json);



        List<User> users1 = MAPPER.readValue(
                json, new TypeReference<List<User>>() {}
        );

        String out = MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(users1);
        System.out.println(out);

    }

    public static void serializeResponse() throws JsonProcessingException {
        User user = new User(1,"David","user",5);
        Response<User> response = Response.success("Success", user);
        String json = MAPPER.writeValueAsString(response);
        System.out.println(json);


        System.out.println("Deserializing response:");
        Response<User> response1 = MAPPER.readValue(json,new TypeReference<Response<User>>(){});

        User u1 = response1.getData();
        System.out.println(u1.getUsername());

    }




}
