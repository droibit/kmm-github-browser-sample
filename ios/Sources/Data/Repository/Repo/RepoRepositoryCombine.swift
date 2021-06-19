// swiftlint:disable weak_delegate

import Combine
import Foundation
import Shared

class RepoRepositoryCombine: RepoRepository {
    private let delegate: RepoRepositoryIos

    init(delegate: RepoRepositoryIos) {
        self.delegate = delegate
    }

    func loadRepos(owner: String, force: Bool) -> AnyPublisher<[Repo], GitHubError> {
        // Workaround: Objective-C and Swift compatibility causes type parameters to erase.
        createFuture(suspendWrapper: delegate.loadRepos(owner: owner, force: force)) { item in
            item as! [Repo]
        }
    }
}
