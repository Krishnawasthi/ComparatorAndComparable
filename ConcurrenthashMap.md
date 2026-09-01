# ConcurrenthashMap


### (first read synchronized)

<img width="956" height="312" alt="image" src="https://github.com/user-attachments/assets/3f1bb8c9-f8be-44ff-9622-0287f0dae9d9" />

## it will lock only those buckets which are under modification remaining buckets can be read at the same time, this is called segment locking. 
## this is how concurrent hashmap works on java 7 and before.
## this has been converted later from segment besed learning to CAS (compare and swap). cuz it is also slow you are locking a perticular segement while modifing.
## in that we doesn't lock the segment we compare and swap.
## it is not locked so chances to check frequently that it is modified or not. first it will check the current value that it is updated or not if it is not then it will remain same and if it is updated it will be swap with the current value.
   ## swap(current value = updated value).
