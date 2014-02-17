StoneDB
=======
This is a NoSQL Database (Document database), which is expected to be done in next one month.

It is conducted by my own.

Code expected: 10,000 ~ 30,000 lines.

The step 1 to 9 have been done.

Structure
=======
StoneDB<br>|<br>|------include<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ core.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ dms.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ dmsRecord.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ msg.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ossLatch.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ossMmapFile.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|------ ossLatch.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ossHash.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ossPrimitiveFileOp.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ossQueue.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ossSocket.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ossUtil.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|------ ossLatch.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ixmBucket.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pd.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmd.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmdEDU.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmdEDUEvent.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmdEDUMgr.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmdOptions.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ rtn.hpp<br>|------oss<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ossSocket.cpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ossPrimitiveFileOp.cpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ossMmapFile.cpp<br>|------pd<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pd.cpp<br>|------rtn<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ rtn.cpp<br>|------msg<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ msg.cpp<br>|------dms<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ dms.cpp<br>|------ixm<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ ixmBucket.cpp<br>|------pmd<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmd.cpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmdEDU.cpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmdEDUMgr.cpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmdAgent.cpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmdMain.cpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmdTCPListener.cpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ pmdOption.cpp<br>|------client<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ command.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ command.cpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ commandFactory.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ commandFactory.cpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ edb.hpp<br>|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; |------ edb.cpp<br>|------Makefile.am<br>|------Makefile.am.Mac.am<br>|------build.sh<br>|------Configure.in<br>|------Configure.in.bak

Milestone
=======

step 1 (Done):

a. install Boost C++ library.

b. use autotools to set up compiling environment.

---files related: Makefile.am, build.sh, configure.in, configure.in.bak.

step 2 (Done):

a. encapsulate all necessary socket functions into ossSocket.

b. coding and compiling source code of server and client.

c. define several basic commands of client, like help, connect and etc.

d. send requests from client to server.

---files related: core.hpp, ossSocket.hpp, ossSocket.cpp, command.cpp, command.hpp, commandFactory.cpp, commandFactory.hpp, edb.cpp, edb.hpp, pmdTcpListener.cpp, pmdAgent.cpp.

step 3 (Done):

a. build lock component to keep multi-threading safety.

b. coding queue structure for threads with mutex and conditional variable.

c. build fundamental file operations component.

d. maintain a kernal controller.

f. initialize configuration file.

e. establish Log component.

---files related: ossLatch.hpp, ossPrimitiveFileOp.hpp, ossPrimitiveFileOp.cpp, pmd.hpp, pmd.cpp, pd.cpp, pd.hpp, pmdMain.cpp, pmdOptions.hpp, pmdOptions.cpp.

step 4 (Done):

a. implement Engine Dispatchable Unit.

b. implement thread scheduling mechanism.

c. implement thread pool.

d. implement lock mechanism on thread queues.

---files related: ossUtil.hpp, pmdEDU.hpp, pmdEDU.cpp, pmdEDUMgr.hpp, pmdEDUMgr.cpp, pmdOptions.hpp, pmdOptions.cpp, edb.cpp, edb.hpp, command.cpp, command.hpp, pmdTCPListener.cpp.

step 5 (Done):

a. complete message encapsulation.

b. client part has been done.

c. define special message protocols for StoneDB.

---files related: msg.hpp, msg.cpp, command.cpp, pmdAgent.cpp. 


step 6 (Done):

establish mapping betweent disk and memory via Mmap.

---files related: ossMmapFile.cpp.


step 7 (Done):

a. BSON and data records structure design.

b. data files design.

---files related: dms.hpp, dms.cpp, dmsRecord.hpp.


step 8 (Done):

a. to finish the file and data operations like insert, delete, query in dms module, etc.

b. to code runtime system connecting database manager and process manager.

---files related: rtn.hpp, rtn.cpp, dms.hpp, dms.cpp, pmd.cpp.

step 9 (Done):

a. implement hash function on keys.

b. achieve automatically establishing index as soon as StoneDB launchs.  

---files related: rtn.hpp, rtn.cpp, ixmBucket.cpp, ixmBucket.hpp, dms.hpp, dms.cpp, ossHash.hpp.

step 10 (In progress):

understand basic concepts of DB, including transaction, log, ect.


step 11:

finish java driver.


step 12:

build a snapshot monitor for StoneDB.


step 13:

understand software testing.


step 14:

build a java app, which can communicate with StoneDB via java driver.


Copyright
=======


   Copyright (C) 2013-2014 Rui WANG.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License, version 3,
   as published by the Free Software Foundation.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program. If not, see <http://www.gnu.org/licenses/gpl.html>.

