package com.cesar.domain.useCase.edit

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface EditOrderUseCase {
    suspend operator fun invoke(order: Order): Flow<Result<Boolean>>
}