AUTOMAKE_OPTIONS=foreign
bin_PROGRAMS=stonedb edb 
stonedb_SOURCES=\
	pmd/pmdMain.cpp pmd/pmdTcpListener.cpp pmd/pmdOptions.cpp\
	pmd/pmd.cpp pmd/pmdEDU.cpp	pmd/pmdEDUMgr.cpp pmd/pmdAgent.cpp	\
	bson/src/bsonobj.cpp bson/src/util/json.cpp bson/src/oid.cpp \
	bson/src/lib/base64.cpp bson/src/lib/md5.cpp bson/src/lib/nonce.cpp \
	oss/ossSocket.cpp oss/ossPrimitiveFileOp.cpp  					\
	oss/ossHash.cpp	mon/monCB.cpp											\
	pd/pd.cpp msg/msg.cpp	dms/dms.cpp rtn/rtn.cpp	ixm/ixmBucket.cpp							\
	oss/ossMmapFile.cpp 

edb_SOURCES=\
	client/edb.cpp client/command.cpp client/commandFactory.cpp \
	bson/src/bsonobj.cpp bson/src/util/json.cpp bson/src/oid.cpp \
	bson/src/lib/base64.cpp bson/src/lib/md5.cpp bson/src/lib/nonce.cpp \
	oss/ossSocket.cpp oss/ossPrimitiveFileOp.cpp 						\
	pd/pd.cpp msg/msg.cpp






stonedb_CXXFLAGS=-I../boost -I./bson/src -I./include -D_FILE_OFFSET_BITS=64 -g -Wall -O0
stonedb_LDADD=-lpthread -lm -lboost_system -lboost_thread -lboost_program_options 
stonedb_LDFLAGS=-fPIC -rdynamic -L../boost/stage/lib -pthread 
##-lboost_thread
edb_CXXFLAGS=-I../boost -I./bson/src -I./include -D_FILE_OFFSET_BITS=64 -g -Wall -O0
edb_LDADD=-lm -lboost_system -lboost_thread  
edb_LDFLAGS=-fPIC -rdynamic -L../boost/stage/lib 


