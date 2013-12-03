AUTOMAKE_OPTIONS=foreign
bin_PROGRAMS=emeralddb
emeralddb_SOURCES=\
	pmd/pmdMain.cpp pmd/pmdTest.cpp\
	bson/src/bsonobj.cpp bson/src/util/json.cpp bson/src/oid.cpp \
	bson/src/lib/base64.cpp bson/src/lib/md5.cpp bson/src/lib/nonce.cpp

emeralddb_CXXFLAGS=-I../boost -I./bson/src -I./include -D_FILE_OFFSET_BITS=64 -ggdb -Wall -O0
emeralddb_LDADD=-lpthread -lm -lboost_system -lboost_thread -lboost_program_options 
emeralddb_LDFLAGS=-fPIC -rdynamic -L../boost/stage/lib -pthread -1rt