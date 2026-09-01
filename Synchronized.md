<img width="935" height="305" alt="image" src="https://github.com/user-attachments/assets/0c713ce2-2af7-4281-a214-d9bdcd2339bc" />
## As you can see in the above picture where seller is changing the price at the time when customers are adding to cart to cart and reading the price the previous price (this time will be of milii seconds ), User will not get the real price because it is changes by the seller. 
## the core idea: when some is writing at the time where multiple threads reading the data that is not thread safe.
## thread safety always comes with slowness.

## to solve this problem you can make your system Synchronized or use hashTable(by default Synchronized).
