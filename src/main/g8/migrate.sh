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

echo "Moving test files from generated-test/ to test/"
rsync -avm --include='*.scala' -f 'hide,! */' ../generated-test/ ../test/
rm -rf ../generated-test/
