package org.example.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import org.example.domain.FileUploadPayload;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {
    private final String _host;
    private final int _port;
    private final ObjectMapper _mapper;

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
    }

    public void run() throws IOException {
        System.out.println("Connecting to server at " + _host + ":" + _port);

        try  (Socket socket = new Socket(_host, _port);
              BufferedReader in = new BufferedReader(
                      new InputStreamReader(socket.getInputStream()));
              PrintWriter out = new PrintWriter(
                      socket.getOutputStream(), true)) {

            System.out.println("Connected\n");
            //TODO Add disconntect functionatliy
            System.out.println("Disconnected\n");
        }
    }

    public FileUploadPayload buildUploadPayload(Path filePath, int entityId) throws IOException {
        byte[] bytes = Files.readAllBytes(filePath);
        String b64 = Base64.getEncoder().encodeToString(bytes);
        String name = filePath.getFileName().toString();

        String detectedMime = Files.probeContentType(filePath);
        String mime = (detectedMime != null) ? detectedMime : "application/octet-stream";

        return new FileUploadPayload(entityId, name, mime, bytes.length, b64);
    }

}
