#include "core.hpp"
#include "ossPrimitiveFileOp.hpp"

int main(int argc, char**argv)
{
	char Buffer[12] = "Hello World";
	ossPrimitiveFileOp f;
	printf("ossPrimitiveFileOp has been created!\n");
	f.Open("/Users/amaliujia/Documents/Public_Course/Database Engine/Engine/emeralddb/src/fileOp/file.txt",OSS_PRIMITIVE_FILE_OP_OPEN_ALWAYS);
	printf("after Open~~!\n");
	if (f.isValid())
	{
		printf("Get in~~\n");
		f.Write(Buffer,sizeof(Buffer));
	}
	printf("gotta to Close\n");
	f.Close();

	return 0;
}
