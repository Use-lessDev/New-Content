#!/bin/bash
set -e
MINDUSTRY_JAR="${MINDUSTRY_JAR:-Mindustry.jar}"
if [ ! -f "$MINDUSTRY_JAR" ]; then
  echo "Set MINDUSTRY_JAR=/path/to/Mindustry.jar"
  exit 1
fi
echo "==> Compiling..."
rm -rf classes && mkdir -p classes
javac -cp "$MINDUSTRY_JAR" -d classes src/newcontent/NewContentMod.java
echo "==> Packaging..."
OUT=New-Content-Java.jar
rm -f "$OUT"
jar cf "$OUT" -C classes .
jar uf "$OUT" mod.hjson icon.png LICENSE README.md
for d in content sprites sprites-override maps schematics sounds music; do
  [ -d "$d" ] && echo "  + $d/" && jar uf "$OUT" "$d"
done
echo "==> Done"; ls -lh "$OUT"
