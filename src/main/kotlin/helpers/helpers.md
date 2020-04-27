# Package helpers

Various helpers used across the entire project

#### Supported commands 

```
| Command             | Information                                                                       |
|---------------------|-----------------------------------------------------------------------------------|
| new                 | Creates a new local user                                                          |
| new <n: Int>        | Creates n users & auto registers them to the server                               |
| register many       | Creates a bunch of users & registers them to the server, also stores them locally |
| use <n: Int>        | Selects the user with **local** ID of n                                           |
| selected            | Prints the currently selected user                                                |
| list                | Prints all **local** users                                                        |
| ls                  | Lists current **local** directory                                                 |
| register            | Registers the selected user to the server                                         |
| remote list         | Lists all users from the **server**                                               |
| upload              | Prompts user to upload photo or post                                              |
| follow <n: Int>     | Sends follow request to user with **remote** ID of <n>                            |
| show requests       | Shows follow requests to user  (stored in the server only)                        |
| reject <id: String> | Rejects follow request of ID: <id>                                                |    
```