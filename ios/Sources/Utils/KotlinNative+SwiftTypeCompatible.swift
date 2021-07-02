import Foundation
import Shared

extension Int {
    func toKotlinInt() -> KotlinInt {
        KotlinInt(value: Int32(self))
    }
}
