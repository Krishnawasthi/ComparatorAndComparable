<img width="935" height="305" alt="image" src="https://github.com/user-attachments/assets/0c713ce2-2af7-4281-a214-d9bdcd2339bc" />

## As you can see in the above picture where seller is changing the price at the time when customers are adding to cart to cart and reading the price the previous price (this time will be of milii seconds ), User will not get the real price because it is changes by the seller. 

## the core idea: when some is writing at the time where multiple threads reading the data that is not thread safe.

## thread safety always comes with slowness.

## to solve this problem you can make your system Synchronized or use hashTable(by default Synchronized).
## if you will make this Synchronized it will not allow reading while writting(completely lock the Obeject).
<img width="1067" height="322" alt="image" src="https://github.com/user-attachments/assets/78169507-5221-4594-91ae-128057643fef" />


## Under this example: here are 10K objects(Products), if you are making changes in one product it will lock the remaining 9999 products Means it will lock the object(prodPrice).
## this is because you have synchronized the object.
## It is good to solve this problem but it will be slow because thread safety always comes with slowness.
## you can used HashTable to solve this problem : where all the methods are by default synchronized.
<img width="1017" height="297" alt="image" src="https://github.com/user-attachments/assets/e5d1986f-0e7e-4290-b00d-cd5bd8bfe9d9" />
