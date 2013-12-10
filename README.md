StoneDB
=======
This is a NoSQL Database, which is expected to be done in next three months.

It is conducted by my own.

Code expected: 10,000 ~ 30,000 lines.

The step 1,2 have been done.

Milestone
=======

step 1 (Done):

a. install Boost C++ library.

b. use autotools to set up compiling environment.

---relating files: Makefile.am, build.sh, configure.in, configure.in.bak.

step 2 (Done):

a. encapsulate all necessary socket functions into ossSocket.

b. coding and compiling source code of server and client.

c. send requests from client to server.

d. server can handle requests from different clients through multithreading.

---relating files: core.hpp, ossSocket.hpp, ossSocket.cpp, command.cpp, command.hpp, commandFactory.cpp, commandFactory.hpp, edb.cpp, edb.hpp, pmdTcpListener.cpp.

step 3 (In progress):

set up some fundamental components, such as log. 


step 4:

a. implement thread data structure.

b. implement thread scheduling mechanism.


step 5:

complete message encapsulation on server and client.


step 6:

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

