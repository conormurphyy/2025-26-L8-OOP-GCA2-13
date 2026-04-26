package org.example.shared;

import com.fasterxml.jackson.databind.JsonNode;

public class ClientRequest {

    private String   _type;
    private JsonNode _payload;
    private String _requestType;

    public ClientRequest() {
        _type    = "";
        _payload = null;
    }

    public ClientRequest(String type, JsonNode payload) {
       _type = type != null ? type : "";
       _payload = null;
    }

    //Public API

    public String getType() { return _type; }

    public void setType(String type) { _type = type != null ? type : ""; }

    public JsonNode getPayload() { return (JsonNode) _payload; }

    public JsonNode setPayload(JsonNode payload) { return _payload = payload; }

    public int getInt(String key) {
        Object v = _payload.get(key);
        if (v == null) return -1;
        try {
            return Integer.parseInt(v.toString());
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public double getDouble(String key) {
        Object v = _payload.get(key);
        if (v == null) return -1;
        try {
            return Double.parseDouble(v.toString());
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }

    public boolean getBoolean(String key) {
        Object v = _payload.get(key);
        if (v == null) return false;
        return Boolean.parseBoolean(v.toString());
    }

    public void setRequestType(String requestType)
    {
        _requestType = requestType;
    }

    public String getString(String username) {
        return _payload.get(username).asText();
    }
}


