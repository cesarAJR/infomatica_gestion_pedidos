package com.cesar.domain.di

import com.cesar.domain.useCase.add.AddOrderUseCase
import com.cesar.domain.useCase.add.AddOrderUseCaseImp
import com.cesar.domain.useCase.delete.DeleteOrderUseCase
import com.cesar.domain.useCase.delete.DeleteOrderUseCaseImp
import com.cesar.domain.useCase.deletePending.DeletePendingUseCase
import com.cesar.domain.useCase.deletePending.DeletePendingUseCaseImp
import com.cesar.domain.useCase.edit.EditOrderUseCase
import com.cesar.domain.useCase.edit.EditOrderUseCaseImp
import com.cesar.domain.useCase.list.ListOderUseCase
import com.cesar.domain.useCase.list.ListOrderUseCaseImp
import com.cesar.domain.useCase.listPending.ListPendingOderUseCase
import com.cesar.domain.useCase.listPending.ListPendingOrderUseCaseImp
import com.cesar.domain.useCase.sendPending.SendPendingUseCase
import com.cesar.domain.useCase.sendPending.SendPendingUseCaseImp
import org.koin.dsl.module

val domainModule = module {
    factory <ListOderUseCase>{ ListOrderUseCaseImp(get())}
    factory <DeleteOrderUseCase>{ DeleteOrderUseCaseImp(get()) }
    factory <AddOrderUseCase>{ AddOrderUseCaseImp(get()) }
    factory <EditOrderUseCase>{ EditOrderUseCaseImp(get()) }
    factory <ListPendingOderUseCase>{ ListPendingOrderUseCaseImp(get()) }
    factory <SendPendingUseCase>{ SendPendingUseCaseImp(get()) }
    factory <DeletePendingUseCase>{ DeletePendingUseCaseImp(get()) }
}