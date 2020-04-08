[computer-networks-project](../index.md) / [helpers](./index.md)

## Package helpers

### Types

| [Logger](-logger/index.md) | A custom logger because it is easier to debug. Every function is static and it supports various terminal colors`object Logger` |

### Functions

| [contains](contains.md) | `operator fun <ERROR CLASS>.contains(text: CharSequence): Boolean` |
| [copyFileUsingStream](copy-file-using-stream.md) | `fun copyFileUsingStream(source: File, destination: File): Unit` |
| [fetchClients](fetch-clients.md) | Reads data graph and returns clients along with their followers`fun fetchClients(dataFile: String = "/data/graph.txt"): List<`[`Client`](../models/-client/index.md)`>` |
| [getIPv4Address](get-i-pv4-address.md) | Reads all network interfaces, picks the IPv4 address`fun getIPv4Address(): String` |
| [interpret](interpret.md) | CLI tool to manage clients (EXPERIMENTAL) This chunk of code is ugly, intentionally`fun interpret(server: `[`Server`](../models/-server/index.md)`): Unit` |
| [sendToServer](send-to-server.md) | Universal packet sender, used by clients`fun sendToServer(payload: `[`Packet`](../interfaces/-packet/index.md)`, sender: `[`Client`](../models/-client/index.md)`, receiver: `[`Server`](../models/-server/index.md)`, callback: ((data: Any?) -> Unit)? = null): Unit` |

