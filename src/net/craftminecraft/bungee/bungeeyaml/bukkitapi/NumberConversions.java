/*  1:   */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi;
/*  2:   */ 
/*  3:   */ public final class NumberConversions
/*  4:   */ {
/*  5:   */   public static int floor(double num)
/*  6:   */   {
/*  7:10 */     int floor = (int)num;
/*  8:11 */     return floor == num ? floor : floor - (int)(Double.doubleToRawLongBits(num) >>> 63);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public static int ceil(double num)
/* 12:   */   {
/* 13:15 */     int floor = (int)num;
/* 14:16 */     return floor == num ? floor : floor + (int)((Double.doubleToRawLongBits(num) ^ 0xFFFFFFFF) >>> 63);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public static int round(double num)
/* 18:   */   {
/* 19:20 */     return floor(num + 0.5D);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public static int toInt(Object object)
/* 23:   */   {
/* 24:24 */     if ((object instanceof Number)) {
/* 25:25 */       return ((Number)object).intValue();
/* 26:   */     }
/* 27:   */     try
/* 28:   */     {
/* 29:29 */       return Integer.valueOf(object.toString()).intValue();
/* 30:   */     }
/* 31:   */     catch (NumberFormatException e) {}catch (NullPointerException e) {}
/* 32:33 */     return 0;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public static float toFloat(Object object)
/* 36:   */   {
/* 37:37 */     if ((object instanceof Number)) {
/* 38:38 */       return ((Number)object).floatValue();
/* 39:   */     }
/* 40:   */     try
/* 41:   */     {
/* 42:42 */       return Float.valueOf(object.toString()).floatValue();
/* 43:   */     }
/* 44:   */     catch (NumberFormatException e) {}catch (NullPointerException e) {}
/* 45:46 */     return 0.0F;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public static double toDouble(Object object)
/* 49:   */   {
/* 50:50 */     if ((object instanceof Number)) {
/* 51:51 */       return ((Number)object).doubleValue();
/* 52:   */     }
/* 53:   */     try
/* 54:   */     {
/* 55:55 */       return Double.valueOf(object.toString()).doubleValue();
/* 56:   */     }
/* 57:   */     catch (NumberFormatException e) {}catch (NullPointerException e) {}
/* 58:59 */     return 0.0D;
/* 59:   */   }
/* 60:   */   
/* 61:   */   public static long toLong(Object object)
/* 62:   */   {
/* 63:63 */     if ((object instanceof Number)) {
/* 64:64 */       return ((Number)object).longValue();
/* 65:   */     }
/* 66:   */     try
/* 67:   */     {
/* 68:68 */       return Long.valueOf(object.toString()).longValue();
/* 69:   */     }
/* 70:   */     catch (NumberFormatException e) {}catch (NullPointerException e) {}
/* 71:72 */     return 0L;
/* 72:   */   }
/* 73:   */   
/* 74:   */   public static short toShort(Object object)
/* 75:   */   {
/* 76:76 */     if ((object instanceof Number)) {
/* 77:77 */       return ((Number)object).shortValue();
/* 78:   */     }
/* 79:   */     try
/* 80:   */     {
/* 81:81 */       return Short.valueOf(object.toString()).shortValue();
/* 82:   */     }
/* 83:   */     catch (NumberFormatException e) {}catch (NullPointerException e) {}
/* 84:85 */     return 0;
/* 85:   */   }
/* 86:   */   
/* 87:   */   public static byte toByte(Object object)
/* 88:   */   {
/* 89:89 */     if ((object instanceof Number)) {
/* 90:90 */       return ((Number)object).byteValue();
/* 91:   */     }
/* 92:   */     try
/* 93:   */     {
/* 94:94 */       return Byte.valueOf(object.toString()).byteValue();
/* 95:   */     }
/* 96:   */     catch (NumberFormatException e) {}catch (NullPointerException e) {}
/* 97:98 */     return 0;
/* 98:   */   }
/* 99:   */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.NumberConversions
 * JD-Core Version:    0.7.0.1
 */