package pl.applover.android.mvvmtest.util.architecture

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * This Event is intended to be used when several classes have to listen to single event.
 * Otherwise use [Event]
 */
open class SingleEvent<out T>(private val content: T) {

    private val classesThatHandledTheEvent = HashSet<String>(0)

    /**
     * Returns the content and prevents its use again from the given class.
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
}