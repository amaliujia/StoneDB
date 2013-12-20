#include "core.hpp"
#include "pd.hpp"
#include "ossLatch.hpp"
#include "ossPrimitiveFileOp.hpp"

const static char *PDLEVELSTRING[] = 
{
	"SEVERE",
	"ERROR",
	"EVENT",
	"WARNING",
	"INFO",
	"DEBUG"
};

const char *getPDLevelDesp(PDLevel level)
{
	if ((unsigned int)level > (unsigned int)PDDEBUG)
	{
		return "Unknown Level";
	}
	return PDLEVELSTRING[(unsigned int)level];
}

const static char *PD_LOG_HEADER_FORMAT="%04d-%02d-%02d-%02d.%02d.%02d.%06d\
										\
	Level:%s"OSS_NEWLINE"PID:%-37dTID:%d"OSS_NEWLINE"Function:%-32sLine:%d"\
	OSS_NEWLINE"File:%s"OSS_NEWLINE"Message:"OSS_NEWLINE"%s"OSS_NEWLINE OSS_NEWLINE;

PDLEVEL _curPDLevel = PD_EFT_DIANLEVEL;
char _pdDiagLogPath [OSS_MAX_PATHSIZE+1] = {0};

ossXLatch _pdLogMutex;
ossPrimitiveFileOp _pdLogFile;


static int _pdLogFileWrite(const char *pData)
{
	int rc = EDB_OK;
	size_t dataSize = strlen(pData);
	
}