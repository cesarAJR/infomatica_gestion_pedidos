package com.cesar.domain.useCase.add

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import com.cesar.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class AddOrderUseCaseImp(val repository: OrderRepository): AddOrderUseCase {
    override suspend fun invoke(order: Order): Flow<Result<Boolean>> {
        return  repository.addOrder(order)
    }
}