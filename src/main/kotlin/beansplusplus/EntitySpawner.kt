package beansplusplus

import org.bukkit.Location
import org.bukkit.entity.EntityType
import java.util.*

class EntitySpawner private constructor(private val spawners: List<SingleEntitySpawner>) {
  companion object {
    fun fromYaml(fileName : String) : EntitySpawner? {
      return null
    }

    private class SingleEntitySpawner(val type: EntityType, private val spawnRates: SortedMap<Double, Double>) {
      fun probabilityForLevel(level: Double): Double {
        return spawnRates[level] ?: interpolatedRate(level)
      }

      private fun interpolatedRate(level: Double): Double {
        val submap = spawnRates.subMap(spawnRates.firstKey(), level)
        val submap2 = spawnRates.subMap(level, spawnRates.lastKey())

        if (submap.isEmpty()) return 0.0
        if (submap2.isEmpty()) return submap[submap.lastKey()]!!

        val prev = submap.lastKey()
        val next = submap2.firstKey()
        val d = next - prev
        val t = (level - prev) / d

        return (1.0 - t) * submap[prev]!! + t * submap2[next]!!
      }
    }
  }

  private val random = Random()

  fun spawn(level: Double, location: Location) {
    for (spawner in spawners) {
      if (random.nextDouble() > spawner.probabilityForLevel(level)) continue
    }
  }
}


//