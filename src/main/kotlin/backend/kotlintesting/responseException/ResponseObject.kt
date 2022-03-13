package backend.kotlintesting.responseException

class ResponseObject {
    var status: String? = null
    var message: String? = null
    var data: Any? = null

    constructor() : super() {}
    constructor(status: String?, message: String?, data: Any?) : super() {
        this.status = status
        this.message = message
        this.data = data
    }
}