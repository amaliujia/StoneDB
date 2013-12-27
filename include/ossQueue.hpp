#ifndef _OSSQUEUE_HPP_
#define _OSSQUEUE_HPP_

#include "core.hpp"

#include <queue>
#include <boost/thread.hpp>
#include <boost/thread/thread_time.hpp>

template<typename Data>
class ossQueue
{
private:
	std::queue<Data> _queue;
	boost::mutex _mutex;
	boost::condition_variable _cond;
public:
	unsigned int size()
	{
		boost::mutex::scoped_lock lock(_mutex);
	}

	void push(Data const &data)
	{
		boost::mutex::scoped_lock lock(_mutex);
		_queue.push(data);
		lock.unlock();
		_cond.notify_one();
	}

	bool empty() const
	{
		boost::mutex::scoped_lock lock(_mutex);
		return _queue.empty();
	}

	bool try_pop(Data &value)
	{
		boost::mutex::scoped_lock lock(_mutex);
		if (_queue.empty())
		{
			return false;
		}
		value = _queue.front();
		_queue.pop();
		return true;
	}

	void wait_and_pop(Data &value)
	{
		boost::mutex::scoped_lock lock(_mutex);
		while(_queue.empty())
		{
			_cond.wait(lock);
		}
		value = _queue.front();
		_queue.pop();
	}

	bool timed_wait_and_pop(Data &value, long long millsec)
	{
		boost::system_time const timeout = boost::get_system_time() + boost::posix_time::milliseconds(millsec);
		boost::mutex::scoped_lock lock(_mutex);
	while(_queue.empty())
	{
		if (!_cond.timed_wait(lock, timeout))
		{
			return false;
		}
	}
		value = _queue.front();
		_queue.pop();
		return true;
	}

	ossQueue(){};
	~ossQueue(){};
};

#endif