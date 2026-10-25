#!/usr/bin/env bash
set -Eeuo pipefail

ROOT_DIR="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT_DIR"

MINDUSTRY_JAR="${MINDUSTRY_JAR:-Mindustry.jar}"
OUT="New-Content-Java.jar"
CLASS_DIR="build/classes"

if ! command -v javac >/dev/null 2>&1; then
  echo "ERROR: javac was not found. Install a JDK (Java 17 or newer)." >&2
  exit 1
fi
if ! command -v jar >/dev/null 2>&1; then
  echo "ERROR: the jar tool was not found. Install a JDK (Java 17 or newer)." >&2
  exit 1
fi
if [[ ! -f "$MINDUSTRY_JAR" ]]; then
  echo "ERROR: Mindustry jar not found: $MINDUSTRY_JAR" >&2
  echo "Set MINDUSTRY_JAR to the path of the Mindustry v154+ jar." >&2
  exit 1
fi
if [[ ! -f "src/newcontent/NewContentMod.java" ]]; then
  echo "ERROR: missing src/newcontent/NewContentMod.java" >&2
  exit 1
fi

if [[ ! -d content || ! -d sprites ]]; then
  echo "WARNING: content/ and/or sprites/ are missing; the produced jar will not contain all mod resources." >&2
fi

echo "==> Compiling Java entry point..."
rm -rf build
mkdir -p "$CLASS_DIR"
javac -encoding UTF-8 -source 17 -target 17 \
  -cp "$MINDUSTRY_JAR" \
  -d "$CLASS_DIR" \
  src/newcontent/NewContentMod.java

echo "==> Packaging mod..."
rm -f "$OUT"
jar --create --file "$OUT" -C "$CLASS_DIR" .

for file in mod.hjson icon.png LICENSE README.md; do
  if [[ -f "$file" ]]; then
    jar --update --file "$OUT" "$file"
  fi
done

for dir in content sprites sprites-override maps schematics sounds music; do
  if [[ -d "$dir" ]]; then
    echo "  + $dir/"
    jar --update --file "$OUT" "$dir"
  fi
done

echo "==> Created $OUT"
ls -lh "$OUT"
