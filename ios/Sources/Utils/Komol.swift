import Shared

enum Komol {
    private static let komol = Komol_coreKomol()

    static func v(_ message: String) {
        komol.v(message: message)
    }

    static func v(_ error: Error, message: String? = nil) {
        komol.v(throwable: error.kotlinThrowable, message: message)
    }

    static func d(_ message: String) {
        komol.d(message: message)
    }

    static func d(_ error: Error, message: String? = nil) {
        komol.d(throwable: error.kotlinThrowable, message: message)
    }

    static func i(_ message: String) {
        komol.i(message: message)
    }

    static func i(_ error: Error, message: String? = nil) {
        komol.i(throwable: error.kotlinThrowable, message: message)
    }

    static func w(_ message: String) {
        komol.w(message: message)
    }

    static func w(_ error: Error, message: String? = nil) {
        komol.w(throwable: error.kotlinThrowable, message: message)
    }

    static func e(_ message: String) {
        komol.e(message: message)
    }

    static func e(_ error: Error, message: String? = nil) {
        komol.e(throwable: error.kotlinThrowable, message: message)
    }

    static func wtf(_ message: String) {
        komol.wtf(message: message)
    }

    static func wtf(_ error: Error, message: String? = nil) {
        komol.wtf(throwable: error.kotlinThrowable, message: message)
    }
}

func currentQueueName() -> String {
    let name = __dispatch_queue_get_label(nil)
    return String(cString: name, encoding: .utf8) ?? "Unknown"
}

// MARK: - Private

private extension Error {
    var kotlinThrowable: KotlinThrowable {
        (self as NSError).kotlinException as? KotlinThrowable
            ?? KotlinThrowable(error: self)
    }
}

private extension KotlinThrowable {
    convenience init(error: Error) {
        self.init(message: error.localizedDescription)
    }
}
