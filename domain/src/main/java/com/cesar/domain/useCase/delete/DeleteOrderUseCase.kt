package com.cesar.domain.useCase.delete

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface DeleteOrderUseCase {
    suspend operator fun invoke(order:Order): Flow<Result<Boolean>>
}