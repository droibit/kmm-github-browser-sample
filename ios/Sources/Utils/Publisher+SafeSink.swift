import Combine
import Foundation

extension Publisher {
    func sink<Object: AnyObject>(
        with object: Object,
        receiveCompletion: @escaping ((Object, Subscribers.Completion<Self.Failure>) -> Void),
        receiveValue: @escaping ((Object, Self.Output) -> Void)
    ) -> AnyCancellable {
        sink { [weak object] completion in
            guard let object = object else { return }
            receiveCompletion(object, completion)
        } receiveValue: { [weak object] value in
            guard let object = object else { return }
            receiveValue(object, value)
        }
    }
}
