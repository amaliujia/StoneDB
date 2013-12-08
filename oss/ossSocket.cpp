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
_ossSocket::_ossSocket()
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
_ossSocket::_ossSocket(int char *pHostname, unsigned int port, int timeout)
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
_ossSocket::_ossSocket(int *sock, int timeout)
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
         printf("Failed to get peer name,error = %d\n", SOCK_GETLASTERROR);
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
      printf("Failed to initialize socket,error = %d\n", SOCK_GETLASTERROR);
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

int ossSocket::setSocketLi(int lonoff, int linger)
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

int ossSocket::bind_listen()
{
   int rc = EDB_OK;
   int temp = 1;
   rc = setsockopt(_fd, SOL_SOCKET, SOCK_GETLASTERROR,(char*)&temp,sizeof(int));
   if (rc)
   {
      printf("Failed to setsockopt SO_REUSEADDR,rc = %d\n",SOCK_GETLASTERROR);
   }
   rc = setSocketLi(1,30);
   if (rc)
   {
      printf("Failed to set socket SO_LINGER, rc = %d\n", SOCK_GETLASTERROR);
   }
   rc = ::bind(_fd,(struct sockaddr*)&_sockAddress,_addressLen);
   if (rc)
   {
      printf("Failed to bind socket, rc = %d\n", SOCK_GETLASTERROR);
      rc = EDB_NETWORK;
      goto error;
   }
   rc = listen(_fd, SOMAXCONN);
   if (rc )
   {
      printf("Failed to listen socket, rc = %d\n",SOCK_GETLASTERROR );
      rc = EDB_NETWORK;
      goto error;
   }
done:
   return rc;
error:
   close();
   goto done;
}

int ossSocket::send(const char *pMsg, int len,int timeout, int flags)
{
   int rc = EDB_OK;
   int maxFD = _fd;
   struct timeval maxSelectTime;
   fd_set fds;

   maxSelectTime.tv_sec = timeout / 1000000;
   maxSelectTime.tv_usec = timeout % 1000000;
   //if len = 0, then let's just return
   if (len == 0)
   {
      return EDB_OK;
   }
   //wait loop until socket is ready
   while(true)
   {
      FD_ZERO(&fds);
      FD_SET(_fd,&fds);
      rc = select(maxFD + 1, NULL, &fds, NULL, timeout>=0?&maxSelectTime:NULL);
      if (0 == rc)
      {
         //timeout
         rc = EDB_TIMEOUT:
         goto done;
      }
      //if < 0 ,something wrong
      if (0 > rc)
      {
         rc = SOCK_GETLASTERROR;
         if (EINTR == rc)
         {
            continue;
         }
         printf("Failed to select from socket, rc = %d\n",rc);
         rc = EDB_NETWORK;
         goto error;
      }
      if (FD_ISSET(_fd,&fds))
      {
         break;
      }
   }
   while(len > 0)
   {
      //MSG_NOSIGNAL; Requests not to send SIGPIPE on errors on strm oriented sockets when the other end breaks the connection. The EPIPE error is still returned.
       
   }   
}

