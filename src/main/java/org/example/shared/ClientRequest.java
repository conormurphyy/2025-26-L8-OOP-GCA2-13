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

    //Public API

    public String getType() { return _type; }

    public void setType(String type) { _type = type != null ? type : ""; }

    public Map<String, Object> getPayload() { return _payload; }

    public void setPayload(Map<String, Object> payload) { _payload = payload != null ? payload : new HashMap<>(); }

    public String getString(String key) {
        Object v = _payload.get(key);
        return v == null ? null : v.toString();

    }

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

}
