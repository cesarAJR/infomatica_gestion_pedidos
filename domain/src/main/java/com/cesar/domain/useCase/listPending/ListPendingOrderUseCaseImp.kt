package com.cesar.domain.useCase.listPending

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import com.cesar.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class ListPendingOrderUseCaseImp(private val repository: OrderRepository) : ListPendingOderUseCase {
    override suspend fun invoke(): Flow<Result<List<Order>>> {
        return repository.getListPendingOrder()
    }
}