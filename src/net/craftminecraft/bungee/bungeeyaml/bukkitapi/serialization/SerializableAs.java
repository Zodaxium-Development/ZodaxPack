package net.craftminecraft.bungee.bungeeyaml.bukkitapi.serialization;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface SerializableAs
{
  String value();
}


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.serialization.SerializableAs
 * JD-Core Version:    0.7.0.1
 */