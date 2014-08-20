#ifndef _SNAPSHOT_HPP_
#define _SNAPSHOT_HPP_

#include <time.h>
#include "ossLatch.hpp"

class MonAppCB
{
public:
   MonAppCB();
   ~MonAppCB();
   void setInsertTimes(long long ainsertTimes);
   long long getInsertTimes () const;
   void increaseInsertTimes();
   void setDelTimes(long long delTimes);
   long long getDelTimes() const;
   void increaseDelTimes();
   void setQueryTimes(long long queryTimes);
   long long getQueryTimes() const;
   void increaseQueryTimes();
   long long getServerRunTime();
   void setConnectionTimes(long long connectTiems);
   long long getConnectionTimes() const;
   void increaseConnectionTimes();
   void decreaseConnectionTimes();
private:
   long long _insertTimes;
   long long _delTimes;
   long long _queryTimes;
   long long _connectionTimes;
   struct timeval _start;
   ossSLatch _mutex;
};

#endif
