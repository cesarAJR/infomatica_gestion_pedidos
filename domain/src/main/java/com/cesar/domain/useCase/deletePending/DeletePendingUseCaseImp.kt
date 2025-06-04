package com.cesar.domain.useCase.deletePending

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import com.cesar.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class DeletePendingUseCaseImp(private val repository: OrderRepository) : DeletePendingUseCase{
    override suspend fun invoke(order: Order): Flow<Result<Boolean>> {
        return  repository.deleteOrderPending(order)
    }
}