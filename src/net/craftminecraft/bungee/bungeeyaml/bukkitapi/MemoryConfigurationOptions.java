/*  1:   */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi;
/*  2:   */ 
/*  3:   */ public class MemoryConfigurationOptions
/*  4:   */   extends ConfigurationOptions
/*  5:   */ {
/*  6:   */   protected MemoryConfigurationOptions(MemoryConfiguration configuration)
/*  7:   */   {
/*  8: 8 */     super(configuration);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public MemoryConfiguration configuration()
/* 12:   */   {
/* 13:13 */     return (MemoryConfiguration)super.configuration();
/* 14:   */   }
/* 15:   */   
/* 16:   */   public MemoryConfigurationOptions copyDefaults(boolean value)
/* 17:   */   {
/* 18:18 */     super.copyDefaults(value);
/* 19:19 */     return this;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public MemoryConfigurationOptions pathSeparator(char value)
/* 23:   */   {
/* 24:24 */     super.pathSeparator(value);
/* 25:25 */     return this;
/* 26:   */   }
/* 27:   */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.MemoryConfigurationOptions
 * JD-Core Version:    0.7.0.1
 */