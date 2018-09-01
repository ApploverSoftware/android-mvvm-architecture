package pl.applover.architecture.mvvm.util.other

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

/**
 * Created by Janusz Hain on 2018-06-27.
 */

/**
 * @param subscribeOn if not set, then creates new Thread (if there is room for new Thread)
 * @param observeOn if not set, then uses MainThread
 */
data class SchedulerProvider(val subscribeOn: Scheduler
                             = Schedulers.from(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()))
                             , val observeOn: Scheduler = AndroidSchedulers.mainThread())