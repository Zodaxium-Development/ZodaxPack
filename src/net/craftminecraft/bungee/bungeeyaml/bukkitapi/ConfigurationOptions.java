/*  1:   */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi;
/*  2:   */ 
/*  3:   */ public class ConfigurationOptions
/*  4:   */ {
/*  5: 7 */   private char pathSeparator = '.';
/*  6: 8 */   private boolean copyDefaults = false;
/*  7:   */   private final Configuration configuration;
/*  8:   */   
/*  9:   */   protected ConfigurationOptions(Configuration configuration)
/* 10:   */   {
/* 11:12 */     this.configuration = configuration;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public Configuration configuration()
/* 15:   */   {
/* 16:21 */     return this.configuration;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public char pathSeparator()
/* 20:   */   {
/* 21:33 */     return this.pathSeparator;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public ConfigurationOptions pathSeparator(char value)
/* 25:   */   {
/* 26:46 */     this.pathSeparator = value;
/* 27:47 */     return this;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean copyDefaults()
/* 31:   */   {
/* 32:62 */     return this.copyDefaults;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public ConfigurationOptions copyDefaults(boolean value)
/* 36:   */   {
/* 37:78 */     this.copyDefaults = value;
/* 38:79 */     return this;
/* 39:   */   }
/* 40:   */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.ConfigurationOptions
 * JD-Core Version:    0.7.0.1
 */