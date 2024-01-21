package be.appwise.core.core

import com.orhanobut.logger.Logger
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Test

class CoreBuilderTest {

    @Test
    fun `CoreApp should give back object with default values`() {
        mockkStatic(Logger::class)

        CoreApp.init {
            initializeLogger("", true)
        }

        verify { Logger.addLogAdapter(any()) }
    }
}