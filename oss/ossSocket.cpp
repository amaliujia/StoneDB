/******************************************************************************
   Copyright (C) 2013 Rui WANG.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License, version 3,
   as published by the Free Software Foundation.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program. If not, see <http://www.gnu.org/license/>.
*******************************************************************************/

#include "ossSocket.hpp"
#include <stdio.h>

class _ossSocket
{
private:
   int _fd;
   socklen_t _addressLen;
   socklen_t _peerAddressLen;
   struct sockaddr_in _sockAddress;
   struct sockaddr_in _peerAddress;
   bool _init;
   int _timeout;

protected:
   unsigned int _getPort(sockaddr_in *addr);
   int _getAddress(sockaddr_in *addr, char *pAddress,unsigned int length);
public:
   int setSocketLi(int lonoff,int linger);
   void setAddress(const char *pHostName, unsigned int port);

   //create a listening socket
   _ossSocket();
   _ossSocket(unsigned int port, int timeout = 0);

   //create a connect socket 
   _ossSocket(const char *pHostName, unsigned int port, int timeout=0);

   //create a socket from exiting socket
   _ossSocket(int *sock, int timeout = 0);
   ~_ossSocket{
      close();
   }
   int initSocket();
   int bind_listen();
   int send(const char *pMsg, int len,
            int timeout = OSS_SOCKET_DET_TIMEOUT,
            int flags = 0);
   int recv(char *pMsg, int len,
            int timeout = OSS_SOCKET_DET_TIMEOUT,
            int flags = 0);
   int recvNF(char *pMsg, int len,
            int timeout = OSS_SOCKET_DET_TIMEOUT);
   int connnect();
   void close();
   int accept(int *sock,struct sockaddr *addr,socklen_t *addrlen,
            int timeout = OSS_SOCKET_DET_TIMEOUT);
   int disableNagle();
   unsigned int getPeerPort();
   int getPeerAddress(char *pAddress,unsigned int length);
   unsigned int getLocalPort();
   int getLocalAddress(char *pAddress, unsigned int length);
   int setTimeout(int seconds);
   static getHostName(char *pName,int nameLen);
   static int getPort(const char *pServiceName, unsigned short &port);

};


//create a listening socket 
_ossSocket::_ossSocket(unsigned int port,int timeout)
{
   _init = false;
   _fd = 0;
   _timeout = timeout;
   memset(&_socketAddress, 0, sizeof(sockaddr_in));
   memset(&_peerAddress, 0, sizeof(sockaddr_in));
   _peerAddressLen = sizeof(_peerAddress);
   _socketAddress.sin_family = AF_INET;
   _socketAddress.sin_addr.s_addr = htonl(INADDR_ANY);
   _socketAddress.sin_port = htons(port);
   _addressLen = sizeof(_socketAddress);
}

// create a socket
_ossSocket :: _ossSocket()
{
   _init = false;
   _fd = 0;
   _timeout = timeout;
   memset(&_socketAddress, 0, sizeof(sockaddr_in));
   memset(&_peerAddress, 0, sizeof(sockaddr_in));
   _peerAddressLen = sizeof(_peerAddress);
   _addressLen = sizeof(_socketAddress);
}

//create a connecting socket 
_ossSocket :: _ossSocket(int char *pHostname, unsigned int port, int timeout)
{
   struct hostent *hp;
   _init = false;
   _timeout = timeout;
   _fd = 0
   memset(&_socketAddress, 0, sizeof(sockaddr_in));
   memset(&_peerAddress, 0, sizeof(sockaddr_in));
   _peerAddressLen = sizeof(_peerAddress);
   _socketAddress.sin_family = AF_INET;
   if (hp = getHostName(pHostname))
   {     
      _socketAddress.sin_addr.s_addr = *((int *)hp -> h_addr_list[0]);
   }else{
      _socketAddress.sin_addr.s_addr = inet_addr(pHostname);
   }
   _socketAddress.sin_port = htons(port);
   _addressLen = sizeof(_socketAddress);
}

// create from a exiting socket
_ossSocket :: _ossSocket(int *sock, int timeout)
{
   int rc = EDB_OK;
   _fd = *sock;
   _init = true;
   _timeout = timeout;
   _addressLen = sizeof(_socketAddress);
   memset(&_socketAddress, 0, sizeof(sockaddr_in));
   _peerAddressLen = sizeof(_peerAddress);
   rc = getsockname(_fd, (sockaddr*)&_sockAddress, &_addressLen);
   if (rc)
   {
      printf("Failed to get sock name, error = %d\n", SOCK_GETLASTERROR);
      _init = false;  
   }else{
      rc = getpeername(_fd, (sockaddr*)&_peerAddress, &_peerAddressLen);
      if (rc)
      {
         printf("Failed to get peer name,error = %d", SOCK_GETLASTERROR);
      }
   }

}

int ossSocket::initSocket()
{
   int rc = EDB_OK;
   if (_init)     
   {
      goto done;
   }
   memset(&_peerAddress, 0, sizeof(sockaddr_in));
   _peerAddressLen = sizeof(_peerAddress);
   _fd = sock(AF_INET, SOCK_STREAM, IPPROTO_TCP);
   if (-1 == _fd)
   {
      printf("Failed to initialize socket,error = %d", SOCK_GETLASTERROR);
      rc = EDB_NETWORK;
      goto error;     
   }
   _init = true;
   //set timeout
   setTimeout(_timeout);
   done:
      return rc;
   error:
      goto done;   
}

int ossSocket :: setSocketLi(int lonoff, int linger)
{
   int rc = EDB_OK;
   struct  linger = _linger;
   _linger.l_onoff = lonoff;
   _linger.l_linger = linger;
   rc = setsockopt(_fd,SOL_SOCKET,SO_LINGER,(const char*)&_linger,sizeof(_linger));
   return rc;
}

void ossSocket::setAddress(const char *pHostname, unsigned int port)
{
   struct hostent *hp;
   memset(&_sockAddress, 0, sizeof(sockaddr_in));
   memset(&_peerAddress, 0, sizeof(sockaddr_in));
   _peerAddressLen = sizeof(_peerAddress);
   _sockAddress.sin_family = AF_INET;
   if ((hp = gethostbyname(pHostname)))
   {
      _sockAddress.sin_addr.s_addr = *((int*)hp -> h_addr_list[0]);
   }else{
      _sockAddress.sin_addr.s_addr = inet_addr(pHostname);
   }
   _sockAddress.sin_port = htons(port);
   _addressLen = sizeof(_sockAddress);
}

