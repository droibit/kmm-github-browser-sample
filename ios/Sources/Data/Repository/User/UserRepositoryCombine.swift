// swiftlint:disable weak_delegate

import Combine
import Foundation
import Shared

class UserRepositoryCombine: UserRepository {
    private let delegate: UserRepositoryIos

    init(delegate: UserRepositoryIos) {
        self.delegate = delegate
    }

    func loadUserWrapper(login: String) -> AnyPublisher<User?, GitHubError> {
        createOptionalFuture(suspendWrapper: delegate.loadUserWrapper(login: login))
    }
}
