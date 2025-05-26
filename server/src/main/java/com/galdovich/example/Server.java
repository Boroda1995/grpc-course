package com.galdovich.example;

import io.grpc.ServerBuilder;

import java.io.IOException;

public class Server {

    public static void main(String[] args) throws IOException, InterruptedException {
        io.grpc.Server server = ServerBuilder.forPort(8080)
            .addService(new PingPongServiceImpl())
            .build();

        server.start();
        System.out.println("Server started on port 8080");

        server.awaitTermination();
    }
}
