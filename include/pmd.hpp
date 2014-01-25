#ifndef _PMD_HPP_
#define _PMD_HPP_

#include "core.hpp"
#include "pmdEDUMgr.hpp"
#include "rtn.hpp"

enum EDB_DB_STATUS
{
	EDB_DB_NORMAL = 0,
	EDB_DB_SHUTDOWN,
	EDB_DB_PANIC
};

#define EDB_IS_DB_NORMAL ( EDB_DB_NORMAL == pmdGetKRCB()->getDBStatus () )
#define EDB_IS_DB_DOWN   ( EDB_DB_SHUTDOWN == pmdGetKRCB()->getDBStatus () || \
                           EDB_DB_PANIC    == pmdGetKRCB()->getDBStatus () )
#define EDB_IS_DB_UP     ( !EDB_IS_DB_DOWN )

#define EDB_SHUTDOWN_DB  { pmdGetKRCB()->setDBStatus(EDB_DB_SHUTDOWN); }

class pmdOptions;
class EDB_KRCB
{
private:
	char _dataFilePath[OSS_MAX_PATHSIZE + 1];
	char _logFilePath[OSS_MAX_PATHSIZE + 1];
	int _maxPool;
	char _svcName[NI_MAXSERV + 1];
	EDB_DB_STATUS _dbStatus;
private:
	pmdEDUMgr _eduMgr;
	rtn	_rtnMgr;
public:
	EDB_KRCB()
	{
		_dbStatus = EDB_DB_NORMAL;
		memset(_dataFilePath, 0, sizeof(_dataFilePath));
		memset(_logFilePath, 0, sizeof(_logFilePath));
		_maxPool = 0;
		memset(_svcName, 0, sizeof(_svcName));
	}
	~EDB_KRCB(){}
	pmdEDUMgr *getEDUMgr()
	{
		return &_eduMgr;
	}
	rtn *getRtnMgr()
	{
		return &_rtnMgr;
	}
	// get db status
	inline EDB_DB_STATUS getDBStatus()
	{
		return _dbStatus;
	}

	//get data file path
	inline const char *getDataFilePath()
	{
		return _dataFilePath;
	}

	// get log file path
	inline const char *getLogFilePath()
	{
		return _logFilePath;
	}

	// get service name
	inline const char *getSvcName()
	{
		return _svcName;
	}

	// get max thread_pool
	inline int getMaxPool()
	{
		return _maxPool;
	}

	//setup db status
	inline void setDBStatus(EDB_DB_STATUS status)
	{
		_dbStatus = status;
	}

	//set data file path
	void setDataFilePath(const char *pPath)
	{
		strncpy(_dataFilePath, pPath, sizeof(_dataFilePath));
	}

	//set log file path
	void setLogFilePath(const char *pPath)
	{
		strncpy(_logFilePath, pPath, sizeof(_logFilePath));
	}

	//set service name
	void setSvcName(const char *pName)
	{
		strncpy(_svcName, pName, sizeof(_svcName));
	}

	//set max pool
	void setMaxPool(int max)
	{
		_maxPool = max;
	}

	//set up from pmdOptions
	int init(pmdOptions *options);
};

extern EDB_KRCB pmd_krcb;
inline EDB_KRCB *pmdGetKRCB()
{
	return &pmd_krcb;
}

#endif
