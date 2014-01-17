#include "core.hpp"
#include "ossMmapFile.hpp"
#include "ossPrimitiveFileOp.hpp"

int main( int argc, char **argv)
{
     unsigned int initSize = 2048;
     char *data = new char[initSize];
     memset(data, 0, initSize);
     ossPrimitiveFileOp _fileOp;
     char *fileName ="test.txt";
     _fileOp.Open(fileName, OSS_PRIMITIVE_FILE_OP_OPEN_ALWAYS);
     _fileOp.Write(data, initSize);
     _fileOp.Close();

     ossMmapFile _ossMmapFile;
     _ossMmapFile.open(fileName, OSS_PRIMITIVE_FILE_OP_OPEN_ALWAYS);
     char *target;
     _ossMmapFile.map(0, initSize, (void**)&target);
     strcpy(target, "It's so lucky!\n");
     _ossMmapFile.close();
     return 0;
}