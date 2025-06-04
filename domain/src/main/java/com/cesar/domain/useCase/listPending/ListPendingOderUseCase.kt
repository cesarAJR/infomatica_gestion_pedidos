package com.cesar.domain.useCase.listPending

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface ListPendingOderUseCase {
    suspend operator fun invoke(): Flow<Result<List<Order>>>
}