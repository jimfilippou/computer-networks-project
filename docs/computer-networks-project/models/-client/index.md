[computer-networks-project](../../index.md) / [models](../index.md) / [Client](./index.md)

# Client

`class Client : Serializable`

The client data model, which also holds methods for client connectivity features from &amp; to the server

### Parameters

`id` - optionally pass an ID if you want, defaults to -1

**Since**
0.0.3

### Constructors

| [&lt;init&gt;](-init-.md) | The client data model, which also holds methods for client connectivity features from &amp; to the server`Client(id: Int = -1)` |

### Properties

| [id](id.md) | optionally pass an ID if you want, defaults to -1`var id: Int` |

### Functions

| [dispatchListUsersEvent](dispatch-list-users-event.md) | `fun dispatchListUsersEvent(destination: `[`Server`](../-server/index.md)`, callback: (usersIDs: Any?) -> Unit): Unit` |
| [dispatchUploadEvent](dispatch-upload-event.md) | `fun dispatchUploadEvent(image: String, destination: `[`Server`](../-server/index.md)`): Unit` |
| [follow](follow.md) | `fun follow(followerID: Int): Unit` |
| [register](register.md) | `fun register(destination: `[`Server`](../-server/index.md)`): Unit` |

