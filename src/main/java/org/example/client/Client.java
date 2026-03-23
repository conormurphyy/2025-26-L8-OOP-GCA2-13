package org.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Scanner;

public class Client {
    private final String _host;
    private final int _port;
    private final ObjectMapper _mapper;
    private final Scanner _scanner;

    public Client(String host, int port)
    {
        if(host == null || host.isBlank())
        {
            throw new IllegalArgumentException("Host must not be empty");
        }
        if(port <1_024 || port > 65_535)
        {
            throw new IllegalArgumentException("Port must be between 1024 and 65535");
        }
        _host = host;
        _port = port;
        _mapper = new ObjectMapper();
        _scanner = new Scanner(System.in);
    }
}
