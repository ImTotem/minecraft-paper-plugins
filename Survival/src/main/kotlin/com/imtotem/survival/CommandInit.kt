package com.imtotem.survival

import com.imtotem.survival.command.CommandWorld.Companion.register
import io.github.monun.kommand.kommand
import org.bukkit.plugin.Plugin

class CommandInit {
    companion object {
        @JvmStatic
        fun initCommands(plugin: Plugin) {
            plugin.kommand {
                register(plugin)
            }
        }
    }
}