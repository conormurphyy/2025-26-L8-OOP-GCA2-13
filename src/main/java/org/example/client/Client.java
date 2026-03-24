package org.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;

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
            showMainMenu(out,in);

            send(out,in,"DISCONNECT", Map.of());
            System.out.println("Disconnected\n");
        }
    }

    private void showMainMenu(PrintWriter out, BufferedReader in) throws Exception{
        boolean running = true;
        while(running)
        {
            System.out.println("Main Menu");
            System.out.println("1. Display Recipes");
            System.out.println("2. Ingredients");
            System.out.println("3. Users");
            System.out.println("4. Exit");

            switch(promt("Choice")) {
                case "1" -> showRecipeMenu(out,in);
                case "2" -> showIngredientMenu(out,in);
                case "3" -> showUserMenu(out,in);
                case "4" -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void showRecipeMenu(PrintWriter out, BufferedReader in) throws Exception{
        boolean running = true;
        while(running)
        {
            System.out.println("Recipe Menu");
            System.out.println("1. Display Recipes");
            System.out.println("2. Ingredients");
            System.out.println("3. Users");
            System.out.println("4. Exit");


        }
    }

    private void showIngredientMenu(PrintWriter out, BufferedReader in) throws Exception{
        boolean running = true;
        while(running)
        {
            System.out.println("Ingredient Menu");
        }
    }

    private void showUserMenu(PrintWriter out, BufferedReader in) throws Exception{
        boolean running = true;
        while(running)
        {
            System.out.println("User Menu");
        }
    }





}
