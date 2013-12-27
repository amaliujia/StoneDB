#ifndef _OSSUTIL_HPP_
#define _OSSUTIL_HPP_

inline void ossSleepmicros(unsigned int s)
{
	struct timespec t;
	t.tv_sec = (time_t)(s/1000000);
	t.tv_nsec = 1000 * (s % 1000000);
	while(nanosleep(&t,&t) == -1 && errno == EINTR);
}

inline void ossSleepmillis(unsigned int s)
{
	ossSleepmicros(s * 1000);
}

typedef pid_t OSSPID;
//typedef pthread_t OSSTID;
typedef int OSSTID;

inline OSSPID ossGetParentProcessID()
{
	return getppid();
}

inline OSSPID ossGetCurrentProcessID()
{
	return getpid();
}

inline OSSTID ossGetCurrentThreadID()
{
	//return syscall(SYS_gettid); on linux SYS_thread_selfid
	return syscall(SYS_gettid);
}

#endif
