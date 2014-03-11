# pull all repot

FOLDERS=`ls -d vendor/*/`

for FILE in $FOLDERS
do
	echo "Process $FILE bundle"
	echo "================================================================"
	cd $FILE
	ant buildall
	cd ../..
done
