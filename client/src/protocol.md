<div style="text-align: center;"><h1>Protocol definition</h1></div>

 ## Basic information
 * TCP based.
 * Multiple connections can be made through a single client. 
 * Connection is opened when client sends data and closed when client receives data. (Just like HTTP)

 ## Packet format for sending

| range (bytes) | type                | name        | description                                              |
|---------------|---------------------|-------------|----------------------------------------------------------|
| 0 to 10       | 10 ASCII characters | header      | Plain "gagaotalk!" characters for header.                |
| 10 to 18      | 8bit unsigned int   | session ID  | Session ID of client. Some action does not require this. |
| 18 to 26      | 8 ASCII characters  | action      | Purpose of this packet.                                  |
| 26 to ..      | bytes               | data        | Content of this packet.                                  |

## Packet format for receiving

| range (bytes) | type                  | name        | description                                              |
|---------------|-----------------------|-------------|----------------------------------------------------------|
| 0 to 10       | 10 ASCII characters   | header      | Plain "gagaotalk!" characters for header.                |
| 10 to 18      | 8 ASCII characters    | action      | Purpose of this packet.                                  |
| 18 to 22      | 4bit unsigned int     | status code | Result of action.                                        |
| 22 to ..      | bytes                 | data        | Content of this packet.                                  |

 ## Status code

| number | meaning                            |
|--------|------------------------------------|
| 0      | Action was successfully completed. |
| 1      | Problem occurred during the action |
| 2      | Invalid session ID                 |
| 3      | <i>TBD</i>                         |

 ## List of actions
 * Name of actions are consisted of 8 ASCII characters. 
 * Capitalization is ignored, however this table uses camelCase for easier reading.

| name     | description                                                         | require session ID | not used for sending |
|----------|---------------------------------------------------------------------|--------------------|----------------------|
| signIn   | Logging in to server.<br> Server will return session ID if success. | no                 | no                   |
| signOut  | Logging out from server.                                            | <b>yes</b>         | no                   |
| signUp   | Registering to server.<br> Requires basic user information.         | no                 | no                   |
| findID   | Finding ID.<br> TBD.                                                | no                 | no                   |
| findPW   | Finding PW.<br> TBD.                                                | no                 | no                   |
| getFrens | Get UIDs of user's friends.                                         | <b>yes</b>         | no                   |
| getUser  | Get information of user.                                            | <b>yes</b>         | no                   |
| sendMsg  | Send message to specific user.                                      | <b>yes</b>         | no                   |
| rcvMsg   | Client received a message.<br> Not used for sending!                | <b>yes</b>         | <b>yes</b>           |

 ## Data format for each actions 
 * These are the pairs of all possible actions and data.
 * There are data for sending and receiving, respectively.
 * Data can be either a JSON string or a binary format.
 

 ### signIn

 * client sending 
<pre>
{
        "id": "[id input]",
        "password": "[password input]"
}
</pre>    

 * status code: 0
<pre>
{
        "session_id": "[session id]"
}
</pre>    

 * status code: 1
<pre>
{
       "error_message": "[detailed error message of failure]"
}
</pre>


### signOut

* client sending
<pre>
null
</pre>    

* status code: 0
<pre>
null
</pre>    
* status code: 1
<pre>
{
       "error_message": "[detailed error message of failure]"
}
</pre>

 ### signUp

 * client sending
<pre>
{
        "id": "[id for user]",
        "nickname": "[nickname for user]",
        "email": "[email for user]",
        "birthday": "[birthday for user in yyyy-mm-dd format]",
        "password": "[password for user]"
}
</pre>    

 * status code: 0
<pre>
null
</pre>    

 * status code: 1
<pre>
{
       "error_message": "[detailed error message of failure]"
}
</pre>

### findID
 * TBD

### findPW
* TBD

### getFrens

* client sending
<pre>
null
</pre>    

* status code: 0
<pre>
{
        "friends_list": [
           "UID1", "UID2", ...
        ]
}
</pre>    

* status code: 1
<pre>
{
       "error_message": "[detailed error message of failure]"
}
</pre>

### getUser

* client sending
<pre>
{
        "uid": "[UID of desired user]"
}
</pre>    

* status code: 0
<pre>
{
        "id": "[id for user]",
        "nickname": "[nickname for user]",
        "email": "[email for user]",
        "birthday": "[birthday for user in yyyy-mm-dd format]",
}
</pre>    

* status code: 1
<pre>
{
       "error_message": "[detailed error message of failure]"
}
</pre>
