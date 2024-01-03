package be.appwise.core.data.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * {@link MediatorLiveData} subclass which may observe 3 other {@code LiveData} objects and react on
 * {@code OnChanged} events from them.
 *
 * Consider the following scenario: We have a usecase where we have to listen to 3 livedata instances e.g. (searchbar ,loading state and to filter changes).
 * We can make 3 instances of {@code LiveData}, let's name them
 * {@code mSearchLive} and {@code mFilterLive}. When the value of either of these {@code LiveData} variables changes it will trigger the associated
 * Transformations.switchmap.
 *
 * <pre>
 * LiveData<String> mSearchLive = ...;
 * LiveData<Boolean> mLoading = ...;
 * LiveData<Filter> mFilterLive = ...;
 *
 * <pre>
 *     (search,filter,loading) == pair<A,B,C> but it renames the fields
 * val objects = Transformations.map(DoubleTrigger(mSearchLive,mFilterLive,mLoading)) { (search,filter)->
 *      objectDao.findAllQuery(search,filter)
 * }
 * </pre>
 *
 * @param <A> Type of first LiveData field to listen to and the type of the defaultValue for {@code a}
 * @param <B> Type of second LiveData field to listen to and the type of the defaultValue for {@code b}
 * @param <C> Type of second LiveData field to listen to and the type of the defaultValue for {@code c}
 * @param a First LiveData field to listen to
 * @param b Second LiveData field to listen to
 * @param c Third LiveData field to listen to
 * @param defaultA The default value for {@param a} when it's value is not set
 * @param defaultB The default value for {@param b} when it's value is not set
 * @param defaultC The default value for {@param c} when it's value is not set
 */
class TripleTrigger<A, B,C>(a: LiveData<A>, b: LiveData<B>,c: LiveData<C>, defaultA : A?, defaultB : B?, defaultC : C?) : MediatorLiveData<Triple<A?, B?, C?>>() {
        init {
            addSource(a) { value = Triple(it , b.value ?: defaultB , c.value ?: defaultC) }
            addSource(b) { value = Triple((a.value ?: defaultA) , it, c.value ?: defaultC) }
            addSource(c) { value = Triple(a.value ?: defaultA ,b.value ?: defaultB, it) }
        }
    }