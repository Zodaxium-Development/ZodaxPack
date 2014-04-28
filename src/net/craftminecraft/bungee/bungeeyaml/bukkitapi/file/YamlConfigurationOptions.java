/*  1:   */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi.file;
/*  2:   */ 
/*  3:   */ import com.google.common.base.Preconditions;
/*  4:   */ 
/*  5:   */ public class YamlConfigurationOptions
/*  6:   */   extends FileConfigurationOptions
/*  7:   */ {
/*  8: 9 */   private int indent = 2;
/*  9:   */   
/* 10:   */   protected YamlConfigurationOptions(YamlConfiguration configuration)
/* 11:   */   {
/* 12:12 */     super(configuration);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public YamlConfiguration configuration()
/* 16:   */   {
/* 17:17 */     return (YamlConfiguration)super.configuration();
/* 18:   */   }
/* 19:   */   
/* 20:   */   public YamlConfigurationOptions copyDefaults(boolean value)
/* 21:   */   {
/* 22:22 */     super.copyDefaults(value);
/* 23:23 */     return this;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public YamlConfigurationOptions pathSeparator(char value)
/* 27:   */   {
/* 28:28 */     super.pathSeparator(value);
/* 29:29 */     return this;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public YamlConfigurationOptions header(String value)
/* 33:   */   {
/* 34:34 */     super.header(value);
/* 35:35 */     return this;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public YamlConfigurationOptions copyHeader(boolean value)
/* 39:   */   {
/* 40:40 */     super.copyHeader(value);
/* 41:41 */     return this;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public int indent()
/* 45:   */   {
/* 46:52 */     return this.indent;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public YamlConfigurationOptions indent(int value)
/* 50:   */   {
/* 51:64 */     Preconditions.checkArgument(value >= 2, "Indent must be at least 2 characters");
/* 52:65 */     Preconditions.checkArgument(value <= 9, "Indent cannot be greater than 9 characters");
/* 53:   */     
/* 54:67 */     this.indent = value;
/* 55:68 */     return this;
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.YamlConfigurationOptions
 * JD-Core Version:    0.7.0.1
 */