package org.example.shared;

import com.fasterxml.jackson.databind.JsonNode;

public class ClientRequest {

    private String   type;
    private JsonNode payload;

    public ClientRequest() {
        type    = "";
        payload = null;
    }

    public ClientRequest(String type, JsonNode payload) {
        this.type    = type;
        this.payload = payload;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public JsonNode getPayload() { return payload; }

    public void setPayload(JsonNode payload) { this.payload = payload; }
}
