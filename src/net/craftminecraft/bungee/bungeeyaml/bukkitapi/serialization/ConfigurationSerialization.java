/*   1:    */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi.serialization;
/*   2:    */ 
/*   3:    */ /*   4:    */ import java.lang.reflect.Constructor;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ import java.lang.reflect.Modifier;
/*   9:    */ import java.util.HashMap;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.logging.Level;
/*  12:    */ import java.util.logging.Logger;

import com.google.common.base.Preconditions;
/*   8:    */ 
/*  13:    */ 
/*  14:    */ public class ConfigurationSerialization
/*  15:    */ {
/*  16:    */   public static final String SERIALIZED_TYPE_KEY = "==";
/*  17:    */   private final Class<? extends ConfigurationSerializable> clazz;
/*  18: 22 */   private static Map<String, Class<? extends ConfigurationSerializable>> aliases = new HashMap<String, Class<? extends ConfigurationSerializable>>();
/*  19:    */   
/*  20:    */   protected ConfigurationSerialization(Class<? extends ConfigurationSerializable> clazz)
/*  21:    */   {
/*  22: 25 */     this.clazz = clazz;
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected Method getMethod(String name, boolean isStatic)
/*  26:    */   {
/*  27:    */     try
/*  28:    */     {
/*  29: 30 */       Method method = this.clazz.getDeclaredMethod(name, new Class[] { Map.class });
/*  30: 32 */       if (!ConfigurationSerializable.class.isAssignableFrom(method.getReturnType())) {
/*  31: 33 */         return null;
/*  32:    */       }
/*  33: 35 */       if (Modifier.isStatic(method.getModifiers()) != isStatic) {
/*  34: 36 */         return null;
/*  35:    */       }
/*  36: 39 */       return method;
/*  37:    */     }
/*  38:    */     catch (NoSuchMethodException ex)
/*  39:    */     {
/*  40: 41 */       return null;
/*  41:    */     }
/*  42:    */     catch (SecurityException ex) {}
/*  43: 43 */     return null;
/*  44:    */   }
/*  45:    */   
/*  46:    */   protected Constructor<? extends ConfigurationSerializable> getConstructor()
/*  47:    */   {
/*  48:    */     try
/*  49:    */     {
/*  50: 49 */       return this.clazz.getConstructor(new Class[] { Map.class });
/*  51:    */     }
/*  52:    */     catch (NoSuchMethodException ex)
/*  53:    */     {
/*  54: 51 */       return null;
/*  55:    */     }
/*  56:    */     catch (SecurityException ex) {}
/*  57: 53 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected ConfigurationSerializable deserializeViaMethod(Method method, Map<String, Object> args)
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64: 59 */       ConfigurationSerializable result = (ConfigurationSerializable)method.invoke(null, new Object[] { args });
/*  65: 61 */       if (result == null) {
/*  66: 62 */         Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call method '" + method.toString() + "' of " + this.clazz + " for deserialization: method returned null");
/*  67:    */       } else {
/*  68: 64 */         return result;
/*  69:    */       }
/*  70:    */     }
/*  71:    */     catch (Throwable ex)
/*  72:    */     {
/*  73: 67 */       Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call method '" + method.toString() + "' of " + this.clazz + " for deserialization", (ex instanceof InvocationTargetException) ? ex.getCause() : ex);
/*  74:    */     }
/*  75: 73 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected ConfigurationSerializable deserializeViaCtor(Constructor<? extends ConfigurationSerializable> ctor, Map<String, Object> args)
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82: 78 */       return (ConfigurationSerializable)ctor.newInstance(new Object[] { args });
/*  83:    */     }
/*  84:    */     catch (Throwable ex)
/*  85:    */     {
/*  86: 80 */       Logger.getLogger(ConfigurationSerialization.class.getName()).log(Level.SEVERE, "Could not call constructor '" + ctor.toString() + "' of " + this.clazz + " for deserialization", (ex instanceof InvocationTargetException) ? ex.getCause() : ex);
/*  87:    */     }
/*  88: 86 */     return null;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public ConfigurationSerializable deserialize(Map<String, Object> args)
/*  92:    */   {
/*  93: 90 */     Preconditions.checkNotNull(args, "Args must not be null");
/*  94:    */     
/*  95: 92 */     ConfigurationSerializable result = null;
/*  96: 93 */     Method method = null;
/*  97: 95 */     if (result == null)
/*  98:    */     {
/*  99: 96 */       method = getMethod("deserialize", true);
/* 100: 98 */       if (method != null) {
/* 101: 99 */         result = deserializeViaMethod(method, args);
/* 102:    */       }
/* 103:    */     }
/* 104:103 */     if (result == null)
/* 105:    */     {
/* 106:104 */       method = getMethod("valueOf", true);
/* 107:106 */       if (method != null) {
/* 108:107 */         result = deserializeViaMethod(method, args);
/* 109:    */       }
/* 110:    */     }
/* 111:111 */     if (result == null)
/* 112:    */     {
/* 113:112 */       Constructor<? extends ConfigurationSerializable> constructor = getConstructor();
/* 114:114 */       if (constructor != null) {
/* 115:115 */         result = deserializeViaCtor(constructor, args);
/* 116:    */       }
/* 117:    */     }
/* 118:119 */     return result;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static ConfigurationSerializable deserializeObject(Map<String, Object> args, Class<? extends ConfigurationSerializable> clazz)
/* 122:    */   {
/* 123:136 */     return new ConfigurationSerialization(clazz).deserialize(args);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public static ConfigurationSerializable deserializeObject(Map<String, Object> args)
/* 127:    */   {
/* 128:152 */     Class<? extends ConfigurationSerializable> clazz = null;
/* 129:154 */     if (args.containsKey("==")) {
/* 130:    */       try
/* 131:    */       {
/* 132:156 */         String alias = (String)args.get("==");
/* 133:158 */         if (alias == null) {
/* 134:159 */           throw new IllegalArgumentException("Cannot have null alias");
/* 135:    */         }
/* 136:161 */         clazz = getClassByAlias(alias);
/* 137:162 */         if (clazz == null) {
/* 138:163 */           throw new IllegalArgumentException("Specified class does not exist ('" + alias + "')");
/* 139:    */         }
/* 140:    */       }
/* 141:    */       catch (ClassCastException ex)
/* 142:    */       {
/* 143:166 */         ex.fillInStackTrace();
/* 144:167 */         throw ex;
/* 145:    */       }
/* 146:    */     } else {
/* 147:170 */       throw new IllegalArgumentException("Args doesn't contain type key ('==')");
/* 148:    */     }
/* 149:173 */     return new ConfigurationSerialization(clazz).deserialize(args);
/* 150:    */   }
/* 151:    */   
/* 152:    */   public static void registerClass(Class<? extends ConfigurationSerializable> clazz)
/* 153:    */   {
/* 154:182 */     DelegateDeserialization delegate = (DelegateDeserialization)clazz.getAnnotation(DelegateDeserialization.class);
/* 155:184 */     if (delegate == null)
/* 156:    */     {
/* 157:185 */       registerClass(clazz, getAlias(clazz));
/* 158:186 */       registerClass(clazz, clazz.getName());
/* 159:    */     }
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static void registerClass(Class<? extends ConfigurationSerializable> clazz, String alias)
/* 163:    */   {
/* 164:198 */     aliases.put(alias, clazz);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static void unregisterClass(String alias)
/* 168:    */   {
/* 169:207 */     aliases.remove(alias);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public static void unregisterClass(Class<? extends ConfigurationSerializable> clazz)
/* 173:    */   {
/* 174:216 */     while (aliases.values().remove(clazz)) {}
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static Class<? extends ConfigurationSerializable> getClassByAlias(String alias)
/* 178:    */   {
/* 179:228 */     return (Class<? extends ConfigurationSerializable>)aliases.get(alias);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public static String getAlias(Class<? extends ConfigurationSerializable> clazz)
/* 183:    */   {
/* 184:238 */     DelegateDeserialization delegate = (DelegateDeserialization)clazz.getAnnotation(DelegateDeserialization.class);
/* 185:240 */     if (delegate != null) {
/* 186:241 */       if ((delegate.value() == null) || (delegate.value() == clazz)) {
/* 187:242 */         delegate = null;
/* 188:    */       } else {
/* 189:244 */         return getAlias(delegate.value());
/* 190:    */       }
/* 191:    */     }
/* 192:248 */     if (delegate == null)
/* 193:    */     {
/* 194:249 */       SerializableAs alias = (SerializableAs)clazz.getAnnotation(SerializableAs.class);
/* 195:251 */       if ((alias != null) && (alias.value() != null)) {
/* 196:252 */         return alias.value();
/* 197:    */       }
/* 198:    */     }
/* 199:256 */     return clazz.getName();
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.serialization.ConfigurationSerialization
 * JD-Core Version:    0.7.0.1
 */