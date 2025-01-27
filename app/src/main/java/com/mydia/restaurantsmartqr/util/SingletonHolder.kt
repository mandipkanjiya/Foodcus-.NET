package com.mydia.restaurantsmartqr.util

/*open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }

}*/
open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator

    @Volatile
    private var instance: T? = null

    protected fun getInstanceInternal(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) return checkInstance
        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) checkInstanceAgain
            else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}
/**
 * If you need to pass only ONE argument to the constructor of the singleton class.
 * Make companion object extended from [SingleArgSingletonHolder] for best match.
 * Ex:
class AppRepository private constructor(private val db: Database) {
companion object : SingleArgSingletonHolder<AppRepository, Database>(::AppRepository)
}

 * Uses:
val appRepository =  AppRepository.getInstance(db)
 */
open class SingleArgSingletonHolder<out T : Any, in A>(creator: (A) -> T) :
    SingletonHolder<T, A>(creator) {
    fun getInstance(arg: A): T = getInstanceInternal(arg)
}

/**
 * If you need to pass TWO arguments to the constructor of the singleton class.
 * Extended from [PairArgsSingletonHolder] for best match.
 * Ex:
class AppRepository private constructor(private val db: Database, private val apiService: ApiService) {
companion object : PairArgsSingletonHolder<AppRepository, Database, ApiService>(::AppRepository)
}
 *
 * Uses:
val appRepository =  AppRepository.getInstance(db, apiService)
 */
open class PairArgsSingletonHolder<out T : Any, in A, in B>(creator: (A, B) -> T) :
    SingletonHolder<T, Pair<A, B>>(creator = { (a, b) -> creator(a, b) }) {

    fun getInstance(arg1: A, arg2: B) = getInstanceInternal(Pair(arg1, arg2))
}


/**
 * If you need to pass THREE arguments to the constructor of the singleton class.
 * Extended from [TripleArgsSingletonHolder] for the best match.
 *
 * Ex:
class AppRepository private constructor(
private val db: Database,
private val apiService: ApiService,
private val storage : Storage
) {
companion object : TripleArgsSingletonHolder<AppRepository, Database, ApiService, Storage>(::AppRepository)
}
 *
 * Uses:
val appRepository =  AppRepository.getInstance(db, apiService, storage)
 */
open class TripleArgsSingletonHolder<out T : Any, in A, in B, in C>(creator: (A, B, C) -> T) :
    SingletonHolder<T, Triple<A, B, C>>(creator = { (a, b, c) -> creator(a, b, c) }) {

    fun getInstance(arg1: A, arg2: B, arg3: C) = getInstanceInternal(Triple(arg1, arg2, arg3))
}

