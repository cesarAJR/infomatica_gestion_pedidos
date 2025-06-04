package com.cesar.domain.useCase.deletePending

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface DeletePendingUseCase {
    suspend operator fun invoke(order:Order): Flow<Result<Boolean>>
}