package com.example.myapplication.eventbus

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

val UI : CoroutineDispatcher = Dispatchers.Main
object EventBus : CoroutineScope {
    private val TAG = "EventBus"
    private val job = SupervisorJob()
    private val hashMap = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()
    private val mStickyEventMap = ConcurrentHashMap<Class<*>, Any>()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default+ job

    @JvmStatic
     fun <T> register(contextName :String,
                   eventDispatcher: CoroutineDispatcher = UI,
                   eventClass: Class<T>,
                   eventCallback:(T)->Unit
    ){
       val eventDataMap = if(hashMap.containsKey(contextName)){
            hashMap[contextName]!!
        }else{
            val mutableMap = mutableMapOf<Class<*>, EventData<*>>()
            hashMap[contextName] = mutableMap
            mutableMap
        }
        eventDataMap[eventClass] = EventData(this,eventDispatcher = eventDispatcher,onEvent = eventCallback)
    }
    @JvmStatic
    fun <T> register(
        contextName: String,
        eventDispatcher: CoroutineDispatcher = UI,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit,
        eventFail:(Throwable)->Unit
    ) {
        val eventDataMap = if (hashMap.containsKey(contextName)) {
            hashMap[contextName]!!
        } else {
            val eventDataMap = mutableMapOf<Class<*>, EventData<*>>()
            hashMap[contextName] = eventDataMap
            eventDataMap
        }

        eventDataMap[eventClass] = EventData(this, eventDispatcher, eventCallback, eventFail)

    }
    @JvmStatic
    fun <T> registerSticky(
        contextName: String,
        eventDispatcher: CoroutineDispatcher = UI,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit
    ) {
        val eventDataMap = if (hashMap.containsKey(contextName)) {
            hashMap[contextName]!!
        } else {
            val eventDataMap = mutableMapOf<Class<*>, EventData<*>>()
            hashMap[contextName] = eventDataMap
            eventDataMap
        }

        eventDataMap[eventClass] = EventData(this, eventDispatcher, eventCallback)

        val event = mStickyEventMap[eventClass]
        event?.let {

            postEvent(it)
        }
    }



    @JvmStatic
    fun <T> registerSticky(
        contextName: String,
        eventDispatcher: CoroutineDispatcher = UI,
        eventClass: Class<T>,
        eventCallback: (T) -> Unit,
        eventFail:(Throwable)->Unit
    ) {
        val eventDataMap = if (hashMap.containsKey(contextName)) {
            hashMap[contextName]!!
        } else {
            val eventDataMap = mutableMapOf<Class<*>, EventData<*>>()
            hashMap[contextName] = eventDataMap
            eventDataMap
        }

        eventDataMap[eventClass] = EventData(this, eventDispatcher, eventCallback, eventFail)

        val event = mStickyEventMap[eventClass]
        event?.let {

            postEvent(it)
        }
    }
    fun unregister(contextName: String) {
        val cloneContexMap = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()
        cloneContexMap.putAll(hashMap)
        val map = cloneContexMap.filter { it.key == contextName }
        for ((string,mutableMap) in map){
            mutableMap.values.forEach{
                it.cancel()
            }
        }
        hashMap.remove(contextName)
    }
    private fun postEvent(event: Any){
        val cloneContexMap = ConcurrentHashMap<String, MutableMap<Class<*>, EventData<*>>>()
        cloneContexMap.putAll(hashMap)
        for ((_,mutableMap) in cloneContexMap){
            mutableMap.keys.firstOrNull { it==event.javaClass }?.let {
                mutableMap[it]?.postEvent(event)
                Log.println(Log.ERROR,"EventBus",event.javaClass.name)
            }
        }
    }

    fun post(event: Any) {
        postEvent(event)
    }

    fun postSticky(event: Any) {
        mStickyEventMap[event.javaClass] = event
    }

    fun <T> removeStickyEvent(java: Class<T>) {
        mStickyEventMap.remove(java)
    }


}