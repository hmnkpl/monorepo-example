package com.pal2hmnk.example.gateway.usecaes

import com.pal2hmnk.example.shared.usecases.Scenario

interface FindOrderHistoriesByUserName: Scenario<OrderHistoryOutputData, String>