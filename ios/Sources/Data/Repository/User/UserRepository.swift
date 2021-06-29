import Combine
import Foundation
import Shared

protocol UserRepository {
    func loadUser(login: String) -> AnyPublisher<User?, GitHubError>
}
