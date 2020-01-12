package com.arun.statsapp.events

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

object RxEventBus {

    private val bus = PublishSubject.create<Any>()

    fun publish(event: Any) {
        bus.onNext(event)
    }

    fun <T> listenTo(clazz: Class<T>): Observable<T> {
        return bus.ofType(clazz)
    }

}