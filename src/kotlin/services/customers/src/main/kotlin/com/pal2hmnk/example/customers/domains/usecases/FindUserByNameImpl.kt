package com.pal2hmnk.example.customers.domains.usecases

import com.pal2hmnk.example.customers.domains.entities.User
import com.pal2hmnk.example.customers.domains.entities.UserRepository
import com.pal2hmnk.example.customers.domains.values.Name
import com.pal2hmnk.example.shared.exceptions.DomainException
import org.springframework.stereotype.Service

@Service
class FindUserByNameImpl(
    private val repo: UserRepository,
): FindUserByName {
    override fun exec(name: String): User =
        repo.findBy(Name(name)) ?: throw DomainException()
}
