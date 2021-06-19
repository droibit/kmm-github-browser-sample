import Combine
import Foundation
import Shared

protocol RepoRepository {
    func loadRepos(owner: String, force: Bool) -> AnyPublisher<[Repo], GitHubError>
}
