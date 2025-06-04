package com.cesar.domain.useCase.edit

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import com.cesar.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class EditOrderUseCaseImp(val repository: OrderRepository): EditOrderUseCase {
    override suspend fun invoke(order: Order): Flow<Result<Boolean>> {
        return  repository.editOrder(order)
    }
}