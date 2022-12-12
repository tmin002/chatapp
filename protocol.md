<div style="text-align: center;"><h1>Protocol definition</h1></div>

 ## Basic information
 * TCP based.
 * Almost every connection are opened by client and closed when server respond. Sockets are created for each request. (Like HTTP)
 * However, There is one socket that maintains connection from login until logout. We call this a persistent socket.
 * Server will send receiving messages, chatroom invitation to client using persistent socket.

 ## Packet format for sending

| range (bytes) | type                | name       | description                                               |
|---------------|---------------------|------------|-----------------------------------------------------------|
| 0 to 9        | 10 ASCII characters | header     | Plain "gagaotalk!" characters for header.                 |
| 10 to 25      | 128bit unsigned int | session ID | Session ID of client. Some action does not require this.  |
| 26 to 33      | 8 ASCII characters  | action     | Purpose of this packet.                                   |
| 34 to ..      | bytes               | data       | Content of this packet.                                   |

## Packet format for receiving

| range (bytes) | type                | name        | description                                                                                            |
|---------------|---------------------|-------------|--------------------------------------------------------------------------------------------------------|
| 0 to 9        | 10 ASCII characters | header      | Plain "gagaotalk!" characters for header.                                                              |
| 10            | 8bit unsigned int   | status code | Result of action.                                                                                      |
| 11 to 18      | 8 ASCII characters  | action      | Purpose of this packet.                                                                                |
| 19 to ..      | bytes               | data        | Content of this packet,<br>or detailed error message of failure in JSON string if status code isn't 0. |

 ## Status code

| number  | meaning                                                                                                                                       |
|---------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| 0       | Action was successfully completed.                                                                                                            |
| 1       | Problem occurred during the action                                                                                                            |
| 2       | Invalid session ID                                                                                                                            |
| 3       | IP of the client is different from the address that lastly logged on to the given session.<br>This means session ID might have been hijacked. |
| rest    | <i>TBD</i>                                                                                                                                    |

 ## List of actions
 * Name of actions are consisted of 8 ASCII characters. 
 * Capitalization is ignored, however this table uses camelCase for easier reading.

| name     | description                                                                                           | require session ID | uses persistent socket |
|----------|-------------------------------------------------------------------------------------------------------|--------------------|------------------------|
| signIn   | Logging in to server.<br> Server will return session ID if success.                                   | no                 | no                     |
| signOut  | Logging out from server.                                                                              | <b>yes</b>         | no                     |
| signUp   | Registering to server.<br> Requires basic user information.                                           | no                 | no                     |
| findID   | Finding ID.<br> Requires user birthday, nickname.                                                     | no                 | no                     |
| findPW   | Finding password.<br> Requires user ID, phone number.<br>(phone number is only used for this purpose) | no                 | no                     |
| upPW     | Changing password.<br> Requires user password once again.                                             | <b>yes</b>         | no                     |
| getFrens | Get Information of user's friends.                                                                    | <b>yes</b>         | no                     |
| unRegs   | Remove myself from database.                                                                          | <b>yes</b>         | no                     |
| getUser  | Get information of user.                                                                              | no                 | no                     |
| chkUsrOn | Check whether the given user is online.                                                               | no                 | no                     |
| getCtRms | Get all chatroom ID user is in.                                                                       | <b>yes</b>         | no                     |
| upUsrInf | Update user information.                                                                              | <b>yes</b>         | no                     |
| mkCtRm   | Make new chatroom with specified users.                                                               | <b>yes</b>         | no                     |
| addCtRm  | Add new user to chatroom.                                                                             | <b>yes</b>         | no                     |
| lvCtRm   | Leave specified chatroom.                                                                             | <b>yes</b>         | no                     |
| sendMsg  | Send message to specific chat room.                                                                   | <b>yes</b>         | no                     |
| rcvMsg   | Client received a message.<br> Not used for sending!                                                  | no                 | <b>yes</b>             |
| invChtRm | Client has been invited to a chatroom.<br> Not used for sending!                                      | no                 | <b>yes</b>             |
| downFile | Request download for specified file ID.                                                               | <b>yes</b>         | no                     |
| uplFile  | Request upload for specified file.                                                                    | <b>yes</b>         | no                     |
| ruOnline | Check whether client is online or not.                                                                | no                 | <b>yes</b>             |
| hi       | Assert connection of persistent socket.                                                               | yes                | yes                    |
| bye      | Assert termination of all connection.                                                                 | no                 | yes                    |

 ## Data format for each actions 
 * These are the pairs of all possible actions and data.
 * There are data for sending and receiving, respectively.
 * Data can be either a JSON string or a binary format.
 
 ### Universal format when something went wrong
