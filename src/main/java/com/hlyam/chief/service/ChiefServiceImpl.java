package com.hlyam.chief.service;

import com.hlyam.chief.grpc.ChiefServiceGrpc;
import com.hlyam.chief.grpc.CookOrderRequest;
import com.hlyam.chief.grpc.CookResponse;
import com.hlyam.waiter.grpc.CreateOrderRequest;
import com.hlyam.waiter.grpc.OrderResponse;
import com.hlyam.waiter.grpc.MutinyWaiterServiceGrpc;

import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcClient;
import io.quarkus.grpc.GrpcService;

@GrpcService
public class ChiefServiceImpl extends ChiefServiceGrpc.ChiefServiceImplBase {

    @GrpcClient("waiter")
    MutinyWaiterServiceGrpc.MutinyWaiterServiceStub waiterService;

    @Override
    public void cookOrder(CookOrderRequest request, StreamObserver<CookResponse> responseObserver) {
        System.out.println("Chief received order: " + request.getOrder() + ". Chief cooking and forwarding to Waiter.");

        CreateOrderRequest cookedRequest = CreateOrderRequest.newBuilder()
                .setOrder(request.getOrder())
                .build();

        waiterService.createOrder(cookedRequest)
            .subscribe().with(
                (OrderResponse waiterResp) -> {
                    CookResponse response = CookResponse.newBuilder()
                        .setMessage("Chief cooked and forwarded to waiter: " + waiterResp.getMessage())
                        .build();
                    responseObserver.onNext(response);
                    responseObserver.onCompleted();
                },
                (Throwable t) -> {
                    responseObserver.onError(t);
                }
            );
    }
}
