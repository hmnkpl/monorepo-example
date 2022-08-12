package com.pal2hmnk.example.gateway.infrastructures.grpc.clients

import com.pal2hmnk.example.gateway.configs.GrpcClientConfig
import com.pal2hmnk.example.gateway.domains.UserId
import com.pal2hmnk.example.gateway.infrastructures.grpc.GrpcChannelFactory
import com.pal2hmnk.example.gateway.infrastructures.grpc.GrpcClient
import com.pal2hmnk.example.generated.grpc.services.OrderHistoryList
import com.pal2hmnk.example.generated.grpc.services.ShopServiceGrpcKt
import org.springframework.stereotype.Component
import com.pal2hmnk.example.generated.grpc.services.UserId as GrpcUserId

@Component
class ShopGrpcClient(
    config: GrpcClientConfig,
) : GrpcClient {
    final override val channel = GrpcChannelFactory.createChannel(
        config.shop.addr,
        config.shop.port,
    )
    private val stub = ShopServiceGrpcKt.ShopServiceCoroutineStub(channel)
    suspend fun findBy(id: UserId): OrderHistoryList {
        val request = GrpcUserId.newBuilder().setId(id.value).build()
        return stub.findOrderHistory(request)
    }
}