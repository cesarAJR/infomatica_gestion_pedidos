package com.cesar.domain.useCase.list

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface ListOderUseCase {
    suspend operator fun invoke(): Flow<Result<List<Order>>>
}