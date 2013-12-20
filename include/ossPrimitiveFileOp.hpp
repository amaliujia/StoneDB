#ifndef _OSSPRIMITIVEFILEOP_HPP_
#define _OSSPRIMITIVEFILEOP_HPP_

#include "core.hpp"

// there is no XXX64 API on Mac
// instead, define macro _FILE_OFFSET_BITS=64
// during compiling, compiler usually convert XX to XX64

#define OSS_HANDLE		   int
#define OSS_F_GETLK        F_GETLK
#define OSS_F_SETLK        F_SETLK
#define OSS_F_SETLKW       F_SETLKW

#define oss_struct_statfs  struct statfs
#define oss_statfs         statfs
#define oss_fstatfs        fstatfs
#define oss_struct_statvfs struct statvfs
#define oss_statvfs        statvfs
#define oss_fstatvfs       fstatvfs
#define oss_struct_stat    struct stat
#define oss_struct_flock   struct flock
#define oss_stat           stat
#define oss_lstat          lstat
#define oss_fstat          fstat

#define oss_open           open

#define oss_lseek          lseek
#define oss_ftruncate      ftruncate

#define oss_off_t          off_t
#define oss_close          close
#define oss_access         access
#define oss_chmod          chmod
#define oss_read           read
#define oss_write          write
#define OSS_INVALID_HANDLE_FD_VALUE (-1)

#define OSS_PRIMITIVE_FILE_OP_FWRITE_BUF_SIZE 2048
#define OSS_PRIMITIVE_FILE_OP_READ_ONLY     (((unsigned int)1) << 1)
#define OSS_PRIMITIVE_FILE_OP_WRITE_ONLY    (((unsigned int)1) << 2)
#define OSS_PRIMITIVE_FILE_OP_OPEN_EXISTING (((unsigned int)1) << 3)
#define OSS_PRIMITIVE_FILE_OP_OPEN_ALWAYS   (((unsigned int)1) << 4)
#define OSS_PRIMITIVE_FILE_OP_OPEN_TRUNC    (((unsigned int)1) << 5)



typedef oss_off_t offsetType;

class ossPrimitiveFileOp
{
public:
	typedef OSS_HANDLE handleType;
private:
	handleType _fileHandle;
	ossPrimitiveFileOp(const ossPrimitiveFileOp &){}
	const ossPrimitiveFileOp &operator= (const ossPrimitiveFileOp &);
	bool _bIsStdout;
protected:
	void setFileHandle(handleType handle);
public:
	ossPrimitiveFileOp();
	int Open(
			const char *pFilePath,
			unsigned int options = OSS_PRIMITIVE_FILE_OP_OPEN_ALWAYS
		);
	void openStdout();
	void Close();
	bool isValid(void);
	int Read(const size_t size, void *const pBuf, int * const pBytesRead);
	int Write(const void* pBuf, size_t len = 0);
	int fWrite(const char* fmt, ...);
	offsetType getCurrentOffset(void) const;
	void seekToOffset(offsetType offset);
	void seekToEnd(void);
	int getSize(offsetType * const pFileSize);
	handleType getHandle(void) const
	{
		return _fileHandle;
	}
};

#endif