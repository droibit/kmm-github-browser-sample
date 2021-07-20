import SwiftUI

extension AnyTransition {
    static var fade: AnyTransition {
        .opacity.animation(.easeInOut(duration: 0.2))
    }
}
