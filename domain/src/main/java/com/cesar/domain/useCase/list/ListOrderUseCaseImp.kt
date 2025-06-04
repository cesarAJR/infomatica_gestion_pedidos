package com.cesar.domain.useCase.list

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import com.cesar.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class ListOrderUseCaseImp(private val repository: OrderRepository) : ListOderUseCase {
    override suspend fun invoke(): Flow<Result<List<Order>>> {
        return repository.getListOrder()
    }
}