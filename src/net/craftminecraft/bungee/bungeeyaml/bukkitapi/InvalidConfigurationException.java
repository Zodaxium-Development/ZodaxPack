/*  1:   */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi;
/*  2:   */ 
/*  3:   */ public class InvalidConfigurationException
/*  4:   */   extends Exception
/*  5:   */ {
/**
	 * 
	 */
	private static final long serialVersionUID = 1132002242874125661L;
/*  6:   */   public InvalidConfigurationException() {}
/*  7:   */   
/*  8:   */   public InvalidConfigurationException(String msg)
/*  9:   */   {
/* 10:19 */     super(msg);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public InvalidConfigurationException(Throwable cause)
/* 14:   */   {
/* 15:28 */     super(cause);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public InvalidConfigurationException(String msg, Throwable cause)
/* 19:   */   {
/* 20:38 */     super(msg, cause);
/* 21:   */   }
/* 22:   */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.InvalidConfigurationException
 * JD-Core Version:    0.7.0.1
 */