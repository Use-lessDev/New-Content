# New Content (Java)

Java conversion of the **New Content** Mindustry mod.

**Original authors:** Rylid Umil & Aegires  
**Sprites:** Aegires

## Project layout

The mod's data-driven definitions remain HJSON/JSON files in `content/`; art and other resources are kept beside them in the top-level resource directories. The Java entry point only handles runtime changes that need code.

```text
.
├── icon.png
├── mod.hjson
├── LICENSE
├── README.md
├── build.sh
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

- Adds the extra research-tree branches after all content is initialized.
- Renames the first crawler weapon to `newunits-death-weapon`.
- Sets the first Mace and Oxynoe weapon light radius to zero.

The mod name in `mod.hjson` and `NewContentMod.MOD_NAME` must stay in sync.

## Build

Requires a JDK 17+ and a Mindustry v154+ JAR. Run from the project folder:

```bash
MINDUSTRY_JAR=/path/to/Mindustry.jar ./build.sh
```

This creates `New-Content-Java.jar`. The script packages any available resource folders, and warns when `content/` or `sprites/` is missing so an incomplete archive is not mistaken for a full build.
