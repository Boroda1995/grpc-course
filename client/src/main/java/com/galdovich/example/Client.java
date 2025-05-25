package com.galdovich.example;

import com.galdovich.pingpong.PingPongServiceGrpc;
import com.galdovich.pingpong.PingRequest;
import com.galdovich.pingpong.PongResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Client {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
            .usePlaintext()
            .build();

        PingPongServiceGrpc.PingPongServiceBlockingStub stub = PingPongServiceGrpc.newBlockingStub(channel);

        PingRequest request = PingRequest.newBuilder()
            .setMessage("Ping")
            .build();

        PongResponse response = stub.ping(request);
        System.out.println("Response: " + response.getMessage());

        channel.shutdown();
    }
}