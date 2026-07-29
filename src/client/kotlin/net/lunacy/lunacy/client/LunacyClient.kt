package net.lunacy.lunacy.client

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import net.fabricmc.api.ClientModInitializer

class LunacyClient : ClientModInitializer {

    override fun onInitializeClient() {
        LOGGER.info("Initializing Lunacy Client")
    }

    companion object {
        // static final логгер
        val LOGGER: Logger = LoggerFactory.getLogger("lunacy")
    }
}
