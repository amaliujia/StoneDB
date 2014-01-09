#ifndef _OSSMMAPFILE_HPP_
#define _OSSMMAPFILE_HPP_

#include "core.hpp"
#include "ossLatch.hpp"
#include "ossPrimitiveFileOp.hpp"

class _ossMmapFile
{
protected :
   class _ossMmapSegment
   {
   public :
      void *_ptr ;
      unsigned int       _length ;
      unsigned long long _offset ;
      _ossMmapSegment ( void *aptr,
                        unsigned int alength,
                        unsigned long long aoffset )
      {
         _ptr = aptr ;
         _length = alength ;
         _offset = aoffset ;
      }
   } ;
   typedef _ossMmapSegment ossMmapSegment ;

   ossPrimitiveFileOp _fileOp ;
   ossXLatch _mutex ;
   bool _opened ;
   std::vector<ossMmapSegment> _segments ;
   char _fileName [ OSS_MAX_PATHSIZE ] ;
public :
   typedef std::vector<ossMmapSegment>::const_iterator CONST_ITR ;

   inline CONST_ITR begin ()
   {
      return _segments.begin () ;
   }

   inline CONST_ITR end ()
   {
      return _segments.end() ;
   }

   inline unsigned int segmentSize ()
   {
      return _segments.size() ;
   }
public :
   _ossMmapFile ()
   {
      _opened = false ;
      memset ( _fileName, 0, sizeof(_fileName) ) ;
   }
   ~_ossMmapFile ()
   {
      close () ;
   }

   int open ( const char *pFilename, unsigned int options ) ;
   void close () ;
   int map ( unsigned long long offset, unsigned int length, void **pAddress ) ;
} ;
typedef class _ossMmapFile ossMmapFile ;

#endif