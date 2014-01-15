#ifndef _DMS_HPP_
#define _DMS_HPP_

#include "ossLatch.hpp"
#include "ossMmapFile.hpp"
#include "bson.h"
#include "dmsRecord.hpp"
#include <vector>

#define DMS_EXTEND_SIZE 65536
// 4 MB for page size
#define DMS_PAGESIZE	4194304
#define DMS_MAX_RECORD (DMS_PAGESIZE - sizeof(dmsHeader) - sizeof(dmsRecord) - sizeof(SLOTOFF))
#define DMS_MAX_PAGES 262144
typedef unsigned int SLOTOFF;
#define DMS_INVALID_SLOTID	0xFFFFFFFF
#define DMS_INVALID_PAGEID	0xFFFFFFFF

#define DMS_KEY_FIELDNAME	"_id"
extern const char *gKeyFieldName;

// each record has the following header, include 4B size and 4B flag
struct dmsRecord
{
	unsigned int _size;
	unsigned int _flag;
	char _data[0];
};

// dms header
#define DMS_HEADER_EYECATCHER "DMSH"
#define DMS_HEADER_EYECATCHER_LEN	4
#define DMS_HEADER_FLAG_NORMAL	0
#define DMS_HEADER_FLAG_DROPPED	1

#define DMS_HEADER_VERSION_0	0
#define DMS_HEADER_VERSION_CURRENT	DMS_HEADER_VERSION_0

struct dmsHeader
{
	char	_eyeCatcher[DMS_HEADER_EYECATCHER_LEN];
	unsigned int _size;
	unsigned int _flag;
	unsigned int _version;
};

// page structure
/******************************************
PAGE STRUCTURE
-------------------------
| PAGE HEADER 			|
-------------------------
| SLOT LIST				|
-------------------------
| FREE SPACE			|
-------------------------
| DATA 					|
-------------------------
*******************************************/
#define DMS_PAGE_EYECATCHER "PAGE"
#define DMS_PAGE_EYECATCHER_LEN	4
#define DMS_PAGE_FLAG_NORMAL	0
#define DMS_PAGE_FLAG_UNALLOC	1
#define DMS_SLOT_EMPTY	0xFFFFFFFF
struct dmsPageHeader
{
	char	_eyeCatcher[DMS_PAGE_EYECATCHER_LEN];
	unsigned int _size;
	unsigned int _flag;
	unsigned int _numSlots;
	unsigned int _slotOffset;
	unsigned int _freeSpace;
	unsigned int _freeOffset;
	char	_data[0];
};


#endif