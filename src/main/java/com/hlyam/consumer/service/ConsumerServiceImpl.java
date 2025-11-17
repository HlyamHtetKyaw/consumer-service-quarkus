package com.hlyam.consumer.service;

import com.hlyam.consumer.grpc.ConsumerServiceGrpc;
import com.hlyam.waiter.grpc.CreateOrderRequest;
import com.hlyam.waiter.grpc.OrderResponse;
import com.hlyam.waiter.grpc.WaiterServiceGrpc;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;

@GrpcService
public class ConsumerServiceImpl extends ConsumerServiceGrpc.ConsumerServiceImplBase {

    @GrpcClient("waiter")
    WaiterServiceGrpc.WaiterServiceBlockingStub waiterService;

    @Override
    public void createOrder(CreateOrderRequest request, 
                            StreamObserver<OrderResponse> responseObserver) {

        System.out.println("Consumer received order: " + request.getOrder() + 
                           ". Forwarding to Waiter.");

        OrderResponse response = waiterService.createOrder(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}