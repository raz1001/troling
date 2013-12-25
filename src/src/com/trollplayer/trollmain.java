package com.trollplayer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

@SuppressWarnings("deprecation")
public class trollmain extends JavaPlugin implements Listener{
	private static boolean[] PlayersDoor = new  boolean[9999999];
	private static boolean[] PlayersLags = new  boolean[9999999];
	String FillInvetoryText;
	int FillInvetoryID;
	int TNTPowerful;
	boolean TNTProtectionPlayer;
	boolean FallingAnvillProtectionPlayer;
	private static Plugin plugin;
	private static Server server;
	private static Logger logger;
	@Override
	public void onEnable(){
		plugin = this;
		server = getServer();
		logger = getLogger();
		logger.info(getName() +" "+ getDescription().getVersion() +" Players Enable!");
		server.getPluginManager().registerEvents(this, plugin);
		if(!getConfig().contains("FillInvetory.NameItem")){
			getConfig().set("FillInvetory.NameItem", "Loser!!!");
			saveConfig();
		}
		if(!getConfig().contains("FillInvetory.ItemId")){
			getConfig().set("FillInvetory.ItemId", 32);
			saveConfig();
		}
		if(!getConfig().contains("TNT-explosion.Powerful")){
			getConfig().set("TNT-explosion.Powerful", 10);
			saveConfig();
		}
		if(!getConfig().contains("TNT-explosion.ProtectionPlayer")){
			getConfig().set("TNT-explosion.ProtectionPlayer", true);
			saveConfig();
		}
		if(!getConfig().contains("FallingAnvill.ProtectionPlayer")){
			getConfig().set("FallingAnvill.ProtectionPlayer", true);
			saveConfig();
		}
		FillInvetoryText = getConfig().getString("FillInvetory.NameItem");
		FillInvetoryID = getConfig().getInt("FillInvetory.ItemId");
		TNTPowerful = getConfig().getInt("TNT-explosion.Powerful");
		TNTProtectionPlayer = getConfig().getBoolean("TNT-explosion.ProtectionPlayer");
		FallingAnvillProtectionPlayer = getConfig().getBoolean("FallingAnvill.ProtectionPlayer");
	}
	@Override
	public void onDisable(){
		logger.info(getName()+" "+ getDescription().getVersion() + " disable!");
	}
	public void CreatreInventory(Player player,Player Troll){
		Inventory TroolInt = Bukkit.createInventory(player, 18,ChatColor.BLUE + "Troll "+Troll.getName());
		AddToInvetory(TroolInt,369,ChatColor.BOLD + "Lighting");
		AddToInvetory(TroolInt,353,ChatColor.GOLD + "SuperSpeed");
		AddToInvetory(TroolInt,46,ChatColor.RED + "TNT-explosion");
		AddToInvetory(TroolInt,381,ChatColor.DARK_GRAY + "Blindess");
		AddToInvetory(TroolInt,90,ChatColor.DARK_PURPLE + "Confusion");
		AddToInvetory(TroolInt,2256,ChatColor.YELLOW + "ScaryMusic");
		AddToInvetory(TroolInt,145,ChatColor.GRAY + "FallingAnvill");
		AddToInvetory(TroolInt,397,ChatColor.GREEN + "CreeperFollow",(short) 4);
		AddToInvetory(TroolInt,324,ChatColor.DARK_RED + "DoorSound");
		AddToInvetory(TroolInt,367,ChatColor.AQUA + "Slow");
		AddToInvetory(TroolInt,386,ChatColor.LIGHT_PURPLE + "Spamer");
		AddToInvetory(TroolInt,54,ChatColor.BLUE + "InvetoryEdit");
		AddToInvetory(TroolInt,130,ChatColor.DARK_BLUE + "EnderChestEdit");
		AddToInvetory(TroolInt,101,ChatColor.DARK_GRAY + "Jail");
		AddToInvetory(TroolInt,32,ChatColor.GOLD + "FillInvetory");
		AddToInvetory(TroolInt,421,ChatColor.DARK_RED + "soon...");
		AddToInvetory(TroolInt,421,ChatColor.DARK_AQUA + "soon...");
		AddToInvetory(TroolInt,421,ChatColor.WHITE + "soon...");
		AddToInvetory(TroolInt,421,ChatColor.DARK_GREEN + "soon...");
		player.openInventory(TroolInt);
	}
	public void AddToInvetory(Inventory inv,int id,String name){
		ItemStack item = new ItemStack(Material.getMaterial(id));
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		item = addGlow(item);
		inv.addItem(item);
	}
	public void AddToInvetory(Inventory inv,int id,String name,short d){
		ItemStack item = new ItemStack(Material.getMaterial(id));
		item.setDurability(d);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		item = addGlow(item);
		inv.addItem(item);
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("troll")){
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if(args.length > 0){
					Player troll = server.getPlayer(args[0]);
					if(troll != null){
						if(!troll.getName().equalsIgnoreCase(player.getName())){
							CreatreInventory(player,troll);
						}else{
							player.sendMessage(ChatColor.RED + "You can`t troll this player!");
						}
					}else{
						player.sendMessage(ChatColor.RED + "The player "+args[0]+" not found!");
					}
					return true;
				}
			}
		}else if(cmd.getName().equalsIgnoreCase("trollme")){
			if (sender instanceof Player) {
				Player player = (Player) sender;
				CreatreInventory(player,player);
				return true;
			}
		}else if(cmd.getName().equalsIgnoreCase("troling")){
			String[] Messages = new String[3];
			Messages[0] = ChatColor.DARK_PURPLE + "Main Coder "+ChatColor.WHITE+"-"+ChatColor.GOLD+" Razdom";
			Messages[1] = ChatColor.DARK_PURPLE + "Main Ideas "+ChatColor.WHITE+"-"+ChatColor.GOLD+" BeatsDJ";
			Messages[2] = ChatColor.DARK_PURPLE + "Main Tester "+ChatColor.WHITE+"-"+ChatColor.GOLD+" dannycool1031";
			sender.sendMessage(Messages);
			return true;
		}
		return false;
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		String IntName = event.getInventory().getName();
		boolean CloseInt = true;
		if(IntName.indexOf("Troll") != -1){
			event.setCancelled(true);
			String PlayerName = IntName.replace(ChatColor.BLUE + "Troll ", "");
			final Player troll = server.getPlayer(PlayerName);
			if(troll != null){
				if(troll.isOnline()){
					ItemStack itemS = event.getCurrentItem();
					if(itemS == null){
						return;
					}
					if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BOLD + "Lighting")){
						troll.getWorld().strikeLightning(troll.getLocation());
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "SuperSpeed")){
						troll.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 80));
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "TNT-explosion")){
						if(troll.isFlying()){
							troll.setFlying(false);
						}
						troll.setVelocity(new Vector(0, TNTPowerful, 0));
						troll.playSound(troll.getLocation(), Sound.EXPLODE, 1f, 1f);
						if(TNTProtectionPlayer){
							troll.addPotionEffect(new PotionEffect(PotionEffectType.getById(11), 200, 255));
						}
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_GRAY + "Blindess")){
						troll.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 300, 10));
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_PURPLE + "Confusion")){
						troll.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 10));
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.YELLOW + "ScaryMusic")){
						troll.playEffect(troll.getLocation(), Effect.RECORD_PLAY, 2256);
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GRAY + "FallingAnvill")){
						if(FallingAnvillProtectionPlayer){
							troll.addPotionEffect(new PotionEffect(PotionEffectType.getById(11), 70, 255));
						}
						final Block block = troll.getLocation().add(0, 5, 0).getBlock();
						for(int y = troll.getLocation().getBlockY();y<=troll.getLocation().getBlockY()+5;y++){
							troll.getWorld().getBlockAt(troll.getLocation().getBlockX(), y, troll.getLocation().getBlockZ()).setTypeId(0);
						}
						block.setTypeId(145);
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							  @Override
							  public void run() {
								  if(block.getLocation().add(0, -5, 0).getBlock().getTypeId() == 145){
									  block.getLocation().add(0, -5, 0).getBlock().setTypeId(0);
								  }
								  this.cancel();
							  }
							}, 2000);
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GREEN + "CreeperFollow")){
						LivingEntity Creppers = troll.getWorld().spawnCreature(troll.getLocation(), CreatureType.CREEPER);
						Creeper crep = (Creeper) Creppers;
						crep.setPassenger(troll);
						crep.setPowered(false);
						troll.addPotionEffect(new PotionEffect(PotionEffectType.getById(11), 200, 255));
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED + "DoorSound")){
						PlayersDoor[troll.getEntityId()] = true;
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							  @Override
							  public void run() {
								  PlayersDoor[troll.getEntityId()] = false;
								  this.cancel();
							  }
							}, 10000);
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.AQUA + "slow")){
						troll.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 80));
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Spamer")){
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							private int Num = 0; 
							  @Override
							  public void run() {
								  String NumShow = "";
								  Num++;
								  for(int i = 0;i<= Num;i++){
									  NumShow += ChatColor.MAGIC + "1";  
								  }
								  troll.sendMessage(ChatColor.LIGHT_PURPLE + NumShow);
								  if(Num >= 100){
									this.cancel();  
								  }
							  }
							},0, 100);
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.BLUE + "InvetoryEdit")){
						event.getWhoClicked().openInventory(troll.getInventory());
						CloseInt = false;
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_BLUE + "EnderChestEdit")){
						event.getWhoClicked().openInventory(troll.getEnderChest());
						CloseInt = false;
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "FillInvetory")){
						final Inventory intPlayer = troll.getInventory();
						final ItemStack itemput = new ItemStack(FillInvetoryID);
						ItemMeta it = itemput.getItemMeta();
						it.setDisplayName(ChatColor.GOLD + FillInvetoryText);
						itemput.setItemMeta(it);
						Timer timer = new Timer();
						timer.schedule(new TimerTask() {
							
							@Override
							public void run() {
								this.cancel();
								for(int i=0;i<intPlayer.getSize();i++){
									intPlayer.setItem(i, addGlow(itemput));
								}
								
							}
						}, 50);
					}else if(itemS.getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.DARK_GRAY + "Jail")){
						World TroolWorld = troll.getWorld();
						Location locationTroll = troll.getLocation();
						int x = locationTroll.getBlockX();
						int y = locationTroll.getBlockY();
						int z = locationTroll.getBlockZ();
						int newX = x+1;
						int newZ = z+1;
						x--;
						z--;
						for(int x2 = x;x2<=newX;x2++){
							for(int z2 = z;z2<=newZ;z2++){
								TroolWorld.getBlockAt(x2, y+3, z2).setType(Material.BEDROCK);
								if(x+1 != x2 || z+1 != z2){
									TroolWorld.getBlockAt(x2, y+1, z2).setTypeId(101);
									TroolWorld.getBlockAt(x2, y+2, z2).setTypeId(101);
								}else{
									TroolWorld.getBlockAt(x2, y+4, z2).setTypeId(89);
								}
								TroolWorld.getBlockAt(x2, y, z2).setType(Material.BEDROCK);
							}
						}
						troll.teleport(new Location(TroolWorld, x+1, y+1, z+1));
					}
					if(CloseInt){
						event.getWhoClicked().closeInventory();
					}
				}else{
					event.getWhoClicked().closeInventory();
				}
			}else{
				event.getWhoClicked().closeInventory();
			}
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		PlayersDoor[event.getPlayer().getEntityId()] = false;
		PlayersLags[event.getPlayer().getEntityId()] = false;
	}
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		if(PlayersDoor[event.getPlayer().getEntityId()]){
			event.getPlayer().playEffect(event.getPlayer().getLocation(), Effect.DOOR_TOGGLE, 330);
		}
	}
	public static ItemStack addGlow(ItemStack item)
    {
		ItemMeta itemM = item.getItemMeta();
		itemM.addEnchant(Enchantment.DURABILITY, 1, true);
		item.setItemMeta(itemM);
		return item;
    }
}
