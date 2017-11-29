#!/bin/bash

echo "Applying migrations..."
cd migrations
for file in *.sh
do
    echo "Applying migration \$file"
    chmod u+x \$file
    /bin/bash \$file
    mv \$file ./applied_migrations
done

