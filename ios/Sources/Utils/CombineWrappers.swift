import Combine
import Foundation
import Shared

// ref. https://github.com/touchlab/SwiftCoroutines/blob/master/ios/SwiftCoroutines/CombineWrappers.swift

func createPublisher<T, E>(flowWrapper: FlowWrapper<T>) -> AnyPublisher<T, E> where E: Error {
    Deferred<Publishers.HandleEvents<PassthroughSubject<T, E>>> {
        let subject = PassthroughSubject<T, E>()
        let job = flowWrapper.subscribe { item in
            subject.send(item)
        } onComplete: {
            subject.send(completion: .finished)
        } onThrow: { error in
            subject.send(completion: .failure(error as! E))
        }
        return subject.handleEvents(receiveCancel: {
            job.cancel(cause: nil)
        })
    }.eraseToAnyPublisher()
}

func createOptionalPublisher<T, E>(flowWrapper: NullableFlowWrapper<T>) -> AnyPublisher<T?, E> where E: Error {
    Deferred<Publishers.HandleEvents<PassthroughSubject<T?, E>>> {
        let subject = PassthroughSubject<T?, E>()
        let job = flowWrapper.subscribe { item in
            subject.send(item)
        } onComplete: {
            subject.send(completion: .finished)
        } onThrow: { error in
            subject.send(completion: .failure(error as! E))
        }
        return subject.handleEvents(receiveCancel: {
            job.cancel(cause: nil)
        })
    }.eraseToAnyPublisher()
}

func createFuture<T, E>(suspendWrapper: SuspendWrapper<T>) -> AnyPublisher<T, E> where E: Error {
    Deferred<Publishers.HandleEvents<Future<T, E>>> {
        var job: Kotlinx_coroutines_coreJob?
        return Future { promise in
            job = suspendWrapper.subscribe(
                onSuccess: { item in
                    promise(.success(item))
                },
                onThrow: { error in
                    promise(.failure(error as! E))
                }
            )
        }.handleEvents(receiveCancel: {
            job?.cancel(cause: nil)
        })
    }
    .eraseToAnyPublisher()
}

func createOptionalFuture<T, E>(suspendWrapper: NullableSuspendWrapper<T>) -> AnyPublisher<T?, E> where E: Error {
    Deferred<Publishers.HandleEvents<Future<T?, E>>> {
        var job: Kotlinx_coroutines_coreJob?
        return Future { promise in
            job = suspendWrapper.subscribe(
                onSuccess: { item in
                    promise(.success(item))
                },
                onThrow: { error in
                    promise(.failure(error as! E))
                }
            )
        }.handleEvents(receiveCancel: {
            job?.cancel(cause: nil)
        })
    }
    .eraseToAnyPublisher()
}
