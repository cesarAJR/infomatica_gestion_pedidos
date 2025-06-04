package com.cesar.infomatica_gestion_pedidos.util


import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cesar.data.datasource.local.OrderLocalDataSourceImp
import com.cesar.data.datasource.remote.api.OrderRemoteDataSource
import com.cesar.data.datasource.remote.api.OrderRemoteDataSourceImp
import com.cesar.data.repository.OrderRepositoryImp
import com.cesar.data.util.NetworkUtil
import com.cesar.domain.repository.OrderRepository
import com.cesar.domain.useCase.deletePending.DeletePendingUseCaseImp
import com.cesar.domain.useCase.listPending.ListPendingOrderUseCaseImp
import com.cesar.domain.useCase.sendPending.SendPendingUseCaseImp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.CoroutineContext

class NetworkChangeWorker(context: Context,
                          workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams), KoinComponent {
    private val orderRepository: OrderRepository by inject()

    override suspend fun doWork(): Result {
        Log.d("infoWor","doWork")
        if (NetworkUtil.isConnected(applicationContext)){
            Log.d("infoWor","online-----")
            println("infoweeeeeeeeeee")
            val listPendingOrderUseCase = ListPendingOrderUseCaseImp(orderRepository)
            val sendPendingUseCase = SendPendingUseCaseImp(orderRepository)
            val deletePendingUseCase = DeletePendingUseCaseImp(orderRepository)
            coroutineScope {
                launch {
                    listPendingOrderUseCase.invoke().collect({it->
                        if (it.data?.isNotEmpty()==true){
                            var i=0
                            it.data?.forEach {
                                    order->
                                sendPendingUseCase.invoke(order).collect {
                                    if (it.data==true){
                                        deletePendingUseCase.invoke(order).collect {
                                            i++
                                        }
                                    }
                                }
                            }

                            if (i == it.data?.size){
                                Notification.createNotification(applicationContext)
                            }
                        }
                    })
                }
            }

        }
        return Result.retry()
    }

}