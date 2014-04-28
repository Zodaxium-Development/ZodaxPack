/*   1:    */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi.file;
/*   2:    */ 
/*   3:    */ import com.google.common.base.Preconditions;
/*   4:    */ import java.io.BufferedReader;
/*   5:    */ import java.io.File;
/*   6:    */ import java.io.FileInputStream;
/*   7:    */ import java.io.FileNotFoundException;
/*   8:    */ import java.io.FileWriter;
/*   9:    */ import java.io.IOException;
/*  10:    */ import java.io.InputStream;
/*  11:    */ import java.io.InputStreamReader;
/*  12:    */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.Configuration;
/*  13:    */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.InvalidConfigurationException;
/*  14:    */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.MemoryConfiguration;
/*  15:    */ 
/*  16:    */ public abstract class FileConfiguration
/*  17:    */   extends MemoryConfiguration
/*  18:    */ {
/*  19:    */   public FileConfiguration() {}
/*  20:    */   
/*  21:    */   public FileConfiguration(Configuration defaults)
/*  22:    */   {
/*  23: 35 */     super(defaults);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void save(File file)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 49 */     Preconditions.checkNotNull(file, "File cannot be null");
/*  30:    */     
/*  31: 51 */     File parent = file.getCanonicalFile().getParentFile();
/*  32: 52 */     if (parent == null) {
/*  33: 60 */       return;
/*  34:    */     }
/*  35: 62 */     parent.mkdirs();
/*  36: 63 */     if (!parent.isDirectory()) {
/*  37: 64 */       throw new IOException("Unable to create parent directories of " + file);
/*  38:    */     }
/*  39: 67 */     String data = saveToString();
/*  40:    */     
/*  41: 69 */     FileWriter writer = new FileWriter(file);
/*  42:    */     try
/*  43:    */     {
/*  44: 72 */       writer.write(data);
/*  45:    */     }
/*  46:    */     finally
/*  47:    */     {
/*  48: 74 */       writer.close();
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void save(String file)
/*  53:    */     throws IOException
/*  54:    */   {
/*  55: 89 */     Preconditions.checkNotNull(file, "File cannot be null");
/*  56:    */     
/*  57: 91 */     save(new File(file));
/*  58:    */   }
/*  59:    */   
/*  60:    */   public abstract String saveToString();
/*  61:    */   
/*  62:    */   public void load(File file)
/*  63:    */     throws FileNotFoundException, IOException, InvalidConfigurationException
/*  64:    */   {
/*  65:116 */     Preconditions.checkNotNull(file, "File cannot be null");
/*  66:    */     
/*  67:118 */     load(new FileInputStream(file));
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void load(InputStream stream)
/*  71:    */     throws IOException, InvalidConfigurationException
/*  72:    */   {
/*  73:133 */     Preconditions.checkNotNull(stream, "Stream cannot be null");
/*  74:    */     
/*  75:135 */     InputStreamReader reader = new InputStreamReader(stream);
/*  76:136 */     StringBuilder builder = new StringBuilder();
/*  77:137 */     BufferedReader input = new BufferedReader(reader);
/*  78:    */     try
/*  79:    */     {
/*  80:    */       String line;
/*  81:143 */       while ((line = input.readLine()) != null)
/*  82:    */       {
/*  83:144 */         builder.append(line);
/*  84:145 */         builder.append('\n');
/*  85:    */       }
/*  86:    */     }
/*  87:    */     finally
/*  88:    */     {
/*  89:148 */       input.close();
/*  90:    */     }
/*  91:151 */     loadFromString(builder.toString());
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void load(String file)
/*  95:    */     throws FileNotFoundException, IOException, InvalidConfigurationException
/*  96:    */   {
/*  97:169 */     Preconditions.checkNotNull(file, "File cannot be null");
/*  98:    */     
/*  99:171 */     load(new File(file));
/* 100:    */   }
/* 101:    */   
/* 102:    */   public abstract void loadFromString(String paramString)
/* 103:    */     throws InvalidConfigurationException;
/* 104:    */   
/* 105:    */   protected abstract String buildHeader();
/* 106:    */   
/* 107:    */   public FileConfigurationOptions options()
/* 108:    */   {
/* 109:200 */     if (this.options == null) {
/* 110:201 */       this.options = new FileConfigurationOptions(this);
/* 111:    */     }
/* 112:204 */     return (FileConfigurationOptions)this.options;
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.FileConfiguration
 * JD-Core Version:    0.7.0.1
 */