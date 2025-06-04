package com.cesar.domain.useCase.add

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface AddOrderUseCase {
    suspend operator fun invoke(order: Order): Flow<Result<Boolean>>
}