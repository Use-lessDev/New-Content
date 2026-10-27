# New Content (Java)

Java conversion of the **New Content** Mindustry mod.

**Original authors:** Rylid Umil & Aegires  
**Sprites:** Aegires

## Project layout

The mod's HJSON/JSON definitions are in `content/`; original art, maps, schematics and audio are kept in their corresponding top-level folders. Java handles runtime changes that need code.

```text
.
├── .github/workflows/import-new-content-assets.yml
├── mod.hjson
├── LICENSE
├── README.md
├── SECURITY.md
├── build.sh
├── drill-item-4.png
├── src/newcontent/NewContentMod.java
├── content/
├── sprites/
├── sprites-override/
├── maps/
├── schematics/
├── sounds/
└── music/
```

## Java behavior

- Adds extra research-tree branches after content initialization.
- Renames the first crawler weapon to `newunits-death-weapon`.
- Sets the first Mace and Oxynoe weapon light radius to zero.

The mod name in `mod.hjson` and `NewContentMod.MOD_NAME` must stay in sync.

## Build

Requires a JDK 17+ and a Mindustry v154+ JAR. Run from the project folder:

```bash
MINDUSTRY_JAR=/path/to/Mindustry.jar ./build.sh
```

This creates `New-Content-Java.jar`. The build script packages `content/`, `sprites/`, `sprites-override/`, `maps/`, `schematics/`, `sounds/` and `music/`. It warns if key content or sprite folders are missing.
