import Combine
import Foundation
import Shared

protocol UserRepository {
    func loadUserWrapper(login: String) -> AnyPublisher<User?, GitHubError>
}
