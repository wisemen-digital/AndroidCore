package be.appwise.core.core

import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CoreBuilderTest {

    @Test
    fun `CoreApp should give back object with default values`() {
        val coreBuilder = mockk<CoreBuilder>()

        CoreApp.init { }

        verify { coreBuilder.initializeLogger(any(), any()) }
    }
}