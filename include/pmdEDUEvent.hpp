#ifndef _PMDEDUEVENT_HPP_
#define _PMDEDUEVENT_HPP_


enum pmdEDUEventTypes
{
	PMD_EDU_EVENT_NONE = 0,
	PMD_EDU_EVENT_TERM,			// terminate EDU
	PMD_EDU_EVENT_RESUME,		// resume a EDU, the data is startEDU's argv
	PMD_EDU_EVENT_ACTIVE,
	PMD_EDU_EVENT_DEACTIVE,
	PMD_EDU_EVENT_MSG,
	PMD_EDU_EVENT_TIMEOUT,
	PMD_EDU_EVENT_LOCKWAKEUP	// transaction lock wake up
};


class pmdEDUEvent
{
public:
	pmdEDUEvent():
	_eventType(PMD_EDU_EVENT_NONE),
	_release(false),
	_Data(NULL)
	{
	}

	pmdEDUEvent(pmdEDUEventTypes event):
	_eventType(event),
	_release(false),
	_Data(NULL)
	{
	}
	
	pmdEDUEvent(pmdEDUEventTypes event, bool aRelease, void *aData):
	_eventType(event),
	_release(aRelease),
	_Data(aData)
	{
	}

	void reset()
	{
		_eventType = PMD_EDU_EVENT_NONE;
		_release = false;
		_Data = NULL;
	}

	
	pmdEDUEventTypes _eventType;
	bool _release;
	void *_Data;
};

#endif