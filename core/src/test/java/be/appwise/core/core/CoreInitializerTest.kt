package be.appwise.core.core

import android.content.Context
import com.orhanobut.hawk.Hawk
import com.orhanobut.hawk.HawkBuilder
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert
import org.junit.Test

class CoreInitializerTest {

    @Test
    fun `create should set context in Core`() {
        val context = mockk<Context>()
        mockkStatic(Hawk::class)
        val hawkBuilder = mockk<HawkBuilder>()

        every { Hawk.init(context) } returns hawkBuilder
        every { hawkBuilder.build() } returns Unit

        CoreInitializer().create(context)

        Assert.assertEquals(context, CoreApp.appContext)
    }

    @Test
    fun `dependencies should be empty`() {
        assert(CoreInitializer().dependencies().isEmpty())
    }
}