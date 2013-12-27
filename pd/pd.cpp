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

const char *getPDLevelDesp(PDLEVEL level)
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

PDLEVEL _curPDLevel = PD_DFT_DIAGLEVEL;
char _pdDiagLogPath [OSS_MAX_PATHSIZE+1] = {0};

ossXLatch _pdLogMutex;
ossPrimitiveFileOp _pdLogFile;

static int _pdLogReopen()
{
    int rc = EDB_OK;
    _pdLogFile.Close();
    rc = _pdLogFile.Open(_pdDiagLogPath);
    if (rc) {
        printf("Failed to open log file, errno = %d", rc);
        goto error;
    }
    _pdLogFile.seekToEnd();
done :
    return rc;
error:
    goto done;
}


static int _pdLogFileWrite(const char *pData)
{
	int rc = EDB_OK;
	size_t dataSize = strlen(pData);
	_pdLogMutex.get();
	if (!_pdLogFile.isValid())
	{
        // open file
        rc = _pdLogReopen();
        if (rc) {
            printf("Failed to open log file, errno = %d", rc);
            goto error;
        }
        
        rc = _pdLogFile.Write(pData, dataSize);
        if (rc) {
            printf("Failed to write into log file, errno = %d", rc);
            goto error;
        }
    }
done:
    _pdLogMutex.release();
    return rc;
error:
    goto done;

}

void pdLog(PDLEVEL level, const char *func, const char *file, unsigned int line, const char *format, ...)
{
int rc = EDB_OK ;
   if ( _curPDLevel < level )
      return  ;
   va_list ap ;
   char userInfo[PD_LOG_STRINGMAX] ; // for user defined message
   char sysInfo[PD_LOG_STRINGMAX] ;  // for log header

   // create user information
   va_start ( ap, format ) ;
   vsnprintf ( userInfo, PD_LOG_STRINGMAX, format, ap ) ;
   va_end ( ap ) ;

#ifdef _WINDOWS
   SYSTEMTIME systime;
   GetLocalTime(&systime);

   snprintf ( sysInfo, PD_LOG_STRINGMAX, PD_LOG_HEADER_FORMAT,  //%04d-%02d-%02d-%02d.%02d.%02d.%06d
       systime.wYear,
       systime.wMonth,
       systime.wDay ,
       systime.wHour ,
       systime.wMinute ,
       systime.wSecond ,
       systime.wMilliseconds*1000 ,
       PDLEVELSTRING[level],
       getpid(),
       pthread_self(),
       func,
       line,
       file,
       userInfo
       ) ;

#else
   struct tm otm ;
   struct timeval tv ;
   struct timezone tz ;
   time_t tt ;

   gettimeofday ( &tv, &tz ) ;
   tt = tv.tv_sec ;
   localtime_r ( &tt, &otm ) ;
   snprintf ( sysInfo, PD_LOG_STRINGMAX, PD_LOG_HEADER_FORMAT,
              otm.tm_year+1900,
              otm.tm_mon+1,
              otm.tm_mday,
              otm.tm_hour,
              otm.tm_min,
              otm.tm_sec,
              tv.tv_usec,
              PDLEVELSTRING[level],
              getpid(),
              // linux  syscall(SYS_gettid) SYS_thread_selfid 
              syscall(SYS_gettid),
              func,
              line,
              file,
              userInfo
   ) ;
#endif // _WINDOWS
   printf ( "%s"OSS_NEWLINE, sysInfo ) ;
   if ( _pdDiagLogPath[0] != '\0' )
   {
      rc = _pdLogFileWrite ( sysInfo ) ;
      if ( rc )
      {
         printf ( "Failed to write into log file, errno = %d"OSS_NEWLINE, rc ) ;
         printf ( "%s"OSS_NEWLINE, sysInfo ) ;
      }
   }
   return ;
}