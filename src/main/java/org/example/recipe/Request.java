package org.example.recipe;

import com.fasterxml.jackson.databind.JsonNode;

public class Request {

    private String   type;
    private JsonNode payload;

    public Request() {
        type    = "";
        payload = null;
    }

    public Request(String type, JsonNode payload) {
        this.type    = type;
        this.payload = payload;
    }

    public String getType() { return type; }

    public void setType(String type) { type = type; }

    public JsonNode getPayload() { return payload; }

    public void setPayload(JsonNode payload) { payload = payload; }
}
