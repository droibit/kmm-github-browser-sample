import Foundation
import Shared

class KotlinError: LocalizedError {
    let throwable: KotlinThrowable

    var errorDescription: String? {
        throwable.message
    }

    init(_ throwable: KotlinThrowable) {
        self.throwable = throwable
    }
}

extension KotlinThrowable: LocalizedError {
    public var errorDescription: String? {
        message
    }

    convenience init(error: Error) {
        self.init(message: error.localizedDescription)
    }
}
