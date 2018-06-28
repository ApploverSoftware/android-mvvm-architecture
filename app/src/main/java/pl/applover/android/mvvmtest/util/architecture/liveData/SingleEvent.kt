package pl.applover.android.mvvmtest.util.architecture.liveData

import java.lang.ref.WeakReference
import java.util.*

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * This Event is intended to be used when several classes have to listen to single event.
 */
open class SingleEvent<out T>(private val content: T) {

    private val classesThatHandledTheEvent = HashSet<String>(0)
    private val classesThatHandledTheEventWeakRef = HashSet<WeakReference<Class<Any>>>()


    /**
     * Returns the content and prevents its use again from the given class.
     * CanonicalName of class is retrieved and stored in hashset to check if given class have used event already
     */
    fun getContentIfNotHandled(classThatWantToUseEvent: Any): T? {
        val canonicalName = classThatWantToUseEvent::javaClass.get().canonicalName

        canonicalName?.let {
            return if (!classesThatHandledTheEvent.contains(canonicalName)) {
                classesThatHandledTheEvent.add(canonicalName)
                content
            } else {
                null
            }
        } ?: return null
    }

    fun hasBeenHandled(classThatMaybeUsedEvent: Any): Boolean {
        val canonicalName = classThatMaybeUsedEvent::javaClass.get().canonicalName
        return classesThatHandledTheEvent.contains(canonicalName)
    }

    /**
     * Returns the content and prevents its use again with the given tag.
     * Tag is stored in hashset to check if given tag have used event already
     */
    fun getContentIfNotHandled(tag: String): T? {
        return if (!classesThatHandledTheEvent.contains(tag)) {
            classesThatHandledTheEvent.add(tag)
            content
        } else {
            null
        }
    }

    fun hasBeenHandled(tag: String): Boolean {
        return classesThatHandledTheEvent.contains(tag)
    }

    /**
     * Returns the content and prevents its use again from the given class.
     * Class is stored as WeakRef in hashset
     */
    fun getContentIfNotHandledWeakRef(classThatWantToUseEvent: Any): T? {
        val classWantingToUseEvent = classThatWantToUseEvent::javaClass.get()

        classWantingToUseEvent?.let {
            return if (!classesThatHandledTheEventWeakRef.contains(WeakReference(it))) {
                classesThatHandledTheEventWeakRef.add(WeakReference(it))
                content
            } else {
                null
            }
        } ?: return null
    }

    fun hasBeenHandledWeakRef(classThatMaybeUsedEvent: Any): Boolean {
        val classWantingToUseEvent = classThatMaybeUsedEvent::javaClass.get()
        return classesThatHandledTheEventWeakRef.contains(WeakReference(classWantingToUseEvent))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SingleEvent<*>

        if (content != other.content) return false

        return true
    }

    override fun hashCode(): Int {
        return content?.hashCode() ?: 0
    }


}