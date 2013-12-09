#include "commandFactory.hpp"

CommandFactory::CommandFactory()
{
	addCommand();
}
ICommand* CommandFactory::getCommandProcesser(const char* pCmd)
{
	ICommand *pProcesser = NULL;
	do
	{
		COMMAND_MAP::iterator iter;
		iter = _cmdMap.find(pCmd);
		if (iter != _cmdMap.end())
		{
			pProcesser = iter -> second;
		}
	}while(0);
	return pProcesser;
}