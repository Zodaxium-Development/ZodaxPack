/*   1:    */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi.file;
/*   2:    */ 
/*   3:    */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.MemoryConfiguration;
/*   4:    */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.MemoryConfigurationOptions;
/*   5:    */ 
/*   6:    */ public class FileConfigurationOptions
/*   7:    */   extends MemoryConfigurationOptions
/*   8:    */ {
/*   9:  9 */   private String header = null;
/*  10: 10 */   private boolean copyHeader = true;
/*  11:    */   
/*  12:    */   protected FileConfigurationOptions(MemoryConfiguration configuration)
/*  13:    */   {
/*  14: 13 */     super(configuration);
/*  15:    */   }
/*  16:    */   
/*  17:    */   public FileConfiguration configuration()
/*  18:    */   {
/*  19: 18 */     return (FileConfiguration)super.configuration();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public FileConfigurationOptions copyDefaults(boolean value)
/*  23:    */   {
/*  24: 23 */     super.copyDefaults(value);
/*  25: 24 */     return this;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public FileConfigurationOptions pathSeparator(char value)
/*  29:    */   {
/*  30: 29 */     super.pathSeparator(value);
/*  31: 30 */     return this;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public String header()
/*  35:    */   {
/*  36: 47 */     return this.header;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public FileConfigurationOptions header(String value)
/*  40:    */   {
/*  41: 64 */     this.header = value;
/*  42: 65 */     return this;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean copyHeader()
/*  46:    */   {
/*  47: 84 */     return this.copyHeader;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public FileConfigurationOptions copyHeader(boolean value)
/*  51:    */   {
/*  52:104 */     this.copyHeader = value;
/*  53:    */     
/*  54:106 */     return this;
/*  55:    */   }
/*  56:    */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.FileConfigurationOptions
 * JD-Core Version:    0.7.0.1
 */