#!/bin/sh

for file in *.csv; do
    if [ -f "$file" ]; then
        echo "$file"
        npx @oslo-flanders/codelist-generator --input $file --output ${file%.csv}.ttl
    fi
done
