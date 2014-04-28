/*  1:   */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Preconditions;
/*  4:   */ import java.util.Map;
/*  6:   */ 
/*  7:   */ public class MemoryConfiguration
/*  8:   */   extends MemorySection
/*  9:   */   implements Configuration
/* 10:   */ {
/* 11:   */   protected Configuration defaults;
/* 12:   */   protected MemoryConfigurationOptions options;
/* 13:   */   
/* 14:   */   public MemoryConfiguration() {}
/* 15:   */   
/* 16:   */   public MemoryConfiguration(Configuration defaults)
/* 17:   */   {
/* 18:29 */     this.defaults = defaults;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void addDefault(String path, Object value)
/* 22:   */   {
/* 23:34 */     Preconditions.checkNotNull(path, "Path may not be null");
/* 24:36 */     if (this.defaults == null) {
/* 25:37 */       this.defaults = new MemoryConfiguration();
/* 26:   */     }
/* 27:40 */     this.defaults.set(path, value);
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void addDefaults(Map<String, Object> defaults)
/* 31:   */   {
/* 32:44 */     Preconditions.checkNotNull(defaults, "Defaults may not be null");
/* 33:46 */     for (Map.Entry<String, Object> entry : defaults.entrySet()) {
/* 34:47 */       addDefault((String)entry.getKey(), entry.getValue());
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void addDefaults(Configuration defaults)
/* 39:   */   {
/* 40:52 */     Preconditions.checkNotNull(defaults, "Defaults may not be null");
/* 41:   */     
/* 42:54 */     addDefaults(defaults.getValues(true));
/* 43:   */   }
/* 44:   */   
/* 45:   */   public void setDefaults(Configuration defaults)
/* 46:   */   {
/* 47:58 */     Preconditions.checkNotNull(defaults, "Defaults may not be null");
/* 48:   */     
/* 49:60 */     this.defaults = defaults;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public Configuration getDefaults()
/* 53:   */   {
/* 54:64 */     return this.defaults;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public ConfigurationSection getParent()
/* 58:   */   {
/* 59:69 */     return null;
/* 60:   */   }
/* 61:   */   
/* 62:   */   public MemoryConfigurationOptions options()
/* 63:   */   {
/* 64:73 */     if (this.options == null) {
/* 65:74 */       this.options = new MemoryConfigurationOptions(this);
/* 66:   */     }
/* 67:77 */     return this.options;
/* 68:   */   }
/* 69:   */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.MemoryConfiguration
 * JD-Core Version:    0.7.0.1
 */