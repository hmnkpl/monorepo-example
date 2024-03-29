package com.pal2hmnk.example.customers.presentations

import com.pal2hmnk.example.customers.domains.usecases.Authenticate
import com.pal2hmnk.example.customers.domains.values.Email
import com.pal2hmnk.example.customers.domains.entities.PasswordRow
import com.pal2hmnk.example.generated.grpc.services.AuthServiceGrpcKt
import com.pal2hmnk.example.generated.grpc.services.TokenResult
import com.pal2hmnk.example.generated.grpc.services.UserAuthInfo
import com.pal2hmnk.example.shared.presentations.UseCaseRunner
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class AuthGRpcService(
    scenario: Authenticate,
) : AuthServiceGrpcKt.AuthServiceCoroutineImplBase() {

    private val useCaseRunner = UseCaseRunner(
        useCase = scenario::exec,
        converter = {
            TokenResult.newBuilder()
                .setRes(TokenResult.Result.CREARTED)
                .setAccessToken(it.accessToken.value)
                .setRefreshToken(it.refreshToken.value)
                .build()
        },
        exceptionHandler = { TokenResult.getDefaultInstance() }
    )

    override suspend fun authenticate(request: UserAuthInfo): TokenResult =
        useCaseRunner
            .initial { Email(request.email) to PasswordRow(request.password) }
            .run()
}
