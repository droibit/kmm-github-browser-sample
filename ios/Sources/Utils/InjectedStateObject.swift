import Combine
import Resolver
import SwiftUI

@available(OSX 10.15, iOS 14.0, tvOS 13.0, watchOS 6.0, *)
@propertyWrapper
struct InjectedStateObject<Service>: DynamicProperty where Service: ObservableObject {
    @StateObject private var service: Service

    init() {
        _service = StateObject(wrappedValue: Resolver.resolve(Service.self))
    }

    init(name: Resolver.Name? = nil, container: Resolver? = nil) {
        _service = StateObject(wrappedValue: container?.resolve(Service.self, name: name)
            ?? Resolver.resolve(Service.self, name: name))
    }

    var wrappedValue: Service { service }

    var projectedValue: ObservedObject<Service>.Wrapper {
        $service
    }
}
