import Combine
import Foundation
import Shared

protocol RepoRepository {
    func loadRepos(owner: String, force: Bool) -> AnyPublisher<[Repo], GitHubError>

    func loadRepo(owner: String, name: String) -> AnyPublisher<Repo?, GitHubError>

    func loadContributors(owner: String, name: String) -> AnyPublisher<[Contributor], GitHubError>
}
