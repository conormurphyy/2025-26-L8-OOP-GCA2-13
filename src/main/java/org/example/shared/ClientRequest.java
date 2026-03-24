package org.example.shared;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

public class ClientRequest {

    private String   _type;
    private Map<String, Object> _payload;

    public ClientRequest() {
        _type    = "";
        _payload = new HashMap<>();
    }

    public ClientRequest(String type, Map<String, Object> payload) {
       _type = type != null ? type : "";
       _payload = payload != null ? payload : new HashMap<>();
    }

    public String getType() { return _type; }

    public void setType(String type) { _type = type != null ? type : ""; }

    public Map<String, Object> getPayload() { return _payload; }

    public void setPayload(Map<String, Object> payload) { _payload = payload != null ? payload : new HashMap<>(); }


    public int getInt(String id) {
        return 0;
    }
}
