package beansplusplus

import beansplusplus.beansgameplugin.Game
import beansplusplus.beansgameplugin.GameConfiguration
import beansplusplus.beansgameplugin.GameState
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

class ApocalypseSurvivalGame(
  private val plugin: ApocalypseSurvivalPlugin,
  config: GameConfiguration,
  private val state: GameState
) : Game, Listener {
  private val value: Int = config.getValue("some_number")

  override fun start() {
    for (player in Bukkit.getOnlinePlayers()) {
      player.health = 20.0
      player.level = 0
      player.foodLevel = 20
      player.inventory.clear()
      player.gameMode = GameMode.SURVIVAL
    }

    var world = Bukkit.getWorld("world")!!
    world.time = 1000

    plugin.server.pluginManager.registerEvents(this, plugin)
    plugin.server.scheduler.scheduleSyncRepeatingTask(plugin, ::timer, 20, 20)
  }

  override fun cleanUp() {
    HandlerList.unregisterAll(this)
    plugin.server.scheduler.cancelTasks(plugin)
  }

  fun timer() {
    if (state.isPaused) return

    for (player in Bukkit.getOnlinePlayers()) {
      player.sendMessage("${ChatColor.YELLOW}Here is a value: $value")
    }
  }
}