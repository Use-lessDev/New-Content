# New Content (Java)

Java conversion of the **New Content** Mindustry mod.

**Original authors:** Rylid Umil & Aegires  
**Sprites:** Aegires

## Structure

```
.
├── icon.png
├── mod.hjson
├── LICENSE
├── README.md
├── build.sh
├── src/newcontent/NewContentMod.java
├── content/          # blocks, units, items, liquids, planets, sectors, status...
├── sprites/
├── sprites-override/
├── maps/
├── schematics/
├── sounds/
└── music/
```

## Java changes

Port of the original mod's Java-side behavior:

- Tech-tree nodes for iron, scorchonite, selevite, poison liquids, etc.
- Crawler weapon name change to `newunits-death-weapon`
- Light-radius fix on Mace and Oxynoe

## Build

Requires JDK 17+ and a Mindustry JAR (v154+).

```bash
MINDUSTRY_JAR=/path/to/Mindustry.jar ./build.sh
```

The script produces `New-Content-Java.jar`.

**Note:** Keep the complete `content/`, `sprites/`, `sprites-override/`, `maps/`, `schematics/`, `sounds/`, and `music/` directories alongside the Java source when building the mod.
