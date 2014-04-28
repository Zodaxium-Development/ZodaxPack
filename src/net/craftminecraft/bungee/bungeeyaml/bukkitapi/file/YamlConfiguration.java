/*   1:    */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi.file;
/*   2:    */ 
/*   3:    */ /*   4:    */ import java.io.File;
/*   5:    */ import java.io.FileNotFoundException;
/*   6:    */ import java.io.IOException;
/*   7:    */ import java.io.InputStream;
/*   8:    */ import java.util.Collections;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.Map;
/*  13:    */ import java.util.logging.Level;
/*  15:    */ import java.util.regex.Pattern;

/*  16:    */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.Configuration;
/*  17:    */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.ConfigurationSection;
/*  18:    */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.InvalidConfigurationException;
/*  19:    */ import net.md_5.bungee.api.ProxyServer;

/*  20:    */ import org.yaml.snakeyaml.DumperOptions;
/*  23:    */ import org.yaml.snakeyaml.Yaml;
/*  24:    */ import org.yaml.snakeyaml.error.YAMLException;
/*  25:    */ import org.yaml.snakeyaml.representer.Representer;

import com.google.common.base.Preconditions;
/*  11:    */ 
/*  12:    */ 
/*  14:    */ 
/*  21:    */ 
/*  22:    */ 
/*  26:    */ 
/*  27:    */ public class YamlConfiguration
/*  28:    */   extends FileConfiguration
/*  29:    */ {
/*  30:    */   protected static final String COMMENT_PREFIX = "# ";
/*  31:    */   protected static final String BLANK_CONFIG = "{}\n";
/*  32: 32 */   private final DumperOptions yamlOptions = new DumperOptions();
/*  33: 33 */   private final Representer yamlRepresenter = new Representer();
/*  34: 34 */   private final Yaml yaml = new Yaml(new YamlConstructor(), this.yamlRepresenter, this.yamlOptions);
/*  35:    */   
/*  36:    */   public String saveToString()
/*  37:    */   {
/*  38: 38 */     this.yamlOptions.setIndent(options().indent());
/*  39: 39 */     this.yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
/*  40: 40 */     this.yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
/*  41:    */     
/*  42: 42 */     StringBuilder builder = new StringBuilder(buildHeader());
/*  43: 43 */     if (builder.length() > 0) {
/*  44: 44 */       builder.append('\n');
/*  45:    */     }
/*  46: 47 */     builder.append(saveConfigSectionWithComments(this, false));
/*  47:    */     
/*  48: 49 */     String dump = builder.toString();
/*  49: 51 */     if (dump.equals("{}\n")) {
/*  50: 52 */       dump = "";
/*  51:    */     }
/*  52: 55 */     return dump;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String saveConfigSectionWithComments(ConfigurationSection section, boolean depth)
/*  56:    */   {
/*  57: 59 */     StringBuilder builder = new StringBuilder();
/*  58: 60 */     for (Iterator<Map.Entry<String, Object>> i = section.getValues(false).entrySet().iterator(); i.hasNext();)
/*  59:    */     {
/*  60: 61 */       Map.Entry<String, Object> entry = i.next();
/*  61:    */       
/*  62:    */ 
/*  63: 64 */       String comment = getComment(entry.getKey());
/*  64: 65 */       if (comment != null) {
/*  65: 66 */         builder.append(buildComment(comment));
/*  66:    */       }
/*  67: 69 */       if ((entry.getValue() instanceof ConfigurationSection))
/*  68:    */       {
/*  69: 71 */         builder.append('\n');
/*  70: 74 */         if (Character.isLetterOrDigit(entry.getKey().codePointAt(0))) {
/*  71: 75 */           builder.append(entry.getKey());
/*  72:    */         } else {
/*  73: 77 */           builder.append("'" + entry.getKey() + "'");
/*  74:    */         }
/*  75: 78 */         builder.append(":" + this.yamlOptions.getLineBreak().getString());
/*  76: 79 */         builder.append(saveConfigSectionWithComments((ConfigurationSection)entry.getValue(), true));
/*  77:    */         
/*  78: 81 */         builder.append('\n');
/*  79:    */       }
/*  80:    */       else
/*  81:    */       {
/*  82: 84 */         builder.append(this.yaml.dump(Collections.singletonMap(entry.getKey(), entry.getValue())));
/*  83:    */       }
/*  84:    */     }
/*  85: 87 */     String dump = builder.toString();
/*  86: 90 */     if (depth)
/*  87:    */     {
/*  88: 91 */       String[] lines = dump.split(Pattern.quote(this.yamlOptions.getLineBreak().getString()));
/*  89: 92 */       StringBuilder indented = new StringBuilder();
/*  90: 93 */       for (int i = 0; i < lines.length; i++)
/*  91:    */       {
/*  92: 94 */         for (int indent = 0; indent < this.yamlOptions.getIndent(); indent++) {
/*  93: 95 */           indented.append(" ");
/*  94:    */         }
/*  95: 97 */         indented.append(lines[i] + this.yamlOptions.getLineBreak().getString());
/*  96:    */       }
/*  97: 99 */       return indented.toString();
/*  98:    */     }
/*  99:101 */     return dump;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void loadFromString(String contents)
/* 103:    */     throws InvalidConfigurationException
/* 104:    */   {
/* 105:107 */     Preconditions.checkNotNull(contents, "Contents cannot be null");
/* 106:    */     Map<?, ?> input;
/* 107:    */     try
/* 108:    */     {
/* 109:111 */       input = (Map<?, ?>)this.yaml.load(contents);
/* 110:    */     }
/* 111:    */     catch (YAMLException e)
/* 112:    */     {
/* 113:113 */       throw new InvalidConfigurationException(e);
/* 114:    */     }
/* 115:    */     catch (ClassCastException e)
/* 116:    */     {
/* 117:115 */       throw new InvalidConfigurationException("Top level is not a Map.");
/* 118:    */     }
/* 119:118 */     String header = parseHeader(contents);
/* 120:119 */     if (header.length() > 0) {
/* 121:120 */       options().header(header);
/* 122:    */     }
/* 123:123 */     if (input != null) {
/* 124:124 */       convertMapsToSections(input, this);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   protected void convertMapsToSections(Map<?, ?> input, ConfigurationSection section)
/* 129:    */   {
/* 130:129 */     for (Map.Entry<?, ?> entry : input.entrySet())
/* 131:    */     {
/* 132:130 */       String key = entry.getKey().toString();
/* 133:131 */       Object value = entry.getValue();
/* 134:133 */       if ((value instanceof Map)) {
/* 135:134 */         convertMapsToSections((Map<?, ?>)value, section.createSection(key));
/* 136:    */       } else {
/* 137:136 */         section.set(key, value);
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   protected String parseHeader(String input)
/* 143:    */   {
/* 144:142 */     String[] lines = input.split("\r?\n", -1);
/* 145:143 */     StringBuilder result = new StringBuilder();
/* 146:144 */     boolean readingHeader = true;
/* 147:145 */     boolean foundHeader = false;
/* 148:147 */     for (int i = 0; (i < lines.length) && (readingHeader); i++)
/* 149:    */     {
/* 150:148 */       String line = lines[i];
/* 151:150 */       if (line.startsWith("# "))
/* 152:    */       {
/* 153:151 */         if (i > 0) {
/* 154:152 */           result.append("\n");
/* 155:    */         }
/* 156:155 */         if (line.length() > "# ".length()) {
/* 157:156 */           result.append(line.substring("# ".length()));
/* 158:    */         }
/* 159:159 */         foundHeader = true;
/* 160:    */       }
/* 161:160 */       else if ((foundHeader) && (line.length() == 0))
/* 162:    */       {
/* 163:161 */         result.append("\n");
/* 164:    */       }
/* 165:162 */       else if (foundHeader)
/* 166:    */       {
/* 167:163 */         readingHeader = false;
/* 168:    */       }
/* 169:    */     }
/* 170:167 */     return result.toString();
/* 171:    */   }
/* 172:    */   
/* 173:    */   protected String buildHeader()
/* 174:    */   {
/* 175:172 */     String header = options().header();
/* 176:174 */     if (options().copyHeader())
/* 177:    */     {
/* 178:175 */       Configuration def = getDefaults();
/* 179:177 */       if ((def != null) && ((def instanceof FileConfiguration)))
/* 180:    */       {
/* 181:178 */         FileConfiguration filedefaults = (FileConfiguration)def;
/* 182:179 */         String defaultsHeader = filedefaults.buildHeader();
/* 183:181 */         if ((defaultsHeader != null) && (defaultsHeader.length() > 0)) {
/* 184:182 */           return defaultsHeader;
/* 185:    */         }
/* 186:    */       }
/* 187:    */     }
/* 188:187 */     if (header == null) {
/* 189:188 */       return "";
/* 190:    */     }
/* 191:191 */     StringBuilder builder = new StringBuilder();
/* 192:192 */     String[] lines = header.split("\r?\n", -1);
/* 193:193 */     boolean startedHeader = false;
/* 194:195 */     for (int i = lines.length - 1; i >= 0; i--)
/* 195:    */     {
/* 196:196 */       builder.insert(0, "\n");
/* 197:198 */       if ((startedHeader) || (lines[i].length() != 0))
/* 198:    */       {
/* 199:199 */         builder.insert(0, lines[i]);
/* 200:200 */         builder.insert(0, "# ");
/* 201:201 */         startedHeader = true;
/* 202:    */       }
/* 203:    */     }
/* 204:205 */     return builder.toString();
/* 205:    */   }
/* 206:    */   
/* 207:    */   public YamlConfigurationOptions options()
/* 208:    */   {
/* 209:210 */     if (this.options == null) {
/* 210:211 */       this.options = new YamlConfigurationOptions(this);
/* 211:    */     }
/* 212:214 */     return (YamlConfigurationOptions)this.options;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public static YamlConfiguration loadConfiguration(File file)
/* 216:    */   {
/* 217:228 */     Preconditions.checkNotNull(file, "File cannot be null");
/* 218:    */     
/* 219:230 */     YamlConfiguration config = new YamlConfiguration();
/* 220:    */     try
/* 221:    */     {
/* 222:233 */       config.load(file);
/* 223:    */     }
/* 224:    */     catch (FileNotFoundException ex) {}catch (IOException ex)
/* 225:    */     {
/* 226:236 */       ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
/* 227:    */     }
/* 228:    */     catch (InvalidConfigurationException ex)
/* 229:    */     {
/* 230:238 */       ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Cannot load " + file, ex);
/* 231:    */     }
/* 232:241 */     return config;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public static YamlConfiguration loadConfiguration(InputStream stream)
/* 236:    */   {
/* 237:255 */     Preconditions.checkNotNull(stream, "Stream cannot be null");
/* 238:    */     
/* 239:257 */     YamlConfiguration config = new YamlConfiguration();
/* 240:    */     try
/* 241:    */     {
/* 242:260 */       config.load(stream);
/* 243:    */     }
/* 244:    */     catch (IOException ex)
/* 245:    */     {
/* 246:262 */       ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Cannot load configuration from stream", ex);
/* 247:    */     }
/* 248:    */     catch (InvalidConfigurationException ex)
/* 249:    */     {
/* 250:264 */       ProxyServer.getInstance().getLogger().log(Level.SEVERE, "Cannot load configuration from stream", ex);
/* 251:    */     }
/* 252:267 */     return config;
/* 253:    */   }
/* 254:    */   
/* 255:    */   protected String buildComment(String comment)
/* 256:    */   {
/* 257:277 */     StringBuilder builder = new StringBuilder();
/* 258:278 */     for (String line : comment.split("\r?\n"))
/* 259:    */     {
/* 260:279 */       builder.append("# ");
/* 261:280 */       builder.append(line);
/* 262:281 */       builder.append('\n');
/* 263:    */     }
/* 264:283 */     return builder.toString();
/* 265:    */   }
/* 266:    */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.YamlConfiguration
 * JD-Core Version:    0.7.0.1
 */