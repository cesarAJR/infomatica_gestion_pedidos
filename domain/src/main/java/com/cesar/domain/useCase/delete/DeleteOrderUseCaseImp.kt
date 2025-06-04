package com.cesar.domain.useCase.delete

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import com.cesar.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class DeleteOrderUseCaseImp(private val repository: OrderRepository) : DeleteOrderUseCase {
    override suspend fun invoke(order: Order): Flow<Result<Boolean>> {
        return  repository.deleteOrder(order)
    }
}