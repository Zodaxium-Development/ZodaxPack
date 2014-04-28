/*  1:   */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi.file;
/*  2:   */ 
/*  3:   */ import java.util.LinkedHashMap;
/*  4:   */ import java.util.Map;

/*  6:   */ import net.craftminecraft.bungee.bungeeyaml.bukkitapi.serialization.ConfigurationSerialization;

/*  7:   */ import org.yaml.snakeyaml.constructor.SafeConstructor;
/*  9:   */ import org.yaml.snakeyaml.error.YAMLException;
/* 10:   */ import org.yaml.snakeyaml.nodes.Node;
/* 11:   */ import org.yaml.snakeyaml.nodes.Tag;
/*  5:   */ 
/*  8:   */ 
/* 12:   */ 
/* 13:   */ public class YamlConstructor
/* 14:   */   extends SafeConstructor
/* 15:   */ {
/* 16:   */   public YamlConstructor()
/* 17:   */   {
/* 18:17 */     this.yamlConstructors.put(Tag.MAP, new ConstructCustomObject());
/* 19:   */   }
/* 20:   */   
/* 21:   */   private class ConstructCustomObject
/* 22:   */     extends SafeConstructor.ConstructYamlMap
/* 23:   */   {
/* 24:   */     private ConstructCustomObject()
/* 25:   */     {
/* 26:20 */       super();
/* 27:   */     }
/* 28:   */     
/* 29:   */     public Object construct(Node node)
/* 30:   */     {
/* 31:23 */       if (node.isTwoStepsConstruction()) {
/* 32:24 */         throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
/* 33:   */       }
/* 34:27 */       Map<?, ?> raw = (Map<?, ?>)super.construct(node);
/* 35:29 */       if (raw.containsKey("=="))
/* 36:   */       {
/* 37:30 */         Map<String, Object> typed = new LinkedHashMap<String, Object>(raw.size());
/* 38:31 */         for (Map.Entry<?, ?> entry : raw.entrySet()) {
/* 39:32 */           typed.put(entry.getKey().toString(), entry.getValue());
/* 40:   */         }
/* 41:   */         try
/* 42:   */         {
/* 43:36 */           return ConfigurationSerialization.deserializeObject(typed);
/* 44:   */         }
/* 45:   */         catch (IllegalArgumentException ex)
/* 46:   */         {
/* 47:38 */           throw new YAMLException("Could not deserialize object", ex);
/* 48:   */         }
/* 49:   */       }
/* 50:42 */       return raw;
/* 51:   */     }
/* 52:   */     
/* 53:   */     public void construct2ndStep(Node node, Object object)
/* 54:   */     {
/* 55:47 */       throw new YAMLException("Unexpected referential mapping structure. Node: " + node);
/* 56:   */     }
/* 57:   */   }
/* 58:   */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.file.YamlConstructor
 * JD-Core Version:    0.7.0.1
 */