<pre>
{
      "error_code": [error code number specified below.]
      "message": "[detailed error message generated by server]"
}
</pre>
 > Important: "error_code" 0 means that an unknown error has occurred. "message" will contain detailed information of error.<br>
 > This is universal for all actions, so error code 0 will not be documented below.

### "message_type" for sendMsg, rcvMsg

| "message_type" | "content"                    | description                                                     |
|----------------|------------------------------|-----------------------------------------------------------------|
| text           | [text message]               | Plain text                                                      |
| link           | [link to website]            | Website link                                                    |

| "message_type" | "file_id"                  | "file_name" | description                                                     |
|----------------|----------------------------|-------------|-----------------------------------------------------------------|
| file           | [file ID stored in server] | [file name] | File less than 50MB<br>"content" will be filled with file name. |

 ### signIn

 * client sending (login with ID and password)
<pre>
{
        "id": "[id input]",
        "password": "[password input]"
}
</pre>    

* client receiving if operation successful 
<pre>
{
        "session_id": "[session id]"
}
</pre>    

* client receiving if something went wrong

| "error_code" | description                          |
|--------------|--------------------------------------|
| 1            | ID or password wrong.                |
| 2            | Already logged in on another device. |

### signOut

* client sending
<pre>
null
</pre>    

* client receiving if operation successful 
<pre>
null
</pre>    

* client receiving if something went wrong
<pre>
"error_code" 0 is used only.
</pre>    

 ### signUp

 * client sending
<pre>
{
        "id": "[id for user]",
        "nickname": "[nickname for user]",
        "phone_number": "[phone number for user]",
        "birthday": "[birthday for user in yyyy-mm-dd format]",
        "password": "[password for user]"
}
</pre>    

* client receiving if operation successful 
<pre>
null
</pre>    

* client receiving if something went wrong

| "error_code" | description                                         |
|--------------|-----------------------------------------------------|
| 1            | ID already exists.                                  |
| 2            | Birth date has wrong format or the date is invalid. |
| 3            | Nickname null string.                               |
| 4            | Phone number has wrong format.                      |
| 5            | Password null string.                               |

### findID

* client sending
<pre>
{
         "nickname": "[nickname that user has]",
         "birthday": "[birthday that user has in yyyy-mm-dd format]",
}
</pre>    

* client receiving if operation successful
<pre>
{
        "user_id": "[found user ID]"
}
</pre>

* client receiving if something went wrong

| "error_code" | description                       |
|--------------|-----------------------------------|
| 1            | No user matches that information. |

### findPW
* client sending
<pre>
{
         "id": "[ID of user]",
         "phone_number": "[Phone number of user]"
}
</pre>    

* client receiving if operation successful
<pre>
{
        "temporary_password": "[Temporary password generated by server]"
}
</pre>

* client receiving if something went wrong

| "error_code" | description         |
|--------------|---------------------|
| 1            | Wrong phone number. |
| 2            | Invalid ID.         |

### upPW
* client sending
<pre>
{
         "previous_password": "[previous password of user]",
         "new_password": "[new password of user]"
}
</pre>    

* client receiving if operation successful
<pre>
null
</pre>

* client receiving if something went wrong

| "error_code" | description                       |
|--------------|-----------------------------------|
| 1            | Previous password does not match. |

### getFrens

* client sending
<pre>
null
</pre>    

* client receiving if opernation successful 
<pre>
{
        "friends_list": [
                {
                   "id": "[ID for user]",
                   "nickname": "[ickname for user]",
                   "birthday": "[birthday for user in yyyy-mm-dd format]",
                   "bio": "[biography for user]",
                }, 

                ...
        ]
}
</pre>    

* client receiving if something went wrong 

<pre>
"error_code" 0 is used only.
</pre>    

### unRegs

* client sending
<pre>
null
</pre>    

* client receiving if operation successful
 > Action 'bye' will be sent from server after deleting database.
<pre>
null
</pre>    

* client receiving if something went wrong

<pre>
"error_code" 0 is used only.
</pre>    

### getUser

* client sending 
<pre>
{
        "user_id": "[ID of desired user]"
}
</pre>    

