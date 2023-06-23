package com.imtotem.survival.command

import com.imtotem.survival.command.CommandWorldCreate.create
import com.imtotem.survival.command.CommandWorldInfo.info
import io.github.monun.kommand.*
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.World.Environment
import org.bukkit.plugin.Plugin

class CommandWorld {
    companion object {
        fun PluginKommand.register(plugin: Plugin) {
            register(plugin, this)
        }

        private fun register(plugin: Plugin, kommand: PluginKommand) {
            kommand.register("world") {
                then("info") {
                    executes {
                        info(player)
                    }

                    val word = KommandArgument.string(StringType.SINGLE_WORD)
                    word.suggests {
                        suggest(Bukkit.getServer().worlds.map { world -> world.name }) {
                            text(it)
                        }
                    }

                    then("name" to word) {
                        executes {
                            val name: String by it
                            info(sender, name)
                        }
                    }
                }

                then("create") {
                    then("name" to string(StringType.SINGLE_WORD), "environment" to environment, "type" to type, "seed" to long()) {
                        environment.suggests {
                            suggest()
                        }
                        executes {
                            val name: String by it
                            val environment: String by it
                            val type: String by it
                            val seed: Long by it

                            create(name, type, environment, seed)
                        }
                    }
                }

                then("modify") {
                    executes {
                        sender.sendMessage("This is Modify")
                    }
                }

                then("delete") {
                    executes {
                        sender.sendMessage("This is Delete")
                    }
                }
            }
        }
    }

}