/*   1:    */ package net.craftminecraft.bungee.bungeeyaml.bukkitapi;
/*   2:    */ 
/*   3:    */ /*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collections;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.LinkedHashMap;
/*   9:    */ import java.util.LinkedHashSet;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  13:    */ import java.util.Set;

import com.google.common.base.Joiner;
/*   4:    */ import com.google.common.base.Preconditions;
/*  12:    */ 
/*  14:    */ 
/*  15:    */ public class MemorySection
/*  16:    */   implements ConfigurationSection
/*  17:    */ {
/*  18: 21 */   protected final Map<String, Object> map = new LinkedHashMap<String, Object>();
/*  19:    */   private final Configuration root;
/*  20:    */   private final ConfigurationSection parent;
/*  21:    */   private final String path;
/*  22:    */   private final String fullPath;
/*  23: 27 */   private final Map<String, String> comments = new HashMap<String, String>();
/*  24:    */   
/*  25:    */   protected MemorySection()
/*  26:    */   {
/*  27: 38 */     if (!(this instanceof Configuration)) {
/*  28: 39 */       throw new IllegalStateException("Cannot construct a root MemorySection when not a Configuration");
/*  29:    */     }
/*  30: 42 */     this.path = "";
/*  31: 43 */     this.fullPath = "";
/*  32: 44 */     this.parent = null;
/*  33: 45 */     this.root = ((Configuration)this);
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected MemorySection(ConfigurationSection parent, String path)
/*  37:    */   {
/*  38: 56 */     Preconditions.checkNotNull(parent, "Parent cannot be null");
/*  39: 57 */     Preconditions.checkNotNull(path, "Path cannot be null");
/*  40:    */     
/*  41: 59 */     this.path = path;
/*  42: 60 */     this.parent = parent;
/*  43: 61 */     this.root = parent.getRoot();
/*  44:    */     
/*  45: 63 */     Preconditions.checkNotNull(this.root, "Path cannot be orphaned");
/*  46:    */     
/*  47: 65 */     this.fullPath = createPath(parent, path);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public Set<String> getKeys(boolean deep)
/*  51:    */   {
/*  52: 69 */     Set<String> result = new LinkedHashSet<String>();
/*  53:    */     
/*  54: 71 */     Configuration root = getRoot();
/*  55: 72 */     if ((root != null) && (root.options().copyDefaults()))
/*  56:    */     {
/*  57: 73 */       ConfigurationSection defaults = getDefaultSection();
/*  58: 75 */       if (defaults != null) {
/*  59: 76 */         result.addAll(defaults.getKeys(deep));
/*  60:    */       }
/*  61:    */     }
/*  62: 80 */     mapChildrenKeys(result, this, deep);
/*  63:    */     
/*  64: 82 */     return result;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Map<String, Object> getValues(boolean deep)
/*  68:    */   {
/*  69: 86 */     Map<String, Object> result = new LinkedHashMap<String, Object>();
/*  70:    */     
/*  71: 88 */     Configuration root = getRoot();
/*  72: 89 */     if ((root != null) && (root.options().copyDefaults()))
/*  73:    */     {
/*  74: 90 */       ConfigurationSection defaults = getDefaultSection();
/*  75: 92 */       if (defaults != null) {
/*  76: 93 */         result.putAll(defaults.getValues(deep));
/*  77:    */       }
/*  78:    */     }
/*  79: 97 */     mapChildrenValues(result, this, deep);
/*  80:    */     
/*  81: 99 */     return result;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public boolean contains(String path)
/*  85:    */   {
/*  86:103 */     return get(path) != null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isSet(String path)
/*  90:    */   {
/*  91:107 */     Configuration root = getRoot();
/*  92:108 */     if (root == null) {
/*  93:109 */       return false;
/*  94:    */     }
/*  95:111 */     if (root.options().copyDefaults()) {
/*  96:112 */       return contains(path);
/*  97:    */     }
/*  98:114 */     return get(path, null) != null;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getCurrentPath()
/* 102:    */   {
/* 103:118 */     return this.fullPath;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getName()
/* 107:    */   {
/* 108:122 */     return this.path;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Configuration getRoot()
/* 112:    */   {
/* 113:126 */     return this.root;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public ConfigurationSection getParent()
/* 117:    */   {
/* 118:130 */     return this.parent;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void addDefault(String path, Object value)
/* 122:    */   {
/* 123:134 */     Preconditions.checkNotNull(path, "Path cannot be null");
/* 124:    */     
/* 125:136 */     Configuration root = getRoot();
/* 126:137 */     if (root == null) {
/* 127:138 */       throw new IllegalStateException("Cannot add default without root");
/* 128:    */     }
/* 129:140 */     if (root == this) {
/* 130:141 */       throw new UnsupportedOperationException("Unsupported addDefault(String, Object) implementation");
/* 131:    */     }
/* 132:143 */     root.addDefault(createPath(this, path), value);
/* 133:    */   }
/* 134:    */   
/* 135:    */   public ConfigurationSection getDefaultSection()
/* 136:    */   {
/* 137:147 */     Configuration root = getRoot();
/* 138:148 */     Configuration defaults = root == null ? null : root.getDefaults();
/* 139:150 */     if ((defaults != null) && 
/* 140:151 */       (defaults.isConfigurationSection(getCurrentPath()))) {
/* 141:152 */       return defaults.getConfigurationSection(getCurrentPath());
/* 142:    */     }
/* 143:156 */     return null;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void set(String path, Object value)
/* 147:    */   {
/* 148:160 */     Preconditions.checkArgument(!path.isEmpty(), "Cannot set to an empty path");
/* 149:    */     
/* 150:162 */     Configuration root = getRoot();
/* 151:163 */     if (root == null) {
/* 152:164 */       throw new IllegalStateException("Cannot use section without a root");
/* 153:    */     }
/* 154:167 */     char separator = root.options().pathSeparator();
/* 155:    */     
/* 156:    */ 
/* 157:170 */     int i1 = -1;
/* 158:171 */     ConfigurationSection section = this;
/* 159:    */     int i2;
/* 160:172 */     while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1)
/* 161:    */     {
/* 162:173 */       String node = path.substring(i2, i1);
/* 163:174 */       ConfigurationSection subSection = section.getConfigurationSection(node);
/* 164:175 */       if (subSection == null) {
/* 165:176 */         section = section.createSection(node);
/* 166:    */       } else {
/* 167:178 */         section = subSection;
/* 168:    */       }
/* 169:    */     }
/* 170:182 */     String key = path.substring(i2);
/* 171:183 */     if (section == this)
/* 172:    */     {
/* 173:184 */       if (value == null) {
/* 174:185 */         this.map.remove(key);
/* 175:    */       } else {
/* 176:187 */         this.map.put(key, value);
/* 177:    */       }
/* 178:    */     }
/* 179:    */     else {
/* 180:190 */       section.set(key, value);
/* 181:    */     }
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Object get(String path)
/* 185:    */   {
/* 186:195 */     return get(path, getDefault(path));
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Object get(String path, Object def)
/* 190:    */   {
/* 191:199 */     Preconditions.checkNotNull(path, "Path cannot be null");
/* 192:201 */     if (path.length() == 0) {
/* 193:202 */       return this;
/* 194:    */     }
/* 195:205 */     Configuration root = getRoot();
/* 196:206 */     if (root == null) {
/* 197:207 */       throw new IllegalStateException("Cannot access section without a root");
/* 198:    */     }
/* 199:210 */     char separator = root.options().pathSeparator();
/* 200:    */     
/* 201:    */ 
/* 202:213 */     int i1 = -1;
/* 203:214 */     ConfigurationSection section = this;
/* 204:    */     int i2;
/* 205:215 */     while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1)
/* 206:    */     {
/* 207:216 */       section = section.getConfigurationSection(path.substring(i2, i1));
/* 208:217 */       if (section == null) {
/* 209:218 */         return def;
/* 210:    */       }
/* 211:    */     }
/* 212:222 */     String key = path.substring(i2);
/* 213:223 */     if (section == this)
/* 214:    */     {
/* 215:224 */       Object result = this.map.get(key);
/* 216:225 */       return result == null ? def : result;
/* 217:    */     }
/* 218:227 */     return section.get(key, def);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public ConfigurationSection createSection(String path)
/* 222:    */   {
/* 223:231 */     Preconditions.checkArgument(!path.isEmpty(), "Cannot create section at empty path");
/* 224:232 */     Configuration root = getRoot();
/* 225:233 */     if (root == null) {
/* 226:234 */       throw new IllegalStateException("Cannot create section without a root");
/* 227:    */     }
/* 228:237 */     char separator = root.options().pathSeparator();
/* 229:    */     
/* 230:    */ 
/* 231:240 */     int i1 = -1;
/* 232:241 */     ConfigurationSection section = this;
/* 233:    */     int i2;
/* 234:242 */     while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1)
/* 235:    */     {
/* 236:243 */       String node = path.substring(i2, i1);
/* 237:244 */       ConfigurationSection subSection = section.getConfigurationSection(node);
/* 238:245 */       if (subSection == null) {
/* 239:246 */         section = section.createSection(node);
/* 240:    */       } else {
/* 241:248 */         section = subSection;
/* 242:    */       }
/* 243:    */     }
/* 244:252 */     String key = path.substring(i2);
/* 245:253 */     if (section == this)
/* 246:    */     {
/* 247:254 */       ConfigurationSection result = new MemorySection(this, key);
/* 248:255 */       this.map.put(key, result);
/* 249:256 */       return result;
/* 250:    */     }
/* 251:258 */     return section.createSection(key);
/* 252:    */   }
/* 253:    */   
/* 254:    */   public ConfigurationSection createSection(String path, Map<?, ?> map)
/* 255:    */   {
/* 256:262 */     ConfigurationSection section = createSection(path);
/* 257:264 */     for (Map.Entry<?, ?> entry : map.entrySet()) {
/* 258:265 */       if ((entry.getValue() instanceof Map)) {
/* 259:266 */         section.createSection(entry.getKey().toString(), (Map<?, ?>)entry.getValue());
/* 260:    */       } else {
/* 261:268 */         section.set(entry.getKey().toString(), entry.getValue());
/* 262:    */       }
/* 263:    */     }
/* 264:272 */     return section;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public String getString(String path)
/* 268:    */   {
/* 269:277 */     Object def = getDefault(path);
/* 270:278 */     return getString(path, def != null ? def.toString() : null);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public String getString(String path, String def)
/* 274:    */   {
/* 275:282 */     Object val = get(path, def);
/* 276:283 */     return val != null ? val.toString() : def;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public boolean isString(String path)
/* 280:    */   {
/* 281:287 */     Object val = get(path);
/* 282:288 */     return val instanceof String;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public int getInt(String path)
/* 286:    */   {
/* 287:292 */     Object def = getDefault(path);
/* 288:293 */     return getInt(path, (def instanceof Number) ? NumberConversions.toInt(def) : 0);
/* 289:    */   }
/* 290:    */   
/* 291:    */   public int getInt(String path, int def)
/* 292:    */   {
/* 293:297 */     Object val = get(path, Integer.valueOf(def));
/* 294:298 */     return (val instanceof Number) ? NumberConversions.toInt(val) : def;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public boolean isInt(String path)
/* 298:    */   {
/* 299:302 */     Object val = get(path);
/* 300:303 */     return val instanceof Integer;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean getBoolean(String path)
/* 304:    */   {
/* 305:307 */     Object def = getDefault(path);
/* 306:308 */     return getBoolean(path, (def instanceof Boolean) ? ((Boolean)def).booleanValue() : false);
/* 307:    */   }
/* 308:    */   
/* 309:    */   public boolean getBoolean(String path, boolean def)
/* 310:    */   {
/* 311:312 */     Object val = get(path, Boolean.valueOf(def));
/* 312:313 */     return (val instanceof Boolean) ? ((Boolean)val).booleanValue() : def;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public boolean isBoolean(String path)
/* 316:    */   {
/* 317:317 */     Object val = get(path);
/* 318:318 */     return val instanceof Boolean;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public double getDouble(String path)
/* 322:    */   {
/* 323:322 */     Object def = getDefault(path);
/* 324:323 */     return getDouble(path, (def instanceof Number) ? NumberConversions.toDouble(def) : 0.0D);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public double getDouble(String path, double def)
/* 328:    */   {
/* 329:327 */     Object val = get(path, Double.valueOf(def));
/* 330:328 */     return (val instanceof Number) ? NumberConversions.toDouble(val) : def;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public boolean isDouble(String path)
/* 334:    */   {
/* 335:332 */     Object val = get(path);
/* 336:333 */     return val instanceof Double;
/* 337:    */   }
/* 338:    */   
/* 339:    */   public long getLong(String path)
/* 340:    */   {
/* 341:337 */     Object def = getDefault(path);
/* 342:338 */     return getLong(path, (def instanceof Number) ? NumberConversions.toLong(def) : 0L);
/* 343:    */   }
/* 344:    */   
/* 345:    */   public long getLong(String path, long def)
/* 346:    */   {
/* 347:342 */     Object val = get(path, Long.valueOf(def));
/* 348:343 */     return (val instanceof Number) ? NumberConversions.toLong(val) : def;
/* 349:    */   }
/* 350:    */   
/* 351:    */   public boolean isLong(String path)
/* 352:    */   {
/* 353:347 */     Object val = get(path);
/* 354:348 */     return val instanceof Long;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public List<?> getList(String path)
/* 358:    */   {
/* 359:353 */     Object def = getDefault(path);
/* 360:354 */     return getList(path, (def instanceof List) ? (List<?>)def : null);
/* 361:    */   }
/* 362:    */   
/* 363:    */   public List<?> getList(String path, List<?> def)
/* 364:    */   {
/* 365:358 */     Object val = get(path, def);
/* 366:359 */     return (List<?>)((val instanceof List) ? val : def);
/* 367:    */   }
/* 368:    */   
/* 369:    */   public boolean isList(String path)
/* 370:    */   {
/* 371:363 */     Object val = get(path);
/* 372:364 */     return val instanceof List;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public List<String> getStringList(String path)
/* 376:    */   {
/* 377:368 */     List<?> list = getList(path);
/* 378:370 */     if (list == null) {
/* 379:371 */       return new ArrayList<String>(0);
/* 380:    */     }
/* 381:374 */     List<String> result = new ArrayList<String>();
/* 382:376 */     for (Object object : list) {
/* 383:377 */       if (((object instanceof String)) || (isPrimitiveWrapper(object))) {
/* 384:378 */         result.add(String.valueOf(object));
/* 385:    */       }
/* 386:    */     }
/* 387:382 */     return result;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public List<Integer> getIntegerList(String path)
/* 391:    */   {
/* 392:386 */     List<?> list = getList(path);
/* 393:388 */     if (list == null) {
/* 394:389 */       return new ArrayList<Integer>(0);
/* 395:    */     }
/* 396:392 */     List<Integer> result = new ArrayList<Integer>();
/* 397:394 */     for (Object object : list) {
/* 398:395 */       if ((object instanceof Integer)) {
/* 399:396 */         result.add((Integer)object);
/* 400:397 */       } else if ((object instanceof String)) {
/* 401:    */         try
/* 402:    */         {
/* 403:399 */           result.add(Integer.valueOf((String)object));
/* 404:    */         }
/* 405:    */         catch (Exception ex) {}
/* 406:402 */       } else if ((object instanceof Character)) {
/* 407:403 */         result.add(Integer.valueOf(((Character)object).charValue()));
/* 408:404 */       } else if ((object instanceof Number)) {
/* 409:405 */         result.add(Integer.valueOf(((Number)object).intValue()));
/* 410:    */       }
/* 411:    */     }
/* 412:409 */     return result;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public List<Boolean> getBooleanList(String path)
/* 416:    */   {
/* 417:413 */     List<?> list = getList(path);
/* 418:415 */     if (list == null) {
/* 419:416 */       return new ArrayList<Boolean>(0);
/* 420:    */     }
/* 421:419 */     List<Boolean> result = new ArrayList<Boolean>();
/* 422:421 */     for (Object object : list) {
/* 423:422 */       if ((object instanceof Boolean)) {
/* 424:423 */         result.add((Boolean)object);
/* 425:424 */       } else if ((object instanceof String)) {
/* 426:425 */         if (Boolean.TRUE.toString().equals(object)) {
/* 427:426 */           result.add(Boolean.valueOf(true));
/* 428:427 */         } else if (Boolean.FALSE.toString().equals(object)) {
/* 429:428 */           result.add(Boolean.valueOf(false));
/* 430:    */         }
/* 431:    */       }
/* 432:    */     }
/* 433:433 */     return result;
/* 434:    */   }
/* 435:    */   
/* 436:    */   public List<Double> getDoubleList(String path)
/* 437:    */   {
/* 438:437 */     List<?> list = getList(path);
/* 439:439 */     if (list == null) {
/* 440:440 */       return new ArrayList<Double>(0);
/* 441:    */     }
/* 442:443 */     List<Double> result = new ArrayList<Double>();
/* 443:445 */     for (Object object : list) {
/* 444:446 */       if ((object instanceof Double)) {
/* 445:447 */         result.add((Double)object);
/* 446:448 */       } else if ((object instanceof String)) {
/* 447:    */         try
/* 448:    */         {
/* 449:450 */           result.add(Double.valueOf((String)object));
/* 450:    */         }
/* 451:    */         catch (Exception ex) {}
/* 452:453 */       } else if ((object instanceof Character)) {
/* 453:454 */         result.add(Double.valueOf(((Character)object).charValue()));
/* 454:455 */       } else if ((object instanceof Number)) {
/* 455:456 */         result.add(Double.valueOf(((Number)object).doubleValue()));
/* 456:    */       }
/* 457:    */     }
/* 458:460 */     return result;
/* 459:    */   }
/* 460:    */   
/* 461:    */   public List<Float> getFloatList(String path)
/* 462:    */   {
/* 463:464 */     List<?> list = getList(path);
/* 464:466 */     if (list == null) {
/* 465:467 */       return new ArrayList<Float>(0);
/* 466:    */     }
/* 467:470 */     List<Float> result = new ArrayList<Float>();
/* 468:472 */     for (Object object : list) {
/* 469:473 */       if ((object instanceof Float)) {
/* 470:474 */         result.add((Float)object);
/* 471:475 */       } else if ((object instanceof String)) {
/* 472:    */         try
/* 473:    */         {
/* 474:477 */           result.add(Float.valueOf((String)object));
/* 475:    */         }
/* 476:    */         catch (Exception ex) {}
/* 477:480 */       } else if ((object instanceof Character)) {
/* 478:481 */         result.add(Float.valueOf(((Character)object).charValue()));
/* 479:482 */       } else if ((object instanceof Number)) {
/* 480:483 */         result.add(Float.valueOf(((Number)object).floatValue()));
/* 481:    */       }
/* 482:    */     }
/* 483:487 */     return result;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public List<Long> getLongList(String path)
/* 487:    */   {
/* 488:491 */     List<?> list = getList(path);
/* 489:493 */     if (list == null) {
/* 490:494 */       return new ArrayList<Long>(0);
/* 491:    */     }
/* 492:497 */     List<Long> result = new ArrayList<Long>();
/* 493:499 */     for (Object object : list) {
/* 494:500 */       if ((object instanceof Long)) {
/* 495:501 */         result.add((Long)object);
/* 496:502 */       } else if ((object instanceof String)) {
/* 497:    */         try
/* 498:    */         {
/* 499:504 */           result.add(Long.valueOf((String)object));
/* 500:    */         }
/* 501:    */         catch (Exception ex) {}
/* 502:507 */       } else if ((object instanceof Character)) {
/* 503:508 */         result.add(Long.valueOf(((Character)object).charValue()));
/* 504:509 */       } else if ((object instanceof Number)) {
/* 505:510 */         result.add(Long.valueOf(((Number)object).longValue()));
/* 506:    */       }
/* 507:    */     }
/* 508:514 */     return result;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public List<Byte> getByteList(String path)
/* 512:    */   {
/* 513:518 */     List<?> list = getList(path);
/* 514:520 */     if (list == null) {
/* 515:521 */       return new ArrayList<Byte>(0);
/* 516:    */     }
/* 517:524 */     List<Byte> result = new ArrayList<Byte>();
/* 518:526 */     for (Object object : list) {
/* 519:527 */       if ((object instanceof Byte)) {
/* 520:528 */         result.add((Byte)object);
/* 521:529 */       } else if ((object instanceof String)) {
/* 522:    */         try
/* 523:    */         {
/* 524:531 */           result.add(Byte.valueOf((String)object));
/* 525:    */         }
/* 526:    */         catch (Exception ex) {}
/* 527:534 */       } else if ((object instanceof Character)) {
/* 528:535 */         result.add(Byte.valueOf((byte)((Character)object).charValue()));
/* 529:536 */       } else if ((object instanceof Number)) {
/* 530:537 */         result.add(Byte.valueOf(((Number)object).byteValue()));
/* 531:    */       }
/* 532:    */     }
/* 533:541 */     return result;
/* 534:    */   }
/* 535:    */   
/* 536:    */   public List<Character> getCharacterList(String path)
/* 537:    */   {
/* 538:545 */     List<?> list = getList(path);
/* 539:547 */     if (list == null) {
/* 540:548 */       return new ArrayList<Character>(0);
/* 541:    */     }
/* 542:551 */     List<Character> result = new ArrayList<Character>();
/* 543:553 */     for (Object object : list) {
/* 544:554 */       if ((object instanceof Character))
/* 545:    */       {
/* 546:555 */         result.add((Character)object);
/* 547:    */       }
/* 548:556 */       else if ((object instanceof String))
/* 549:    */       {
/* 550:557 */         String str = (String)object;
/* 551:559 */         if (str.length() == 1) {
/* 552:560 */           result.add(Character.valueOf(str.charAt(0)));
/* 553:    */         }
/* 554:    */       }
/* 555:562 */       else if ((object instanceof Number))
/* 556:    */       {
/* 557:563 */         result.add(Character.valueOf((char)((Number)object).intValue()));
/* 558:    */       }
/* 559:    */     }
/* 560:567 */     return result;
/* 561:    */   }
/* 562:    */   
/* 563:    */   public List<Short> getShortList(String path)
/* 564:    */   {
/* 565:571 */     List<?> list = getList(path);
/* 566:573 */     if (list == null) {
/* 567:574 */       return new ArrayList<Short>(0);
/* 568:    */     }
/* 569:577 */     List<Short> result = new ArrayList<Short>();
/* 570:579 */     for (Object object : list) {
/* 571:580 */       if ((object instanceof Short)) {
/* 572:581 */         result.add((Short)object);
/* 573:582 */       } else if ((object instanceof String)) {
/* 574:    */         try
/* 575:    */         {
/* 576:584 */           result.add(Short.valueOf((String)object));
/* 577:    */         }
/* 578:    */         catch (Exception ex) {}
/* 579:587 */       } else if ((object instanceof Character)) {
/* 580:588 */         result.add(Short.valueOf((short)((Character)object).charValue()));
/* 581:589 */       } else if ((object instanceof Number)) {
/* 582:590 */         result.add(Short.valueOf(((Number)object).shortValue()));
/* 583:    */       }
/* 584:    */     }
/* 585:594 */     return result;
/* 586:    */   }
/* 587:    */   
/* 588:    */   public List<Map<?, ?>> getMapList(String path)
/* 589:    */   {
/* 590:598 */     List<?> list = getList(path);
/* 591:599 */     List<Map<?, ?>> result = new ArrayList<Map<?, ?>>();
/* 592:601 */     if (list == null) {
/* 593:602 */       return result;
/* 594:    */     }
/* 595:605 */     for (Object object : list) {
/* 596:606 */       if ((object instanceof Map)) {
/* 597:607 */         result.add((Map<?, ?>)object);
/* 598:    */       }
/* 599:    */     }
/* 600:611 */     return result;
/* 601:    */   }
/* 602:    */   
/* 603:    */   public ConfigurationSection getConfigurationSection(String path)
/* 604:    */   {
/* 605:615 */     Object val = get(path, null);
/* 606:616 */     if (val != null) {
/* 607:617 */       return (val instanceof ConfigurationSection) ? (ConfigurationSection)val : null;
/* 608:    */     }
/* 609:620 */     val = get(path, getDefault(path));
/* 610:621 */     return (val instanceof ConfigurationSection) ? createSection(path) : null;
/* 611:    */   }
/* 612:    */   
/* 613:    */   public boolean isConfigurationSection(String path)
/* 614:    */   {
/* 615:625 */     Object val = get(path);
/* 616:626 */     return val instanceof ConfigurationSection;
/* 617:    */   }
/* 618:    */   
/* 619:    */   protected boolean isPrimitiveWrapper(Object input)
/* 620:    */   {
/* 621:630 */     return ((input instanceof Integer)) || ((input instanceof Boolean)) || ((input instanceof Character)) || ((input instanceof Byte)) || ((input instanceof Short)) || ((input instanceof Double)) || ((input instanceof Long)) || ((input instanceof Float));
/* 622:    */   }
/* 623:    */   
/* 624:    */   protected Object getDefault(String path)
/* 625:    */   {
/* 626:637 */     Preconditions.checkNotNull(path, "Path cannot be null");
/* 627:    */     
/* 628:639 */     Configuration root = getRoot();
/* 629:640 */     Configuration defaults = root == null ? null : root.getDefaults();
/* 630:641 */     return defaults == null ? null : defaults.get(createPath(this, path));
/* 631:    */   }
/* 632:    */   
/* 633:    */   protected void mapChildrenKeys(Set<String> output, ConfigurationSection section, boolean deep)
/* 634:    */   {
/* 635:645 */     if ((section instanceof MemorySection))
/* 636:    */     {
/* 637:646 */       MemorySection sec = (MemorySection)section;
/* 638:648 */       for (Map.Entry<String, Object> entry : sec.map.entrySet())
/* 639:    */       {
/* 640:649 */         output.add(createPath(section, (String)entry.getKey(), this));
/* 641:651 */         if ((deep) && ((entry.getValue() instanceof ConfigurationSection)))
/* 642:    */         {
/* 643:652 */           ConfigurationSection subsection = (ConfigurationSection)entry.getValue();
/* 644:653 */           mapChildrenKeys(output, subsection, deep);
/* 645:    */         }
/* 646:    */       }
/* 647:    */     }
/* 648:    */     else
/* 649:    */     {
/* 650:657 */       Set<String> keys = section.getKeys(deep);
/* 651:659 */       for (String key : keys) {
/* 652:660 */         output.add(createPath(section, key, this));
/* 653:    */       }
/* 654:    */     }
/* 655:    */   }
/* 656:    */   
/* 657:    */   protected void mapChildrenValues(Map<String, Object> output, ConfigurationSection section, boolean deep)
/* 658:    */   {
/* 659:666 */     if ((section instanceof MemorySection))
/* 660:    */     {
/* 661:667 */       MemorySection sec = (MemorySection)section;
/* 662:669 */       for (Map.Entry<String, Object> entry : sec.map.entrySet())
/* 663:    */       {
/* 664:670 */         output.put(createPath(section, (String)entry.getKey(), this), entry.getValue());
/* 665:672 */         if (((entry.getValue() instanceof ConfigurationSection)) && 
/* 666:673 */           (deep)) {
/* 667:674 */           mapChildrenValues(output, (ConfigurationSection)entry.getValue(), deep);
/* 668:    */         }
/* 669:    */       }
/* 670:    */     }
/* 671:    */     else
/* 672:    */     {
/* 673:679 */       Map<String, Object> values = section.getValues(deep);
/* 674:681 */       for (Map.Entry<String, Object> entry : values.entrySet()) {
/* 675:682 */         output.put(createPath(section, (String)entry.getKey(), this), entry.getValue());
/* 676:    */       }
/* 677:    */     }
/* 678:    */   }
/* 679:    */   
/* 680:    */   public static String createPath(ConfigurationSection section, String key)
/* 681:    */   {
/* 682:697 */     return createPath(section, key, section == null ? null : section.getRoot());
/* 683:    */   }
/* 684:    */   
/* 685:    */   public static String createPath(ConfigurationSection section, String key, ConfigurationSection relativeTo)
/* 686:    */   {
/* 687:711 */     Preconditions.checkNotNull(section, "Cannot create path without a section");
/* 688:712 */     Configuration root = section.getRoot();
/* 689:713 */     if (root == null) {
/* 690:714 */       throw new IllegalStateException("Cannot create path without a root");
/* 691:    */     }
/* 692:716 */     char separator = root.options().pathSeparator();
/* 693:    */     
/* 694:718 */     StringBuilder builder = new StringBuilder();
/* 695:719 */     if (section != null) {
/* 696:720 */       for (ConfigurationSection parent = section; (parent != null) && (parent != relativeTo); parent = parent.getParent())
/* 697:    */       {
/* 698:721 */         if (builder.length() > 0) {
/* 699:722 */           builder.insert(0, separator);
/* 700:    */         }
/* 701:725 */         builder.insert(0, parent.getName());
/* 702:    */       }
/* 703:    */     }
/* 704:729 */     if ((key != null) && (key.length() > 0))
/* 705:    */     {
/* 706:730 */       if (builder.length() > 0) {
/* 707:731 */         builder.append(separator);
/* 708:    */       }
/* 709:734 */       builder.append(key);
/* 710:    */     }
/* 711:737 */     return builder.toString();
/* 712:    */   }
/* 713:    */   
/* 714:    */   public String toString()
/* 715:    */   {
/* 716:742 */     Configuration root = getRoot();
/* 717:743 */     return getClass().getSimpleName() + "[path='" + getCurrentPath() + "', root='" + (root == null ? null : root.getClass().getSimpleName()) + "']";
/* 718:    */   }
/* 719:    */   
/* 720:    */   public String getComment(String path)
/* 721:    */   {
/* 722:760 */     Preconditions.checkNotNull(path, "Path cannot be null");
/* 723:    */     
/* 724:762 */     Configuration root = getRoot();
/* 725:763 */     if (root == null) {
/* 726:764 */       throw new IllegalStateException("Cannot access section without a root");
/* 727:    */     }
/* 728:768 */     if (path.length() == 0) {
/* 729:769 */       return getParent() == null ? null : getParent().getComment(getName());
/* 730:    */     }
/* 731:773 */     char separator = root.options().pathSeparator();
/* 732:    */     
/* 733:    */ 
/* 734:776 */     int i1 = -1;
/* 735:777 */     ConfigurationSection section = this;
/* 736:    */     int i2;
/* 737:778 */     while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1)
/* 738:    */     {
/* 739:779 */       section = section.getConfigurationSection(path.substring(i2, i1));
/* 740:780 */       if (section == null) {
/* 741:781 */         return null;
/* 742:    */       }
/* 743:    */     }
/* 744:785 */     String key = path.substring(i2);
/* 745:786 */     if (section == this) {
/* 746:787 */       return (String)this.comments.get(key);
/* 747:    */     }
/* 748:789 */     return section.getComment(key);
/* 749:    */   }
/* 750:    */   
/* 751:    */   public void setComment(String path, String... comment)
/* 752:    */   {
/* 753:800 */     Preconditions.checkArgument(!path.isEmpty(), "Cannot set to an empty path");
/* 754:    */     
/* 755:802 */     Configuration root = getRoot();
/* 756:803 */     if (root == null) {
/* 757:804 */       throw new IllegalStateException("Cannot use section without a root");
/* 758:    */     }
/* 759:807 */     char separator = root.options().pathSeparator();
/* 760:    */     
/* 761:    */ 
/* 762:810 */     int i1 = -1;
/* 763:811 */     ConfigurationSection section = this;
/* 764:    */     int i2;
/* 765:812 */     while ((i1 = path.indexOf(separator, i2 = i1 + 1)) != -1)
/* 766:    */     {
/* 767:813 */       String node = path.substring(i2, i1);
/* 768:814 */       ConfigurationSection subSection = section.getConfigurationSection(node);
/* 769:815 */       if (subSection == null) {
/* 770:816 */         section = section.createSection(node);
/* 771:    */       } else {
/* 772:818 */         section = subSection;
/* 773:    */       }
/* 774:    */     }
/* 775:822 */     String key = path.substring(i2);
/* 776:823 */     if (section == this)
/* 777:    */     {
/* 778:824 */       if ((comment != null) && (comment.length > 0))
/* 779:    */       {
/* 780:825 */         String s = Joiner.on('\n').join(comment);
/* 781:826 */         this.comments.put(key, s);
/* 782:    */       }
/* 783:    */       else
/* 784:    */       {
/* 785:828 */         this.comments.remove(key);
/* 786:    */       }
/* 787:    */     }
/* 788:    */     else {
/* 789:831 */       section.setComment(key, comment);
/* 790:    */     }
/* 791:    */   }
/* 792:    */   
/* 793:    */   public Map<String, String> getComments(boolean deep)
/* 794:    */   {
/* 795:842 */     return Collections.unmodifiableMap(this.comments);
/* 796:    */   }
/* 797:    */   
/* 798:    */   public void setComments(Map<String, String> comments)
/* 799:    */   {
/* 800:851 */     this.comments.clear();
/* 801:852 */     if (comments != null) {
/* 802:853 */       this.comments.putAll(comments);
/* 803:    */     }
/* 804:    */   }
/* 805:    */ }


/* Location:           C:\Users\Dylan\Desktop\BungeeYAML.jar
 * Qualified Name:     net.craftminecraft.bungee.bungeeyaml.bukkitapi.MemorySection
 * JD-Core Version:    0.7.0.1
 */