* client receiving if operation successful 
<pre>
{
        "id": "[id for user]",
        "nickname": "[nickname for user]",
        "birthday": "[birthday for user in yyyy-mm-dd format]",
        "bio": "[biography for user]",
}
</pre>    

* client receiving if something went wrong 

| "error_code" | description   |
|--------------|---------------|
| 1            | Invalid ID.   |

### chkUsrOn

* client sending
<pre>
{
        "user_id": "[ID of desired user]"
}
</pre>    

* client receiving if operation successful
<pre>
{
        "user_online": [true/false];
}
</pre>    

* client receiving if something went wrong

| "error_code" | description   |
|--------------|---------------|
| 1            | Invalid ID.   |
### getCtRms

* client sending
<pre>
null
</pre>    

* client receiving if operation successful
<pre>
{
      "chatroom_list": [
            {
                  "chatroom_id": "[chatroom id]",
                  "chatroom_name": "[chatroom name]",
                  "chatroom_people_count": "[chatroom people count]",
            },

            ...
      ]
}
</pre>    

* client receiving if something went wrong

<pre>
"error_code" 0 is used only.
</pre>    

### upUsrInf

* client sending 
<pre>
{
        "nickname": "[new nickname for user]",
        "birthday": "[new birthday for user in yyyy-mm-dd format]",
        "bio": "[new biography for user]",
}
</pre>    
> Every 3 keys are not neccessary. You just have to put the information you want to update.<br>
> For example, "{}" means not to update anything.

* client receiving if operation successful
<pre>
null
</pre>    

* client receiving if something went wrong

| "error_code" | description                              |
|--------------|------------------------------------------|
| 1            | Birth date invalid or in a wrong format. |
| 2            | Nickname null string.                    |

### mkCtRm

* client sending
<pre>
{
      "chatroom_name": "[desired chatroom name]",
      "chatroom_people": [
            "user ID1", "user ID2", "user ID3" , ... (Every user except client)
      ]
}
</pre>

* client receiving if operation successful
<pre>
{
      "chatroom_id": "[created chatroom ID]"
}
</pre>    

* client receiving if something went wrong

| "error_code" | description               |
|--------------|---------------------------|
| 1            | Invalid user ID included. |
| 2            | No user included.         |
| 3            | Cannot include own ID.    |

### addCtRm

* client sending
<pre>
{
      "id_to_add": [
            "user ID1", "user ID2", "user ID3" , ...
      ]
      "chatroom_id": "[chatroom to put user in]",
}
</pre>

* client receiving if operation successful
<pre>
null
</pre>    

* client receiving if something went wrong

| "error_code" | description               |
|--------------|---------------------------|
| 1            | Invalid user ID included. |

### lvCtRm

* client sending
<pre>
{
      "chatroom_id": "[chatroom to leave]",
}
</pre>

* client receiving if operation successful
<pre>
null
</pre>    

* client receiving if something went wrong

| "error_code" | description          |
|--------------|----------------------|
| 1            | Invalid chatroom ID. |

### sendMsg

* client sending (message_type is text or link)
<pre>
{
      "chatroom_id": "[chatroom ID to send the message to]",
      "message_type": "[type of the message (documented upper)]",
      "content": "[content of the message]",
}
</pre>

* client sending (message_type is file)
<pre>
{
      "chatroom_id": "[chatroom ID that the message was sent from]",
      "message_type": "[type of the message (documented upper)]",
      "file_name": "[file name]",
      "file_id": "[file id stored in server]",
}
</pre>

* client receiving if operation successful
<pre>
null
</pre>    

* client receiving if something went wrong

| "error_code" | description                     |
|--------------|---------------------------------|
| 1            | Invalid chatroom ID.            |
| 2            | Message content is null string. |
| 3            | File name is null string.       |
| 4            | Invalid file ID.                |

### rcvMsg

> Unlike other actions, rcvMsg and invChtRm should have their own receive-only socket.

* client receiving (message_type is text or link)
<pre>
{
      "chatroom_id": "[chatroom ID that the message was sent from]",
      "user_id": "[user ID who sent the message]",
      "message_type": "[type of the message (documented upper)]",
      "content": "[content of the message]",
}
</pre>
* client receiving (message_type is file)
<pre>
{
      "chatroom_id": "[chatroom ID that the message was sent from]",
      "user_id": "[user ID who sent the message]",
      "message_type": "[type of the message (documented upper)]",
      "file_name": "[file name]",
      "file_id": "[file id stored in server]",
}
</pre>

