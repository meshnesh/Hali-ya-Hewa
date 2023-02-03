package com.munene.haliyahewa.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

/**
 * A generic class that can provide a resource backed by both the room database and the network.
 *
 * We can read more about it in the [Architecture Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
 **/
object NetworkBoundResource {
    inline fun <ResultType, RequestType> networkBoundResource(
        crossinline query: () -> Flow<ResultType>,
        crossinline fetch: suspend () -> RequestType,
        crossinline saveFetchResponse: suspend (RequestType) -> Unit,
        crossinline shouldFetch: (ResultType) -> Boolean = { true },
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) = flow {

        val data = query().first()
        val flow = if (shouldFetch(data)) {
            emit(LiveResource.loading(data))
            try {
                saveFetchResponse(fetch())
                query().map { LiveResource.success(it) }
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                query().map { LiveResource.error(it, throwable.message.toString()) }
            }
        } else {
            query().map { LiveResource.success(it) }
        }

        emitAll(flow)

    }.flowOn(coroutineDispatcher)
}
