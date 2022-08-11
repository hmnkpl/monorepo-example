package com.pal2hmnk.example.user

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.pal2hmnk.example.domain.user.entities.UserRepository
import com.pal2hmnk.example.shared.infrastructures.SqlSessionProvider
import com.pal2hmnk.example.shared.presentations.GrpcServerBase
import com.pal2hmnk.example.user.infrastructures.UserRepositoryImpl
import com.pal2hmnk.example.user.infrastructures.persistence.UserSqlSessionProvider
import com.pal2hmnk.example.user.presentations.UserController
import com.pal2hmnk.example.user.usecases.FindByName
import com.pal2hmnk.example.user.usecases.FindByNameImpl
import io.grpc.BindableService

fun main() {
    val kodein = Kodein {
        bind<SqlSessionProvider>() with singleton { UserSqlSessionProvider() }
        bind<UserRepository>() with singleton { UserRepositoryImpl(instance()) }
        bind<FindByName>() with singleton { FindByNameImpl(instance()) }
        bind<List<BindableService>>() with singleton { listOf(UserController(instance())) }
    }
    GrpcServerBase(kodein.instance()).runServer()
}
