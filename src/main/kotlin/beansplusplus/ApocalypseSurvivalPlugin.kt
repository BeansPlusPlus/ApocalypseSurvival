package beansplusplus

import beansplusplus.beansgameplugin.*
import com.google.common.collect.ImmutableList
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import java.io.InputStream

class ApocalypseSurvivalPlugin : JavaPlugin(), GameCreator {
  override fun onEnable() {
    var beansGamePlugin = server.pluginManager.getPlugin("BeansGamePlugin") as BeansGamePlugin
    beansGamePlugin.registerGame(this)
  }

  override fun createGame(config: GameConfiguration, state: GameState): Game {
    return ApocalypseSurvivalGame(this, config, state)
  }

  override fun isValidSetup(sender: CommandSender, config: GameConfiguration): Boolean {
    return true
  }

  override fun config(): InputStream {
    return getResource("config.yml")!!
  }

  override fun rulePages(): ImmutableList<String> {
    return ImmutableList.of("Test")
  }

  override fun name(): String {
    return "Apocalypse Survival"
  }

}