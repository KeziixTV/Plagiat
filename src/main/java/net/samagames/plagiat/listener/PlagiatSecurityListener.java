package net.samagames.plagiat.listener;

import net.samagames.plagiat.Plagiat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Plagiat main Listener
 */
public class PlagiatSecurityListener implements Listener
{
    private Plagiat plugin;

    /**
     * Plagiat listener constructor
     *
     * @param plugin Plagiat plugin instance
     */
    public PlagiatSecurityListener(Plagiat plugin)
    {
        this.plugin = plugin;
    }

    /**
     * Cancel damages before game start
     *
     * @param event Bukkit event instance
     */
    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageEvent event)
    {
        if (!this.plugin.getGame().areDamagesActivated())
            event.setCancelled(true);
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID && !this.plugin.getGame().isGameStarted())
            event.getEntity().teleport(this.plugin.getGame().getLobby());
    }

    /**
     * Cancel build before game start
     *
     * @param event Bukkit event instance
     */
    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event)
    {
        if (!this.plugin.getGame().isBuildActivated())
            event.setCancelled(true);
    }

    /**
     * Cancel build before game start
     *
     * @param event Bukkit event instance
     */
    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockPlaceEvent event)
    {
        if (!this.plugin.getGame().isBuildActivated())
            event.setCancelled(true);
    }

    /**
     * Cancel rain and other weather changes
     *
     * @param event Bukkit event instance
     */
    @EventHandler(ignoreCancelled = true)
    public void onWeatherChange(WeatherChangeEvent event)
    {
        if (event.toWeatherState())
            event.setCancelled(true);
    }

    /**
     * Cancel food loss if game not started
     *
     * @param event Bukkit event instance
     */
    @EventHandler(ignoreCancelled = true)
    public void onFoodLevelChange(FoodLevelChangeEvent event)
    {
        if (!this.plugin.getGame().areDamagesActivated())
        {
            event.setCancelled(true);
            if (event.getEntity() instanceof Player)
                ((Player)event.getEntity()).setFoodLevel(20);
        }
    }
}
