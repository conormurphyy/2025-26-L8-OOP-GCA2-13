package org.example.user;

import com.fasterxml.jackson.databind.JsonNode;

public class Request {
    private String fType;
    private JsonNode fData;

    public Request(String type, JsonNode data)
    {
        fType=type;
        fData=data;
    }
    public Request()
    {
        fType="";
        fData=null;
    }

    public String getType()
    {
        return fType;
    }
    public JsonNode getData()
    {
        return fData;
    }
    public void setData(JsonNode data)
    {
        fData=data;
    }
    public void setType(String type)
    {
        fType=type;
    }
}
