<img width="935" height="305" alt="image" src="https://github.com/user-attachments/assets/0c713ce2-2af7-4281-a214-d9bdcd2339bc" />

## As you can see in the above picture where seller is changing the price at the time when customers are adding to cart to cart and reading the price the previous price (this time will be of milii seconds ), User will not get the real price because it is changes by the seller. 
## if you will not synchronized this people will might see the different price.
## the core idea: when some is writing at the time where multiple threads reading the data that is not thread safe.

## thread safety always comes with slowness.
## it may be that 2 million people will be on the waiting for 200 milli seconds. (that is the huge time for the CPU.
## to solve this problem you can make your system Synchronized or use hashTable(by default Synchronized).
## if you will make this Synchronized it will not allow reading while writting(completely lock the Obeject).
<img width="1067" height="322" alt="image" src="https://github.com/user-attachments/assets/78169507-5221-4594-91ae-128057643fef" />


## Under this example: here are 10K objects(Products), if you are making changes in one product it will lock the remaining 9999 products Means it will lock the object(prodPrice).
## this is because you have synchronized the object.
## It is good to solve this problem but it will be slow because thread safety always comes with slowness.
## you can used HashTable to solve this problem : where all the methods are by default synchronized.
<img width="1017" height="297" alt="image" src="https://github.com/user-attachments/assets/e5d1986f-0e7e-4290-b00d-cd5bd8bfe9d9" />
<img width="500" height="38" alt="image" src="https://github.com/user-attachments/assets/7854a6cc-66b8-4399-9b53-4b85fee7393a" />

## now this will be the single threaded , means if one thread is performing something are working on, no other thread will be able to read and modifying the data.
## this is solving the data problem or the race problem.

<img width="1040" height="137" alt="image" src="https://github.com/user-attachments/assets/5ab51a58-2dbc-410e-b9be-8bbf82e1974a" />

                                                               # Major problem
## But it also has some problem: It solves the problem of thread safety but it is slow, it locks the object completely means other thread will stop working and       user will face the major problem.
## But here is the twist java introduced to solve ConcurrentHashMap to solve this problem where map's object will not be completely lock.
