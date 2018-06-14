package pl.applover.android.mvvmtest.util.other

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors


/**
 * Created by Janusz Hain on 2017-07-26.
 */
object MyScheduler {
    private var scheduler: Scheduler? = null

    /**
     * <pre>
     * Creates scheduler with fixed numbers of threads
     * Numbers of threads = Runtime.getRuntime().availableProcessors()
    </pre> *
     */
    @Synchronized
    fun getScheduler(): Scheduler {
        if (scheduler == null) {
            scheduler = Schedulers.from(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
        }
        return scheduler!!
    }

    /**
     * <pre>
     * Gets scheduler working on main(UI) thread
    </pre> *
     */
    @Synchronized
    fun getMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}