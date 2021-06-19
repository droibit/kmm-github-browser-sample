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
        createFuture(suspendWrapper: delegate.loadReposWrapper(owner: owner, force: force)) {
            $0 as! [Repo]
        }
    }

    func loadRepo(owner: String, name: String) -> AnyPublisher<Repo?, GitHubError> {
        createOptionalFuture(suspendWrapper: delegate.loadRepoWrapper(owner: owner, name: name))
    }

    func loadContributors(owner: String, name: String) -> AnyPublisher<[Contributor], GitHubError> {
        createFuture(suspendWrapper: delegate.loadContributorsWrapper(owner: owner, name: name)) {
            $0 as! [Contributor]
        }
    }
}
