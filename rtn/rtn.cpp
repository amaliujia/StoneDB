#include "core.hpp"
#include "rtn.hpp"
#include "pd.hpp"
#include "pmd.hpp"

using namespace bson;

rtn:rtn():_dmsFile(NULL)
{

}

rtn::~rtn()
{
	if(_dmsFile)
	{
		delete _dmsFile;
	}
}

int rtn:rtnInitialize()
{
	int rc = EDB_OK;
	_dmsFile = new(std::nothrow)dmsFile();
	if(!_dmsFile)
	{
		rc = EDB_OOM;
		PD_LOG(PDERROR,"Failed to get new dms file");
		goto error;		
	}
	//init dms file
	rc = _dmsFile->initialize(pmdGetKRCB()->getDataFilePath());
	if(rc)
	{	
		PD_LOG(PDERROR,"Failed to call dms initialize function, rc = %d",rc);
		goto error;
	}
done:
	return rc;
error:
	goto done;
}

int rtn::rtnInsert(BSONObj &record)
{
	int rc = EDB_OK;
	dmsRecordID recordID;
	BSONObj outRecord;
	rc = dmsFile->insert(record, outRecord,recordID);
	if(rc)
	{
		PD_LOG(PDERROR,"Failed to call dms insert function,rc = %d",rc);
		goto error;
	}
done:
	return rc;
error:
	goto done;
}

int rtn::rtnFind(BSONObj &inRecord, BSONObj &outRecord)
{
	return EDB_OK;
}

int rtn::rtnRemove(BSONObj &record)
{
	return EDB_OK;
}