### invChtRm
> Unlike other actions, rcvMsg and invChtRm should have their own receive-only socket.

* client receiving
<pre>
{
      "chatroom_id": "[chatroom ID that user was invited]",
}
</pre>

### downFile

> File download and upload has some special procedures.<br>
> Request ID for the entire packet related to the operation will be the same!<br>
> Sender (both client and server can be the sender) will transmit each file parts (2KB) sequentially.<br>
>  Procedure of downFile goes like this:
> 1. client initial request
> 2. server sending ack of initial request
> 3. server sending file parts (client will not respond to each packet of the file)
> 4. client sending final ack

* client initial request
<pre>
{
      "file_id": "[file ID to download]",
}
</pre>

* server ack of initial request if request was OK:
<pre>
{
      "file_name": "[file name to send]",
      "file_parts_count": "[count of file parts]",
}
</pre> 

* server ack of initial request if request was wrong:

| "error_code" | description                  |
|--------------|------------------------------|
| 1            | Count of file parts is zero. |
| 2            | Invalid file ID.             |

* server sending file parts:

| range (bytes) | type                | name        | description                                              |
|---------------|---------------------|-------------|----------------------------------------------------------|
| 0 to ..       | bytes               | data        | 2KB binary file part. Last part can be smaller than 2KB. |

* client sending final ack if file was transmitted successfully:
<pre>null</pre>

* client sending final ack if something went wrong in transmission:
<pre>
"error_code" 0 is used only.
</pre>    

### uplFile

> File download and upload has some special procedures.<br>
> Request ID for the entire packet related to the operation will be the same!<br>
> Sender (both client and server can be the sender) will transmit each file parts (2KB) sequentially.<br>
>  Procedure of upFile goes like this:
> 1. client initial request
> 2. server sending ack of initial request
> 3. client sending file parts (server will not respond to each packet of the file)
> 4. server sending final ack

* client initial request
<pre>
{
      "file_name": "[file name to upload]",
      "file_parts_count": "[count of file parts]",
}
</pre>

* server ack of initial request if request was OK:
<pre>
null
</pre> 

* server ack of initial request if request was wrong:

| "error_code" | description                  |
|--------------|------------------------------|
| 1            | Count of file parts is zero. |

* client sending file parts:

| range (bytes) | type                | name        | description                                              |
|---------------|---------------------|-------------|----------------------------------------------------------|
| 0 to ..       | bytes               | data        | 2KB binary file part. Last part can be smaller than 2KB. |
 

* server sending final ack if file was transmitted successfully:
<pre>
{
      "file_id": "[ID of the file that has successfully transmitted to the server]"
}
</pre>

* server sending final ack if something went wrong in transmission:
<pre>
"error_code" 0 is used only.
</pre>    

### hi
 > Only used for receive-only socket.
* client sending
<pre>
null
</pre>    

* client receiving if operation successful
<pre>
null
</pre>    

* client receiving if something went wrong
<pre>
"error_code" 0 is used only.
</pre>    

### bye
> Only used for receive-only socket.

* client sending
<pre>
null
</pre>    

* client receiving if operation successful
<pre>
null
</pre>    

* client receiving if something went wrong
<pre>
"error_code" 0 is used only.
</pre>    

## Example

 * client sending: 

| range (bytes) | type                | name       | content                                                                                                                               |
|---------------|---------------------|------------|---------------------------------------------------------------------------------------------------------------------------------------|
| 0 to 9        | 10 ASCII characters | header     | "gagaotalk!"                                                                                                                          |
| 10            | 8bit unsigned int   | request ID | 2341292                                                                                                                               |
| 11            | 8bit unsigned int   | session ID | 1293894                                                                                                                               |
| 12 to 19      | 8 ASCII characters  | action     | "sendMsg"                                                                                                                             |
| 20 to ..      | bytes               | data       | <pre>{<br/>"chatroom_id": "1a3b34",<br/>"user_id": "tmin002",<br/>"message_type": "text",<br/>"content": "Hello, World!",<br/>}</pre> |

* client receiving: 

| range (bytes) | type                | name        | content      |
|---------------|---------------------|-------------|--------------|
| 0 to 9        | 10 ASCII characters | header      | "gagaotalk!" |
| 10            | 8bit unsigned int   | request ID  | 2341292      |
| 11            | 8bit unsigned int   | status code | 0            |
| 12 to 19      | 8 ASCII characters  | action      | "sendMsg"    |
| 20 to ..      | bytes               | data        | null         |
