#!/bin/bash
set -e
# Set MINDUSTRY_JAR to the path of your Mindustry desktop JAR.
MINDUSTRY_JAR="${MINDUSTRY_JAR:-../mindustry.jar}"

if [ ! -f "$MINDUSTRY_JAR" ]; then
  echo "ERROR: Mindustry.jar not found at $MINDUSTRY_JAR"
  echo "Set MINDUSTRY_JAR=/path/to/Mindustry.jar"
  exit 1
fi

echo "Compiling..."
rm -rf classes
mkdir -p classes
javac -cp "$MINDUSTRY_JAR" -d classes src/newcontent/NewContentMod.java

echo "Packaging..."
OUT=New-Content-Java.jar
rm -f "$OUT"
jar cf "$OUT" -C classes .
jar uf "$OUT" mod.hjson icon.png README.md

for dir in content sprites sprites-override maps schematics sounds music; do
  if [ -d "$dir" ]; then
    echo "  + $dir/"
    jar uf "$OUT" "$dir"
  else
    echo "  ! missing $dir/ (skipping)"
  fi
done

echo "Done -> $OUT"
ls -lh "$OUT"
