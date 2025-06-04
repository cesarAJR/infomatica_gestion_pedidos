package com.cesar.domain.useCase.sendPending

import com.cesar.domain.core.Result
import com.cesar.domain.model.Order
import kotlinx.coroutines.flow.Flow

interface SendPendingUseCase {
    suspend operator fun invoke(order: Order): Flow<Result<Boolean>>
}