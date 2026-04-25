package org.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
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
            while(true){
            socket.close();
            }
        }
    }
    private void viewAllRecipes(PrintWriter out, BufferedReader in) throws Exception
    {
        System.out.println("Fetching all recipes");

    }
    private void uploadRecipeImage(PrintWriter out, BufferedReader in) throws Exception {
        System.out.println("Upload Recipe Image");

    }






}
