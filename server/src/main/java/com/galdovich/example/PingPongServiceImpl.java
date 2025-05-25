package com.galdovich.example;

import io.grpc.stub.StreamObserver;
import com.galdovich.pingpong.PingPongServiceGrpc;
import com.galdovich.pingpong.PingRequest;
import com.galdovich.pingpong.PongResponse;

public class PingPongServiceImpl extends PingPongServiceGrpc.PingPongServiceImplBase {

    @Override
    public void ping(PingRequest request, StreamObserver<PongResponse> responseObserver) {
        System.out.println("Received: " + request.getMessage());

        PongResponse response = PongResponse.newBuilder()
            .setMessage("Pong")
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
