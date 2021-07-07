// swiftlint:disable identifier_name

import Combine
import CombineSchedulers
import Foundation
import Shared

class UserViewModel: ObservableObject {
    private let userRepository: UserRepository

    private let repoRepository: RepoRepository

    private let mainScheduler: AnySchedulerOf<DispatchQueue>

    private var cancellables = Set<AnyCancellable>()

    @Published private(set) var getUserUiModel: GetUserUiModel

    init(userRepository: UserRepository,
         repoRepository: RepoRepository,
         mainScheduler: AnySchedulerOf<DispatchQueue>,
         getUserUiModel: GetUserUiModel = .init())
    {
        self.userRepository = userRepository
        self.repoRepository = repoRepository
        self.mainScheduler = mainScheduler
        self.getUserUiModel = getUserUiModel
    }

    deinit {
        Komol.d("deinit: \(type(of: self))")
    }

    func onAppear(login: String) {
        guard cancellables.isEmpty else {
            return
        }
        getUser(login: login)
    }

    func getUser(login: String) {
        guard !getUserUiModel.inProgress else {
            return
        }
        getUserUiModel = GetUserUiModel(inProgress: true)

        Publishers.Zip(
            userRepository.loadUser(login: login),
            repoRepository.loadRepos(owner: login, force: false)
        )
        .receive(on: mainScheduler)
        .sink(with: self) { owner, completion in
            if case let .failure(error) = completion {
                owner.getUserUiModel = GetUserUiModel(error: error.message)
            }
        } receiveValue: { owner, value in
            let (_user, repos) = value
            guard let user = _user else {
                owner.getUserUiModel = GetUserUiModel(error: "Unknown user: \(login)")
                return
            }
            owner.getUserUiModel = GetUserUiModel(
                userUiModel: UserUiModel(user: user, repos: repos)
            )
        }.store(in: &cancellables)
    }
}
