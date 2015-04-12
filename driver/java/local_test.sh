SOURCE_DIR=./
BIN_DIR=./bin
find . -name "*.class" |xargs rm -rf
javac `find $SOURCE_DIR -name "*.java" -print` -d $BIN_DIR
