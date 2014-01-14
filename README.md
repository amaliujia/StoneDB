StoneDB
=======
This is a NoSQL Database (save files), which is expected to be done in next two months.

It is conducted by my own.

Code expected: 10,000 ~ 30,000 lines.

The step 1 to 5 have been done.

Milestone
=======

step 1 (Done):

a. install Boost C++ library.

b. use autotools to set up compiling environment.

---relating files: Makefile.am, build.sh, configure.in, configure.in.bak.

step 2 (Done):

a. encapsulate all necessary socket functions into ossSocket.

b. coding and compiling source code of server and client.

c. define several basic commands of client, like help, connect and etc.

d. send requests from client to server.

---relating files: core.hpp, ossSocket.hpp, ossSocket.cpp, command.cpp, command.hpp, commandFactory.cpp, commandFactory.hpp, edb.cpp, edb.hpp, pmdTcpListener.cpp, pmdAgent.cpp.

step 3 (Done):

a. build lock component to keep multi-threading safety.

b. coding queue structure for threads with mutex and conditional variable.

c. build fundamental file operations component.

d. maintain a kernal controller.

f. initialize configuration file.

e. establish Log component.

---relating files: ossLatch.hpp, ossPrimitiveFileOp.hpp, ossPrimitiveFileOp.cpp, pmd.hpp, pmd.cpp, pd.cpp, pd.hpp, pmdMain.cpp, pmdOptions.hpp, pmdOptions.cpp.

step 4 (Done):

a. implement Engine Dispatchable Unit.

b. implement thread scheduling mechanism.

c. implement thread pool.

d. implement lock mechanism on thread queues.

---relating files: ossUtil.hpp, pmdEDU.hpp, pmdEDU.cpp, pmdEDUMgr.hpp, pmdEDUMgr.cpp, pmdOptions.hpp, pmdOptions.cpp, edb.cpp, edb.hpp, command.cpp, command.hpp, pmdTCPListener.cpp.

step 5 (Done):

a. complete message encapsulation.

b. client part has been done.

c. define special message protocols for StoneDB.

---relating files: msg.hpp, msg.cpp, command.cpp, pmdAgent.cpp. 


step 6 (In progress):

a. establish mapping betweent disk and memory via Mmap.

b. complete allocation and deallocation data blocks.


step 7:

a. design of BSON and structure of data records.

b. design of data files.


step 8:

a. to finish the operations like insert, delete, query, etc.

b. to finish relating functions on client.


step 9:

a. implement hash function on keys.

b. achieve automatically establishing index as soon as StoneDB launchs.  


step 10:

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


   Copyright (C) 2013 Rui WANG.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU Affero General Public License, version 3,
   as published by the Free Software Foundation.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
   GNU Affero General Public License for more details.

   You should have received a copy of the GNU Affero General Public License
   along with this program. If not, see <http://www.gnu.org/licenses/gpl.html>.

