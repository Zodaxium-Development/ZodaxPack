/*  1:   */ package net.craftminecraft.bungee.bungeeyaml.pluginapi;
/*  2:   */ 
/*  3:   */ import java.io.File;
/*  4:   */ import java.io.FileOutputStream;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.io.InputStream;
/*  7:   */ import java.io.OutputStream;
/*  8:   */ import java.util.logging.Level;

/* 10:   */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.FileConfiguration;
/* 11:   */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.YamlConfiguration;
/* 13:   */ import net.md_5.bungee.api.plugin.Plugin;
/*  9:   */ 
/* 12:   */ 
/* 14:   */ 
/* 15:   */ 
/* 16:   */ public class ConfigurablePlugin
/* 17:   */   extends Plugin
/* 18:   */ {
/* 19:15 */   private FileConfiguration newConfig = null;
/* 20:16 */   private File configFile = null;
/* 21:   */   
/* 22:   */   public FileConfiguration getConfig()
/* 23:   */   {
/* 24:19 */     if (this.newConfig == null) {
/* 25:20 */       reloadConfig();
/* 26:   */     }
/* 27:22 */     return this.newConfig;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void reloadConfig()
/* 31:   */   {
/* 32:26 */     this.newConfig = YamlConfiguration.loadConfiguration(this.configFile);
/* 33:   */     
/* 34:28 */     InputStream defConfigStream = getResourceAsStream("config.yml");
/* 35:29 */     if (defConfigStream != null)
/* 36:   */     {
/* 37:30 */       YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
/* 38:   */       
/* 39:32 */       this.newConfig.setDefaults(defConfig);
/* 40:   */     }
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void saveConfig()
/* 44:   */   {
/* 45:   */     try
/* 46:   */     {
/* 47:38 */       getConfig().save(this.configFile);
/* 48:   */     }
/* 49:   */     catch (IOException ex)
/* 50:   */     {
/* 51:40 */       getProxy().getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, ex);
/* 52:   */     }
/* 53:   */   }
/* 54:   */   
/* 55:   */   public void saveDefaultConfig()
/* 56:   */   {
/* 57:45 */     if (!this.configFile.exists()) {
/* 58:46 */       saveResource("config.yml", false);
/* 59:   */     }
/* 60:   */   }
/* 61:   */   
/* 62:   */   public void saveResource(String resourcePath, boolean replace)
/* 63:   */   {
/* 64:51 */     if ((resourcePath == null) || (resourcePath.equals(""))) {
/* 65:52 */       throw new IllegalArgumentException("ResourcePath cannot be null or empty");
/* 66:   */     }
/* 67:55 */     resourcePath = resourcePath.replace('\\', '/');
/* 68:56 */     InputStream in = getResourceAsStream(resourcePath);
/* 69:57 */     if (in == null) {
/* 70:58 */       throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + getDescription().getName());
/* 71:   */     }
/* 72:61 */     File outFile = new File(getDataFolder(), resourcePath);
/* 73:62 */     int lastIndex = resourcePath.lastIndexOf('/');
/* 74:63 */     File outDir = new File(getDataFolder(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
/* 75:65 */     if (!outDir.exists()) {
/* 76:66 */       outDir.mkdirs();
/* 77:   */     }
/* 78:   */     try
/* 79:   */     {
/* 80:70 */       if ((!outFile.exists()) || (replace))
/* 81:   */       {
/* 82:71 */         OutputStream out = new FileOutputStream(outFile);
/* 83:72 */         byte[] buf = new byte[1024];
/* 84:   */         int len;
/* 85:74 */         while ((len = in.read(buf)) > 0) {
/* 86:75 */           out.write(buf, 0, len);
/* 87:   */         }
/* 88:77 */         out.close();
/* 89:78 */         in.close();
/* 90:   */       }
/* 91:   */       else
/* 92:   */       {
/* 93:80 */         getProxy().getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
/* 94:   */       }
/* 95:   */     }
/* 96:   */     catch (IOException ex)
/* 97:   */     {
/* 98:83 */       getProxy().getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
/* 99:   */     }
/* :0:   */   }
/* :1:   */   
/* :2:   */   public final void onLoad()
/* :3:   */   {
/* :4:89 */     this.configFile = new File(getDataFolder(), "config.yml");
/* :5:90 */     onLoading();
/* :6:   */   }
/* :7:   */   
/* :8:   */   public void onLoading() {}
/* :9:   */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.pluginapi.ConfigurablePlugin
 * JD-Core Version:    0.7.0.1
 */