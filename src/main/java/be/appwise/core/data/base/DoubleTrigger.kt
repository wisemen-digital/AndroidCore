package be.appwise.core.data.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * {@link MediatorLiveData} subclass which may observe 2 other {@code LiveData} objects and react on
 * {@code OnChanged} events from them.
 *
 * Consider the following scenario: We have a usecase where we have to listen to 2 livedata instances e.g. (searchbar and to filter changes).
 * We can make 2 instances of {@code LiveData}, let's name them
 * {@code mSearchLive} and {@code mFilterLive}. When the value of either of these {@code LiveData} variables changes it will trigger the associated
 * Transformations.switchmap.
 *
 * <pre>
 * LiveData<String> mSearchLive = ...;
 * LiveData<Filter> mFilterLive = ...;
 *
 *
 * <pre>
 *     (search,filter) == pair<A,B> but it renames the fields
 * val objects = Transformations.map(DoubleTrigger(mSearchLive,mFilterLive)) { (search,filter)->
 *      objectDao.findAllQuery(search,filter)
 * }
 * </pre>
 *
 * @param <A> Type of first LiveData field to listen to
 * @param <B> Type of second LiveData field to listen to
 * @param a First LiveData field to listen to
 * @param b Second LiveData field to listen to
 */

class DoubleTrigger<A, B>(a: LiveData<A>, b: LiveData<B>) : MediatorLiveData<Pair<A?, B?>>() {
    init {
        addSource(a) { value = it to b.value }
        addSource(b) { value = a.value to it }
    }
}