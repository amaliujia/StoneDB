#include "core.hpp"
#include "rtn.hpp"
#include "pd.hpp"
#include "pmd.hpp"

using namespace bson;

rtn::rtn()
:_dmsFile(NULL), _ixmBucketMgr(NULL)
{
}

rtn::~rtn()
{
	if(_ixmBucketMgr)
	{
		delete _ixmBucketMgr;
	}
	if(_dmsFile)
	{
		delete _dmsFile;
	}
}

int rtn::rtnInitialize()
{
	int rc = EDB_OK;
	_ixmBucketMgr = new (std::nothrow) ixmBucketManager();
	if(!_ixmBucketMgr)
	{	
		rc = EDB_OOM;
		PD_LOG(PDERROR, "Failed to initialize new bucket manager!");
		goto error;
	}
	_dmsFile = new(std::nothrow)dmsFile(_ixmBucketMgr);
	if(!_dmsFile)
	{
		rc = EDB_OOM;
		PD_LOG(PDERROR,"Failed to get new dms file");
		goto error;		
	}
	rc = _ixmBucketMgr->initialize();
	if(rc)
	{
		PD_LOG(PDERROR, "Failed to call bucketMgr initialize, rc = %d", rc);
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
   int rc = EDB_OK ;
   dmsRecordID recordID;
   BSONObj outRecord;
   // check if _id exists
   rc = _ixmBucketMgr->isIDExist(record);
   PD_RC_CHECK(rc, PDERROR, "Failed to call isIDExist, rc = %d", rc);
   // write data into file
   rc = _dmsFile->insert(record, outRecord, recordID);
   if(rc)
   {
      PD_LOG(PDERROR, "Failed to call dms insert, rc = %d", rc);
      goto error;
   }
   rc = _ixmBucketMgr->createIndex(outRecord, recordID);
   PD_RC_CHECK(rc, PDERROR, "Failed to call ixmCreateIndex, rc = %d", rc);
done:
   return rc;
error:
   goto done;
}

int rtn::rtnFind(BSONObj &inRecord, BSONObj &outRecord)
{
   int rc = EDB_OK;
   dmsRecordID recordID;
   rc = _ixmBucketMgr->findIndex(inRecord, recordID);
   PD_RC_CHECK(rc, PDERROR, "Failed to call ixm findIndex, rc = %d", rc);
   rc = _dmsFile->find(recordID, outRecord);
   PD_RC_CHECK(rc, PDERROR, "Failed to call dms find, rc = %d", rc);
done:
   return rc;
error:
   goto done;
}

int rtn::rtnRemove(BSONObj &record)
{
   int rc = EDB_OK;
   dmsRecordID recordID;
   rc = _ixmBucketMgr->removeIndex(record, recordID);
   PD_RC_CHECK(rc, PDERROR, "Failed to call ixm removeIndex, rc = %d", rc);
   rc = _dmsFile->remove(recordID);
   PD_RC_CHECK(rc, PDERROR, "Failed to call dms remove, rc = %d", rc);
done:
   return rc;
error:
   goto done;
}
